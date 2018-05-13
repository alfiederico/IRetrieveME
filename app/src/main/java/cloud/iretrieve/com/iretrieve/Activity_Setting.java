package cloud.iretrieve.com.iretrieve;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
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

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import cloud.iretrieve.com.iretrieve.domain.Report;
import cloud.iretrieve.com.iretrieve.domain.Staff;
import cloud.iretrieve.com.iretrieve.domain.User;


public class Activity_Setting extends Activity {

    private Form mForm;
    private EditText mEmail;
    EditText mName;
    EditText mNameLast;
    EditText mPassword;
    EditText mRadius;
    EditText mToken;
    EditText mPoints;

    EditText mPhone;
    Button btnRegister;
    Context context = null;

    private static final String SERVICE_URL = "http://192.168.254.2:8089";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
        validation();
        createListener();

        context = this;
        new GetUserTask(context).execute();

    }

    public void init(){
;
        mEmail = (EditText)findViewById(R.id.editEmail);
        mName = (EditText)findViewById(R.id.editName);
        mNameLast = (EditText)findViewById(R.id.editLastName);
        mPassword = (EditText)findViewById(R.id.editPassword);
        mRadius = (EditText)findViewById(R.id.editRadius);
        mToken = (EditText)findViewById(R.id.editToken);
        mPoints = (EditText)findViewById(R.id.editPoints);

        mPhone = (EditText)findViewById(R.id.editPhone);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        mEmail.setEnabled(false);
        mToken.setEnabled(false);
        mPoints.setEnabled(false);


    }

    public void validation(){
        mForm = new Form(this);

        mForm.addField(Field.using(mName).validate(Validation_NotEmpty.build(this)));
        mForm.addField(Field.using(mEmail).validate(Validation_NotEmpty.build(this)).validate(Validation_IsEmail.build(this)));
        mForm.addField(Field.using(mPassword).validate(Validation_NotEmpty.build(this)));
        mForm.addField(Field.using(mPhone).validate(Validation_NotEmpty.build(this)));
        mForm.addField(Field.using(mRadius).validate(Validation_NotEmpty.build(this)));
 ;
    }
    public void createListener(){


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNow();

            }
        });



    }

    public void registerNow(){
        if(mForm.isValid()){
           new UpdateUserTask(this).execute();
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
            InputMethodManager inputManager = (InputMethodManager) Activity_Setting.this
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(
                    Activity_Setting.this.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }catch(Exception ex){
            showMessage(ex.toString());
        }


    }

    private class GetUserTask extends AsyncTask<Void,Void,User>{



        private Context mContext = null;
        private ProgressDialog pDlg = null;

        public GetUserTask(Context mContext){
            this.mContext = mContext;
        }

        @Override
        protected User doInBackground(Void... params) {
            try{

                AccountManager accountManager = AccountManager.get(mContext);
                Account[] accounts = accountManager.getAccountsByType("IRetrieve");

                final String url = SERVICE_URL + "/mobile/setting?userid=" + accounts[0].name;

                RestTemplate restTemplate = new RestTemplate();
                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();


                List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
                supportedMediaTypes.add(new MediaType("text", "plain"));
                supportedMediaTypes.add(new MediaType("application", "json"));
                converter.setSupportedMediaTypes(supportedMediaTypes);

                restTemplate.getMessageConverters().add(converter);

                return restTemplate.getForObject(url,User.class);

            }catch(Exception ex){
                showMessage(ex.toString());
                return null;
            }


        }

        @Override
        protected void onPreExecute() {
            try {
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
                    builder.setTitle("Setting Failed");
                    builder.setMessage("The user doesnt appear to belong to an account. Please try again.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            NavUtils.navigateUpFromSameTask((Activity) mContext);
                        }
                    });
                    builder.show();
                }else{
                    mEmail.setText(user.getEmail());
                    mName.setText(user.getName());
                    mNameLast.setText(user.getLastName());
                    mPassword.setText("");

                    mPhone.setText(user.getPhone());
                    mRadius.setText(user.getRadius());
                    mToken.setText(new Integer(user.getToken()).toString());
                    mPoints.setText(new Integer(user.getPoints()).toString());

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

    private class UpdateUserTask extends AsyncTask<Void,Void,User>{



        private Context mContext = null;
        private ProgressDialog pDlg = null;

        public UpdateUserTask(Context mContext){
            this.mContext = mContext;
        }

        @Override
        protected User doInBackground(Void... params) {
            try{

                AccountManager accountManager = AccountManager.get(mContext);
                Account[] accounts = accountManager.getAccountsByType("IRetrieve");

                final String url = SERVICE_URL + "/mobile/updateSetting";

                RestTemplate restTemplate = new RestTemplate();
                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();


                List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
                supportedMediaTypes.add(new MediaType("text", "plain"));
                supportedMediaTypes.add(new MediaType("application", "json"));
                converter.setSupportedMediaTypes(supportedMediaTypes);

                User user = new User();
                user.setName(mName.getText().toString());
                user.setLastName(mNameLast.getText().toString());
                user.setPassword(mPassword.getText().toString());
                user.setPhone(mPhone.getText().toString());
                user.setUserId(Integer.parseInt(accounts[0].name));
                user.setRadius(mRadius.getText().toString());


                restTemplate.getMessageConverters().add(converter);

                User newuser =  restTemplate.postForObject(url,user,User.class);

                return newuser;

            }catch(Exception ex){
                showMessage(ex.toString());
                return null;
            }


        }

        @Override
        protected void onPreExecute() {
            try {
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
                    builder.setTitle("Update Setting Failed");
                    builder.setMessage("The user doesnt appear to belong to an account. Please try login again.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            NavUtils.navigateUpFromSameTask((Activity) mContext);
                        }
                    });
                    builder.show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Update Setting Successful");
                    final String dummy = user.getRadius();
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //NavUtils.navigateUpFromSameTask((Activity) mContext);

                            Intent intent = new Intent();
                            intent.putExtra("intRadius", dummy);
                            setResult(4, intent);
                            finish();
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
