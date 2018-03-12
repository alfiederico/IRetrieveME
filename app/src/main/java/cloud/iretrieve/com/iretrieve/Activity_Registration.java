package cloud.iretrieve.com.iretrieve;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cloud.iretrieve.com.iretrieve.domain.Staff;
import cloud.iretrieve.com.iretrieve.domain.User;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


public class Activity_Registration extends Activity {
    TextView txtLogin;
    private Form mForm;
    private EditText mEmail;
    EditText mName;
    EditText mLastName;
    EditText mPassword;
    EditText mRePassword;
    EditText mPhone;
    Button btnRegister;

    private static final String SERVICE_URL = "http://alfiederico.com/iRetrieve-0.0.1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        validation();
        createListener();

    }

    public void init(){
        txtLogin = (TextView)findViewById(R.id.txtLogin);
        mEmail = (EditText)findViewById(R.id.editEmail);
        mName = (EditText)findViewById(R.id.editName);
        mLastName = (EditText)findViewById(R.id.editLastName);

        mPassword = (EditText)findViewById(R.id.editPassword);
        mRePassword = (EditText)findViewById(R.id.editRetypePassword);
        mPhone = (EditText)findViewById(R.id.editPhone);
        btnRegister = (Button)findViewById(R.id.btnRegister);


    }

    public void validation(){
        mForm = new Form(this);
        mForm.addField(Field.using(mName).validate(Validation_NotEmpty.build(this)));
        mForm.addField(Field.using(mLastName).validate(Validation_NotEmpty.build(this)));
        mForm.addField(Field.using(mEmail).validate(Validation_NotEmpty.build(this)).validate(Validation_IsEmail.build(this)));
        mForm.addField(Field.using(mPassword).validate(Validation_NotEmpty.build(this)));
        mForm.addField(Field.using(mRePassword).validate(Validation_NotEmpty.build(this)));
        mForm.addField(Field.using(mPhone).validate(Validation_NotEmpty.build(this)));
        mForm.addField(Field.using(mPassword,mRePassword).validate(Validation_IsEqual.build(this)));
    }
    public void createListener(){
        txtLogin.setMovementMethod(LinkMovementMethod.getInstance());
        txtLogin.setText(txtLogin.getText().toString(), TextView.BufferType.SPANNABLE);
        Spannable mySpannable = (Spannable)txtLogin.getText();
        ClickableSpan myClickableSpan = new ClickableSpan()
        {
            @Override
            public void onClick(View widget) {
                setResult(1234, null);
                finish();
            }
        };
        mySpannable.setSpan(myClickableSpan, 25, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNow();

            }
        });

        mPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    registerNow();
                    return true;
                }
                return false;
            }

        });

    }

    public void registerNow(){
        if(mForm.isValid()){
           new AddUserTask(this).execute();
        }
    }

    public void showMessage(String e) {
        AlertDialog.Builder diag = new AlertDialog.Builder(this);
        diag.setTitle("Message");
        diag.setMessage(e);
        diag.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void hideKeyboard() {
        try{
            InputMethodManager inputManager = (InputMethodManager) Activity_Registration.this
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(
                    Activity_Registration.this.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }catch(Exception ex){
            showMessage(ex.toString());
        }


    }

    private class AddUserTask extends AsyncTask<Void,Void,User>{
        private String url = SERVICE_URL + "/mobile/registration";
        private RestTemplate rest = new RestTemplate();

        private Context mContext = null;
        private ProgressDialog pDlg = null;

        public AddUserTask(Context mContext){
            this.mContext = mContext;
        }

        @Override
        protected User doInBackground(Void... params) {
            try{
                User newUser = new User();
                newUser.setName(mName.getText().toString());
                newUser.setLastName(mLastName.getText().toString());
                newUser.setPassword(mPassword.getText().toString());
                newUser.setPhone(mPhone.getText().toString());
                newUser.setEmail(mEmail.getText().toString());
                newUser.setRadius("50");

                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();


                List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
                supportedMediaTypes.add(new MediaType("text", "plain"));
                supportedMediaTypes.add(new MediaType("application", "json"));
                converter.setSupportedMediaTypes(supportedMediaTypes);

                rest.getMessageConverters().add(converter);

                return rest.postForObject(url,newUser,User.class);

            }catch(Exception ex){
                showMessage(ex.toString());
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            try {
                hideKeyboard();
                showProgressDialog();
            } catch (Exception ex) {
                showMessage(ex.toString());
            }
        }

        @Override
        protected void onPostExecute(User user) {
            try{
                pDlg.dismiss();
                if(user == null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Registration Failed");
                    builder.setMessage("The email you entered already belong to an account. Please try again.");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                }
                else if(user.getName().equals("Verify") && user.getLastName().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("There is already a user registered with the email provided. Please check confirmation message sent to this email account.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = new Intent(mContext, MainActivity.class);
                            mContext.startActivity(i);
                        }
                    });
                    builder.show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("You registered successfully. We will send you a confirmation message to your email account.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                           Intent i = new Intent(mContext, MainActivity.class);
                            mContext.startActivity(i);
                        }
                    });
                    builder.show();

                }
            }catch(Exception ex){
                showMessage(ex.toString());
            }
        }

        private void showProgressDialog() {
            pDlg = new ProgressDialog(mContext);
            pDlg.setMessage("Sending");
            pDlg.setProgressDrawable(mContext.getWallpaper());
            pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDlg.setCancelable(false);
            pDlg.show();

        }
    }
}
