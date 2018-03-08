package cloud.iretrieve.com.iretrieve;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import cloud.iretrieve.com.iretrieve.domain.Message;

import static junit.framework.Assert.assertTrue;


public class MainActivity extends Activity {
    private static final String SERVICE_URL = "http://192.168.254.12:8089";
    TextView txtRegister;
    TextView txtForgot;
    Button btnLogin;
    EditText editPassword;
    EditText editUsername;
    CheckBox chkRemember;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String MyURL = "192.168.254.10:8089";
    public static final String Name = "nameKey";
    public static final String Password = "passwordKey";
    public static final String Remember = "rememberKey";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_main);

            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            init();
            createListener();
        } catch (Exception ex) {
            showMessage(ex.toString());
        }

    }

    public void init() {
        chkRemember = (CheckBox) findViewById(R.id.chkRemember);
        txtRegister = (TextView) findViewById(R.id.txtRegister);
        txtForgot = (TextView) findViewById(R.id.txtForgot);
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editPassword.setTypeface(Typeface.DEFAULT);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        editUsername.setText(sharedpreferences.getString(Name, ""));
        editPassword.setText(sharedpreferences.getString(Password, ""));
        chkRemember.setChecked(sharedpreferences.getBoolean(Remember, false));

        if (editUsername.getText().toString().equals("") && editPassword.getText().toString().equals("")) {
            btnLogin.setEnabled(false);
        } else {
            btnLogin.setEnabled(true);
        }
    }

    public void createListener() {
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
        txtForgot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View e) {
                try {
                    Intent intent = new Intent(context, Activity_ForgotPassword.class);

                    startActivityForResult(intent, 1234);
                } catch (Exception ex) {
                    showMessage(ex.toString());
                }
            }
        });
        editPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginClick();
                }
                return false;
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View e) {

                loginClick();

            }
        });

        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ((!editPassword.getText().toString().equals("")) && (!editUsername.getText().toString().equals(""))) {
                    btnLogin.setEnabled(true);
                    btnLogin.setBackgroundColor(Color.parseColor("#42A5F5"));
                } else {
                    btnLogin.setEnabled(false);
                    btnLogin.setBackgroundColor(Color.parseColor("#64B5F6"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ((!editPassword.getText().toString().equals("")) && (!editUsername.getText().toString().equals(""))) {
                    btnLogin.setEnabled(true);
                    btnLogin.setBackgroundColor(Color.parseColor("#42A5F5"));
                } else {
                    btnLogin.setEnabled(false);
                    btnLogin.setBackgroundColor(Color.parseColor("#64B5F6"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void loginClick() {
        try {
            authenticateUser();
        } catch (Exception ex) {
            showMessage(ex.toString());
        }
    }

    public void showMessage(String e) {
        AlertDialog.Builder diag = new AlertDialog.Builder(this);
        diag.setTitle("Message");
        diag.setMessage(e);
        diag.show();
    }

    public void createAccount(String email, String password, String authToken) {
        Account account = new Account(email, "IRetrieve");

        AccountManager am = AccountManager.get(this);
        am.addAccountExplicitly(account, password, null);
        am.setAuthToken(account, "full_access", authToken);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        try {
            InputMethodManager inputManager = (InputMethodManager) MainActivity.this
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(
                    MainActivity.this.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ex) {
            showMessage(ex.toString());
        }


    }



    public void authenticateUser() {
        try {
            new AuthenticateTask(this).execute();

        } catch (Exception ex) {
            showMessage(ex.toString());
        }
    }


    private class AuthenticateTask extends AsyncTask<Void, Void, Message> {
        private Context mContext = null;
        private ProgressDialog pDlg = null;

        public AuthenticateTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected Message doInBackground(Void... params) {
            try {
                String username = editUsername.getText().toString();
                String psw = editPassword.getText().toString();

                final String url = SERVICE_URL + "/mobile/login?username=" + username + "&password=" + psw;


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
            try {
                hideKeyboard();
                showProgressDialog();
            } catch (Exception ex) {
                showMessage(ex.toString());
            }
        }

        @Override
        protected void onPostExecute(Message message) {
            boolean bOk = true;
            try {
                pDlg.dismiss();



                if(message != null && message.getContent().equals("success") ){
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    if (chkRemember.isChecked() == true) {
                        editor.putString(Name, editUsername.getText().toString());
                        editor.putString(Password, editPassword.getText().toString());
                        editor.putBoolean(Remember, true);
                    }
                    else {
                        editor.putString(Name, "");
                        editor.putString(Password, "");
                        editor.putBoolean(Remember, false);
                    }
                    editor.commit();

                    AccountManager accountManager = AccountManager.get(mContext);
                    Account[] accounts = accountManager.getAccountsByType("IRetrieve");
                    boolean bCreate = true;
                    for (Account availableAccount : accounts) {
                        if(new Long(message.getId()).toString().equals(availableAccount.name)){
                            bCreate = false;
                        }
                    }

                    if(accounts != null && accounts.length >= 1 && bCreate == true){
                        for ( Account availableAccount : accounts) {
                            final AccountManagerFuture<Boolean> booleanAccountManagerFuture = accountManager.removeAccount(availableAccount, null, null);
                        }

                        createAccount(new Long(message.getId()).toString(), editPassword.getText().toString(),message.getContent());

                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("Switch Account");
                        builder.setMessage("New user detected. Please login again to switch account .");
                        builder.setPositiveButton("OK", null);
                        builder.show();

                        return;
                    }

                    if(bCreate){
                        createAccount(new Long(message.getId()).toString(), editPassword.getText().toString(),message.getContent());
                    }


                    Intent i = new Intent(mContext, Activity_Dashboard.class);
                    i.putExtra("iRadius",message.getRadius());
                    mContext.startActivity(i);
                }
                else if(message != null && message.getContent().equals("activate") ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Account not active");
                    builder.setMessage("User account not active. Please check your email.");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Login Failed");
                    builder.setMessage("The username or password you entered doesnt appear to belong to an account. Please try again.");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                }

            } catch (Exception ex) {
                showMessage(ex.toString());
            }


        }


        private void showProgressDialog() {
            pDlg = new ProgressDialog(mContext);
            pDlg.setMessage("Authenticate...");
            pDlg.setProgressDrawable(mContext.getWallpaper());
            pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDlg.setCancelable(false);
            pDlg.show();

        }


    }


}
