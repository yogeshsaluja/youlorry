package com.youlorryintracity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.youlorryintracity.ExtraaClasses.Constant;
import com.youlorryintracity.ExtraaClasses.Global;
import com.youlorryintracity.ExtraaClasses.Helper;
import com.youlorryintracity.Modules.DirectionFinderListener;
import com.youlorryintracity.Modules.Route;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

import static com.google.android.gms.maps.model.JointType.ROUND;

public class BookingConfiremActivity extends AppCompatActivity implements OnMapReadyCallback,DirectionFinderListener, View.OnClickListener {
GoogleMap mMap;
LatLng Origin,Destination,currentPosition;
ProgressBar progressDialog;
Double OrgLat,OrgLong,DesLat,DesLong;
String PickUp,Drop,DriverName,DriverContact,TimeDuration;
SharedPreferences sharedPreferences;
String Amount;
static String SelectedTruck="";
    private static final String TAG = Home.class.getSimpleName();
Spinner PaymentSpinner;
CardView ConLayoutCurrentBooking;
RelativeLayout ProgressLayout;
    PulsatorLayout pulsator;
    ImageView PaymentImage;
    private long pulseDuration = 3000;
Constant.TransitionType type;
    private PolylineOptions polylineOptions, blackPolylineOptions;
    private Polyline blackPolyline, greyPolyLine;
    private List<LatLng> polyLineList;
    private Marker marker;
    private float v;
    private double lat, lng;

