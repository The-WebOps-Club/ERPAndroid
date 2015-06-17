package org.saarang.erp.Utils;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * Created by Ahammad on 16/06/15.
 */
public class UIUtils {

    public static void showSnackBar(View view, String message){

        Snackbar snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        View snackView = snackBar.getView();
        TextView tv = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackBar.show();

    }
}
