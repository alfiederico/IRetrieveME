package cloud.iretrieve.com.iretrieve;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import cloud.iretrieve.com.iretrieve.domain.Order;
import cloud.iretrieve.com.iretrieve.domain.OrderList;
import cloud.iretrieve.com.iretrieve.domain.Report;
import cloud.iretrieve.com.iretrieve.domain.ReportList;

import java.util.ArrayList;
import java.util.List;


public class Activity_SettleFeed extends Activity {

    public int myreportid = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_settlefeed);
            //ArrayList<Order> articles = getIntent().getParcelableArrayListExtra("articles");

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                myreportid = extras.getInt("reportid");
                //The key argument here must match that used in the other activity
            }

          new SettleFeedTask(this).execute();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sales_feed, menu);
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

    private class MySimpleArrayAdapter extends ArrayAdapter<Report> {
        private final Context context;
        private final ArrayList<Report> values;

        public MySimpleArrayAdapter(Context context, ArrayList<Report> values) {
            super(context, -1, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_settle_feed_description, parent, false);
            TextView id = (TextView) rowView.findViewById(R.id.id);
            TextView type = (TextView) rowView.findViewById(R.id.type);
            TextView subject = (TextView) rowView.findViewById(R.id.subject);
            TextView description = (TextView) rowView.findViewById(R.id.description);
            TextView date = (TextView) rowView.findViewById(R.id.date);
            TextView contact = (TextView) rowView.findViewById(R.id.contact);

            id.setText(new Integer(values.get(position).getId()).toString());
            type.setText(values.get(position).getType());
            subject.setText(values.get(position).getSubject());
            description.setText(values.get(position).getDescription());
            date.setText(values.get(position).getDate());
            contact.setText(values.get(position).getContact());


            return rowView;


        }

    }

    private class SettleFeedTask extends AsyncTask<Void, Void, Report[]> {
        private Context mContext = null;
        private ProgressDialog pDlg = null;

        public SettleFeedTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected Report[] doInBackground(Void... params) {
            try {
                final String url = "http://192.168.254.10:8089/mobile/isettlefeed?isettle=" + myreportid;

                RestTemplate restTemplate = new RestTemplate();
                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();


                List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
                supportedMediaTypes.add(new MediaType("text", "plain"));
                supportedMediaTypes.add(new MediaType("application", "json"));
                converter.setSupportedMediaTypes(supportedMediaTypes);

                restTemplate.getMessageConverters().add(converter);
                Report[] reports =  restTemplate.getForObject(url, Report[].class);

                return reports;
            } catch (Exception ex) {
                showMessage(ex.getMessage());
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
        protected void onPostExecute(Report[] reports) {
            try {
                pDlg.dismiss();
                if(reports != null && reports.length > 0){
                    ArrayList<Report> arrReport = new ArrayList<Report>();
                    for(Report e: reports){
                        arrReport.add(e);
                    }
                    final ListView listview = (ListView) findViewById(R.id.listview);
                    final MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(mContext,arrReport);
                    listview.setAdapter(adapter);
                }

            } catch (Exception ex) {
                showMessage(ex.toString());
            }
        }


        private void showProgressDialog() {
            pDlg = new ProgressDialog(mContext);
            pDlg.setMessage("Retrieving data...");
            pDlg.setProgressDrawable(mContext.getWallpaper());
            pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDlg.setCancelable(false);
            pDlg.show();

        }


    }
}
