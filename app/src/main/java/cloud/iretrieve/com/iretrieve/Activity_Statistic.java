package cloud.iretrieve.com.iretrieve;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cloud.iretrieve.com.iretrieve.model.LineSet;

public class Activity_Statistic extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity__statistic);

            ArrayList<Integer> colors = new ArrayList<Integer>();

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

            PieChart mPieChart = (PieChart) findViewById(R.id.chart1);

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

            ArrayList<PieEntry> yVals1 = new ArrayList<PieEntry>();

            yVals1.add(new PieEntry(30, "Lost"));
            yVals1.add(new PieEntry(70, "Found"));


            PieDataSet dataSet = new PieDataSet(yVals1,"");
            dataSet.setSliceSpace(2f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(colors);

            PieData data = new PieData(dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(Color.parseColor("#26A69A"));
            //data.setValueTypeface(tf);
            mPieChart.setData(data);
            Description piedesc = new Description();
            piedesc.setText("2018");
            mPieChart.setDescription(piedesc);

            Legend l = mPieChart.getLegend();
            l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(0f);
            l.setYOffset(0f);


            LineChart mChart1 = (LineChart) findViewById(R.id.linechart1);




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






            ArrayList<ILineDataSet> dataSets1 = new ArrayList<ILineDataSet>();
            dataSets1.add(getLineSet("2018",colors.get(0))); // add the datasets



            Description desc = new Description();
            desc.setText("X = Lost, Y = Found");
            // create a data object with the datasets
            LineData data1 = new LineData(dataSets1);
            mChart1.setData(data1);
            mChart1.setDescription(desc);



        }catch(Exception ex){
            showMessage(ex.getMessage());
        }
    }

    private LineDataSet getLineSet(String f, Integer g){
        ArrayList<Entry> values = new ArrayList<Entry>();
        for (int i = 0; i < 12; i++) {

            float val = (float) (Math.random() * 100) + 3;
            values.add(new Entry(i, val));
        }

        LineDataSet set1 = new LineDataSet(values,f);
        set1.setDrawIcons(false);

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(g);
        set1.setCircleColor(g);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);
        set1.setFormLineWidth(1f);
        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);


        set1.setFillColor(Color.BLACK);

        return set1;
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

}
