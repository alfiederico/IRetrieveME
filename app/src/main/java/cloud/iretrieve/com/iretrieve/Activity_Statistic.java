package cloud.iretrieve.com.iretrieve;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import org.joda.time.DateTime;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cloud.iretrieve.com.iretrieve.custom.DayAxisValueFormatter;
import cloud.iretrieve.com.iretrieve.custom.MyAxisValueFormatter;
import cloud.iretrieve.com.iretrieve.custom.XYMarkerView;
import cloud.iretrieve.com.iretrieve.domain.History;
import cloud.iretrieve.com.iretrieve.model.LineSet;

public class Activity_Statistic extends Activity {

    private static final String SERVICE_URL = "http://192.168.254.3:8089";
    Context context = null;
    PieChart mPieChart;
    LineChart mChart1;
    BarChart mBarChart;
    ArrayList<Integer> colors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity__statistic);

            context = this;


            colors = new ArrayList<Integer>();

            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.LIBERTY_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.PASTEL_COLORS)
                colors.add(c);

            colors.add(ColorTemplate.getHoloBlue());

            mPieChart = (PieChart) findViewById(R.id.chart1);

            mPieChart.setUsePercentValues(true);

            mPieChart.setExtraOffsets(5, 10, 5, 5);

            mPieChart.setDragDecelerationFrictionCoef(0.95f);

            mPieChart.setDescription(null);

            //Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
            // mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
            mPieChart.setCenterText(generateCenterSpannableText("Lost vs Found"));

            mPieChart.setDrawHoleEnabled(true);

            mPieChart.setTransparentCircleColor(Color.WHITE);
            mPieChart.setTransparentCircleAlpha(110);



            mPieChart.setHoleRadius(58f);
            mPieChart.setTransparentCircleRadius(61f);

            mPieChart.setDrawCenterText(true);

            mPieChart.setRotationAngle(0);
            // enable rotation of the chart by touch
            mPieChart.setRotationEnabled(true);
            mPieChart.setHighlightPerTapEnabled(true);





            mChart1 = (LineChart) findViewById(R.id.linechart1);




            mChart1.setDrawGridBackground(false);


            // no description text
            mChart1.getDescription().setEnabled(false);


            // enable touch gestures
            mChart1.setTouchEnabled(true);


            // enable scaling and dragging
            mChart1.setDragEnabled(true);
            mChart1.setScaleEnabled(true);
            ;
            // mChart.setScaleXEnabled(true);
            // mChart.setScaleYEnabled(true);

            // if disabled, scaling can be done on x- and y-axis separately
            mChart1.setPinchZoom(true);


            mChart1.getAxisRight().setEnabled(false);


            mBarChart = (BarChart) findViewById(R.id.chartbar);

           // mBarChart.setOnChartValueSelectedListener(context);

            mBarChart.setDrawBarShadow(false);
            mBarChart.setDrawValueAboveBar(true);

            mBarChart.getDescription().setEnabled(false);

            // if more than 60 entries are displayed in the chart, no values will be
            // drawn
            mBarChart.setMaxVisibleValueCount(60);

            // scaling can now only be done on x- and y-axis separately
            mBarChart.setPinchZoom(false);

            mBarChart.setDrawGridBackground(false);
            // mChart.setDrawYLabels(false);

            IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mBarChart);

            XAxis xAxis = mBarChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            //xAxis.setTypeface(mTfLight);
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(1f); // only intervals of 1 day
            xAxis.setLabelCount(6);
            xAxis.setValueFormatter(xAxisFormatter);

            IAxisValueFormatter custom = new MyAxisValueFormatter();

            YAxis leftAxis = mBarChart.getAxisLeft();
           // leftAxis.setTypeface(mTfLight);
            leftAxis.setLabelCount(6, false);
            leftAxis.setValueFormatter(custom);
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            leftAxis.setSpaceTop(15f);
            leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

            YAxis rightAxis = mBarChart.getAxisRight();
            rightAxis.setDrawGridLines(false);
            //rightAxis.setTypeface(mTfLight);
            rightAxis.setLabelCount(6, false);
            rightAxis.setValueFormatter(custom);
            rightAxis.setSpaceTop(15f);
            rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

            /**Legend l = mBarChart.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setForm(Legend.LegendForm.SQUARE);
            l.setFormSize(9f);
            l.setTextSize(11f);
            l.setXEntrySpace(4f);**/
            // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
            // "def", "ghj", "ikl", "mno" });
            // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
            // "def", "ghj", "ikl", "mno" });

            XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
            mv.setChartView(mBarChart); // For bounds control
            mBarChart.setMarker(mv); // Set the marker to the chart



            new GetHistoryTask(context).execute();







        }catch(Exception ex){
            showMessage(ex.getMessage());
        }
    }

    private void setData(int count, int range, History[] histories ) {

        float start = 0f;

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        String[] mcategory = new String[]{
                "Device", "ID", "Key", "Money", "Pet", "Wallet", "Others"
        };


        for (int i = (int) start; i < start + count ; i++) {
            int mult = (range);
            int val = 0;

            for(History e: histories){
                if(e.getType().toUpperCase().equals("LOST")){
                    System.out.println(e.getSubject().toUpperCase() + " == " + mcategory[i].toUpperCase() );
                    if(e.getSubject().toUpperCase().equals(mcategory[i].toUpperCase())){
                        val += 1;
                    }
                }

            }

            yVals1.add(new BarEntry(i, val));
        }

        BarDataSet set1;

        if (mBarChart.getData() != null &&
                mBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Lost & found per category");

            set1.setDrawIcons(false);

            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            //data.setValueTypeface(mTfLight);
            data.setBarWidth(0.9f);

            mBarChart.setData(data);
        }

        mBarChart.invalidate();
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

                final String url = SERVICE_URL + "/mobile/historystats?id=0";

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
                    builder.setMessage("Report item not found. Report item first.. Please try again.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            NavUtils.navigateUpFromSameTask((Activity) mContext);
                        }
                    });
                    builder.show();
                }else{

                    ArrayList<ILineDataSet> dataSets1 = new ArrayList<ILineDataSet>();

                    int isettle = 0;
                    ArrayList<Entry> values = new ArrayList<Entry>();
                    for (int i = 0; i < 12; i++) {
                        int counter = 0;
                        for(History e: histories){

                            try {
                                String[] date = e.getDate().split("-");

                                if(Integer.parseInt(date[1]) == (i + 1) && e.getType().equals("LOST")){
                                    counter = counter + 1;
                                    isettle += 1;
                                }

                            } catch (Exception exc) {

                            }
                        }

                        values.add(new Entry(i, counter));
                    }

                    LineDataSet set1 = new LineDataSet(values,"# of Settle per month");
                    set1.setDrawIcons(false);

                    // set the line to be drawn like this "- - - - - -"
                    set1.enableDashedLine(10f, 5f, 0f);
                    set1.enableDashedHighlightLine(10f, 5f, 0f);
                    set1.setColor(colors.get(0));
                    set1.setCircleColor(colors.get(0));
                    set1.setLineWidth(1f);
                    set1.setCircleRadius(3f);
                    set1.setDrawCircleHole(false);
                    set1.setValueTextSize(9f);
                    set1.setDrawFilled(true);
                    set1.setFormLineWidth(1f);
                    set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
                    set1.setFormSize(15.f);


                    set1.setFillColor(Color.BLACK);

                    dataSets1.add(set1); // add the datasets



                    Description desc = new Description();
                    desc.setText("X = Month, Y = Settle");
                    // create a data object with the datasets
                    LineData data1 = new LineData(dataSets1);
                    mChart1.setData(data1);
                    mChart1.setDescription(desc);
                    mChart1.invalidate();


                    int iLost = 0;
                    for(History e: histories){

                        try {
                            if(e.getType().equals("-LOST")){
                                iLost = iLost + 1;
                            }

                        } catch (Exception exc) {

                        }
                    }

                    ArrayList<PieEntry> yVals1 = new ArrayList<PieEntry>();
                    int iTot = isettle + iLost;

                    if(iLost == 0){
                        if(iTot > 0){
                            if(iLost > 0){
                                yVals1.add(new PieEntry((iTot / iLost) * 100, "Lost"));
                            }else{
                                yVals1.add(new PieEntry(0, "Lost"));
                            }

                            if(isettle > 0){
                                yVals1.add(new PieEntry((iTot / isettle) * 100, "Found"));
                            }else{
                                yVals1.add(new PieEntry(0, "Found"));
                            }
                        }else{
                            yVals1.add(new PieEntry(0, "Lost"));
                            yVals1.add(new PieEntry(0, "Found"));
                        }
                    }else{
                        if(iTot > 0){
                            if(iLost > 0){
                                yVals1.add(new PieEntry((iTot / iLost) * 100, "Found"));
                            }else{
                                yVals1.add(new PieEntry(0, "Found"));
                            }

                            if(isettle > 0){
                                yVals1.add(new PieEntry((iTot / isettle) * 100, "Lost"));
                            }else{
                                yVals1.add(new PieEntry(0, "Lost"));
                            }
                        }else{
                            yVals1.add(new PieEntry(0, "Lost"));
                            yVals1.add(new PieEntry(0, "Found"));
                        }
                    }

                    PieDataSet dataSet = new PieDataSet(yVals1,"");
                    dataSet.setSliceSpace(2f);
                    dataSet.setSelectionShift(5f);

                    //ArrayList<Integer> colors1 = new ArrayList<Integer>();
                    ////colors1.add(Color.GREEN);
                    //colors1.add(Color.RED);

                    dataSet.setColors(colors);


                    PieData data = new PieData(dataSet);
                    data.setValueFormatter(new PercentFormatter());
                    data.setValueTextSize(11f);
                    data.setValueTextColor(Color.parseColor("#26A69A"));

                    Description piedesc = new Description();
                    piedesc.setText(new Integer(iTot).toString() + " total items");
                    mPieChart.setDescription(piedesc);

                    //data.setValueTypeface(tf);
                    mPieChart.setData(data);

                    Legend l = mPieChart.getLegend();
                    l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
                    l.setXEntrySpace(7f);
                    l.setYEntrySpace(0f);
                    l.setYOffset(0f);

                    mPieChart.invalidate();


                    setData(7, isettle, histories);




                }
            }catch(Exception ex){
               // showMessage(ex.toString());
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
