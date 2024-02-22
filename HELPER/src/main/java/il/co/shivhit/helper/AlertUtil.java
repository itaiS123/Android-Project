package il.co.shivhit.helper;

import android.content.Context;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class AlertUtil {

    public static void alertOk(Context context, String title, String message, boolean cancelAble, int icon){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(cancelAble)
                .setPositiveButton("OK", null);

        if (icon != 0)
            builder.setIcon(icon);

        builder.create().show();
    }
}
