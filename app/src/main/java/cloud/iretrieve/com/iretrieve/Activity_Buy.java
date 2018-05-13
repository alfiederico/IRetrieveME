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
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import cloud.iretrieve.com.iretrieve.domain.Message;
import cloud.iretrieve.com.iretrieve.domain.User;

public class Activity_Buy extends Activity {

    Spinner mPrice;
    Spinner mPayment;
    EditText mAccount;
    Button mBuy;

    Context context = null;

    private static final String SERVICE_URL = "http://192.168.254.2:8089";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__buy);

        init();
    }

    public void buyNow(){
        new UpdateTokenTask(this).execute();
    }

    public void init() {
        mPrice = (Spinner) findViewById(R.id.editPrice);
        mPayment = (Spinner) findViewById(R.id.editPayment);
        mAccount = (EditText) findViewById(R.id.editAccount);
        mBuy = (Button)findViewById(R.id.btnBuy);

        mBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyNow();

            }
        });

        ArrayList<String> prices = new ArrayList<String>();

        prices.add("P90.00 = 50 Token");
        prices.add("P320.00 = 250 Token");
        prices.add("P615.00 = 500 Token");
        prices.add("P1,200.00 = 1000 Token");
        prices.add("P2,605.00 = 2200 Token");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, prices);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrice.setAdapter(dataAdapter);


        ArrayList<String> payments = new ArrayList<String>();

        payments.add("GCash");
        payments.add("Globe Prepaid Load");
        payments.add("Credit Card");


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
            InputMethodManager inputManager = (InputMethodManager) Activity_Buy.this
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(
                    Activity_Buy.this.getCurrentFocus()
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

                int total = 0;

                switch(mPrice.getSelectedItemPosition()){
                    case 0:
                        total = 50;
                        break;
                    case 1:
                        total = 250;
                        break;
                    case 2:
                        total = 500;
                        break;
                    case 3:
                        total = 1000;
                        break;
                    case 4:
                        total = 2200;
                        break;

                }


                final String url = SERVICE_URL + "/mobile/updateBalance?userid=" + accounts[0].name + "&total=" + total + "&balance=token";

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
}
