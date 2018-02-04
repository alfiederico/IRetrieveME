package cloud.iretrieve.com.iretrieve;

/**
 * Created by User on 24/11/2015.
 */
public interface Validation {

    String getErrorMessage();

    boolean isValid(String text);
    boolean isEqual(String text1, String text2);

}
