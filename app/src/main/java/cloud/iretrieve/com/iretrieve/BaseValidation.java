package cloud.iretrieve.com.iretrieve;

/**
 * Created by User on 24/11/2015.
 */
import android.content.Context;


abstract class BaseValidation implements Validation {

    protected Context mContext;

    protected BaseValidation(Context context) {
        mContext = context;
    }

}

