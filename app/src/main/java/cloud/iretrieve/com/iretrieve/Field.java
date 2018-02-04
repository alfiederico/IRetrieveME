package cloud.iretrieve.com.iretrieve;

/**
 * Created by User on 24/11/2015.
 */
import android.widget.EditText;

import java.util.LinkedList;
import java.util.List;


public class Field {

    private List<Validation> mValidations = new LinkedList<Validation>();
    private EditText mTextView;
    private EditText mTextCompare;
    private boolean bCompare = false;

    private Field(EditText textView) {
        this.mTextView = textView;
    }
    private Field(EditText textView1, EditText textView2) {
        this.mTextView = textView1;
        this.mTextCompare = textView2;
    }

    public static Field using(EditText textView) {
        return new Field(textView);
    }

    public static Field using(EditText textView1, EditText textView2) {
        return new Field(textView1,textView2);
    }

    public Field validate(Validation what) {
        mValidations.add(what);
        return this;
    }

    public EditText getTextView() {
        return mTextView;
    }

    public boolean isValid() throws FieldValidationException {
        for (Validation validation : mValidations) {
            if(mTextCompare != null){

                if (!validation.isEqual(mTextView.getText().toString(),mTextCompare.getText().toString())){
                    String errorMessage = validation.getErrorMessage();
                    throw new FieldValidationException(errorMessage, mTextCompare);
                }
            }
            else if (!validation.isValid(mTextView.getText().toString())) {
                String errorMessage = validation.getErrorMessage();
                throw new FieldValidationException(errorMessage, mTextView);
            }
        }
        return true;
    }

}
