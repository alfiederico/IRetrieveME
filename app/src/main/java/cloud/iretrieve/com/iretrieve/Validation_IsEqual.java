package cloud.iretrieve.com.iretrieve;

/**
 * Created by User on 24/11/2015.
 */
import android.content.Context;
import android.text.TextUtils;

/**
 * Created by User on 24/11/2015.
 */
class Validation_IsEqual extends BaseValidation {

    public static Validation build(Context context) {
        return new Validation_IsEqual(context);
    }

    private Validation_IsEqual(Context context) {
        super(context);
    }

    @Override
    public String getErrorMessage() {
        return "Your password don't match";
    }

    @Override
    public boolean isValid(String text) {
        return !TextUtils.isEmpty(text);
    }

    public boolean isEqual(String text1, String text2) {
        return text1.equals(text2);
    }
}
