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

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import cloud.iretrieve.com.iretrieve.domain.Message;


public class Activity_ForgotPassword extends Activity {
    private Form mForm;
    private EditText mEmail;
    private TextView txtRegister;
    private TextView txtLogin;
    Button btnRecover;

    private static final String SERVICE_URL = "http://192.168.254.4:8089";

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
            new EmailTask(this).execute();
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

    private class EmailTask extends AsyncTask<Void, Void, Message> {



        private Context mContext = null;
        private ProgressDialog pDlg = null;

        public EmailTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected Message doInBackground(Void... params) {

            try{

                String url = SERVICE_URL + "/mobile/forgotpassword?email=" + mEmail.getText().toString();

                RestTemplate restTemplate = new RestTemplate();
                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();


                List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
                supportedMediaTypes.add(new MediaType("text", "plain"));
                supportedMediaTypes.add(new MediaType("application", "json"));
                converter.setSupportedMediaTypes(supportedMediaTypes);

                restTemplate.getMessageConverters().add(converter);
                Message message = restTemplate.getForObject(url,Message.class);

                return message;

            } catch (Exception ex) {
                showMessage(ex.toString());
                return null;
            }

        }

        @Override
        protected void onPreExecute() {
            hideKeyboard();
            showProgressDialog();

        }

        public void showMessage(String e) {
            AlertDialog.Builder diag = new AlertDialog.Builder(mContext);
            diag.setTitle("Message");
            diag.setMessage(e);
            diag.show();
        }
        @Override
        protected void onPostExecute(Message message) {

            pDlg.dismiss();



            if(message.getContent().equals("success") ){
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Success");
                builder.setMessage("Please check your email to recover password");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(mContext, MainActivity.class);
                        mContext.startActivity(i);
                    }
                });
                builder.show();
            }else if(message.getContent().equals("fail")){
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Fail");
                builder.setMessage("Email does not belong to any account.");
                builder.setPositiveButton("OK", null);
                builder.show();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Exception");
                builder.setMessage(message.getContent());
                builder.setPositiveButton("OK", null);
                builder.show();
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
