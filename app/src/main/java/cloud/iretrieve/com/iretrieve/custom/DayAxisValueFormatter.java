package cloud.iretrieve.com.iretrieve.custom;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by Alfie on 06/03/18.
 */

public class DayAxisValueFormatter implements IAxisValueFormatter
{

    String[] mcategory = new String[]{
            "Device", "ID", "Key", "Money", "Pet", "Wallet", "Others"
    };

    private BarLineChartBase<?> chart;

    public DayAxisValueFormatter(BarLineChartBase<?> chart) {
        this.chart = chart;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        int days = (int) value;

        return mcategory[days];
    }


}