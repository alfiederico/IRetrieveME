package cloud.iretrieve.com.iretrieve;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Activity_ForgotPassword extends Activity {
    private Form mForm;
    private EditText mEmail;
    private TextView txtRegister;
    private TextView txtLogin;
    Button btnRecover;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();
        validation();
        createListener();
    }

    public void init(){
        mEmail = (EditText)findViewById(R.id.editEmail);
        txtRegister = (TextView)findViewById(R.id.txtRegister);
        txtLogin = (TextView)findViewById(R.id.txtLogin);
        btnRecover = (Button)findViewById(R.id.btnRecover);
    }
    public void validation(){
        mForm = new Form(this);
        mForm.addField(Field.using(mEmail).validate(Validation_NotEmpty.build(this)).validate(Validation_IsEmail.build(this)));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1234) {
            setResult(1234, null);
            finish();
        }
    }
    public void createListener(){
        final Context context = this;
        txtRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View e) {
                try {
                    Intent intent = new Intent(context, Activity_Registration.class);
                    startActivityForResult(intent, 1234);
                } catch (Exception ex) {

                }
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View e) {
                try {
                    setResult(1234, null);
                    finish();
                } catch (Exception ex) {

                }
            }
        });

        btnRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mForm.isValid()){
                    sendEmailData();
                }
            }
        });

        mEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(mForm.isValid()){
                        sendEmailData();
                    }
                    return true;
                }
                return false;
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__forgot_password, menu);
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
    public void sendEmailData() {
        try{
            EmailTask est = new EmailTask(1, this, "Sending data...",mEmail.getText().toString());
            est.execute(new String[]{""});
        }catch(Exception w){

        }
    }
    private void hideKeyboard() {

        InputMethodManager inputManager = (InputMethodManager) Activity_ForgotPassword.this
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(
                Activity_ForgotPassword.this.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private class EmailTask extends AsyncTask<String, Integer, String> {

        private ProgressDialog pDlg = null;
        private Context mContext = null;
        private String processMessage = "Processing...";
        private String emailDestination;
        private String message = "Dear Sir/Madam,\n\nYou may now log-in to DPos cloud mobile using the information below:\n\n";

        public EmailTask(int taskType, Context mContext, String processMessage, String email) {
            this.mContext = mContext;
            this.processMessage = processMessage;
            this.emailDestination = email;
        }

        @Override
        protected String doInBackground(String... params) {
            doSendEmail();
            return null;
        }

        @Override
        protected void onPreExecute() {
            hideKeyboard();
            showProgressDialog();

        }
        @Override
        protected void onPostExecute(String response) {

            pDlg.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage("Email already sent");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent i = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(i);
                }
            });
            builder.show();

        }

        private void showProgressDialog() {

            pDlg = new ProgressDialog(mContext);
            pDlg.setMessage(processMessage);
            pDlg.setProgressDrawable(mContext.getWallpaper());
            pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDlg.setCancelable(false);
            pDlg.show();

        }

        public void doSendEmail() {
            try {
                Email_GMailSender sender = new Email_GMailSender("alfiederico@gmail.com", "bdsxy1523");
                message += "Username: admin\nRetrieved Password: admin1236\n\n";
                message += "Should you encounter any problems accessing your account, please call the Deliverit at (03) 9803 1611  or email us at member_relations@sss.gov.ph for assistance.\n" +
                        "Thank you for using the DPos cloud mobile.\n\n";
                message += "This is a system-generated e-mail. Please do not reply.";

                sender.sendMail("DPos Cloud Forgot Password",
                        message,
                        "alfiederico@gmail.com",
                        emailDestination);
            } catch (Exception e) {
            }
        }
    }

}
