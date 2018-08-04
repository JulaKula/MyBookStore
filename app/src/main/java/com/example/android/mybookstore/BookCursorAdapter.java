package com.example.android.mybookstore;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.mybookstore.data.StoreContract;

class BookCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new BookCursorAdapter.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the data (in the current row pointed to by cursor) to the given
     * list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        TextView titleTextView = view.findViewById(R.id.name);
        TextView priceTextView = view.findViewById(R.id.price);
        final TextView quantityTextView = view.findViewById(R.id.quantity);
        Button sale = view.findViewById(R.id.sale_button);

        // Read the attributes from the Cursor for the current book
        String bookTitle = cursor.getString(cursor.getColumnIndex(StoreContract.BookEntry.COLUMN_PRODUCT_NAME));
        double price = cursor.getDouble(cursor.getColumnIndex(StoreContract.BookEntry.COLUMN_PRICE));
        final int quantity = cursor.getInt(cursor.getColumnIndex(StoreContract.BookEntry.COLUMN_QUANTITY));

        // Update the TextViews with the attributes for the current book
        titleTextView.setText(bookTitle);
        priceTextView.setText(String.valueOf(price));
        quantityTextView.setText(String.valueOf(quantity));

        final int idInt = cursor.getInt(cursor.getColumnIndex(StoreContract.BookEntry._ID));

        // When quantity is less than 1, sale button is not visible
        if (quantity < 1) {
            sale.setVisibility(View.INVISIBLE);
        } else {
            sale.setVisibility(View.VISIBLE);

            sale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri currentBookUri = ContentUris.withAppendedId(StoreContract.BookEntry.CONTENT_URI, idInt);
                    ContentValues values = new ContentValues();
                    values.put(StoreContract.BookEntry.COLUMN_QUANTITY, quantity - 1);
                    int rowsAffected = context.getContentResolver().update(currentBookUri, values, null, null);
                }
            });
        }
    }
}
