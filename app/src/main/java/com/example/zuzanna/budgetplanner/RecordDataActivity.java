package com.example.zuzanna.budgetplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecordDataActivity extends AppCompatActivity {

    public static final String EXTRA_ROW_ID = "com.example.zuzanna.budgetplanner.newRowId";
    EditText editAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_data);
        editAmount = (EditText) findViewById(R.id.edit_amount_to_record);
        // Limits amount value to ########.## or ##########
        editAmount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(10,2)});
    }

    public void recordData(View view) {
        //get all the data from the input from the screen
        float amount;
        try{
            // Let's check first if value was empty to avoid crash
            amount = Float.valueOf(editAmount.getText().toString());
        }catch(NumberFormatException e){
            // If exception arises, stop further execution
            // and warn the user
            Toast.makeText(getBaseContext(), "Amount can't be empty", Toast.LENGTH_SHORT).show();
            /** For upper Android versions, Snackbars are awesome!
             * Snackbar.make(view, "Amount can't be empty", Snackbar.LENGTH_SHORT).show();
             */
            return;
        }

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

    public class DecimalDigitsInputFilter implements InputFilter {

        Pattern mPattern;

        public DecimalDigitsInputFilter(int digitsBeforeZero,int digitsAfterZero) {
            mPattern=Pattern.compile("[0-9]{0," + (digitsBeforeZero-1) + "}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher=mPattern.matcher(dest);
            if(!matcher.matches())
                return "";
            return null;
        }

    }
}
