package com.youlorryintracity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.youlorryintracity.ExtraaClasses.Constant;
import com.youlorryintracity.ExtraaClasses.Helper;

import io.fabric.sdk.android.Fabric;

public class Splash extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    static boolean isGPSEnabled = false;




    String User,Status="0";
    LinearLayout SplashLayout;
    RelativeLayout Slider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_splash);
        SplashLayout=(LinearLayout)findViewById(R.id.layoutsplash);

        final SharedPreferences sharedPreferences= Helper.getSharedPref(Splash.this);
        User=  sharedPreferences.getString(Constant.KEY_USER_LOGIN,"");
         Status=  sharedPreferences.getString(Constant.SPLASH_STATUS,"");

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkLocationPermission();
            }else {
                LoginUser();
            }
        }else {
            LoginUser();
        }

        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showSettingsAlert();
        }

    }


    public  void LoginUser(){
        if (User.length()!=0)
        {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                 //   ActivityOptions activityOptions=ActivityOptions.makeSceneTransitionAnimation(Splash.this);
                    Intent intent=new Intent(Splash.this,Home.class);
                   // intent.putExtra(Constant.KEY_AUTH_TYPE,Constant.TransitionType.ExplodeJava);
                    startActivity(intent);
                    finish();
                }
            },2000);
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Splash.this,LoginActivity.class));
                    finish();
                }
            },2000);

         }
    }








    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Splash.this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                isGPSEnabled = true;
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        LoginUser();
                        Toast.makeText(this, "Allowed", Toast.LENGTH_SHORT).show();

                        /* mMap.setMyLocationEnabled(true);
                        mLocationPermissionGranted = true;*/
                    }

                } else {
                        checkLocationPermission();

                    Toast.makeText(this, "You need to allow to continue", Toast.LENGTH_LONG).show();
                }
                return;
            }
            case 1:
            {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();

                }else {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
                }
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }


    @Override
    public void onResume()
    {
        super.onResume();
        Intent intent=getIntent();
        startActivity(intent);

    }
}
