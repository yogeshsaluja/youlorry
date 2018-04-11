package com.youlorryintracity.ExtraaClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

/**
 * Created by user_adnig on 6/6/16.
 */
public class Helper {
    private static SharedPreferences sharedPreferences;
    private static int screenSize = 0;

    public static SharedPreferences getSharedPref(Context context) {
        if (context != null && sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(Constant.MYPREFRENCES, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static int getScreen(Context context) {
        if (screenSize == 0)
            screenSize = getScreenSize(context);
        return screenSize;
    }

    private static int getScreenSize(Context applicationContext) {
        int screenSize;
        int screenLayout = applicationContext.getResources().getConfiguration().screenLayout;
        if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
//			toaster(applicationContext, "LARGE");
            screenSize = Configuration.SCREENLAYOUT_SIZE_LARGE;
        } else if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
//			toaster(applicationContext, "XLARGE");
            screenSize = Configuration.SCREENLAYOUT_SIZE_XLARGE;
        } else if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
//			toaster(applicationContext, "SMALL");
            screenSize = Configuration.SCREENLAYOUT_SIZE_SMALL;
        } else if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
//			toaster(applicationContext, "NORMAL");
            screenSize = Configuration.SCREENLAYOUT_SIZE_NORMAL;
        } else {
            screenSize = Configuration.SCREENLAYOUT_SIZE_UNDEFINED;
        }

        return screenSize;
    }
}
