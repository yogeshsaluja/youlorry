package com.youlorryintracity.ExtraaClasses;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.youlorryintracity.Home;
import com.youlorryintracity.R;

import java.util.Random;

/**
 * Created by hp on 2/13/2018.
 */
public class PollingService extends IntentService {

    private static final String TAG = "PollingService";
    private static final Random random = new Random();
    public static final String INTENT_KEY = "THE_QUOTE";
    public static final String INTENT_KEY_2 = "INTENT_KEY_2";

    public PollingService() {
        super(TAG);
    }

    @Override
    public void onHandleIntent(Intent intent) {
        Log.i(TAG, "ENTERED onHandleIntent");
        sendNotification();
    }

    public void sendNotification() {
        Log.i(TAG, "ENTERED sendNotification");

        String randomQuote = getRandomQuote();
        Log.i(TAG, "QUOTE: " + randomQuote);

        Intent showFullQuoteIntent = new Intent(this, Home.class);
        showFullQuoteIntent.putExtra(INTENT_KEY, randomQuote);

        // from stackoverflow.com/questions/11551195/intent-from-notification-does-not-have-extras
        //showFullQuoteIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // stackoverflow.com/questions/7370324/notification-passes-old-intent-extras/9330144
        // @see http://developer.android.com/reference/android/app/PendingIntent.html#FLAG_UPDATE_CURRENT

        // both of these approaches now work: FLAG_CANCEL, FLAG_UPDATE; the uniqueInt may be the real solution.
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, uniqueInt, showFullQuoteIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, uniqueInt, showFullQuoteIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(randomQuote)
                .setSmallIcon(android.R.drawable.ic_menu_view)
                .setContentTitle(randomQuote)
                .setContentText(randomQuote)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    private String getRandomQuote() {
        Resources res = getResources();
        String[] quotes = res.getStringArray(R.array.truckslist);
        int randomIndex = random.nextInt(quotes.length);
        String randomQuote = quotes[randomIndex];
        return randomQuote;
    }

}