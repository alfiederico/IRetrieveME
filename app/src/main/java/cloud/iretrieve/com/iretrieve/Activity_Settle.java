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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import cloud.iretrieve.com.iretrieve.domain.Message;
import cloud.iretrieve.com.iretrieve.domain.Report;
import cloud.iretrieve.com.iretrieve.domain.Staff;


public class Activity_Settle extends Activity {
    private Form mForm;
    EditText mID;
 EditText mType;
    EditText mSubject;
    EditText mDescription;
    EditText mDate;
    EditText mPlace;
    EditText mLocation;
    EditText mReferenceID;
    Button btnReport;
    Context context = null;
    private static final String SERVICE_URL = "http://192.168.254.3:8089";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settle);
            init();
            //validation();
            createListener();
           context = this;
            new GetReportTask(context).execute();
        }catch(Exception ex){
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setTitle("Login Failed");
            builder.setMessage(ex.getMessage());
            builder.setPositiveButton("OK", null);
            builder.show();
        }


    }

    public void init(){
        mID = (EditText)findViewById(R.id.editID);
        mType = (EditText)findViewById(R.id.editType);
        mSubject = (EditText)findViewById(R.id.editSubject);
        mDescription = (EditText)findViewById(R.id.editDescription);
        mDate = (EditText)findViewById(R.id.editDate);
        mPlace = (EditText)findViewById(R.id.editPlace);
        mLocation = (EditText)findViewById(R.id.editLocation);
        mReferenceID = (EditText)findViewById(R.id.editReferenceID);
        btnReport = (Button)findViewById(R.id.btnReport);

        mID.setEnabled(false);


    }

    public void validation(){
        mForm = new Form(this);
        mForm.addField(Field.using(mID).validate(Validation_NotEmpty.build(this)));
        mForm.addField(Field.using(mType).validate(Validation_NotEmpty.build(this)));
        mForm.addField(Field.using(mSubject).validate(Validation_NotEmpty.build(this)));
        mForm.addField(Field.using(mDescription).validate(Validation_NotEmpty.build(this)));
        mForm.addField(Field.using(mDate).validate(Validation_NotEmpty.build(this)));
        mForm.addField(Field.using(mPlace).validate(Validation_NotEmpty.build(this)));
        mForm.addField(Field.using(mLocation).validate(Validation_NotEmpty.build(this)));
        mForm.addField(Field.using(mReferenceID).validate(Validation_NotEmpty.build(this)));
    }
    public void createListener(){


        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mID.getText().toString().equals(mReferenceID.getText().toString().trim())){

                    showMessage("settleid and reportid supplied cannot be the same.");
                    return;
                }
                settleNow();

            }
        });



    }

    public void settleNow(){
        new SetReportTask(this, mReferenceID.getText().toString()).execute();
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
            InputMethodManager inputManager = (InputMethodManager) Activity_Settle.this
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(
                    Activity_Settle.this.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }catch(Exception ex){
            showMessage(ex.toString());
        }


    }

    private class GetReportTask extends AsyncTask<Void,Void,Report>{


        private Context mContext = null;
        private ProgressDialog pDlg = null;

        public GetReportTask(Context mContext){
            this.mContext = mContext;
        }

        @Override
        protected Report doInBackground(Void... params) {
            try{
                AccountManager accountManager = AccountManager.get(mContext);
                Account[] accounts = accountManager.getAccountsByType("IRetrieve");

                final String url = SERVICE_URL + "/mobile/settle?userid=" + accounts[0].name;

                RestTemplate restTemplate = new RestTemplate();
                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();


                List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
                supportedMediaTypes.add(new MediaType("text", "plain"));
                supportedMediaTypes.add(new MediaType("application", "json"));
                converter.setSupportedMediaTypes(supportedMediaTypes);

                restTemplate.getMessageConverters().add(converter);
                Report report = restTemplate.getForObject(url,Report.class);



                return report;
            }catch(Exception ex){
                showMessage(ex.toString());
                return null;
            }


        }

        @Override
        protected void onPreExecute() {
            try {
                // hideKeyboard();
                showProgressDialog();
            } catch (Exception ex) {
                showMessage(ex.toString());
            }
        }

        @Override
        protected void onPostExecute(Report report) {
            try{
                pDlg.dismiss();
                if(report == null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Settle Failed");
                    builder.setMessage("Report item not found. Report item first.. Please try again.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            NavUtils.navigateUpFromSameTask((Activity) mContext);
                        }
                    });
                    builder.show();
                }else{


                    mID.setText(new Integer(report.getId()).toString());
                    mType.setText(report.getType());
                    mSubject.setText(report.getSubject());
                    mDescription.setText(report.getDescription());
                    mDate.setText(report.getDate());
                    mPlace.setText(report.getPlace());
                    mLocation.setText(report.getLocation());
                    mReferenceID.setText(new Integer(report.getIsettle()).toString());

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


    private class SetReportTask extends AsyncTask<Void,Void,Message>{


        private Context mContext = null;
        private ProgressDialog pDlg = null;
        private String id = "";

        public SetReportTask(Context mContext, String id){
            this.mContext = mContext;
            this.id  = id;
        }

        @Override
        protected Message doInBackground(Void... params) {
            try{
                AccountManager accountManager = AccountManager.get(mContext);
                Account[] accounts = accountManager.getAccountsByType("IRetrieve");

                final String url = SERVICE_URL + "/mobile/isettle?isettle=" + id + "&userid=" + accounts[0].name ;

                RestTemplate restTemplate = new RestTemplate();
                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();


                List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
                supportedMediaTypes.add(new MediaType("text", "plain"));
                supportedMediaTypes.add(new MediaType("application", "json"));
                converter.setSupportedMediaTypes(supportedMediaTypes);

                restTemplate.getMessageConverters().add(converter);
                Message message = restTemplate.getForObject(url,Message.class);



                return message;
            }catch(Exception ex){
                showMessage(ex.toString());
                return null;
            }


        }

        @Override
        protected void onPreExecute() {
            try {
                // hideKeyboard();
                showProgressDialog();
            } catch (Exception ex) {
                showMessage(ex.toString());
            }
        }

        @Override
        protected void onPostExecute(Message message) {
            try{
                pDlg.dismiss();
                if(message.getId() == 3){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Settle Failed");
                    builder.setMessage(message.getContent());
                    builder.setPositiveButton("OK", null);
                    builder.show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Settle Successful");
                    builder.setMessage(message.getContent());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {
                         NavUtils.navigateUpFromSameTask((Activity) mContext);
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