    private LatLng startPosition, endPosition;
TextView Dname,DCont,PickUpText,DropText,EstimeteTime,EstimateDistance,CalculatedAmount,CON_BOOk_NOW;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirem_book);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
            toolbar.setTitle("Current Booking");
            toolbar.setSubtitle("by YouLorry");
            initPage();
        initAnimation();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setTitle("Current Booking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mapFragment.getMapAsync(this);
        ProgressLayout = (RelativeLayout) findViewById(R.id.progressBarlayout);
        pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        Dname=(TextView)findViewById(R.id.tv_contact_name);
        DCont=(TextView)findViewById(R.id.tv_contact_no);
        PaymentImage = (ImageView) findViewById(R.id.imagemode);
        PickUpText=(TextView)findViewById(R.id.tv_pickup);
        DropText=(TextView)findViewById(R.id.tv_drop);
        EstimateDistance=(TextView)findViewById(R.id.tv_distance);
        EstimeteTime=(TextView)findViewById(R.id.tv_time_name);
        progressDialog=(ProgressBar)findViewById(R.id.progressBar);
        ConLayoutCurrentBooking=(CardView) findViewById(R.id.conbookincardlayout);
        CalculatedAmount=(TextView)findViewById(R.id.tvprize);
        PaymentSpinner = (Spinner) findViewById(R.id.spiinerpayment);
        CON_BOOk_NOW = (TextView) findViewById(R.id.conbtnbooknow);
        CON_BOOk_NOW.setOnClickListener(this);
        Global.currntbooking=0;
        sharedPreferences= Helper.getSharedPref(BookingConfiremActivity.this);
        Bundle bundle=getIntent().getExtras();
        OrgLat= bundle.getDouble("Olat");
        OrgLong = bundle.getDouble("Olong");
         DesLat=bundle.getDouble("Dlat");
         DesLong=bundle.getDouble("Dlong");
         String  OriginName=bundle.getString("oname");
         String Dropname=bundle.getString("dname");
        SelectedTruck=bundle.getString("distance");
        TimeDuration=bundle.getString("time_duration");
         Amount=bundle.getString("price");

         if (bundle!=null)
         {
             Origin=new LatLng(OrgLat,OrgLong);
             Destination=new LatLng(DesLat,DesLong);
             String pickupadd=    getCompleteAddressString(OrgLat,OrgLong);
             String Desadd=  getCompleteAddressString(DesLat,DesLong);

             PickUpText.setText(OriginName);
             DropText.setText(Dropname);
             CalculatedAmount.setText(Amount);


         }

        PaymentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                String mode = PaymentSpinner.getSelectedItem().toString();
                if (mode.equals("Cash")) {
                    PaymentImage.setImageResource(R.drawable.ic_notes);
                } else if (mode.equals("Banking")) {
                    PaymentImage.setImageResource(R.drawable.ic_creditcard);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
            try {
                progressDialog.setVisibility(View.VISIBLE);

                if (Origin==null)
                {
                    Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
                }else if (Destination!=null)
                {
                    if (Origin!=null)
                    {
                     if (Destination!=null)
                     {
                         polyanimatiomn(Origin.latitude,Origin.longitude,Destination.latitude,Destination.longitude);
                         //new DirectionFinder(this,Origin,Destination).execute();

                     }else {
                         onBackPressed();
                     }

                    } else {
                        onBackPressed();
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        if (menuItem.getItemId()== android.R.id.home){

            finish();
        }
    return super.onOptionsItemSelected(menuItem);
    }
    @Override
    public void onDirectionFinderStart() {

    }

    @Override
    public void onTokenRefresh() {

    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {


       progressDialog.setVisibility(View.GONE);
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();
        MarkerOptions markerOptions=new MarkerOptions();

        for (Route route : routes) {
           // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 15));
            LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
            boundsBuilder.include(route.startLocation);
            boundsBuilder.include(route.endLocation);
          //  boundsBuilder.include(currentPosition);

            LatLngBounds bounds = boundsBuilder.build();

            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));


            markerOptions.icon(bitmapDescriptorFromVector(BookingConfiremActivity.this,R.drawable.ic_pin));
            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .title(route.startAddress)
                    .position(route.startLocation)));


            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(bitmapDescriptorFromVector(BookingConfiremActivity.this,R.drawable.ic_pin_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));


            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(getResources().getColor(R.color.black)).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));
            polylinePaths.add(mMap.addPolyline(polylineOptions));
           // onReady(route.points);
           // animateCarMove(mk,route.startLocation,route.endLocation,6000);
            //animateMarkerList(mMap,mk,route.points,false);
            //displayLocation();


        }
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
    public void onResult(@NonNull Status status) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_contact_no:
            {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+DCont.getText().toString().trim()));
                startActivity(intent);
            }
            break;
            case R.id.conbtnbooknow:{

                try {
                    ProgressLayout.setVisibility(View.VISIBLE);
                    pulsator.setVisibility(View.VISIBLE);
                    pulsator.start();

                    NewBookingURl();

                    // new DirectionFinder(this, Origin, Destination).execute();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;
        }
    }
    ///////////////////////New Boking API/////////////////////
    public void NewBookingURl()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://youlorry.com/wp-json/intracity/youlorry_new_booking", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                ProgressLayout.setVisibility(View.GONE);
                pulsator.setVisibility(View.GONE);
                pulsator.start();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                   Boolean msg= jsonObject.getBoolean("success");
                   if (msg){
                       String quoteid=jsonObject.getString("ID");
                       Global.Quotation_ID=quoteid;
                       CON_BOOk_NOW.setEnabled(false);
                     //  Global.currntbooking=1;
                       Toast.makeText(BookingConfiremActivity.this,"Booking Confirmed!", Toast.LENGTH_SHORT).show();

                       Intent intent1 = new Intent(BookingConfiremActivity.this, Home.class);
                       intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                       startActivity(intent1);

                   }else {
                       Toast.makeText(BookingConfiremActivity.this,"No Driver found! try again", Toast.LENGTH_SHORT).show();

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
                String phoneNumber= sharedPreferences.getString(Constant.KEY_USER_LOGIN,"");
                hashMap.put("username",phoneNumber);
                hashMap.put("destination",DropText.getText().toString());
                hashMap.put("vehicle_type","truck_"+SelectedTruck);

                return hashMap;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 20000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }
