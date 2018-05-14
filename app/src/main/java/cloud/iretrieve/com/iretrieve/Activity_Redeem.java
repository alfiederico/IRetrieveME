package cloud.iretrieve.com.iretrieve;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cloud.iretrieve.com.iretrieve.domain.Message;
import cloud.iretrieve.com.iretrieve.domain.User;

public class Activity_Redeem extends Activity {


    Spinner mPayment;
    EditText mAccount;
    Button mRedeem;
    TextView mRedeemPoints;
    TextView mCurrentPoint;

    Context context = null;

    private static final String SERVICE_URL = "http://192.168.254.2:8089";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__redeem);
        init();

        context = this;
        new GetUserTask(context).execute();
    }

    public void redeemNow(){
        if(Integer.parseInt(mCurrentPoint.getText().toString()) <  Integer.parseInt(mRedeemPoints.getText().toString())){
            showMessage("Points to redeem should be lower than or equal to current points");
            return ;
        }
        new UpdateTokenTask(context).execute();
    }

    public void init() {
        mRedeemPoints = (EditText) findViewById(R.id.editRedeemPoints);
        mCurrentPoint = (TextView) findViewById(R.id.txtCurrentPoints);
        mPayment = (Spinner) findViewById(R.id.editPayment);
        mAccount = (EditText) findViewById(R.id.editAccount);
        mRedeem = (Button)findViewById(R.id.btnRedeem);

        mRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redeemNow();

            }
        });

        ArrayList<String> payments = new ArrayList<String>();

        payments.add("GCash");


        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, payments);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPayment.setAdapter(subjectAdapter);
    }

    public void showMessage(String e) {
        AlertDialog.Builder diag = new AlertDialog.Builder(this);
        diag.setTitle("Message");
        diag.setMessage(e);
        diag.show();
    }

    private void hideKeyboard() {
        try{
            InputMethodManager inputManager = (InputMethodManager) Activity_Redeem.this
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(
                    Activity_Redeem.this.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }catch(Exception ex){
            showMessage(ex.toString());
        }


    }


    private class UpdateTokenTask extends AsyncTask<Void,Void,Message> {



        private Context mContext = null;
        private ProgressDialog pDlg = null;

        public UpdateTokenTask(Context mContext){
            this.mContext = mContext;
        }

        @Override
        protected Message doInBackground(Void... params) {
            try{

                AccountManager accountManager = AccountManager.get(mContext);
                Account[] accounts = accountManager.getAccountsByType("IRetrieve");

                int total = Integer.parseInt(mRedeemPoints.getText().toString()) * -1;

                final String url = SERVICE_URL + "/mobile/updateBalance?userid=" + accounts[0].name + "&total=" + total + "&balance=point";

                RestTemplate restTemplate = new RestTemplate();
                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();


                List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
                supportedMediaTypes.add(new MediaType("text", "plain"));
                supportedMediaTypes.add(new MediaType("application", "json"));
                converter.setSupportedMediaTypes(supportedMediaTypes);


                restTemplate.getMessageConverters().add(converter);

                Message newmessage =  restTemplate.getForObject(url,Message.class);

                return newmessage;

            }catch(Exception ex){
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
            try{
                pDlg.dismiss();
                if(message != null){
                    showMessage(message.getContent());
                }else{
                    showMessage("Failed to buy token. Please try again.");
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


    private class GetUserTask extends AsyncTask<Void,Void,User> {



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
            pDlg.dismiss();
            mCurrentPoint.setText(new Integer(user.getPoints()).toString());
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
