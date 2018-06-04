package com.example.android.mybookstore;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.mybookstore.data.BookDbHelper;
import com.example.android.mybookstore.data.StoreContract;

public class MainActivity extends AppCompatActivity {

    BookDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        dbHelper = new BookDbHelper(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertBook();
                displayDbInfo();
            }
        });
        insertBook();
        displayDbInfo();
    }

    private void displayDbInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(StoreContract.BookEntry.TABLE_NAME, null, null,
                null, null, null, null);

        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // book table in the database).
            TextView displayView = findViewById(R.id.text_view);
            displayView.setText(getString(R.string.book_contains) + cursor.getCount() + getString(R.string.books));

            displayView.append(StoreContract.BookEntry._ID + " - "
                    + StoreContract.BookEntry.COLUMN_PRODUCT_NAME + " - "
                    + StoreContract.BookEntry.COLUMN_PRICE + " - "
                    + StoreContract.BookEntry.COLUMN_QUANTITY + " - "
                    + StoreContract.BookEntry.COLUMN_SUPPLIER_NAME + " - "
                    + StoreContract.BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER + "\n");

            while (cursor.moveToNext()) {

                displayView.append(cursor.getInt(cursor.getColumnIndex(StoreContract.BookEntry._ID)) + " - "
                        + cursor.getString(cursor.getColumnIndex(StoreContract.BookEntry.COLUMN_PRODUCT_NAME)) + " - $"
                        + cursor.getDouble(cursor.getColumnIndex(StoreContract.BookEntry.COLUMN_PRICE)) / 100 + " - "
                        // price is divided by 100 to display proper price with cents
                        + cursor.getInt(cursor.getColumnIndex(StoreContract.BookEntry.COLUMN_QUANTITY)) + " - "
                        + cursor.getString(cursor.getColumnIndex(StoreContract.BookEntry.COLUMN_SUPPLIER_NAME)) + " - "
                        + cursor.getString(cursor.getColumnIndex(StoreContract.BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER))
                        + "\n");
            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private void insertBook() {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(StoreContract.BookEntry.COLUMN_PRODUCT_NAME, "Dinosaurs A-Z");
        values.put(StoreContract.BookEntry.COLUMN_PRICE, 1699);
        values.put(StoreContract.BookEntry.COLUMN_QUANTITY, 23);
        values.put(StoreContract.BookEntry.COLUMN_SUPPLIER_NAME, "Best Books Ever");
        values.put(StoreContract.BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER, "123 456 789");

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(StoreContract.BookEntry.TABLE_NAME, null, values);

        Log.v("MainActivity", "new row ID: " + newRowId);
    }
}
