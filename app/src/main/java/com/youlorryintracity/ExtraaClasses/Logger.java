package com.youlorryintracity.ExtraaClasses;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by user_adnig on 6/6/16.
 */
public class Logger {
    private static final boolean isLoggable = false;

    public static void makeToast(Context context, String msg) {
        if (context != null && msg != null) {
            Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public static void d(String TAG, String msg) {
        try {
            if (isLoggable)
                Log.d(TAG, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
