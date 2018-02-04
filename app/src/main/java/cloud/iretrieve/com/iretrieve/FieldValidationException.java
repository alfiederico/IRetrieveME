package cloud.iretrieve.com.iretrieve;

/**
 * Created by User on 24/11/2015.
 */
import android.widget.EditText;


public class FieldValidationException extends Exception {

    private EditText mTextView;

    public FieldValidationException(String message, EditText textView) {
        super(message);
        mTextView = textView;
    }

    public EditText getTextView() {
        return mTextView;
    }
}
