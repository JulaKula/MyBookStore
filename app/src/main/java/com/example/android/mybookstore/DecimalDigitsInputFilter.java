package com.example.android.mybookstore;

import android.text.Spanned;
import android.text.method.DigitsKeyListener;

/**
 * I found this solution at: https://gist.github.com/gaara87/3607765
 * Solution presented by @jjcampac
 * <p>
 * Input filter that limits the number of decimal digits that are allowed to be entered.
 */
public class DecimalDigitsInputFilter extends DigitsKeyListener {

    private final int beforeDecimal;
    private final int afterDecimal;

    public DecimalDigitsInputFilter(int beforeDecimal, int afterDecimal) {
        super(Boolean.FALSE, Boolean.TRUE);
        this.beforeDecimal = beforeDecimal;
        this.afterDecimal = afterDecimal;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        StringBuilder builder = new StringBuilder(dest);
        builder.insert(dstart, source);
        String temp = builder.toString();

        if (temp.equals(".")) {
            return "0.";
        } else if (temp.indexOf('.') == -1) {
            if (temp.length() > beforeDecimal) {
                return "";
            }
        } else {
            if (temp.substring(0, temp.indexOf('.')).length() > beforeDecimal || temp.substring(temp.indexOf('.') + 1, temp.length()).length() > afterDecimal) {
                return "";
            }
        }

        return super.filter(source, start, end, dest, dstart, dend);
    }

}
