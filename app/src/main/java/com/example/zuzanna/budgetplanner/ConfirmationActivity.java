package com.example.zuzanna.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Intent intent = getIntent();
        long addedRowId = intent.getLongExtra(RecordDataActivity.EXTRA_ROW_ID, -1);
        String confirmationMessage = "Thank you! You just added your spending record number " + addedRowId + ". Keep up the work to get meaningful reports.";
        TextView textView = (TextView) findViewById(R.id.confirmationText);
        textView.setText(confirmationMessage);
    }

    public void onClick (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
