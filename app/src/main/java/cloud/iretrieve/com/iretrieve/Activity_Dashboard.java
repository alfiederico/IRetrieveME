package cloud.iretrieve.com.iretrieve;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.actionitembadge.library.ActionItemBadgeAdder;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import cloud.iretrieve.com.iretrieve.domain.Article;
import cloud.iretrieve.com.iretrieve.domain.Report;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.mikepenz.fontawesome_typeface_library.FontAwesome.Icon.faw_android;


public class Activity_Dashboard extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener, ExpandableListView.OnGroupExpandListener, Button.OnClickListener {
    List_ExpandableAdapter listAdapter;
    ExpandableListView expListView;

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    boolean bFlag = true;
    TextView txtCustomRange;
    TextView txtHome;
    TextView txtReport;
    TextView txtSettle;
    TextView txtHistory;
    TextView txtSetting;
    TextView txtStatistics;
    TextView txtLogout;


    TextView txtCustomRangeSub;
    TextView txtHomeSub;
    TextView txtReportSub;
    TextView txtSettleSub;
    TextView txtHistorySub;
    TextView txtSettingSub;
    TextView txtStatisticSub;
    TextView txtLogoutSub;

    HorizontalScrollView hsView;

    final Point screen = new Point();
    int pos = 0;


    Paint gridPaint;
    ArrayList<Integer> colors;

    ArrayList<Article> articles = new ArrayList<Article>();
    String today = "20130217";
    String category = "";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    Context context = null;

    private int badgeCount = 0;
    private int myreportid = 0;
    public Menu badgeMenu;
    private static final int SAMPLE2_ID = 34535;

    private GoogleMap mMap;

    private double longitude;
    private double latitude;

    private GoogleApiClient googleApiClient;

    private static final String SERVICE_URL = "http://alfiederico.com/iRetrieve-0.0.1";