///////////////////////////////////////////////////////////////////////
private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
    String strAdd = "";
    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    try {
        List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
        if (addresses != null) {
            Address returnedAddress = addresses.get(0);
            StringBuilder strReturnedAddress = new StringBuilder("");
            strReturnedAddress.append(returnedAddress.getFeatureName() + "," + returnedAddress.getSubLocality() + "," + returnedAddress.getAdminArea());
/*
                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
*/
            strAdd = strReturnedAddress.toString();
        } else {
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return strAdd;
}



    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
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

    public void polyanimatiomn(Double slat,Double slang,Double dlat,Double dLang){
        final LatLng defaultlat=new LatLng(28.610029, 77.372804);
        LatLng pickup = null;


        try {
            String requestUrl = null;
            requestUrl = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "mode=driving&"
                    + "transit_routing_preference=less_driving&"
                    + "origin=" + slat + "," + slang + "&"
                    + "destination=" + dlat+","+dLang+ "&"
                    + "key=" + getResources().getString(R.string.google_directions_key);
            Log.d(TAG, requestUrl);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    requestUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response + "");
                            progressDialog.setVisibility(View.INVISIBLE);
                            try {
                                JSONArray jsonArray = response.getJSONArray("routes");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject route = jsonArray.getJSONObject(i);
                                    JSONObject poly = route.getJSONObject("overview_polyline");
                                    String polyline = poly.getString("points");
                                    polyLineList = decodePoly(polyline);
                                    Log.d(TAG, polyLineList + "");
                                }
                                //Adjusting bounds
                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                for (LatLng latLng : polyLineList) {
                                    builder.include(latLng);
                                }
                                LatLngBounds bounds = builder.build();
                                CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 50);

                                mMap.animateCamera(mCameraUpdate);

                                polylineOptions = new PolylineOptions();

                                polylineOptions.color(Color.GRAY);
                                polylineOptions.width(5);
                                polylineOptions.startCap(new SquareCap());
                                polylineOptions.endCap(new SquareCap());
                                polylineOptions.jointType(ROUND);
                                polylineOptions.addAll(polyLineList);
                                greyPolyLine = mMap.addPolyline(polylineOptions);

                                blackPolylineOptions = new PolylineOptions();
                                blackPolylineOptions.width(8);
                                blackPolylineOptions.color(Color.BLACK);
                                blackPolylineOptions.startCap(new SquareCap());
                                blackPolylineOptions.endCap(new SquareCap());
                                blackPolylineOptions.jointType(ROUND);
                                blackPolyline = mMap.addPolyline(blackPolylineOptions);
                                mMap.addMarker(new MarkerOptions()
                                        .position(Origin).icon(bitmapDescriptorFromVector(BookingConfiremActivity.this,R.drawable.ic_pin_green)));
                                mMap.addMarker(new MarkerOptions()
                                        .position(Destination).icon(bitmapDescriptorFromVector(BookingConfiremActivity.this,R.drawable.ic_pin)));

                                ValueAnimator polylineAnimator = ValueAnimator.ofInt(0, 100);
                                polylineAnimator.setDuration(2000);
                                polylineAnimator.setInterpolator(new LinearInterpolator());
                                polylineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                        List<LatLng> points = greyPolyLine.getPoints();
                                        int percentValue = (int) valueAnimator.getAnimatedValue();
                                        int size = points.size();
                                        int newPoints = (int) (size * (percentValue / 100.0f));
                                        List<LatLng> p = points.subList(0, newPoints);
                                        blackPolyline.setPoints(p);
                                    }
                                });
                                polylineAnimator.start();

/*
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (index < polyLineList.size() - 1) {
                                            index++;
                                            next = index + 1;
                                        }
                                        if (index < polyLineList.size() - 1) {
                                            startPosition = polyLineList.get(index);
                                            endPosition = polyLineList.get(next);

                                          //startPosition=DriverPreLoc;
                                        // endPosition=DriverNewLoc;
                                        }
                                        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                                        valueAnimator.setDuration(3000);
                                        valueAnimator.setInterpolator(new LinearInterpolator());
                                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                            @Override
                                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                                v = valueAnimator.getAnimatedFraction();
                                                lng = v * endPosition.longitude + (1 - v)
                                                        * startPosition.longitude;
                                                lat = v * endPosition.latitude + (1 - v)
                                                        * startPosition.latitude;
                                                LatLng newPos = new LatLng(lat, lng);
                                                marker.setPosition(newPos);
                                                marker.setAnchor(0.5f, 0.5f);
                                                marker.setRotation(getBearing(startPosition, newPos));
                                                mMap.moveCamera(CameraUpdateFactory
                                                        .newCameraPosition
                                                                (new CameraPosition.Builder()
                                                                        .target(newPos)
                                                                        .zoom(15.5f)
                                                                        .build()));
                                            }
                                        });
                                        valueAnimator.start();
                                        handler.postDelayed(this, 3000);
                                    }
                                }, 3000);
*/


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, error + "");
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

}
