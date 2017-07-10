package com.example.zuzanna.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        TextView textView = (TextView) findViewById(R.id.totalSum);
        String textToDisplay = String.format("Total money spent up to date: %f", databaseHandler.getTotalSum());
        textView.setText(textToDisplay);
        databaseHandler.close();
    }

    public void onClick (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
