package com.example.android.mybookstore.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BookDbHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "bookStore.db";
    private final static int DB_VERSION = 1;


    public BookDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDb) {
        String SQL_CREATE_BOOK_TABLE = "CREATE TABLE " + StoreContract.BookEntry.TABLE_NAME + " ("
                + StoreContract.BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + StoreContract.BookEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + StoreContract.BookEntry.COLUMN_PRICE + " INTEGER NOT NULL, "
                // price is saved as an integer, when displaying should be divided by 100
                + StoreContract.BookEntry.COLUMN_QUANTITY + " INTEGER NOT NULL, "
                + StoreContract.BookEntry.COLUMN_SUPPLIER_NAME + " TEXT, "
                + StoreContract.BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER + " TEXT);";

        // Execute the SQL statement
        sqLiteDb.execSQL(SQL_CREATE_BOOK_TABLE);

        Log.v("BookDbHelper", "create statement" + SQL_CREATE_BOOK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
