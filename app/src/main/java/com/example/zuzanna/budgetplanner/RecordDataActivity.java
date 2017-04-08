package com.example.zuzanna.budgetplanner;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.sql.Timestamp;
import java.util.Calendar;

public class RecordDataActivity extends AppCompatActivity {

    public static final String EXTRA_ROW_ID = "com.example.zuzanna.budgetplanner.newRowId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_data);
    }

    public void recordData(View view) {
        //get all the data from the input from the screen
        EditText editAmount = (EditText) findViewById(R.id.edit_amount_to_record);
        float amount = Float.valueOf(editAmount.getText().toString());

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup_container);
        RadioButton radioButtonSelected = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        String category = radioButtonSelected.getText().toString();

        EditText editComment = (EditText) findViewById(R.id.edit_comment);
        String comment = editComment.getText().toString();
        //create the com.example.zuzanna.budgetplanner.BudgetRecord
        BudgetRecord budgetRecord = new BudgetRecord(new Timestamp(Calendar.getInstance().getTimeInMillis()), amount, category, comment);
        //insert into the DB
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        long newRowid = databaseHandler.addRecord(budgetRecord);
        databaseHandler.close();

        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(EXTRA_ROW_ID, newRowid);
        startActivity(intent);

    }
}
