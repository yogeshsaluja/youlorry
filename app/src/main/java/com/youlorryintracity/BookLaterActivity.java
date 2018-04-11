package com.youlorryintracity;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.youlorryintracity.ExtraaClasses.Constant;
import com.youlorryintracity.NotificationClasses.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class BookLaterActivity extends AppCompatActivity {
TextView PickUp,DropCity,Booking,TimePickUp;
public GregorianCalendar gregorianCalendar;
String selecteddate,PickupLoc,DropLoc;
Constant.TransitionType type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_later);
        PickUp=(TextView)findViewById(R.id.pickup);
        DropCity=(TextView)findViewById(R.id.drop);
        Booking=(TextView)findViewById(R.id.btnbooking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Booking Details");
        TimePickUp=(TextView)findViewById(R.id.timepick);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calender); // get the reference of CalendarView
          selecteddate = String.valueOf(calendarView.getDate());
        final Calendar c = Calendar.getInstance();
        long currentTime = System.currentTimeMillis();
        Toast.makeText(this, ""+calendarView.getDate(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "new"+currentTime, Toast.LENGTH_SHORT).show();
        calendarView.setMinDate(calendarView.getDate());

        Bundle bundle =getIntent().getExtras();
        PickupLoc=bundle.getString("pickup");
         DropLoc=bundle.getString("drop");
        PickUp.setText(PickupLoc);
        DropCity.setText(DropLoc);

           initPage();
            initAnimation();

 Booking.setOnClickListener(new View.OnClickListener() {

     @Override
     public void onClick(View view) {
         if (!selecteddate.isEmpty())
         {
             if (!TimePickUp.getText().toString().isEmpty()){
                // displayNotification();
                 BookingConfirmation();
             }else {
                 Toast.makeText(BookLaterActivity.this, "Select booking  Time", Toast.LENGTH_SHORT).show();
             }
         }else {
             Toast.makeText(BookLaterActivity.this, "Select date of booking", Toast.LENGTH_SHORT).show();
         }
     }
 });
        final AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build();

        PickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //PickUpLayout.setElevation(20);
                    //DropLayout.setElevation(0);
                }
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .setFilter(typeFilter)
                                    .build(BookLaterActivity.this);
                    startActivityForResult(intent, 1);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });
        TimePickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiemPicker();
            }
        });
        DropCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .setFilter(typeFilter)
                                    .build(BookLaterActivity.this);
                    startActivityForResult(intent, 2);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                place.getLatLng();
                PickUp.setText(place.getName());
                Toast.makeText(this, "" + place.getName(), Toast.LENGTH_SHORT).show();
               } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.


            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                place.getLatLng();
                 DropCity.setText(place.getName());
                Toast.makeText(this, "" + place.getName(), Toast.LENGTH_SHORT).show();
             } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.


            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
    private void tiemPicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        final int[] mHour = {c.get(Calendar.HOUR_OF_DAY)};
        final int[] mMinute = {c.get(Calendar.MINUTE)};
        final int[] ampm = {c.get(Calendar.AM_PM)};

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(BookLaterActivity.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour[0] = hourOfDay;
                        mMinute[0] = minute;
                         TimePickUp.setText(hourOfDay + ":" + minute);
                    }
                }, mHour[0], mMinute[0], true);
        timePickerDialog.show();
    }
    public void BookingConfirmation()
    {
        final ProgressDialog progressDialog=new ProgressDialog(BookLaterActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://youlorry.com/wp-json/intracity/book-later", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Boolean status=jsonObject.getBoolean("status");
                    if (status)
                    {
                        progressDialog.dismiss();

                   //   sendNotification();
                     //   Notification();
                       //  startService(new Intent(BookLaterActivity.this, PollingService.class));
                         Toast.makeText(BookLaterActivity.this, "Booking Saved", Toast.LENGTH_SHORT).show();

                            onBackPressed();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public HashMap<String,String> getParams()
            {
                HashMap<String,String> hashMap=new HashMap<String, String>();
                hashMap.put("user","1234567890");
                hashMap.put("pickup",PickupLoc);
                hashMap.put("drop",DropLoc);
                hashMap.put("time",TimePickUp.getText().toString());
                hashMap.put("date",selecteddate);

                return hashMap;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public  void Notification(){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(BookLaterActivity.this);
        builder.setSmallIcon(R.drawable.ic_alarm);
        builder.setContentTitle("Notification Alert");
        builder.setContentText("Hi Yogesh");
        Intent intent=new Intent(this,Home.class);
        TaskStackBuilder taskStackBuilder=TaskStackBuilder.create(BookLaterActivity.this);
        taskStackBuilder.addParentStack(Home.class);
        taskStackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent=taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(441,builder.build());

    }
     public void sendNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.journaldev.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcherlogo));
        builder.setContentTitle("Notifications Title");
        builder.setContentText("Your notification content here.");
        builder.setSubText("Tap to view the website.");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Will display the notification in the notification bar
        notificationManager.notify(1, builder.build());
    }
    protected void displayNotification() {
        NotificationManager mNotificationManager;
        int numMessages=4;
        Log.i("Start", "notification");

   /* Invoking the default notification service */
        NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setContentTitle("New Message");
        mBuilder.setContentText("You've received new message.");
        mBuilder.setContentInfo("Hello");
        mBuilder.setTicker("New Message Alert!");
        mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
     //   mBuilder.setSmallIcon(R.drawable.ic_alarm);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
         v.vibrate(500);
   /* Increase notification number every time a new notification arrives */
        mBuilder.setNumber(++numMessages);
        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        notificationUtils.playNotificationSound();
   /* Add Big View Specific Configuration */
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        String[] events = new String[6];
        events[0] = new String("This is first line....");
        events[1] = new String("This is second line...");
        events[2] = new String("This is third line...");
        events[3] = new String("This is 4th line...");
        events[4] = new String("This is 5th line...");
        events[5] = new String("This is 6th line...");

        // Sets a title for the Inbox style big view
        inboxStyle.setBigContentTitle("Big Title Details:");

        // Moves events into the big view
        for (int i=0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }

        mBuilder.setStyle(inboxStyle);
     //   mBuilder.setContentIntent(resultPendingIntent);
   /* Creates an explicit intent for an Activity in your app */

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
       mNotificationManager.notify(12, mBuilder.build());
        PendingIntent();

    }
    public  void PendingIntent(){
        Intent resultIntent = new Intent(this, Home.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Home.class);

   /* Adds the Intent that starts the Activity to the top of the stack */
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
   /* notificationID allows you to update the notification later on. */
        final Calendar c = Calendar.getInstance();
        long currentTime = System.currentTimeMillis();
        long oneMinute = 30 * 1000;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, currentTime+oneMinute,resultPendingIntent );
    }
    private void initPage() {
        type=(Constant.TransitionType)getIntent().getSerializableExtra(Constant.KEY_AUTH_TYPE);

        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setTitle("Exode animation");
    }
    private void initAnimation(){
        switch (type){
            case ExplodeJava:
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Explode enterexplode=new Explode( );
                    enterexplode.setDuration(getResources().getInteger(R.integer.long_run));
                    getWindow().setEnterTransition(enterexplode);
                    break;
                }
            case SlideJava:
                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
                {
                    Slide slide=new Slide();
                    slide.setDuration(getResources().getInteger(R.integer.long_run));
                    slide.setSlideEdge(Gravity.LEFT);
                    getWindow().setEnterTransition(slide);
                }

        }
    }

}
