package com.youlorryintracity.NotificationClasses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.youlorryintracity.ExtraaClasses.Constant;
import com.youlorryintracity.ExtraaClasses.Helper;

/**
 * Created by hp on 1/9/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
SharedPreferences sharedPreferences;
String USER;
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            sharedPreferences= Helper.getSharedPref(getApplicationContext());
            USER=sharedPreferences.getString(Constant.KEY_USER_LOGIN,"");

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {


    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref =  Helper.getSharedPref(getApplicationContext());
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constant.AUTH_VALUE, token);
        editor.commit();
    }



}