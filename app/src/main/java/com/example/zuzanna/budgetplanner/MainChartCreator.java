package com.example.zuzanna.budgetplanner;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Zuzanna on 25/06/2017.
 */

public class MainChartCreator {

    private BarChart barChart;
    private TextView lastWeek;
    private DatabaseHandler databaseHandler;
    final static private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.ENGLISH);

    public MainChartCreator() {
    }

    public MainChartCreator(BarChart barChart, TextView lastWeek, DatabaseHandler databaseHandler) {
        this.barChart = barChart;
        this.lastWeek = lastWeek;
        this.databaseHandler = databaseHandler;
    }

    public void invoke() {
        Map<String, String> lastWeekSpendings = databaseHandler.getLastSevenDays();

        if (lastWeekSpendings != null && lastWeekSpendings.size() != 0) {
            lastWeek.setVisibility(View.VISIBLE);
            configureChartsLook();
            final List<String> lastWeekDates = getDatesAxis(7); //todo make all related methods parameterized too
            List<BarEntry> entries = getBarEntries(lastWeekSpendings, lastWeekDates);
            BarData dataChart = configureDataToBeDisplayed(entries);
            setXAxisValues(lastWeekDates);

            barChart.setData(dataChart);
        } else {
            lastWeek.setVisibility(View.INVISIBLE);
            barChart.setVisibility(View.INVISIBLE);
        }
    }

    private void setXAxisValues(final List<String> lastWeekDates) {
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return lastWeekDates.get((int) value);
            }
        });
    }

    @NonNull
    private BarData configureDataToBeDisplayed(List<BarEntry> entries) {
        BarDataSet spendings = new BarDataSet(entries, "Last 7 days spendings");
        List<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add((IBarDataSet) spendings);
        BarData dataChart = new BarData(dataSets);
        spendings.setColors(ColorTemplate.COLORFUL_COLORS);
        return dataChart;
    }

    @NonNull
    private List<BarEntry> getBarEntries(Map<String, String> lastWeekSpendings, List<String> lastWeekDates) {
        List<BarEntry> entries = new ArrayList<>();
        for (int columnPosition = 0; columnPosition < lastWeekDates.size(); columnPosition++) {
            String dateForChart = lastWeekDates.get(columnPosition);
            if (lastWeekSpendings.containsKey(dateForChart)) {
                entries.add(new BarEntry(columnPosition, Float.parseFloat(lastWeekSpendings.get(dateForChart))));
            } else {
                entries.add(new BarEntry(columnPosition, 0));
            }
        }
        return entries;
    }

    private void configureChartsLook() {
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(false);
        barChart.animateXY(3000, 3000);
        barChart.setHorizontalScrollBarEnabled(true);
        barChart.setDoubleTapToZoomEnabled(true);
        barChart.setDrawBarShadow(false);
        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
    }

    @NonNull
    public List<String> getDatesAxis(int daysBack) {
        final List<String> lastWeekDates = new ArrayList<>();
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, -daysBack);
        for (int i = 0; i < daysBack; i++) {
            cal.add(Calendar.DATE, 1);
            lastWeekDates.add(dateFormat.format(cal.getTime()));
        }
        return lastWeekDates;
    }
}
