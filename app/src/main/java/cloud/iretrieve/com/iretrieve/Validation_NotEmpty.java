package cloud.iretrieve.com.iretrieve;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by User on 24/11/2015.
 */
class Validation_NotEmpty extends BaseValidation {

    public static Validation build(Context context) {

        return new Validation_NotEmpty(context);
    }

    private Validation_NotEmpty(Context context) {
        super(context);
    }

    @Override
    public String getErrorMessage() {
        return mContext.getString(R.string.zvalidations_empty);
    }

    @Override
    public boolean isValid(String text) {
        return !TextUtils.isEmpty(text);
    }

    @Override
    public boolean isEqual(String text1, String text2) {
        return false;
    }
}
