package cloud.iretrieve.com.iretrieve;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import cloud.iretrieve.com.iretrieve.domain.History;
import cloud.iretrieve.com.iretrieve.domain.HistoryParcelable;
import cloud.iretrieve.com.iretrieve.domain.Report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class Activity_History extends Activity {

    ArticleList_ExpandableAdapter listAdapter;
    ExpandableListView expListView;

    List<String> listDataHeader;
    HashMap<String, List<History>> listDataChild;
    Context context = null;

    private static final String SERVICE_URL = "http://192.168.254.3:8089";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            setContentView(R.layout.activity_history);

            // get the listview
            expListView = (ExpandableListView) findViewById(R.id.lvExp);

            context = this;
            new GetHistoryTask(context).execute();

            //ArrayList<HistoryParcelable> historyParcelable = getIntent().getParcelableArrayListExtra("histories");

            // preparing list data
            //prepareListData(historyParcelable);


        }catch(Exception ex){

        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__revenue, menu);
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
    /**private class MySimpleArrayAdapter extends ArrayAdapter<Article> {
        private final Context context;
        private final ArrayList<Article> values;

        public MySimpleArrayAdapter(Context context, ArrayList<Article> values) {
            super(context, -1, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_article_description, parent, false);
            TextView txtDescription = (TextView) rowView.findViewById(R.id.txtDescription);
            TextView txtQty = (TextView) rowView.findViewById(R.id.txtQty);
            TextView txtSales = (TextView) rowView.findViewById(R.id.txtTotalSales);

            if(values.get(position).getCategory().equals("")){
                txtDescription.setText(values.get(position).getPLU());
            }else{
                txtDescription.setText(values.get(position).getDescription());
            }

            txtQty.setText(new Integer(values.get(position).getQty()).toString());
            txtSales.setText( "$" + new Double(values.get(position).getTotalSales()).toString());

            return rowView;
        }
    }
        **/

    public class ArticleList_ExpandableAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<String> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<History>> _listDataChild;
        public boolean bReset = false;
        public Date range1;
        public Date range2;


        public ArticleList_ExpandableAdapter(Context context, List<String> listDataHeader,
                                      HashMap<String, List<History>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }


        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final History childText = (History) getChild(groupPosition, childPosition);
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_article_description, null);

            TextView txtID = (TextView) convertView.findViewById(R.id.txtID);
            TextView txtTypeID = (TextView) convertView.findViewById(R.id.txtTypeID);
            TextView txtType = (TextView) convertView.findViewById(R.id.txtType);
            TextView txtSubject = (TextView) convertView.findViewById(R.id.txtSubject);
            TextView txtDescription = (TextView) convertView.findViewById(R.id.txtDescription);
            TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            TextView txtPlace = (TextView) convertView.findViewById(R.id.txtPlace);
            TextView txtLocation = (TextView) convertView.findViewById(R.id.txtLocation);
            TextView txtSettleID = (TextView) convertView.findViewById(R.id.txtSettleID);


            txtID.setText(new Integer(childText.getId()).toString());
            txtTypeID.setText(new Integer(childText.getTypeId()).toString());
            txtType.setText(new String(childText.getType()).toString());
            txtSubject.setText(new String(childText.getSubject()).toString());
            txtDescription.setText(new String(childText.getDescription()).toString());
            txtDate.setText(new String(childText.getDate()).toString());
            txtPlace.setText(new String(childText.getPlace()).toString());
            txtLocation.setText(new String(childText.getLocation()).toString());
            txtSettleID.setText(new Integer(childText.getSettleId()).toString());

            return convertView;
        }

        public void showMessage(String e) {
            AlertDialog.Builder diag = new AlertDialog.Builder(this._context);
            diag.setTitle("Message");
            diag.setMessage(e);
            diag.show();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {

            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_namesubcat_description, null);
            }

            TextView lblListHeader = (TextView) convertView.findViewById(R.id.txtListItem);
             lblListHeader.setText(headerTitle);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    public void showMessage(String e) {
        AlertDialog.Builder diag = new AlertDialog.Builder(this);
        diag.setTitle("Message");
        diag.setMessage(e);
        diag.show();
    }


    private class GetHistoryTask extends AsyncTask<Void,Void,History[]> {


        private Context mContext = null;
        private ProgressDialog pDlg = null;

        public GetHistoryTask(Context mContext){
            this.mContext = mContext;
        }

        @Override
        protected History[] doInBackground(Void... params) {
            try{
                AccountManager accountManager = AccountManager.get(mContext);
                Account[] accounts = accountManager.getAccountsByType("IRetrieve");

                final String url = SERVICE_URL + "/mobile/history?userid=" + accounts[0].name;

                RestTemplate restTemplate = new RestTemplate();
                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();


                List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
                supportedMediaTypes.add(new MediaType("text", "plain"));
                supportedMediaTypes.add(new MediaType("application", "json"));
                converter.setSupportedMediaTypes(supportedMediaTypes);

                restTemplate.getMessageConverters().add(converter);
                History[] histories = restTemplate.getForObject(url,History[].class);



                return histories;
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
        protected void onPostExecute(History[] histories) {
            try{
                pDlg.dismiss();
                if(histories == null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Settle Failed");
                    builder.setMessage("History item not found. Report item first.. Please try again.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            NavUtils.navigateUpFromSameTask((Activity) mContext);
                        }
                    });
                    builder.show();
                }else{

                    listDataHeader = new ArrayList<String>();
                    listDataChild = new HashMap<String, List<History>>();
                    int itemCount = 0;
                    int headerCount = 0;
                    for(History e: histories){
                        listDataHeader.add(new Integer(headerCount).toString());
                        ArrayList<History> arrHistories = new ArrayList<History>();
                        arrHistories.add(e);
                        listDataChild.put(listDataHeader.get(headerCount), arrHistories);
                        headerCount+=1;
                    }

                    listAdapter = new ArticleList_ExpandableAdapter(mContext, listDataHeader, listDataChild);

                    // setting list adapter
                    expListView.setAdapter(listAdapter);


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
