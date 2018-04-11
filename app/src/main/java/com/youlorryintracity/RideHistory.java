package com.youlorryintracity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.youlorryintracity.Adapter.truckListAdapter;
import com.youlorryintracity.ExtraaClasses.Constant;
import com.youlorryintracity.ExtraaClasses.Helper;
import com.youlorryintracity.ModalClasses.BookingDataBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RideHistory extends AppCompatActivity {
RecyclerView recyclerView;
ArrayList<BookingDataBean> arrayList;
RecyclerView.Adapter adapter;
ImageView  NoData;
Constant.TransitionType type;
    String  status=" ",dname=" ",pickup=" ",drop=" ",ride_duration=" ",distance=" ",droptime=" ",totalfare=" ",pickuptime=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        arrayList=new ArrayList<BookingDataBean>();
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        NoData=(ImageView)findViewById(R.id.noo_data);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Bookings");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        initPage();
        initAnimation();

        BookingsData();

    }
@Override
public boolean  onOptionsItemSelected(MenuItem menuItem)
{
    if (menuItem.getItemId()==android.R.id.home)
    {
        finish();
    }

return super.onOptionsItemSelected(menuItem);
}
    public void BookingsData()
    {
        final ProgressDialog  progressDialog=new ProgressDialog(RideHistory.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://youlorry.com/wp-json/intracity/youlorry_booking_history", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Boolean sucess=jsonObject.getBoolean("success");
                    JSONArray  jsonArray=jsonObject.getJSONArray("data");
                        if (jsonArray.length()>0){
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject dataobject=jsonArray.getJSONObject(i);
                                status=dataobject.getString("status");
                                dname=dataobject.getString("username");
                                pickup="Guhwahti";
                                drop=dataobject.getString("destination");

                                BookingDataBean bookingDataBean=new BookingDataBean();
                                bookingDataBean.setDrop(drop);
                                bookingDataBean.setPickup(pickup);
                                bookingDataBean.setName(dname);
                                bookingDataBean.setStatus(status);
                                arrayList.add(bookingDataBean);
                               /* if (status.equals("trip_ended")){
                                    distance=dataobject.getString("distance");
                                    ride_duration=dataobject.getString("duration_in_minutes");
                                    droptime=dataobject.getString("booking_ended_timestamp");
                                    totalfare=dataobject.getString("total_fare");
                                    pickuptime=dataobject.getString("booking_started_timestamp");
                                    bookingDataBean.setDate(pickuptime);
                                    bookingDataBean.setTime(ride_duration);
                                    bookingDataBean.setAmount(totalfare);
                                    bookingDataBean.setDuration(ride_duration);
                                    bookingDataBean.setDistance(distance);
                                    bookingDataBean.setDrop(drop);
                                    bookingDataBean.setPickup(pickup);
                                    bookingDataBean.setName(dname);
                                    bookingDataBean.setStatus(status);
                                    arrayList.add(bookingDataBean);
                                }else if (status.equals("cancelled")){
                                    bookingDataBean.setDate(pickuptime);
                                    bookingDataBean.setTime(ride_duration);
                                    bookingDataBean.setAmount(totalfare);
                                    bookingDataBean.setDuration(ride_duration);
                                    bookingDataBean.setDistance(distance);
                                    bookingDataBean.setDrop(drop);
                                    bookingDataBean.setPickup(pickup);
                                    bookingDataBean.setName(dname);
                                    bookingDataBean.setStatus(status);
                                    arrayList.add(bookingDataBean);
                                }
*/
                                //  arrayList.add(bookingDataBean);


                            }
                            adapter=new truckListAdapter(RideHistory.this,arrayList);
                            recyclerView.setAdapter(adapter);
                        }else {
                            NoData.setVisibility(View.VISIBLE);
                        }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();


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
                SharedPreferences sharedPreferences= Helper.getSharedPref(RideHistory.this);
              String p=  sharedPreferences.getString(Constant.KEY_USER_LOGIN,"");
                hashMap.put("customer_username",p);

                return hashMap;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
