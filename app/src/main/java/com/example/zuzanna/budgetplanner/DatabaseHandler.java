package com.example.zuzanna.budgetplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zuzanna.budgetplanner.BudgetRecord;

/**
 * Created by Zuzanna on 02/04/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "budgetManager";

    // Contacts table name
    private static final String TABLE_BUDGET = "budget";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_COMMENT = "comment";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_BUDGET + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " INTEGER,"
                + KEY_AMOUNT + " REAL," + KEY_CATEGORY + " TEXT," + KEY_COMMENT + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGET);

        // Create tables again
        onCreate(db);
    }

    //Adding new record
    public long addRecord(BudgetRecord record) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, record.getTimestamp().getTime());
        values.put(KEY_AMOUNT, record.getAmount());
        values.put(KEY_CATEGORY, record.getCategory());
        values.put(KEY_COMMENT, record.getComment());

        // Inserting Row
        long newRow = db.insert(TABLE_BUDGET, null, values);
        db.close(); // Closing database connection
        return newRow;
    }

    public float getTotalSum() {
        String query = "SELECT " + KEY_AMOUNT + " FROM " + TABLE_BUDGET;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int column_index = cursor.getColumnIndex(KEY_AMOUNT);
        float sum = 0;
        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                sum += cursor.getFloat(column_index);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return sum;
    }
}
