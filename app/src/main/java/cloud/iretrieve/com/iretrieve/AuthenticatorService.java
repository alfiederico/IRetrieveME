package cloud.iretrieve.com.iretrieve;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Alfie on 07/01/18.
 */

public class AuthenticatorService  extends Service {
    // Instance field that stores the authenticator object
    // Notice, this is the same Authenticator class we defined earlier
    private Authenticator mAuthenticator;
    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new Authenticator(this);
    }
    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}