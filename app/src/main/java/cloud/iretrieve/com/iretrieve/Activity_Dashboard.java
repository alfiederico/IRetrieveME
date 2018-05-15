package cloud.iretrieve.com.iretrieve;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
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
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
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
import cloud.iretrieve.com.iretrieve.domain.History;
import cloud.iretrieve.com.iretrieve.domain.Message;
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
        GoogleMap.OnMapLongClickListener, ExpandableListView.OnGroupExpandListener, Button.OnClickListener, GoogleMap.OnMarkerClickListener {
    List_ExpandableAdapter listAdapter;
    ReportList_ExpandableAdapter listReportAdapter;
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
    TextView txtBuy;
    TextView txtRedeem;
    TextView txtStatistics;
    TextView txtLogout;


    TextView txtCustomRangeSub;
    TextView txtHomeSub;
    TextView txtReportSub;
    TextView txtSettleSub;
    TextView txtHistorySub;
    TextView txtSettingSub;
    TextView txtBuySub;
    TextView txtRedeemSub;
    TextView txtStatisticSub;
    TextView txtLogoutSub;
    ScrollView mScrollView;

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

    ExpandableListView expReports;

    List<String> listReportDataHeader;
    HashMap<String, List<Report>> listReportDataChild;

    boolean mLocationPermissionGranted = false;


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

        expReports = (ExpandableListView) findViewById(R.id.lvExpReports);

        mScrollView = (ScrollView) findViewById(R.id.scrollView1);

        expReports
                .setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                    @Override
                    public boolean onChildClick(
                            ExpandableListView parent, View v,
                            int groupPosition, int childPosition,
                            long id) {

                        final TextView txtID = (TextView) v.findViewById(R.id.txtID);
                        final TextView txtUserID = (TextView) v.findViewById(R.id.txtUserID);
                        final TextView txtSettle = (TextView) v.findViewById(R.id.txtSettled);

                        if(txtSettle.getText().toString().contains("YES")){
                            showMessage("This item is already settled by your account");
                            return false;
                        }

                        AccountManager accountManager = AccountManager.get(context);
                        Account[] accounts = accountManager.getAccountsByType("IRetrieve");

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);

                        if(txtUserID.getText().equals(accounts[0].name)){
                            builder.setMessage("If you settle your own report then it will be marked as cancelled or deleted. Continue?");
                        }else{
                            builder.setMessage("Settle " + txtID.getText().toString());
                        }

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.dismiss();
                                new SetReportTask(context, txtID.getText().toString().replace("ID : ","")).execute();
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                                dialog.dismiss();
                            }
                        });

                        builder.show();

                        return false;
                    }
                });


        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.layoutDashboard);

        //LinearLayout linearBody = (LinearLayout) findViewById(R.id.lvBody);

        // preparing list data
        prepareListData();

        listAdapter = new List_ExpandableAdapter(this, listDataHeader, listDataChild);

        //listReportAdapter = new ReportList_ExpandableAdapter(this, listReportDataHeader, listReportDataChild);

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
        txtBuy = (TextView) findViewById(R.id.txtBuy);
        txtRedeem = (TextView) findViewById(R.id.txtRedeem);
        txtLogout = (TextView) findViewById(R.id.txtLogout);
        txtStatistics = (TextView) findViewById(R.id.txtStatistics);

        txtCustomRangeSub = (TextView) findViewById(R.id.txtCustomRangeSub);
        txtHomeSub = (TextView) findViewById(R.id.txtHomeSub);
        txtReportSub = (TextView) findViewById(R.id.txtReportSub);
        txtSettleSub = (TextView) findViewById(R.id.txtSettleSub);
        txtHistorySub = (TextView) findViewById(R.id.txtHistorySub);
        txtSettingSub = (TextView) findViewById(R.id.txtSettingSub);
        txtBuySub = (TextView) findViewById(R.id.txtBuySub);
        txtRedeemSub = (TextView) findViewById(R.id.txtRedeemSub);
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
        txtBuy.setWidth(screen.x / 3);
        txtRedeem.setWidth(screen.x / 3);
        txtStatistics.setWidth(screen.x / 3);
        txtLogout.setWidth(screen.x / 3);


        txtCustomRangeSub.setWidth(screen.x / 3);
        txtHomeSub.setWidth(screen.x / 3);
        txtReportSub.setWidth(screen.x / 3);
        txtSettleSub.setWidth(screen.x / 3);
        txtHistorySub.setWidth(screen.x / 3);
        txtSettingSub.setWidth(screen.x / 3);
        txtBuySub.setWidth(screen.x / 3);
        txtRedeemSub.setWidth(screen.x / 3);
        txtStatisticSub.setWidth(screen.x / 3);
        txtLogoutSub.setWidth(screen.x / 3);


        txtCustomRange.setOnClickListener(this);
        txtHome.setOnClickListener(this);
        txtReport.setOnClickListener(this);
        txtSettle.setOnClickListener(this);
        txtHistory.setOnClickListener(this);
        txtSetting.setOnClickListener(this);
        txtBuy.setOnClickListener(this);
        txtRedeem.setOnClickListener(this);
        txtLogout.setOnClickListener(this);

        txtCustomRangeSub.setOnClickListener(this);
        txtHomeSub.setOnClickListener(this);
        txtReportSub.setOnClickListener(this);
        txtSettleSub.setOnClickListener(this);
        txtHistorySub.setOnClickListener(this);
        txtSettingSub.setOnClickListener(this);
        txtBuySub.setOnClickListener(this);
        txtRedeemSub.setOnClickListener(this);
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
                    } else if (txtBuy.getText().toString().equals(erm)) {
                        txtSetting.setTextColor(Color.parseColor("#F44336"));
                        //txtSettingSub.setText("current location");
                        category = "Buy";
                        new PopulateChartTask(context).execute();
                    } else if (txtRedeem.getText().toString().equals(erm)) {
                        txtSetting.setTextColor(Color.parseColor("#F44336"));
                        //txtSettingSub.setText("current location");
                        category = "Redeem";
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

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
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
            mLocationPermissionGranted = false;
            return;
        }
        mLocationPermissionGranted = true;
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

        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.here)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.getUiSettings().setZoomControlsEnabled(true);


    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        try{
            Report e = (Report)marker.getTag();
            System.out.println(e.getLocation());
            category = "home";

            if(e.getCategory() == null)
                e.setCategory("");

            if(e.getCategory().equals("")||e.getCategory() == null){
                if(e.getType().equals("FOUND"))
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.selectfound));
                else
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.selectlost));
            }else{
                if(e.getCategory().equals("hotspot"))
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.selecthotspot));
                else
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.selectgroup));
            }


            new PopulateChartTask(context, e.getLocation()).execute();

        }catch(Exception ex){
            //selecting current location
        }


        return true;
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight() * 8;
            totalHeight += 100;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(this);
        mMap.setInfoWindowAdapter(customInfoWindow);

        mMap.setOnMarkerClickListener(this);


        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(27.746974, 85.301582);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Kathmandu, Nepal"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // return;
        //}
        //

        updateLocationUI();

    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                getCurrentLocation();
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                //mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            //Log.e("Exception: %s", e.getMessage());
        }
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
        txtBuy.setTextColor(Color.parseColor("#ffffff"));
        txtRedeem.setTextColor(Color.parseColor("#ffffff"));
        txtStatistics.setTextColor(Color.parseColor("#ffffff"));
        txtLogout.setTextColor(Color.parseColor("#ffffff"));

        txtCustomRangeSub.setText("");
        txtHomeSub.setText("");
        txtReportSub.setText("");
        txtSettleSub.setText("");
        txtHistorySub.setText("");
        txtSettingSub.setText("");
        txtBuySub.setText("");
        txtRedeemSub.setText("");
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
        top250.add("Buy");
        top250.add("Redeem");
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

    public class ReportList_ExpandableAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<String> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<Report>> _listDataChild;
        public boolean bReset = false;
        public Date range1;
        public Date range2;


        public ReportList_ExpandableAdapter(Context context, List<String> listDataHeader,
                                            HashMap<String, List<Report>> listChildData) {
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



        @SuppressLint("NewApi")
        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final Report childText = (Report) getChild(groupPosition, childPosition);
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_reports_description, null);

            TextView txtID = (TextView) convertView.findViewById(R.id.txtID);
            TextView txtType = (TextView) convertView.findViewById(R.id.txtType);
            TextView txtSubject = (TextView) convertView.findViewById(R.id.txtSubject);
            TextView txtDescription = (TextView) convertView.findViewById(R.id.txtDescription);
            TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            TextView txtPlace = (TextView) convertView.findViewById(R.id.txtPlace);
            TextView txtContact = (TextView) convertView.findViewById(R.id.txtContact);
            ImageView mPhoto = (ImageView) convertView.findViewById(R.id.imageView);
            TextView txtSettled = (TextView) convertView.findViewById(R.id.txtSettled);
            TextView txtUserID = (TextView) convertView.findViewById(R.id.txtUserID);



            txtID.setText("ID : " + new Integer(childText.getId()).toString());

            txtType.setText("Type : " + new String(childText.getType()).toString());
            txtSubject.setText("Subject : " + new String(childText.getSubject()).toString());
            txtDescription.setText("Description : " + new String(childText.getDescription()).toString());
            txtDate.setText("Date : " + new String(childText.getDate()).toString());
            txtPlace.setText("Place : " + new String(childText.getPlace()).toString());
            txtContact.setText("Contact : " + new String(childText.getContact()).toString());
            txtUserID.setText(new Integer(childText.getUserId()).toString());


            if(childText.isSettled()){
                txtSettled.setText("Settled: YES");
            }else{
                txtSettled.setText("Settled: NO");
            }


            try{
                if(childText.getPhoto()!= null && !childText.getPhoto().equals("") ){
                    final byte[] decodedBytes =  Base64.decode(childText.getPhoto(),Base64.DEFAULT);
                    mPhoto.setImageBitmap(BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length));
                }else{
                    mPhoto.setImageDrawable(getDrawable(R.drawable.no_image));
                }
            }catch(Exception ex){
                mPhoto.setImageDrawable(getDrawable(R.drawable.no_image));
            }


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

    private class PopulateChartTask extends AsyncTask<Void, Void, Report[]> {
        private Context mContext = null;
        private ProgressDialog pDlg = null;
        private String location = "";


        public PopulateChartTask(Context mContext){
            this.mContext = mContext;
        }

        public PopulateChartTask(Context mContext, String location){
            this.mContext = mContext;
            this.location = location;
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


                    case "Buy":
                        Intent iBuy = new Intent(mContext, Activity_Buy.class);

                        startActivityForResult(iBuy,10);
                        break;
                    case "Redeem":
                        Intent iRedeem = new Intent(mContext, Activity_Redeem.class);

                        startActivityForResult(iRedeem,11);
                        break;
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
                showProgressDialog();
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
                pDlg.dismiss();
                badgeCount = 0;
                if(category == "home"){

                    if(reports == null){
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.here)));
                        ActionItemBadge.hide(badgeMenu.findItem(SAMPLE2_ID));
                        if(listReportAdapter != null){
                            listReportDataHeader.clear();
                            listReportDataChild.clear();
                            listReportAdapter.notifyDataSetChanged();
                            setListViewHeightBasedOnChildren(expReports);
                        }
                        return;
                    }

                    AccountManager accountManager = AccountManager.get(mContext);
                    Account[] accounts = accountManager.getAccountsByType("IRetrieve");

                    listReportDataHeader = new ArrayList<String>();
                    listReportDataChild = new HashMap<String, List<Report>>();
                    int itemCount = 0;
                    int headerCount = 0;
                    int myreportids = 0;
                    for(Report e: reports){
                        if(e.getUserId() == Integer.parseInt(accounts[0].name)){
                            myreportids = e.getId();
                            break;
                        }
                    }

                    for(Report e: reports){
                        if(location != ""){
                            if(!e.getLocation().equals(location)){
                                continue;
                            }
                        }else{
                            if(e.getUserId() == Integer.parseInt(accounts[0].name)){
                                continue;
                            }
                        }

                        if((e.getUsettle() == myreportids)&& (myreportids != 0)){
                            e.setSettled(true);
                        }else{
                            e.setSettled(false);
                        }

                        listReportDataHeader.add((headerCount + 1) + ". " +  e.getSubject().toUpperCase() + " (" + e.getDate() + ")");

                        ArrayList<Report> arrReports = new ArrayList<Report>();
                        arrReports.add(e);
                        listReportDataChild.put(listReportDataHeader.get(headerCount), arrReports);
                        headerCount+=1;
                    }

                    listReportAdapter = new ReportList_ExpandableAdapter(mContext, listReportDataHeader, listReportDataChild);

                    // setting list adapter
                    expReports.setAdapter(listReportAdapter);

                    setListViewHeightBasedOnChildren(expReports);

                    for(int i=0; i < listReportAdapter.getGroupCount(); i++)
                        expReports.expandGroup(i);

                    if(location != ""){
                        mScrollView.smoothScrollTo(0,500);
                        return;
                    }


                    mMap.clear();

                    mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.here)));

                    LatLngBounds.Builder bounds = new LatLngBounds.Builder();

                    bounds.include(new LatLng(latitude, longitude));

                    LatLng myLatLng = null;

                    boolean doBounds = false;

                    int reportID = 0;

                    //list of duplicate here
                    ArrayList<Integer> collect = new ArrayList<>();

                    for(int i = 0; i < reports.length - 1; i ++){
                        for(int j = i + 1; j < reports.length; j++){
                            if(reports[i].getLocation().equals(reports[j].getLocation())){
                                collect.add(j);
                                if(reports[i].getCategory() == null)
                                    reports[i].setCategory("");

                                if(!reports[i].getCategory().equals("hotspot"))
                                    reports[i].setCategory("group");
                            }
                        }
                    }

                    for(Report e: reports){
                        String[] f = e.getLocation().split(",");
                        LatLng latlng = new LatLng(Double.parseDouble(f[0]), Double.parseDouble(f[1]));
                        if(e.getUserId() == Integer.parseInt(accounts[0].name)){

                            if(e.getCategory() == null)
                                e.setCategory("");

                            Report info = new Report();
                            info.setSubject("Subject : " + e.getSubject() );
                            info.setDescription("Description : " + e.getDescription());
                            info.setDate("Date : " + e.getDate());
                            info.setContact(e.getContact());
                            info.setLocation(e.getLocation());
                            info.setType(e.getType());
                            info.setCategory(e.getCategory());

                            myLatLng = latlng;

                            if(e.getType().equals("FOUND")){
                                Marker m;
                                if(e.getCategory().equals("hotspot"))
                                    m = mMap.addMarker(new MarkerOptions().position(latlng).title(new Integer(e.getId()).toString()).snippet(e.getType()).icon(BitmapDescriptorFactory.fromResource(R.drawable.hotspot)));
                                else if(e.getCategory().equals("group"))
                                    m = mMap.addMarker(new MarkerOptions().position(latlng).title(new Integer(e.getId()).toString()).snippet(e.getType()).icon(BitmapDescriptorFactory.fromResource(R.drawable.group)));
                                else
                                    m = mMap.addMarker(new MarkerOptions().position(latlng).title(new Integer(e.getId()).toString()).snippet(e.getType()).icon(BitmapDescriptorFactory.fromResource(R.drawable.found)));


                                m.setTag(info);
                            }else{
                                Marker m;

                                if(e.getCategory().equals("hotspot"))
                                    m = mMap.addMarker(new MarkerOptions().position(latlng).title(new Integer(e.getId()).toString()).snippet(e.getType()).icon(BitmapDescriptorFactory.fromResource(R.drawable.hotspot)));
                                else if(e.getCategory().equals("group"))
                                    m = mMap.addMarker(new MarkerOptions().position(latlng).title(new Integer(e.getId()).toString()).snippet(e.getType()).icon(BitmapDescriptorFactory.fromResource(R.drawable.group)));
                                else
                                    m = mMap.addMarker(new MarkerOptions().position(latlng).title(new Integer(e.getId()).toString()).snippet(e.getType()).icon(BitmapDescriptorFactory.fromResource(R.drawable.lost)));

                                m.setTag(info);
                            }



                            bounds.include(new LatLng(Double.parseDouble(f[0]), Double.parseDouble(f[1])));

                            reportID = e.getId();

                            myreportid = reportID;
                        }

                    }

                    if(myLatLng == null){
                        return;
                    }


                    int counters = 0;

                    for(Report e: reports){

                        boolean bCont = false;
                        for(int f : collect){
                            if(counters == f)
                                bCont = true;
                        }

                        if(bCont)
                            continue;

                        String[] f = e.getLocation().split(",");
                        LatLng latlng = new LatLng(Double.parseDouble(f[0]), Double.parseDouble(f[1]));
                        if(e.getUserId() == Integer.parseInt(accounts[0].name)){

                        }else if(distance(myLatLng.latitude,myLatLng.longitude,latlng.latitude,latlng.longitude,'K') <= iRadius){

                            if(e.getCategory() == null)
                                e.setCategory("");

                            Report info = new Report();
                            info.setSubject("Subject : " + e.getSubject() );
                            info.setDescription("Description : " + e.getDescription());
                            info.setDate("Date : " + e.getDate());
                            info.setContact(e.getContact());
                            info.setLocation(e.getLocation());
                            info.setType(e.getType());
                            info.setCategory(e.getCategory());

                            if(e.getType().equals("FOUND")){
                                Marker m;
                                if(e.getCategory().equals("hotspot"))
                                    m = mMap.addMarker(new MarkerOptions().position(latlng).title(new Integer(e.getId()).toString()).snippet(e.getType()).icon(BitmapDescriptorFactory.fromResource(R.drawable.hotspot)));
                                else if(e.getCategory().equals("group"))
                                    m = mMap.addMarker(new MarkerOptions().position(latlng).title(new Integer(e.getId()).toString()).snippet(e.getType()).icon(BitmapDescriptorFactory.fromResource(R.drawable.group)));
                                else
                                    m = mMap.addMarker(new MarkerOptions().position(latlng).title(new Integer(e.getId()).toString()).snippet(e.getType()).icon(BitmapDescriptorFactory.fromResource(R.drawable.found)));


                                m.setTag(info);
                            }else{
                                Marker m;

                                if(e.getCategory().equals("hotspot"))
                                    m = mMap.addMarker(new MarkerOptions().position(latlng).title(new Integer(e.getId()).toString()).snippet(e.getType()).icon(BitmapDescriptorFactory.fromResource(R.drawable.hotspot)));
                                else if(e.getCategory().equals("group"))
                                    m = mMap.addMarker(new MarkerOptions().position(latlng).title(new Integer(e.getId()).toString()).snippet(e.getType()).icon(BitmapDescriptorFactory.fromResource(R.drawable.group)));
                                else
                                    m = mMap.addMarker(new MarkerOptions().position(latlng).title(new Integer(e.getId()).toString()).snippet(e.getType()).icon(BitmapDescriptorFactory.fromResource(R.drawable.lost)));

                                m.setTag(info);
                            }



                            bounds.include(new LatLng(Double.parseDouble(f[0]), Double.parseDouble(f[1])));

                            doBounds = true;
                        }

                        if(reportID == e.getIsettle()){
                            badgeCount += 1;
                        }

                        counters += 1;
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

        public void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight() * 8;
                totalHeight += 100;
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();
        }


        public Double distance(double lat1, double lon1, double lat2, double lon2, char unit){
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
            System.out.println("Distance " + dist);
            return dist;
        }

        public double deg2rad(double deg){
            return (deg * Math.PI / 180.0);
        }



        public double rad2deg(double rad){
            return rad / Math.PI * 180.0;
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
                            //NavUtils.navigateUpFromSameTask((Activity) mContext);
                            //Intent intent = new Intent();
                            //intent.putExtra("intRadius", dummy);
                            //setResult(5, intent);
                            // finish();
                            category = "home";
                            new PopulateChartTask(context).execute();
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