    private static int iRadius = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__dashboard);

        iRadius = getIntent().getIntExtra("iRadius",50);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Initializing googleApiClient
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        context = this;

        prepareListData();

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.layoutDashboard);

        //LinearLayout linearBody = (LinearLayout) findViewById(R.id.lvBody);

        // preparing list data
        prepareListData();

        listAdapter = new List_ExpandableAdapter(this, listDataHeader, listDataChild);

        expListView.setOnGroupExpandListener(this);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout ll = (LinearLayout) layoutInflater.inflate(R.layout.list_namesubcatheader, null, false);
        try {
            //expListView.addHeaderView(ll);
        } catch (Exception ex) {
            showMessage(ex.toString());
        }

        // setting list adapter
        expListView.setAdapter(listAdapter);


        txtCustomRange = (TextView) findViewById(R.id.txtCustomRange);
        txtHome = (TextView) findViewById(R.id.txtHome);
        txtReport = (TextView) findViewById(R.id.txtReport);
        txtSettle = (TextView) findViewById(R.id.txtSettle);
        txtHistory = (TextView) findViewById(R.id.txtHistory);
        txtSetting = (TextView) findViewById(R.id.txtSetting);
        txtLogout = (TextView) findViewById(R.id.txtLogout);
        txtStatistics = (TextView) findViewById(R.id.txtStatistics);

        txtCustomRangeSub = (TextView) findViewById(R.id.txtCustomRangeSub);
        txtHomeSub = (TextView) findViewById(R.id.txtHomeSub);
        txtReportSub = (TextView) findViewById(R.id.txtReportSub);
        txtSettleSub = (TextView) findViewById(R.id.txtSettleSub);
        txtHistorySub = (TextView) findViewById(R.id.txtHistorySub);
        txtSettingSub = (TextView) findViewById(R.id.txtSettingSub);
        txtStatisticSub = (TextView) findViewById(R.id.txtStatisticsSub);
        txtLogoutSub = (TextView) findViewById(R.id.txtLogoutSub);


        hsView = (HorizontalScrollView) findViewById(R.id.hsView);

        try {
            SimpleDateFormat sub;
            Calendar c = Calendar.getInstance();
            sub = new SimpleDateFormat("yyyyMMdd");
            String formattedDate = sub.format(c.getTime());
            Date date = sub.parse(formattedDate);
            sub = new SimpleDateFormat("MMM-dd");
            txtHome.setTextColor(Color.parseColor("#F44336"));
            txtCustomRangeSub.setText(sub.format(date));
        } catch (Exception ex) {

        }


        Display display = getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(screen);
        } else {
            screen.x = display.getWidth();
            screen.y = display.getHeight();
        }

        txtCustomRange.setWidth(screen.x / 3);
        txtHome.setWidth(screen.x / 3);
        txtReport.setWidth(screen.x / 3);
        txtSettle.setWidth(screen.x / 3);
        txtHistory.setWidth(screen.x / 3);
        txtSetting.setWidth(screen.x / 3);
        txtStatistics.setWidth(screen.x / 3);
        txtLogout.setWidth(screen.x / 3);


        txtCustomRangeSub.setWidth(screen.x / 3);
        txtHomeSub.setWidth(screen.x / 3);
        txtReportSub.setWidth(screen.x / 3);
        txtSettleSub.setWidth(screen.x / 3);
        txtHistorySub.setWidth(screen.x / 3);
        txtSettingSub.setWidth(screen.x / 3);
        txtStatisticSub.setWidth(screen.x / 3);
        txtLogoutSub.setWidth(screen.x / 3);


        txtCustomRange.setOnClickListener(this);
        txtHome.setOnClickListener(this);
        txtReport.setOnClickListener(this);
        txtSettle.setOnClickListener(this);
        txtHistory.setOnClickListener(this);
        txtSetting.setOnClickListener(this);
        txtLogout.setOnClickListener(this);

        txtCustomRangeSub.setOnClickListener(this);
        txtHomeSub.setOnClickListener(this);
        txtReportSub.setOnClickListener(this);
        txtSettleSub.setOnClickListener(this);
        txtHistorySub.setOnClickListener(this);
        txtSettingSub.setOnClickListener(this);
        txtStatisticSub.setOnClickListener(this);
        txtLogoutSub.setOnClickListener(this);


        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(
                    ExpandableListView parent, View v,
                    int groupPosition, int childPosition,
                    long id) {
                try {
                    pos = childPosition - 1;
                    if (pos < 0) {
                        pos = 0;
                    }
                    hsView.scrollTo((screen.x / 3) * pos, 0);
                    String erm = listAdapter.getChild(groupPosition, childPosition).toString();

                    resetHeaderClick();
                    SimpleDateFormat sub;

                    if (txtCustomRange.getText().toString().equals(erm)) {
                        txtCustomRange.setTextColor(Color.parseColor("#F44336"));

                        Calendar c = Calendar.getInstance();
                        sub = new SimpleDateFormat("yyyyMMdd");
                        String formattedDate = sub.format(c.getTime());
                        Date date = sub.parse(formattedDate);
                        sub = new SimpleDateFormat("MMM-dd");
                        txtCustomRangeSub.setText(sub.format(date));


                        expListView.expandGroup(1);
                        return true;
                    } else if (txtHome.getText().toString().equals(erm)) {

                        txtHome.setTextColor(Color.parseColor("#F44336"));
                       // txtHomeSub.setText("report locations");
                        category = "home";
                        new PopulateChartTask(context).execute();
                    } else if (txtReport.getText().toString().equals(erm)) {

                        txtReport.setTextColor(Color.parseColor("#F44336"));
                        //txtReportSub.setText("current location");
                        category = "Report";
                        new PopulateChartTask(context).execute();
                    } else if (txtSettle.getText().toString().equals(erm)) {
                        txtSettle.setTextColor(Color.parseColor("#F44336"));
                        //txtSettleSub.setText("current location");
                        category = "Settle";
                        new PopulateChartTask(context).execute();
                    } else if (txtHistory.getText().toString().equals(erm)) {
                        txtHistory.setTextColor(Color.parseColor("#F44336"));
                        //txtHistorySub.setText("current location");
                        category = "History";
                        new PopulateChartTask(context).execute();
                    } else if (txtSetting.getText().toString().equals(erm)) {
                        txtSetting.setTextColor(Color.parseColor("#F44336"));
                        //txtSettingSub.setText("current location");
                        category = "Setting";
                        new PopulateChartTask(context).execute();
                    } else if (txtStatistics.getText().toString().equals(erm)) {
                        txtStatistics.setTextColor(Color.parseColor("#F44336"));
                        //txtSettingSub.setText("current location");
                        category = "Statistics";
                        new PopulateChartTask(context).execute();
                    } else if (txtLogout.getText().toString().equals(erm)) {
                        NavUtils.navigateUpFromSameTask((Activity) context);
                    }

                } catch (Exception ex) {
                    showMessage(ex.toString());
                }
                showList();
                return true;
            }
        });

        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (bFlag == false) {
                        if (expListView.isGroupExpanded(1)) {
                            txtCustomRangeSub.setText("select range");
                        }
                        showList();
                    }
                }
                return true;
            }
        });

        LinearLayout linearBody = (LinearLayout) findViewById(R.id.lvBody);
        // linearBody.setPadding(0, 140, 0, 0);

        expListView.setDividerHeight(0);

        try {

            category = "home";
            new PopulateChartTask(context).execute();
        } catch (Exception ex) {
            showMessage(ex.toString());
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            category = "home";
            new PopulateChartTask(context).execute();
            return;
        }
        if (requestCode == 4) {
            iRadius = Integer.parseInt(data.getStringExtra("intRadius"));
        }

        category = "home";
        new PopulateChartTask(context).execute();
    }

    public void onMapSearch(View view) {

        mMap.clear();
        EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    //Getting current location
    private void getCurrentLocation() {
        mMap.clear();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            //moving the map to location
            moveMap();
        }


    }

    private void moveMap() {
        /**
         * Creating the latlng object to store lat, long coordinates
         * adding marker to map
         * move the camera with animation
         */
        LatLng latLng = new LatLng(latitude, longitude);
        /**mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true)
                .title("Marker in India"));**/

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.getUiSettings().setZoomControlsEnabled(true);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(this);
        mMap.setInfoWindowAdapter(customInfoWindow);


        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(27.746974, 85.301582);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Kathmandu, Nepal"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // return;
        //}
        //
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    private SpannableString generateCenterSpannableText(String e) {

        SpannableString s = new SpannableString(e);
        s.setSpan(new RelativeSizeSpan(1.3f), 0, e.length(), 0);
        // s.setSpan(new StyleSpan(Typeface.NORMAL), 17, s.length() - 15, 0);
        // s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        //s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        //s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#009688")), 0, s.length(), 0);
        return s;
    }






    public void resetHeaderClick() {
        txtCustomRange.setTextColor(Color.parseColor("#ffffff"));
        txtHome.setTextColor(Color.parseColor("#ffffff"));
        txtReport.setTextColor(Color.parseColor("#ffffff"));
        txtSettle.setTextColor(Color.parseColor("#ffffff"));
        txtHistory.setTextColor(Color.parseColor("#ffffff"));
        txtSetting.setTextColor(Color.parseColor("#ffffff"));
        txtStatistics.setTextColor(Color.parseColor("#ffffff"));
        txtLogout.setTextColor(Color.parseColor("#ffffff"));

        txtCustomRangeSub.setText("");
        txtHomeSub.setText("");
        txtReportSub.setText("");
        txtSettleSub.setText("");
        txtHistorySub.setText("");
        txtSettingSub.setText("");
        txtStatisticSub.setText("");
        txtLogoutSub.setText("");

    }


    public void showList() {
        int len = listAdapter.getGroupCount();

        if (bFlag == true) {
            expListView.expandGroup(0);
            bFlag = false;
        } else {
            expListView.collapseGroup(0);
            bFlag = true;
        }
        expListView.collapseGroup(1);

    }

    public void showMessage(String e) {
        AlertDialog.Builder diag = new AlertDialog.Builder(this);
        diag.setTitle("Message");
        diag.setMessage(e);
        diag.show();
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("");
        listDataHeader.add("asa");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("Calendar");
        top250.add("Home");
        top250.add("Report");
        top250.add("Settle");
        top250.add("History");
        top250.add("Setting");
        top250.add("Statistics");
        top250.add("Logout");

        List<String> top251 = new ArrayList<String>();
        top251.add("Custom Range");


        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), top251); // Header, Child data
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__dashboard, menu);
        if (badgeCount > 0) {
            ActionItemBadge.update(this, menu.findItem(SAMPLE2_ID), faw_android, ActionItemBadge.BadgeStyles.GREY, badgeCount);
        } else {
           //ActionItemBadge.hide(menu.findItem(SAMPLE2_ID));
        }


        new ActionItemBadgeAdder().act(this).menu(menu).title(R.string.sample_2).itemDetails(0, SAMPLE2_ID, 1).showAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS).add(ActionItemBadge.BadgeStyles.GREY_LARGE, badgeCount);

        ActionItemBadge.hide(menu.findItem(SAMPLE2_ID));

        badgeMenu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.



      int id = item.getItemId();

        if (id == SAMPLE2_ID && myreportid != 0) {

            try {
                Intent i = new Intent(this   , Activity_SettleFeed.class);
                i.putExtra("reportid", myreportid);
                startActivityForResult(i, 7);
                invalidateOptionsMenu();
                return true;
            } catch (Exception ex) {
                showMessage(ex.getMessage());
            }
        }



        return  super.onOptionsItemSelected(item);
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        int len = listAdapter.getGroupCount();

        for (int i = 0; i < len; i++) {
            if (i != groupPosition) {
                expListView.collapseGroup(i);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                showList();
        }
    }

    private class PopulateChartTask extends AsyncTask<Void, Void, Report[]> {
        private Context mContext = null;
        private ProgressDialog pDlg = null;


        public PopulateChartTask(Context mContext){
            this.mContext = mContext;
        }


        @Override
        protected Report[] doInBackground(Void... params) {
            try {

                switch(category){
                    case "home":
                        try{
                            final String url = SERVICE_URL + "/mobile/reports";

                            RestTemplate restTemplate = new RestTemplate();
                            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();


                            List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
                            supportedMediaTypes.add(new MediaType("text", "plain"));
                            supportedMediaTypes.add(new MediaType("application", "json"));
                            converter.setSupportedMediaTypes(supportedMediaTypes);

                            restTemplate.getMessageConverters().add(converter);
                            return restTemplate.getForObject(url, Report[].class);
                        }catch(Exception exw){
                            return null;
                        }


                    case "Setting":
                        Intent iSetting = new Intent(mContext, Activity_Setting.class);

                        startActivityForResult(iSetting,4);
                        break;
                    case "Report":
                        Intent i = new Intent(mContext, Activity_Report.class);

                        startActivityForResult(i, 3);
                        break;
                    case "Settle":
                        Intent ii = new Intent(mContext, Activity_Settle.class);

                        startActivityForResult(ii, 5);
                        break;
                    case "History":

                        Intent iii = new Intent(mContext, Activity_History.class);

                        startActivityForResult(iii, 6);

                        break;
                    case "Statistics":
                        Intent iStatistics = new Intent(mContext, Activity_Statistic.class);

                        startActivityForResult(iStatistics,9);
                        break;


                    default:



                }

            } catch (Exception ex) {
                showMessage(ex.toString());
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            try {
               // showProgressDialog();
            } catch (Exception ex) {
                showMessage(ex.toString());
            }
        }

        private void showProgressDialog() {
            pDlg = new ProgressDialog(mContext);
            pDlg.setMessage("Retrieving Data...");
            pDlg.setProgressDrawable(mContext.getWallpaper());
            pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDlg.setCancelable(false);
            pDlg.show();

        }


        @Override
        protected void onPostExecute(Report[] reports) {

            try{
                //add bounds

                badgeCount = 0;
                if(category == "home"){

                    if(reports == null){
                        ActionItemBadge.hide(badgeMenu.findItem(SAMPLE2_ID));
                        return;
                    }

                    AccountManager accountManager = AccountManager.get(mContext);
                    Account[] accounts = accountManager.getAccountsByType("IRetrieve");

                    mMap.clear();

                    LatLngBounds.Builder bounds = new LatLngBounds.Builder();

                    LatLng myLatLng = null;

                    boolean doBounds = false;

                    int reportID = 0;

                    for(Report e: reports){
                        String[] f = e.getLocation().split(",");
                        LatLng latlng = new LatLng(Double.parseDouble(f[0]), Double.parseDouble(f[1]));
                        if(e.getUserId() == Integer.parseInt(accounts[0].name)){



                            Report info = new Report();
                            info.setSubject("Subject : " + e.getSubject() );
                            info.setDescription("Description : " + e.getDescription());
                            info.setDate("Date : " + e.getDate());
                            info.setContact(e.getContact());

                            myLatLng = latlng;


                            Marker m = mMap.addMarker(new MarkerOptions().position(myLatLng).title(new Integer(e.getId()).toString()).icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).snippet(e.getType()));

                            m.setTag(info);

                            bounds.include(new LatLng(Double.parseDouble(f[0]), Double.parseDouble(f[1])));

                            reportID = e.getId();

                            myreportid = reportID;
                        }

                    }

                    if(myLatLng == null){
                        return;
                    }

                    for(Report e: reports){
                        String[] f = e.getLocation().split(",");
                        LatLng latlng = new LatLng(Double.parseDouble(f[0]), Double.parseDouble(f[1]));
                        if(e.getUserId() == Integer.parseInt(accounts[0].name)){

                        }else if(distance(myLatLng.latitude,myLatLng,longitude,latlng.latitude,latlng.longitude,'K') <= iRadius){

                            Report info = new Report();
                            info.setSubject("Subject : " + e.getSubject() );
                            info.setDescription("Description : " + e.getDescription());
                            info.setDate("Date : " + e.getDate());
                            info.setContact(e.getContact());

                            if(e.getType().equals("FOUND")){
                                Marker m = mMap.addMarker(new MarkerOptions().position(latlng).title(new Integer(e.getId()).toString()).snippet(e.getType()).icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                                m.setTag(info);
                            }else{
                                Marker m = mMap.addMarker(new MarkerOptions().position(latlng).title(new Integer(e.getId()).toString()).snippet(e.getType()));

                                m.setTag(info);
                            }



                            bounds.include(new LatLng(Double.parseDouble(f[0]), Double.parseDouble(f[1])));

                            doBounds = true;
                        }

                        if(reportID == e.getIsettle()){
                            badgeCount += 1;
                        }


                    }

                    if (badgeCount > 0) {
                        ActionItemBadge.update((Activity) mContext, badgeMenu.findItem(SAMPLE2_ID), faw_android, ActionItemBadge.BadgeStyles.GREY, badgeCount);
                    } else {
                        ActionItemBadge.hide(badgeMenu.findItem(SAMPLE2_ID));
                    }

                    if(doBounds == false){
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(myLatLng));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 15));
                    }else{
                        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 15));
                    }

                    mMap.getUiSettings().setZoomControlsEnabled(true);

                }



            }catch(Exception ex){
                showMessage(ex.getMessage());
            }
        }


        public Double distance(double lat1, LatLng myLatLng, double lon1, double lat2, double lon2, char unit){
            double theta  = lon1 - lon2;
            double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            if(unit == 'K'){
                dist = dist * 1.609344;
            }else{
                dist = dist * 0.8684;
            }

            return dist;
        }

        public double deg2rad(double deg){
            return (deg * Math.PI / 180.0);
        }



        public double rad2deg(double rad){
            return rad / Math.PI * 180.0;
        }



    }
}
