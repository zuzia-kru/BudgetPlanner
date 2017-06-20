package com.example.zuzanna.budgetplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    BarChart barChart;
    TextView lastWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);

        ArrayList<String[]> lastWeekSpendings = databaseHandler.getLastSevenDays();
        barChart = (BarChart) findViewById(R.id.chart);
        lastWeek = (TextView) findViewById(R.id.lastWeekText);

        if(lastWeekSpendings.size()!=0){
            lastWeek.setVisibility(View.VISIBLE);

            /** Tweak the chart's look */
            barChart.setTouchEnabled(true);
            barChart.setDragEnabled(true);
            barChart.setScaleEnabled(false);
            barChart.animateXY(3000, 3000);
            barChart.setHorizontalScrollBarEnabled(true);
            barChart.setDoubleTapToZoomEnabled(true);
            barChart.setDrawBarShadow(false);
            barChart.getLegend().setEnabled(false);
            barChart.getDescription().setEnabled(false);

            /** Define X axis as dd/MM date values */
            final ArrayList<String> lastWeekDates = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
            for (int i = 6; i >= 0; i--) {
                Calendar cal = new GregorianCalendar();
                cal.add(Calendar.DAY_OF_MONTH, -i);
                lastWeekDates.add(sdf.format(cal.getTime()));
            }

            /** Define bars data */
            ArrayList<BarEntry> entries = new ArrayList<>();
            for (int d = 0; d< lastWeekDates.size(); d++){
                for (int c = 0; c < lastWeekSpendings.size(); c++){
                    String wDate = lastWeekDates.get(d);
                    String wSpending = lastWeekSpendings.get(c)[0];
                    if(wDate.equals(wSpending)){
                        entries.add(new BarEntry(d, Float.parseFloat(lastWeekSpendings.get(c)[1])));
                    } else {
                        /** BarEntry(colPos, value); */
                        entries.add(new BarEntry(d, 0));
                    }
                }
            }

            BarDataSet spendings = new BarDataSet(entries, "Last 7 days spendings");
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add((IBarDataSet) spendings);
            BarData DataChart = new BarData(dataSets);
            spendings.setColors(ColorTemplate.COLORFUL_COLORS);

            /** Set X axis values */
            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis)
                {
                    return lastWeekDates.get((int)value);
                }
            });

            barChart.setData(DataChart);
        } else {
            lastWeek.setVisibility(View.INVISIBLE);
            barChart.setVisibility(View.INVISIBLE);
        }

    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, RecordDataActivity.class);
        startActivity(intent);
    }

    public void getReport(View view) {
        Intent intent = new Intent(this, ViewReportActivity.class);
        startActivity(intent);
    }
}
