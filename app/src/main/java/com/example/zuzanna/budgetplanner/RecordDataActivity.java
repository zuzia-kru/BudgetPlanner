package com.example.zuzanna.budgetplanner;

import android.content.Intent;
import android.support.annotation.NonNull;
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
        editAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
    }

    public void recordData(View view) {

        if (!editTextContainsNumber(editAmount)) {
            warnTheUser("Amount can't be empty");
            return;
        }
        //get all the data from the input from the screen
        float amount = convertEditTextToFloat(editAmount);
        String category = obtainCategory();
        String comment = obtainComment();
        //create the com.example.zuzanna.budgetplanner.BudgetRecord
        BudgetRecord budgetRecord = new BudgetRecord(new Timestamp(Calendar.getInstance().getTimeInMillis()), amount, category, comment);
        long newRowid = insertBudgetRecordOnDatabase(budgetRecord);
        startActivity(createIntent(newRowid));
    }

    private boolean editTextContainsNumber(EditText editText) {
        try {
            convertEditTextToFloat(editText);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private Float convertEditTextToFloat(EditText editAmount) {
        return Float.valueOf(editAmount.getText().toString());
    }

    private void warnTheUser(String text) {
        /** For upper Android versions, Snackbars are awesome!
         * Snackbar.make(view, "Amount can't be empty", Snackbar.LENGTH_SHORT).show();
         */
        Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
    }
    @NonNull
    private String obtainComment() {
        EditText editComment = (EditText) findViewById(R.id.edit_comment);
        return editComment.getText().toString();
    }

    @NonNull
    private String obtainCategory() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup_container);
        RadioButton radioButtonSelected = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        return radioButtonSelected.getText().toString();
    }

    private long insertBudgetRecordOnDatabase(BudgetRecord budgetRecord) {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        long newRowid = databaseHandler.addRecord(budgetRecord);
        databaseHandler.close();
        return newRowid;
    }

    @NonNull
    private Intent createIntent(long newRowid) {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(EXTRA_ROW_ID, newRowid);
        return intent;
    }

    public class DecimalDigitsInputFilter implements InputFilter {

        Pattern mPattern;

        public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
            mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }

    }
}
