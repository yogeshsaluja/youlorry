package com.youlorryintracity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.youlorryintracity.Modules.DirectionFinder;
import com.youlorryintracity.Modules.DirectionFinderListener;
import com.youlorryintracity.Modules.Route;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TrackActivity extends AppCompatActivity implements OnMapReadyCallback,DirectionFinderListener, View.OnClickListener {
GoogleMap mMap;
LatLng Origin,Destination,currentPosition;
ProgressDialog progressDialog;
String PickUp,Drop,DriverName,DriverContact,Distance,duration,amount;
TextView Dname,DCont,PickUpText,DropText,EstimeteTime,EstimateDistance;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Current Booking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mapFragment.getMapAsync(this);
        Bundle bundle=getIntent().getExtras();
          PickUp= bundle.getString("pick");
          Drop = bundle.getString("drop");
        duration= bundle.getString("duration");
        Distance = bundle.getString("distance");
        amount=bundle.getString("amount");
         DriverName=bundle.getString("drivername");
        Dname=(TextView)findViewById(R.id.tv_contact_name);
        DCont=(TextView)findViewById(R.id.tv_contact_no);
        PickUpText=(TextView)findViewById(R.id.tv_pickup);
        DropText=(TextView)findViewById(R.id.tv_drop);
        EstimateDistance=(TextView)findViewById(R.id.tv_distance);
        EstimeteTime=(TextView)findViewById(R.id.tv_time_name);

        Dname.setText(DriverName);
EstimateDistance.setText(Distance);
EstimeteTime.setText(duration);

        PickUpText.setText(PickUp);
        DropText.setText(Drop);
        DCont.setOnClickListener(this);
       Origin=getLocationFromAddress(this,PickUp);
       Destination=getLocationFromAddress(this,Drop);
        Location location1 = new Location("");
        location1.setLatitude(Origin.latitude);
        location1.setLongitude(Origin.longitude);

        Location location2 = new Location("");
        location2.setLatitude(Destination.latitude);
        location2.setLongitude(Destination.longitude);

        float distanceInMeters = location1.distanceTo(location2);
        int speedIs10MetersPerMinute = 40000;
        float estimatedDriveTimeInMinutes = distanceInMeters / speedIs10MetersPerMinute;
        String formattedString = String.format("%.02f", estimatedDriveTimeInMinutes);
        EstimeteTime.setText(""+formattedString+" Hour");
        EstimateDistance.setText(""+(int) (distanceInMeters/1000)+" KM");
       // Toast.makeText(this, ""+Origin+Destination, Toast.LENGTH_SHORT).show();
        progressDialog=new ProgressDialog(this);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(false);
            try {
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                if (Origin==null)
                {
                    Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
                }else if (Destination!=null)
                {
                    new DirectionFinder(this,Origin,Destination).execute();

                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
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


       progressDialog.dismiss();
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
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 12));


            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .title(route.startAddress)
                    .position(route.startLocation)));


            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .title(route.endAddress)
                    .position(route.endLocation)));


            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(getResources().getColor(R.color.md_light_blue_600)).
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
        }
    }
}
