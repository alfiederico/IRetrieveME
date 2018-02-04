package cloud.iretrieve.com.iretrieve;

/**
 * Created by User on 24/11/2015.
 */
import android.content.Context;


public class Validation_IsEmail extends BaseValidation {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private Validation_IsEmail(Context context) {
        super(context);
    }

    public static Validation build(Context context) {
        return new Validation_IsEmail(context);
    }

    @Override
    public String getErrorMessage() {
        return mContext.getString(R.string.zvalidations_not_email);
    }

    @Override
    public boolean isValid(String text) {
        return text.matches(EMAIL_PATTERN);
    }

    @Override
    public boolean isEqual(String text1, String text2) {
        return false;
    }
}
