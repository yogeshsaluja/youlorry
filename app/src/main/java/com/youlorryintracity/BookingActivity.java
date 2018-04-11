package com.youlorryintracity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.youlorryintracity.ExtraaClasses.Global;
import com.taishi.flipprogressdialog.FlipProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener {

    TextView DName,DCont,PickUp,Drop,Booking,Time,Distance,TruckType,Price,Home,Track;
    String pick="",drop,time,dist,type,driver_number,driver_name,TruckNo,Id;
    FlipProgressDialog dialog;
    LinearLayout DoneLayout,MainLayout;
    Animation animation;
    TextView EstimateDistance,EstimeteTime;
    LatLng Origin,Destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        MainLayout=(LinearLayout)findViewById(R.id.mainlayout);
        DoneLayout=(LinearLayout)findViewById(R.id.donelayout);
        EstimateDistance=(TextView)findViewById(R.id.tv_distance);
        EstimeteTime=(TextView)findViewById(R.id.tv_time_name);
        DoneLayout.setVisibility(View.GONE);
        MainLayout.setVisibility(View.VISIBLE);
          animation = AnimationUtils.loadAnimation(this, R.anim.slide_left);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        List<Integer> imageList = new ArrayList<Integer>();
        imageList.add(R.drawable.ic_hourglass_empty_white_24dp);
        imageList.add(R.drawable.ic_hourglass_full_white_24dp);
          dialog = new FlipProgressDialog();
        dialog.setImageList(imageList);
        dialog.setOrientation("rotationY");
        dialog.setBackgroundColor(Color.parseColor("#000000"));
        dialog.setBackgroundAlpha(0.1f);
        dialog.setCornerRadius(32);
        dialog.setDimAmount(0.8f);
        dialog.setCanceledOnTouchOutside(false);



        PickUp=(TextView)findViewById(R.id.tv_pickup);
        Drop=(TextView)findViewById(R.id.tv_drop);
        Booking=(TextView)findViewById(R.id.btnbooking);

        Distance=(TextView)findViewById(R.id.tv_distance);
        Price=(TextView)findViewById(R.id.tv_price);
        Home = (TextView)findViewById(R.id.back);
        Track=(TextView)findViewById(R.id.track_tv);

        Bundle bundle=getIntent().getExtras();
      pick=  bundle.getString("pickup");
      drop=  bundle.getString("drop");
      time=  bundle.getString("time");
      dist=  bundle.getString("dist");
      type=  bundle.getString("type");
        dist=  bundle.getString("dist");
        type=  bundle.getString("type");
       PickUp.setText(pick);
       Drop.setText(drop);

       Distance.setText(dist);
       /* Origin=getLocationFromAddress(this,pick);
        Destination=getLocationFromAddress(this,drop);
        Location location1 = new Location("");
        location1.setLatitude(Origin.latitude);
        location1.setLongitude(Origin.longitude);

        Location location2 = new Location("");
        location2.setLatitude(Destination.latitude);
        location2.setLongitude(Destination.longitude);*/

       // float distanceInMeters = location1.distanceTo(location2);
        int speedIs10MetersPerMinute = 40000;
       // float estimatedDriveTimeInMinutes = distanceInMeters / speedIs10MetersPerMinute;
       // String formattedString = String.format("%.02f", estimatedDriveTimeInMinutes);
       // EstimeteTime.setText(""+formattedString+" Hour");
       // EstimateDistance.setText(""+(int) (distanceInMeters/1000)+" KM");
       Booking.setOnClickListener(this);

       Home.setOnClickListener(this);
       Track.setOnClickListener(this);



    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        if (menuItem.getItemId()==android.R.id.home){
                   finish();
        }

    return super.onOptionsItemSelected(menuItem);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnbooking:
            {
                BookingConfirmation();
            }
            break;
            case R.id.track_tv:
            {
                Global.currntbooking=1;
                Intent intent=new Intent(this,Home.class);

                startActivity(intent);
                finish();
            }
            break;
            case R.id.tv_contact_no:
            {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+DCont.getText().toString().trim()));
                startActivity(intent);
            }
            break;
            case R.id.back:
            {
                onBackPressed();
            }
            break;
        }

    }

    public void BookingConfirmation()
    {
        dialog.show(getFragmentManager(),"");

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://youlorry.com/wp-json/intracity/book", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Boolean status=jsonObject.getBoolean("status");
                    if (status)
                    {
                       driver_number=jsonObject.getString("driver_number");
                        driver_name=jsonObject.getString("driver_name");
                       TruckNo=jsonObject.getString("truck_number");
                        Id=jsonObject.getString("ID");
                        dialog.dismiss();
                        MainLayout.setVisibility(View.GONE);
                        //   DoneLayout.startAnimation(animation);
                        DoneLayout.setVisibility(View.VISIBLE);
                        getSupportActionBar().hide();

                      //  Toast.makeText(BookingActivity.this, "Booked", Toast.LENGTH_SHORT).show();



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                dialog.dismiss();

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
                hashMap.put("pickup",pick);
                hashMap.put("drop",drop);

            return hashMap;
            }

        };
         RequestQueue requestQueue= Volley.newRequestQueue(this);
         requestQueue.add(stringRequest);

    }



}
