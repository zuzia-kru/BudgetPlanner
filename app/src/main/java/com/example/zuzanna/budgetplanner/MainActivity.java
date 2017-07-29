package com.example.zuzanna.budgetplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;

public class MainActivity extends AppCompatActivity {

    BarChart barChart;
    TextView lastWeek;
    EditText numberOfDaysBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getRecentSpendingsChart();

    }

    public void getRecentSpendingsChart(View view) {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        barChart = (BarChart) findViewById(R.id.chart);
        lastWeek = (TextView) findViewById(R.id.lastWeekText);
        numberOfDaysBack = (EditText) findViewById(R.id.editNumber);
        new MainChartCreator(barChart, lastWeek, databaseHandler).invoke();
    }

    /**
     * Called when the user taps the Send button
     */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, RecordDataActivity.class);
        startActivity(intent);
    }

    public void getReport(View view) {
        Intent intent = new Intent(this, ViewReportActivity.class);
        startActivity(intent);
    }
}
