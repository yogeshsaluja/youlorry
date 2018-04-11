package com.youlorryintracity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.transition.Slide;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.youlorryintracity.Adapter.TRUCKS;
import com.youlorryintracity.ExtraaClasses.ConnectivityReceiver;
import com.youlorryintracity.ExtraaClasses.Constant;
import com.youlorryintracity.ExtraaClasses.Global;
import com.youlorryintracity.ExtraaClasses.Helper;
import com.youlorryintracity.ExtraaClasses.MainActivity;
import com.youlorryintracity.ExtraaClasses.MyApplication;
import com.youlorryintracity.ModalClasses.Trucks_Modal;
import com.youlorryintracity.Modules.DirectionFinderListener;
import com.youlorryintracity.Modules.Route;
import com.youlorryintracity.NotificationClasses.Config;
import com.youlorryintracity.NotificationClasses.NotificationUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

import static com.google.android.gms.maps.model.JointType.ROUND;
import static com.youlorryintracity.ExtraaClasses.Constant.TransitionType.ExplodeJava;
import static com.youlorryintracity.ExtraaClasses.Constant.TransitionType.SlideJava;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener, ConnectivityReceiver.ConnectivityReceiverListener,
        GoogleMap.OnMarkerDragListener, GoogleMap.OnMapLongClickListener,
        AdapterView.OnItemClickListener, GoogleMap.OnMyLocationButtonClickListener, View.OnClickListener, DirectionFinderListener {
    final static int REQUEST_LOCATION = 199;

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String TAG = Home.class.getSimpleName();
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    static LatLng Origin = null, Destination = null;
    static String SelectedTruck = "";
    static Boolean CameraPickUp = false, CameraDrop = false;
    static LatLng DriverPreLoc, DriverNewLoc;
    private static String DistMesure, TimeDuration;
    public Location mLastLocation;
    GoogleMap mMap;
    GoogleMap.CancelableCallback cancelableCallback;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<Trucks_Modal> arrayList;
    ProgressDialog progressDialogmain, progressDialog;
    TextView BOOk_NOW, CON_BOOk_NOW;
    TextView Book_LATER;
   static int distStatus=0;
    Marker mCurrLocationMarker, DropMarker, DriverMarker;
    LatLng currentPosition;
    LinearLayout PickUpLayout,NoSurving, DropLayout, BookReqLayout, DriverCall, CancelBook, ShareBook, MsgDriver, PaymentModeLayout;
    CardView LayoutCurrentBooking, ConLayoutCurrentBooking;
    TextView PickUp, DropCity, CalculatedAmount;
    ImageView GreenPinMark, ProgressClose, PaymentImage, LikeImageView;
    RelativeLayout ProgressLayout,RootLayout;
    Bitmap mMarkerIcon;
    LayoutAnimationController animationf;
    int mIndexCurrentPoint;
    SharedPreferences sharedPreferences;
    Double DistanceMesured;
    FrameLayout SearchLayout;
    String DestPlaces[]={"NIRJULI","NAHARLAGUN","ITANAGAR","SEPPA","ZIRO","SAGALI","YAZALI","KHONSA","PASIGHAT","NAMSAI"
            ,"ROING","TEZU","KOLORIANG","ALONG","YINGKIONG","DAPORIJO","AMINGAON","GUWAHATI -CITY","BIJOYNAGAR",
            "PUTHIMARI (BAIHATA)","RANGIA","JORABAT 9TH MILE","NARENGI","HAJO","JAGIROAD","MORIGAON","MANGALDOI",
            "BOKO","TIHU","KHARUPETIA","PATHSALA","NAGAON","UDALGURI","BARPETA","DHING","DOBOKA","MISAMARI",
            "DHEKIAJULI","GOALPARA","HOJAI","JOKHALABANDHA","BONGAIGAON","LANKA","TEZPUR","BALIPARA","LAKHIPUR (GOALPARA)",
            "LUMDING","KOKRAJHAR","BOKAKHAT","GOSAIGAON","MANKACHAR","BILASIPARA","DIPHU","B. CHARIALI","DERGAON","DHUBRI",
            "GOHPUR","GOLAGHAT","BADARPUR","CHAGOLIA","JORHAT","MASIMPUR","SARUPATHAR","SILCHAR","KARIMGANJ","HAILAKANDI",
            "TEOK","MARIANI","HAFLONG","BIHPURIA","SIBSAGAR","BANDERDEWA","AMGURI","NORTH LAKHIMPUR","SONARI","DHEMAJI","DIBRUGARH",
            "NAMRUP","SILAPATHAR","CHABUA","MAJULI","DULIAJAN","TINSUKIA","JONAI","DOOM DOOMA","MARGHERITA","DIGBOI","SENAPATI - RURAL",
            "IMPHAL (IMPHAL E)","CHURACHANDPUR - RURA","UKHRUL - RURAL","NONGPOH","SHILLONG","JOWAI","NONGSTON","TURA","AIZAWL","LUNGLEI","DIMAPUR",
            "CHUMUKDIMA","KOHIMA","WOKHA","MOKOKCHUNG","DHARAMNAGAR","DHALAI","KHOWAI","TELIAMURA","AMARPUR",
            "AGARTALA TRIPURA","AMBASA","UDAIPUR","BISHALGARH","BELONIA","LAKHIPUR (GOALPARA)"};

    String[] nameArray={"407","1109","207 DI"};
    int[] secondimages = {R.drawable.ic_delivery_truck2, R.drawable.ic_delivery_truck_new2, R.drawable.ic_crane2, R.drawable.ic_cranenew2, R.drawable.ic_delivery_truck2};
    int[] images = {R.drawable.ic_delivery_truck, R.drawable.ic_delivery_truck_new, R.drawable.ic_crane, R.drawable.ic_cranenew, R.drawable.ic_delivery_truck};
    ArrayList<Integer> integers, secondicon;
    ArrayList<String> nameslist;
    Spinner PaymentSpinner;
    String pickUpName, DropName, TimeExpect, TruckType, DistanceExp;
    TextView DriverName, AmountPaid, PaymentDone, RideKm, RideDuration, TotalAmount, TripDate;
    ArrayList<String> timelist;
    AutocompleteFilter typeFilter;
    Boolean address = true;
    boolean hasBeenInitialized = false;
    PulsatorLayout pulsator;
    CoordinatorLayout coordinatorLayout;
    LocationManager manager;
    RatingBar ratingBar;
    TextView tvLocation, BaseFare, TotalFare, DistanceCovered;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Constant.TransitionType type;
    boolean doubleBackToExitPressedOnce = false;
    Marker mk = null;
    private List<LatLng> mPathPolygonPoints;
    private int markerCount = 0;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private GoogleApiClient googleApiClient;
    private Circle lastUserCircle;
    private long pulseDuration = 3000;
    private ValueAnimator lastPulseAnimator;
    private AutoCompleteTextView t_f_load_s_city, t_f_load_d_city;
    private int fingers = 0;
    private GoogleMap googleMap;
    private long lastZoomTime = 0;
    private float lastSpan = -1;
    private Handler handler = new Handler();
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private PolylineOptions polylineOptions, blackPolylineOptions;
    private Polyline blackPolyline, greyPolyLine;
    private List<LatLng> polyLineList;
    private Marker marker;
    private float v;
    private double lat, lng;
    private LatLng startPosition, endPosition;
    private int index, next;
    private LatLng sydney;
    private Button button;
    private EditText destinationEditText;
    Spinner DropSpinner;

    /////////////////////////////////////////////////OnCreate Finished Here///////////////////////////////////////////

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        // toolbar.setLogo(getDrawable(R.mipmap.ic_schoolbus));
        toolbar.setTitle("YouLorry");
        // toolbar.setSubtitle("powered by Intracity");
        //   toolbar.setBackgroundColor(Color.TRANSPARENT);
        Global.currntbooking = 0;
        Global.notification = 0;
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Status bar :: Transparent
        Window window = this.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        //StartAnimation();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

        pulsator = findViewById(R.id.pulsator);

        sharedPreferences = Helper.getSharedPref(Home.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constant.KEY_USER, "7056420829");
        editor.commit();
        createLocationRequest();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mMarkerIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.car);

        int resId = R.anim.layout_animation_from_right;
        animationf = AnimationUtils.loadLayoutAnimation(this, resId);
        mapFragment.getMapAsync(this);
        //  new DownloadWebPageTask().execute();
        //

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        coordinatorLayout = findViewById(R.id.content);
       // drawer.setScrimColor(getResources().getColor(android.R.color.transparent));
        final float scaleFactor = 6f;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                coordinatorLayout.setTranslationX(slideX);
                coordinatorLayout.setTranslationX(slideX);
                coordinatorLayout.setScaleX(1 - (slideOffset / scaleFactor));
                coordinatorLayout.setScaleY(1 - (slideOffset / scaleFactor));
            }
        };
        drawer.addDrawerListener(toggle);
        drawer.setDrawerShadow(R.drawable.trasparent, GravityCompat.START);
      //  drawer.setScrimColor(Color.TRANSPARENT);
       // drawer.addDrawerListener(toggle);
        toggle.syncState();
        arrayList = new ArrayList<Trucks_Modal>();
        integers = new ArrayList<Integer>();
        timelist = new ArrayList<String>();
        nameslist = new ArrayList<String>();
        secondicon = new ArrayList<Integer>();
        for (int i = 0; i <3; i++) {
            integers.add(images[i]);
            secondicon.add(secondimages[i]);
            nameslist.add(nameArray[i]);
            integers.add(images[i]);

        }
        ratingBar = findViewById(R.id.ratingBar1);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(Home.this, "Thanks your response good for us!", Toast.LENGTH_SHORT).show();
            }
        });
        progressDialogmain = new ProgressDialog(this);
        progressDialogmain.setTitle("Please wait checking route");
        progressDialogmain.setMessage("loading.....");
        progressDialogmain.setCancelable(true);
        DropSpinner=findViewById(R.id.drop_spinner);
        progressBar = findViewById(R.id.progressBar);
        SearchLayout = findViewById(R.id.searcbarframelayout);
        PaymentSpinner = findViewById(R.id.spiinerpayment);
        PickUp = findViewById(R.id.pickup);
        DropCity = findViewById(R.id.drop);
        NoSurving=findViewById(R.id.not_surving);
        BOOk_NOW = findViewById(R.id.btnbooknow);
        CON_BOOk_NOW = findViewById(R.id.conbtnbooknow);
        Book_LATER = findViewById(R.id.btnbooklater);
        CalculatedAmount = findViewById(R.id.tvprize);
        TotalFare = findViewById(R.id.tv_amount_paid);
        BaseFare = findViewById(R.id.fairprize);
        DistanceCovered = findViewById(R.id.tv_distance_covered);
        //   AmountPaid=(TextView)findViewById(R.id.tv_amount_tobepaid);
        TripDate = findViewById(R.id.tvtripdate);
        PaymentDone = findViewById(R.id.paymentCollect);
        RootLayout=findViewById(R.id.relative);
        PickUpLayout = findViewById(R.id.pickuplayout);
        PaymentImage = findViewById(R.id.imagemode);
        DropLayout = findViewById(R.id.droplayout);
        recyclerView = findViewById(R.id.recycler);
        ProgressLayout = findViewById(R.id.progressBarlayout);
        ProgressClose = findViewById(R.id.closeprogress);
        LayoutCurrentBooking = findViewById(R.id.currntBookinglayout);
        ConLayoutCurrentBooking = findViewById(R.id.conbookincardlayout);
        BookReqLayout = findViewById(R.id.bookrequestlayout);
        DriverCall = findViewById(R.id.tvdrivercall);
        PaymentModeLayout = findViewById(R.id.ll_payment_mode);
        CancelBook = findViewById(R.id.llcancel);
        ShareBook = findViewById(R.id.llshare);
        MsgDriver = findViewById(R.id.lltextmsg);
        DriverName = findViewById(R.id.tvdriver_name);
        DriverCall.setOnClickListener(this);
        CancelBook.setOnClickListener(this);
        ShareBook.setOnClickListener(this);
        MsgDriver.setOnClickListener(this);
        CON_BOOk_NOW.setOnClickListener(this);

        PaymentDone.setOnClickListener(this);

        // BookReqLayout.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
       // progressBar.setVisibility(View.VISIBLE);
        //new TruckListUrl().execute();
        adapter = new TRUCKS(Home.this, integers, secondicon, nameslist);
        recyclerView.setAdapter(adapter);
        recyclerView.setAdapter(adapter);
        // recyclerView.setLayoutAnimation(animationf);
        PickUpLayout.setOnClickListener(this);
        DropLayout.setOnClickListener(this);
        Book_LATER.setOnClickListener(this);
        BOOk_NOW.setOnClickListener(this);
        ProgressClose.setOnClickListener(this);
        PickUp.setText("Guwahati,Assam,India");
        if (PickUp.getText().toString().equals("Guwahati,Assam,India")){
            LatLng latLng=new LatLng(26.1445169,91.7362365);
            Origin=latLng;
        }

        GreenPinMark = findViewById(R.id.customgreenmarker);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,DestPlaces);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DropSpinner.setAdapter(adapter);
/////////////////////Location Builder////////////////////
        manager = (LocationManager) Home.this.getSystemService(Context.LOCATION_SERVICE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(Home.this)) {


                }
                if (!hasGPSDevice(Home.this)) {
                    Toast.makeText(Home.this, "Gps not Supported", Toast.LENGTH_SHORT).show();
                }

                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(Home.this)) {
                    Log.e("keshav", "Gps already enabled");
                    Toast.makeText(Home.this, "Gps not enabled", Toast.LENGTH_SHORT).show();
                    enableLoc();
                } else {
                    Log.e("keshav", "Gps already enabled");
                    //Toast.makeText(Home.this,"Gps already enabled",Toast.LENGTH_SHORT).show();
                }
            }
        }, 3000);

        ImageView FAB = findViewById(R.id.myLocationButton);
        typeFilter = new AutocompleteFilter.Builder().setCountry("IN")
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS)
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_GEOCODE)
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ESTABLISHMENT)
                .build();
        PickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, MainActivity.class);
                intent.putExtra("pick", "Enter pick up");
                startActivityForResult(intent, 1);

                CameraPickUp = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    PickUpLayout.setElevation(30);
                    DropLayout.setElevation(0);
                    DropLayout.setBackgroundColor(getResources().getColor(R.color.grey));
                    PickUp.setBackgroundColor(getResources().getColor(R.color.white));
                }

            }
        });
        DropCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DestinationSearchDialog();

            }
        });
        PaymentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String mode = PaymentSpinner.getSelectedItem().toString();
                if (mode.equals("Cash")) {
                    PaymentImage.setImageResource(R.drawable.ic_notes);
                } else if (mode.equals("Banking")) {
                    PaymentImage.setImageResource(R.drawable.ic_creditcard);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(Home.this)) {
                    Log.e("keshav", "Gps already enabled");
                    Toast.makeText(Home.this, "Gps not enabled", Toast.LENGTH_SHORT).show();
                    enableLoc();
                } else {
                    Log.e("keshav", "Gps already enabled");
                    try {

                        final LatLng  location=new LatLng(mMap.getMyLocation().getLatitude(),mMap.getMyLocation().getLongitude());
                        Boolean  valid=  ValidLocationRegion(Origin);
                        if (!valid){
                            Toast.makeText(Home.this, "We are Not Surving Here!", Toast.LENGTH_SHORT).show();
                            getSupportActionBar().hide();
                            BookReqLayout.setVisibility(View.INVISIBLE);
                            DropLayout.setVisibility(View.INVISIBLE);
                            NoSurving.setVisibility(View.VISIBLE);
                        }else {
                            getSupportActionBar().show();
                            BookReqLayout.setVisibility(View.VISIBLE);
                            DropLayout.setVisibility(View.VISIBLE);
                            NoSurving.setVisibility(View.GONE);

                        }
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .bearing(90).zoom(18).tilt(30).target(location).build();
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                        mMap.animateCamera(cameraUpdate, 2000, new GoogleMap.CancelableCallback() {
                            @Override
                            public void onFinish() {
                                Origin=location;

                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                        PickUp.setText(getCompleteAddressString(location.latitude, location.longitude));
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    //Toast.makeText(Home.this,"Gps already enabled",Toast.LENGTH_SHORT).show();
                }



             }
        });
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       // drawer.setScrimColor(getResources().getColor(android.R.color.transparent));

///////////////////////////////////////////Notification Listner////////////////////////////////////////
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {

                    String message = intent.getStringExtra("message");
                    if (message.equals("Booking Cancelled")) {
                        Global.notification = 0;
                        Global.Quotation_ID = "";
                        Global.TRIP_Status = "";
                        Global.DriverName = "";
                        Global.Driver_Contact = "";
                        Global.trucksel = 0;
                        Global.currntbooking = 0;
                        Intent intent1 = new Intent(Home.this, Home.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);

                    } else if (message.equals("Driver is On the way!")) {
                        Global.notification++;
                        if (Global.notification == 1) {
                            showChangeLangDialog();
                        }
                    } else if (message.equals("Loading Done, Trip Started")) {
                        polyanimatiomn(currentPosition.latitude, currentPosition.longitude, Destination.latitude, Destination.longitude);

                    }

                    DriverName.setText(Global.DriverName);
                    PulseProgressBar();

                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Global.currntbooking = 0;
                    if (Global.TRIP_Status.equals("Truck Reached Destination")) {
                        progressBar.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                PaymentModeLayout.setVisibility(View.VISIBLE);
                                LayoutCurrentBooking.setVisibility(View.GONE);
                                BaseFare.setText(Global.Base_Fare);
                                TotalFare.setText(Global.RideFare);
                                DistanceCovered.setText(Global.Distance);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                                String currentDateandTime = sdf.format(new Date());
                                TripDate.setText(currentDateandTime);
                                Global.notification = 0;
                                Global.Quotation_ID = "";
                                Global.TRIP_Status = "";
                                Global.DriverName = "";
                                Global.Driver_Contact = "";
                                Global.trucksel = 0;
                                Global.currntbooking = 0;
                            }
                        }, 3000);
                    }


                }
            }
        };

        displayFirebaseRegId();


    }

    private void DestinationSearchDialog() {
        final Dialog dialog = new Dialog(Home.this);
        LayoutInflater inflater = LayoutInflater.from(Home.this);
        View view1 = inflater.inflate(R.layout.search_dialog, null);
        ListView listView = view1.findViewById(R.id.listView);
        SearchView searchView = view1.findViewById(R.id.searchView);

        final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(Home.this, android.R.layout.simple_list_item_1,DestPlaces);
        listView.setAdapter(stringArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DropCity.setText(stringArrayAdapter.getItem(i));
                Destination=GetaddressLocation(stringArrayAdapter.getItem(i));


               /* DropCity.setText(stringArrayAdapter.getItem(i));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(Destination);
                markerOptions.title("Drop");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                DropMarker = mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(Destination));
                mMap.setTrafficEnabled(false);
                mMap.setIndoorEnabled(false);
                mMap.setBuildingsEnabled(false);
                mMap.getUiSettings().setZoomControlsEnabled(false);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(Destination));
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                        .target(mMap.getCameraPosition().target).zoom(20).bearing(30).build()));*/
                dialog.dismiss();

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {

                DropCity.setText(newText);
                dialog.dismiss();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                stringArrayAdapter.getFilter().filter(newText);

                return false;
            }
        });
        dialog.setContentView(view1);
        dialog.show();
    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        Log.e(TAG, "Refreshed token: " + refreshedToken);
        Log.v(TAG, "Refreshed token: " + refreshedToken);


    }
     @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Press again back to exit!", Snackbar.LENGTH_LONG);

        snackbar.show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    String address = data.getStringExtra("result");
                    String latlang = data.getStringExtra("numbers");
                    
                    PickUp.setText(address);
                    String[] latlong = latlang.split(",");
                    String s = latlong[0].substring(10, latlong[0].length() - 1);
                    String ss = latlong[1].substring(0, latlong[1].length() - 1);
                    double latitude = Double.parseDouble(s);
                    double longitude = Double.parseDouble(ss);
                    Origin = new LatLng(latitude, longitude);
                    Boolean  valid=  ValidLocationRegion(Origin);
                    if (!valid){
                        Toast.makeText(Home.this, "We are Not Surving Here!", Toast.LENGTH_SHORT).show();
                        getSupportActionBar().hide();
                        BookReqLayout.setVisibility(View.INVISIBLE);
                        DropLayout.setVisibility(View.INVISIBLE);
                        NoSurving.setVisibility(View.VISIBLE);
                    }else {
                        getSupportActionBar().show();
                        BookReqLayout.setVisibility(View.VISIBLE);
                        DropLayout.setVisibility(View.VISIBLE);
                        NoSurving.setVisibility(View.GONE);
                     }
                    // Toast.makeText(this, "hello"+Origin, Toast.LENGTH_SHORT).show();
                    mMap.clear();
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(Origin);
                    markerOptions.title("Pick up");
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    DropMarker = mMap.addMarker(markerOptions);

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Origin,15));

                    mMap.setTrafficEnabled(false);
                    mMap.setIndoorEnabled(false);
                    mMap.setBuildingsEnabled(false);
                    mMap.getUiSettings().setZoomControlsEnabled(false);

                } catch (Exception e) {
                    Toast.makeText(this, "ewewe" + e, Toast.LENGTH_SHORT).show();
                }


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.


            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                try {
                    String address = data.getStringExtra("result");
                    String latlang = data.getStringExtra("numbers");
                    DropCity.setText(address);
                    String[] latlong = latlang.split(",");
                    String s = latlong[0].substring(10, latlong[0].length() - 1);
                    String ss = latlong[1].substring(0, latlong[1].length() - 1);
                    double latitude = Double.parseDouble(s);
                    double longitude = Double.parseDouble(ss);
                    Destination = new LatLng(latitude, longitude);
                    // Toast.makeText(this, "hello"+Destination, Toast.LENGTH_SHORT).show();
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(Destination);
                    markerOptions.title("Drop");

                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    DropMarker = mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(Destination));
                    mMap.setTrafficEnabled(false);
                    mMap.setIndoorEnabled(false);
                    mMap.setBuildingsEnabled(false);
                    mMap.getUiSettings().setZoomControlsEnabled(false);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(Destination));
                    mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                            .target(mMap.getCameraPosition().target).zoom(15).bearing(30).build()));

                } catch (Exception e) {
                    Log.e("yogi", "" + e);
                //    Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
                }


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.


            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
    public LatLng GetaddressLocation(String location){
        progressBar.setVisibility(View.VISIBLE);
        final LatLng[] latlang = {null};
        StringRequest  stringRequest =new StringRequest(Request.Method.GET, "https://maps.googleapis.com/maps/api/geocode/json?address="+location+"&key=AIzaSyBtO2qdSc46VKwgZeVbOaSO7PCBNT2qqDo", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    progressBar.setVisibility(View.INVISIBLE);
                    String status=jsonObject.getString("status");
                    if (status.equals("OK")){
                        JSONArray jsonArray=jsonObject.getJSONArray("results");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            JSONObject locationobj=object.getJSONObject("geometry");
                            JSONObject loc=locationobj.getJSONObject("location");
                            Double lat=loc.getDouble("lat");
                            Double lang=loc.getDouble("lng");
                            Destination =new LatLng(lat,lang);
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(Destination);
                            markerOptions.title("Drop");
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            DropMarker = mMap.addMarker(markerOptions);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(Destination));
                            mMap.setTrafficEnabled(false);
                            mMap.setIndoorEnabled(false);
                            mMap.setBuildingsEnabled(false);
                            mMap.getUiSettings().setZoomControlsEnabled(false);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(Destination));
                            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                    .target(mMap.getCameraPosition().target).zoom(20).bearing(30).build()));
                        }
                    }else {
                        Toast.makeText(Home.this, "Retry", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 20000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    return latlang[0];
    }

    public LatLng getLocationFromAddress(Context context,String strAddress) {

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
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this);
            Intent intent = new Intent(Home.this, RideHistory.class);
            intent.putExtra(Constant.KEY_AUTH_TYPE, ExplodeJava);
            startActivity(intent, activityOptions.toBundle());


        }
         if (id == R.id.support) {
            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.logout) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(Home.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /////Map Ready//////////////////////////////////////////////////////////////////
    @SuppressLint("NewApi")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        checkConnection();
        View locationButton = ((View)findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
// position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_RIGHT, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        rlp.setMargins(0, 90, 90, 0);
      //  mMap.setPadding(50, 70, 0, 0);
        mMap.setOnMarkerClickListener(this);
        mMap.getUiSettings().setZoomGesturesEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setOnMarkerDragListener(this);
        CameraPickUp = true;
        // PickUpPinMarker();
        // Add a marker in Sydney and move the camera
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();

                mMap.setMyLocationEnabled(true);

            }
        } else {
            buildGoogleApiClient();

            mMap.setMyLocationEnabled(true);
            mMap.setTrafficEnabled(false);
            mMap.setIndoorEnabled(false);
            mMap.setBuildingsEnabled(true);
            mMap.getUiSettings().setCompassEnabled(false);
            mMap.getUiSettings().setZoomControlsEnabled(false);

            //    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));
            //   mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(mMap.getCameraPosition().target).zoom(20).tilt(30).bearing(90).build()));

        }
        if (PickUp.getText().toString().equals("Guwahati,Assam,India")){
            LatLng latLng=new LatLng(26.1445169,91.7362365);
            Origin=latLng;
            mMap.clear();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(Origin);
            markerOptions.title("Pick up");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            DropMarker = mMap.addMarker(markerOptions);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(Origin));
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                    .target(Origin).zoom(15).bearing(30).build()));

            mMap.setTrafficEnabled(false);
            mMap.setIndoorEnabled(false);
            mMap.setBuildingsEnabled(false);
            mMap.getUiSettings().setZoomControlsEnabled(false);
            BookReqLayout.setVisibility(View.VISIBLE);
            DropLayout.setVisibility(View.VISIBLE);
            NoSurving.setVisibility(View.GONE);
        }



        scaleGestureDetector = new ScaleGestureDetector(Home.this, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                if (lastSpan == -1) {
                    lastSpan = detector.getCurrentSpan();
                } else if (detector.getEventTime() - lastZoomTime >= 50) {
                    lastZoomTime = detector.getEventTime();
                    mMap.animateCamera(CameraUpdateFactory.zoomBy(getZoomValue(detector.getCurrentSpan(), lastSpan)), 50, null);
                    lastSpan = detector.getCurrentSpan();
                }
                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                lastSpan = -1;
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                lastSpan = -1;

            }
        });
        gestureDetector = new GestureDetector(Home.this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {

                disableScrolling();
                mMap.animateCamera(CameraUpdateFactory.zoomIn(), 400, null);

                return true;
            }
        });


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                fingers = fingers + 1;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                fingers = fingers - 1;
                break;
            case MotionEvent.ACTION_UP:
                fingers = 0;
                break;
            case MotionEvent.ACTION_DOWN:
                fingers = 1;
                break;
        }
        if (fingers > 1) {
            disableScrolling();
        } else if (fingers < 1) {
            enableScrolling();
        }
        if (fingers > 1) {
            return scaleGestureDetector.onTouchEvent(ev);
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    private void enableScrolling() {
        if (googleMap != null && !googleMap.getUiSettings().isScrollGesturesEnabled()) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    googleMap.getUiSettings().setAllGesturesEnabled(true);
                }
            }, 50);
        }
    }

    private void disableScrolling() {
        handler.removeCallbacksAndMessages(null);
        if (googleMap != null && googleMap.getUiSettings().isScrollGesturesEnabled()) {
            googleMap.getUiSettings().setAllGesturesEnabled(false);
        }
    }

    private float getZoomValue(float currentSpan, float lastSpan) {
        double value = (Math.log(currentSpan / lastSpan) / Math.log(1.55d));
        return (float) value;
    }

    public void PickUpPinMarker() {

        if (CameraPickUp) {
            GreenPinMark.setVisibility(View.VISIBLE);
            GreenPinMark.setImageResource(R.drawable.pinmarkergreen);
            mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {
                    LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
                    LatLng location = new LatLng(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
                    //   Toast.makeText(Home.this, "Camera Stop"+location, Toast.LENGTH_SHORT).show();
                    Log.i("NEwLatlang", String.valueOf(location.latitude));

                    Log.i("centerLong", String.valueOf(location.longitude));
                    Origin = new LatLng(location.latitude, location.longitude);
                    Global.Refresh = 1;
                    //    adapter.notifyDataSetChanged();
                   // new TruckListUrl().execute();
                    //
                    String adddata = getCompleteAddressString(location.latitude, location.longitude);

                    // DataData();
                    if (address) {
                        PickUp.setText(adddata);

                    }

                    //addMarker(mMap,location.latitude,location.longitude);
                }
            });
        } else if (CameraDrop) {
            GreenPinMark.setVisibility(View.VISIBLE);
            GreenPinMark.setImageResource(R.drawable.redd);
            mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {
                    LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
                    LatLng location = new LatLng(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
                    // Toast.makeText(Home.this, "Camera Stop"+location, Toast.LENGTH_SHORT).show();
                    Log.i("NEwLatlang", String.valueOf(location.latitude));
                    adapter.notifyDataSetChanged();
                   // new TruckListUrl().execute();
                    Log.i("centerLong", String.valueOf(location.longitude));
                    Destination = new LatLng(location.latitude, location.longitude);
                    String addrdata = getCompleteAddressString(location.latitude, location.longitude);
                    if (address) {
                        DropCity.setText(addrdata);
                    }

                    // addMarker(mMap,location.latitude,location.longitude);
                }
            });

        }

    }

    @Override
    public void onLocationChanged(Location location) {
        // Toast.makeText(this, ""+location, Toast.LENGTH_SHORT).show();

        currentPosition = new LatLng(location.getLatitude(), location.getLongitude());


    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");
                strReturnedAddress.append(returnedAddress.getFeatureName() + "," + returnedAddress.getSubLocality() + "," + returnedAddress.getAdminArea());
                strAdd = strReturnedAddress.toString();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (mLastLocation != null) {
                    LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    currentPosition = latLng;

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title("My Location");
                   /* if (currentPosition != null) {
                      //  PickUp.setText(getCompleteAddressString(latLng.latitude, latLng.longitude));
                        Origin = latLng;
                    }
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pinmarker));
                    mCurrLocationMarker = mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                    mMap.setTrafficEnabled(false);
                    mMap.setIndoorEnabled(false);
                    mMap.setBuildingsEnabled(false);
                    mMap.getUiSettings().setZoomControlsEnabled(false);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                            .target(mMap.getCameraPosition().target).zoom(15).tilt(18).bearing(30).build()));
*/
                }


            } else {
                Toast.makeText(this, "Wait creating connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        final Handler handler = new Handler();

        final long startTime = SystemClock.uptimeMillis();
        final long duration = 2000;

        Projection proj = mMap.getProjection();
        final LatLng markerLatLng = marker.getPosition();
        Point startPoint = proj.toScreenLocation(markerLatLng);
        startPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);

        final BounceInterpolator interpolator = new BounceInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - startTime;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                double lng = t * markerLatLng.longitude + (1 - t) * startLatLng.longitude;
                double lat = t * markerLatLng.latitude + (1 - t) * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });

        //return false; //have not consumed the event
        return true; //have consumed the event


    }

    ///OnDirection Successs/////////////////////////////////////////////////////
    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {


        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();
        MarkerOptions markerOptions = new MarkerOptions();

        for (Route route : routes) {
            // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 10));
            LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
            boundsBuilder.include(route.startLocation);
            boundsBuilder.include(route.endLocation);
            boundsBuilder.include(currentPosition);
            // pan to see all markers on map:///////////////////////////////////////////////////////////////////////////////////////////
            LatLngBounds bounds = boundsBuilder.build();
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 15));


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
                    color(getResources().getColor(R.color.colorPrimaryDark)).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));
            mPathPolygonPoints = route.points;
            polylinePaths.add(mMap.addPolyline(polylineOptions));
            // onReady(route.points);
            // animateCarMove(mk, route.startLocation, route.endLocation, 6000);
            //  animateMarkerList(mMap, mk, route.points, false);
            //displayLocation();


        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        marker.getPosition();
        //  Toast.makeText(this, "" + marker.getPosition(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Toast.makeText(this, "" + marker.getPosition(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        // Toast.makeText(Home.this, "Loaded", Toast.LENGTH_SHORT).show();
        pickUpName = getCompleteAddressString(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
        PickUp.setText(getCompleteAddressString(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));
        return false;
    }

    ///ON CLICK METHOD///////////////////////////////////////////////////////////////////////////
    @Override
    public void onClick(View view) {
        //  pulsator.start();
        switch (view.getId()) {

            case R.id.pickuplayout: {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (Origin != null) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(Origin));
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                .target(mMap.getCameraPosition().target).zoom(20).bearing(30).build()));
                    }
                   // PickUpLayout.setElevation(20);
                    //DropLayout.setElevation(0);
                }
            }
            case R.id.closeprogress: {
                ProgressLayout.setVisibility(View.GONE);
            }
            break;
            case R.id.droplayout: {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (Destination != null) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(Destination));
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                .target(mMap.getCameraPosition().target).zoom(20).bearing(30).build()));
                    }
                   // PickUpLayout.setElevation(0);
                    //DropLayout.setElevation(20);
                }
            }
            break;
            case R.id.paymentCollect: {
                progressBar.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);

                        PaymentModeLayout.setVisibility(View.GONE);
                        Intent intent = getIntent();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }, 2000);

            }
            break;
            case R.id.btnbooklater: {
                if (Origin == null) {
                    CameraPickUp = true;

                    Intent intent = new Intent(Home.this, MainActivity.class);
                    intent.putExtra("pick", "Enter pick up");
                    startActivity(intent);

                    Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (Destination == null) {
                    DestinationSearchDialog();
                    Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this);

                    Intent intent = new Intent(this, BookLaterActivity.class);
                    intent.putExtra(Constant.KEY_AUTH_TYPE, SlideJava);
                    intent.putExtra("pickup", pickUpName);
                    intent.putExtra("drop", DropName);
                    startActivity(intent, activityOptions.toBundle());
                }
            }
            break;
            case R.id.tvdrivercall: {
                String number = Global.Driver_Contact;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);


            }
            break;
            case R.id.llcancel: {
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CancelApi();
                    }
                }, 3000);

            }
            break;
            case R.id.lltextmsg: {
                Intent intent_sms = new Intent(Intent.ACTION_VIEW);
                intent_sms.setData(Uri.parse("sms:"));
                intent_sms.putExtra("sms_body", "Hello");
                startActivity(intent_sms);

            }
            break;
            case R.id.llshare: {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/html");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>This is the text that will be shared.</p>"));
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
            break;

            case R.id.btnbooknow: {
                GreenPinMark.setVisibility(View.GONE);
                if (Origin == null) {
                    Intent intent = new Intent(Home.this, MainActivity.class);
                    intent.putExtra("pick", "Enter pickup");
                    startActivityForResult(intent, 1);
                    Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (DropCity.getText().toString().length()== 0) {
                    DestinationSearchDialog();
                    Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    progressDialogmain.show();
                new BookingPriceConfirm().execute();
                    // DistanceMesured=distance(Origin.latitude,Origin.longitude,Destination.latitude,Destination.longitude);
                    // BookReqLayout.setVisibility(View.GONE);
                    // progressBar.setVisibility(View.VISIBLE);

                    // BookinLayout(DistanceMesured);
                    //  new BookingPriceConfirm().execute();

                }
            }
            break;
        }
    }

    /////////////////////////////////////////////////Methods Start ***********************************************
    public void DataData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FirebaseOptions options = new FirebaseOptions.Builder()
                            .setApplicationId("1:673426911334:android:31ed7fa834e0c6f4") // Required for Analytics.
                            .setApiKey("AIzaSyD8o7uS21z1ZeN17cTdIjx6EQBV3lgJoQs") // Required for Auth.
                            .setDatabaseUrl("https://intracitydriver.firebaseio.com/") // Required for RTDB.
                            .build();
                    FirebaseApp.initializeApp(Home.this, options, "sec");
                    FirebaseApp secondary = FirebaseApp.getInstance("sec");
                    FirebaseDatabase secondaryDatabase = FirebaseDatabase.getInstance(secondary);

                    secondaryDatabase.getReference(Global.Driver_Contact).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                                String location = dataSnapshot.getValue().toString();
                                String[] strings = location.split("=");
                                String latitude = strings[1].replaceAll("[^\\d.]", "");
                                String longitude = strings[2].replaceAll("[^\\d.]", "");
                                Double lat = Double.valueOf(latitude);
                                Double lon = Double.valueOf(longitude);
                                LatLng latLng = new LatLng(lat, lon);
                                DriverNewLoc = latLng;
                                if (DriverPreLoc == null) {
                                    DriverPreLoc = DriverNewLoc;
                                }

                                LatLngBounds.Builder builder = new LatLngBounds.Builder();


                            } catch (Exception e) {
                                Toast.makeText(Home.this, "" + e, Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } catch (Exception e) {
                    //  Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    Log.e("yogesh Crash Find: ", "" + e);
                }

            }
        }).start();

    }

    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onDirectionFinderStart() {

        if (isNetworkAvailable()) {
            progressDialog = ProgressDialog.show(this, "Please wait.....",
                    "Finding direction..!", true);
        }


        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onResult(@NonNull Status status) {

    }

    private boolean isNetworkAvailable() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        return connected;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);

    }
    public Boolean ValidLocationRegion(LatLng latLng){
        Boolean valid=false;
        LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(26.11993, 91.61579), new LatLng(26.20091, 91.84707));
        if (BOUNDS_INDIA.contains(latLng))
        {
            valid=true;
            
        }else 
            valid=false;
        return valid;
    }

    //////////////////////////////////ConfirmBook Layout Methpd/////////////////////////////
    public void BookinLayout(String type) {



        SelectedTruck = type;
        //  Toast.makeText(this, SelectedTruck, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Global.currntbooking == 1) {
            ProgressLayout.setVisibility(View.VISIBLE);
            pulsator.setVisibility(View.VISIBLE);
            pulsator.start();
            mMap.clear();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (ProgressLayout.getVisibility() == View.VISIBLE) {
                        Intent intent = getIntent();
                        startActivity(intent);
                        finish();
                        Toast.makeText(Home.this, "Driver Not Respond Try anthor ride", Toast.LENGTH_SHORT).show();
                    }
                }
            }, 60000);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    buildGoogleApiClient();

                    mMap.setMyLocationEnabled(true);

                }
            }
        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        String user = sharedPreferences.getString(Constant.KEY_USER_LOGIN, "");
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = hView.findViewById(R.id.user_no);
        nav_user.setText(user);

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
        MyApplication.getInstance().setConnectivityListener(this);

    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    //////////////////////////////////////////////////////////////Calling ////////////
    @Override
    public void onStop() {
        super.onStop();
        //  Toast.makeText(this, "Finish", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Toast.makeText(this, "Distroy", Toast.LENGTH_SHORT).show();
    }

    public void CancelApi() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://youlorry.com/wp-json/intracity/cancel_booking", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean msg = jsonObject.getBoolean("success");
                    if (msg) {
                        Toast.makeText(Home.this, "SuccessFully Canceled", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Home.this, Home.class));
                        finish();
                    } else {
                        Toast.makeText(Home.this, "Retry Again!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this, "Wait...." + error, Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public HashMap<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                String username = sharedPreferences.getString(Constant.KEY_USER_LOGIN, "");
                Log.e("Yogesh Error Found", Global.Quotation_ID + username);
                hashMap.put("quote_ID", Global.Quotation_ID);
                hashMap.put("username", username);
                hashMap.put("cancelled_by", "customer");

                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 20000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


///API START//////////////////////////////////////////////////////////////////////////////////////////

    public void PulseProgressBar() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pulsator.stop();
                pulsator.setVisibility(View.GONE);
                ProgressLayout.setVisibility(View.GONE);
                BookReqLayout.setVisibility(View.GONE);

            }
        }, 100);

    }

    ////////////////////////////////////////////////////Alert For Success//////////////////////////
    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.aleartboxfinish, null);
        dialogBuilder.setView(dialogView);
        progressBar.setVisibility(View.GONE);


        //  dialogBuilder.setTitle("Custom dialog");Pulse
        // dialogBuilder.setMessage("Enter text below");
        dialogBuilder.setPositiveButton("Ok thanks", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        LayoutCurrentBooking.setVisibility(View.VISIBLE);
                        mMap.clear();
                        SearchLayout.setVisibility(View.GONE);
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ContextCompat.checkSelfPermission(Home.this,
                                    Manifest.permission.ACCESS_FINE_LOCATION)
                                    == PackageManager.PERMISSION_GRANTED) {
                                buildGoogleApiClient();
                                polyanimatiomn(currentPosition.latitude, currentPosition.longitude, Global.Driver_Lat, Global.Driver_Lang);
                                DataData();
                                mMap.setMyLocationEnabled(true);

                            }
                        }


                    }
                }, 2000);
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        b.show();
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.GREEN;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
            AlertDialog alertDialog = new AlertDialog.Builder(Home.this).create();

            alertDialog.setTitle("Info");
            alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
            //alertDialog.setIcon(R.drawable.alerticon);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    checkConnection();
                }
            });

            alertDialog.show();
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.myLocationButton), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();

    }

    private void enableLoc() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(Home.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            Log.d("Location error", "Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {

                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(Home.this, REQUEST_LOCATION);


                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        }
    }

    ///////////////////////////////////FireBase Methods/////////////////////
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

       /* if (!TextUtils.isEmpty(regId))
            Toast.makeText(this, "" + regId, Toast.LENGTH_SHORT).show();
            // txtRegId.setText("Firebase Reg Id: " + regId);
        else
            Toast.makeText(this, "Mot Found", Toast.LENGTH_SHORT).show();*/
        //txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    /////////////////////////////////////////////UNSUSED METHODES//////////////////////////////////////
    public void StartAnimation() {
        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.LEFT);
        slide.setDuration(getResources().getInteger(R.integer.long_run));
        getWindow().setReenterTransition(slide);

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public void polyanimatiomn(Double slat, Double slang, Double dlat, Double dLang) {
        final LatLng defaultlat = new LatLng(28.610029, 77.372804);
        LatLng pickup = null;
        if (Global.Driver_Lat != null && Global.Driver_Lang != null) {
            DriverNewLoc = new LatLng(Global.Driver_Lat, Global.Driver_Lang);
        } else {
            DriverNewLoc = defaultlat;
        }

        try {
            String requestUrl = null;
            requestUrl = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "mode=driving&"
                    + "transit_routing_preference=less_driving&"
                    + "origin=" + dlat + "," + dLang + "&"
                    + "destination=" + slat + "," + slang + "&"
                    + "key=" + getResources().getString(R.string.google_directions_key);
            Log.d(TAG, requestUrl);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    requestUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response + "");
                            progressBar.setVisibility(View.INVISIBLE);
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
                                CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2);
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
                                        .position(polyLineList.get(polyLineList.size() - 1)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_green)));

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
                                marker = mMap.addMarker(new MarkerOptions().position(defaultlat)
                                        .flat(true)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car)));
                                handler = new Handler();
                                index = -1;
                                next = 1;


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

    public void AnimateNewLocationMarker() {
        if (index < polyLineList.size() - 1) {
            index++;
            next = index + 1;
        }
        startPosition = DriverPreLoc;
        endPosition = DriverNewLoc;
       /* if (index < polyLineList.size() - 1) {
            startPosition = polyLineList.get(index);
            endPosition = polyLineList.get(next);
            startPosition=DriverPreLoc;
            endPosition=DriverNewLoc;
        }*/
        try {
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

        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        DriverPreLoc = endPosition;
    }

    private float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }

    /////////////////////////////////

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

    private interface LatLngInterpolator {
        LatLng interpolate(float fraction, LatLng a, LatLng b);

        class LinearFixed implements LatLngInterpolator {
            @Override
            public LatLng interpolate(float fraction, LatLng a, LatLng b) {
                double lat = (b.latitude - a.latitude) * fraction + a.latitude;
                double lngDelta = b.longitude - a.longitude;
                // Take the shortest path across the 180th meridian.
                if (Math.abs(lngDelta) > 180) {
                    lngDelta -= Math.signum(lngDelta) * 360;
                }
                double lng = lngDelta * fraction + a.longitude;
                return new LatLng(lat, lng);
            }
        }
    }

    private class BookingPriceConfirm extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://youlorry.com/wp-json/intracity/youlorry_fare_estimate", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (progressDialogmain.isShowing())
                        progressDialogmain.dismiss();

                    //   ConLayoutCurrentBooking.setVisibility(View.VISIBLE);

                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Boolean msg = jsonObject.getBoolean("success");
                        if (msg) {

                            String string = jsonObject.getString("final_fare_estimate");
                            // CalculatedAmount.setText(string);
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Home.this);
                                Intent intent = new Intent(Home.this, BookingConfiremActivity.class);
                                intent.putExtra(Constant.KEY_AUTH_TYPE, ExplodeJava);
                                intent.putExtra(Constant.Key_Title, "Explode by java");
                                intent.putExtra("Olat", Origin.latitude);
                                intent.putExtra("Olong", Origin.longitude);
                                intent.putExtra("Dlat", Destination.latitude);
                                intent.putExtra("Dlong", Destination.longitude);
                                intent.putExtra("oname", PickUp.getText().toString());
                                intent.putExtra("dname", DropCity.getText().toString());
                                intent.putExtra("distance",SelectedTruck);
                                intent.putExtra("time_duration", TimeDuration);
                                intent.putExtra("price", string);
                                startActivity(intent, activityOptions.toBundle());
                                //new BookingPriceConfirm().execute();

                            } else {
                                Intent intent = new Intent(Home.this, BookingConfiremActivity.class);
                                intent.putExtra(Constant.Key_Title, "Explode by java");
                                intent.putExtra("Olat", Origin.latitude);
                                intent.putExtra("Olong", Origin.longitude);
                                intent.putExtra("Dlat", Destination.latitude);
                                intent.putExtra("Dlong", Destination.longitude);
                                intent.putExtra("distance", DistMesure);
                                intent.putExtra("time_duration", TimeDuration);
                                intent.putExtra("oname", PickUp.getText().toString());
                                intent.putExtra("dname", DropCity.getText().toString());
                                intent.putExtra("price", string);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(Home.this, "Retry!", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialogmain.dismiss();
                    Toast.makeText(Home.this, "Please try again" , Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                public HashMap<String, String> getParams() {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("destination", DropCity.getText().toString());
                    hashMap.put("vehicle_type", "truck_"+SelectedTruck);

                    return hashMap;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            int socketTimeout = 20000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            requestQueue.add(stringRequest);
            return null;
        }
    }

    private class TruckListUrl extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {

            StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://youlorry.com/wp-json/intracity/dashboard", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Boolean msg = jsonObject.getBoolean("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        if (msg) {
                            Global.Refresh = 1;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String catname = object.getString("category_name");
                                String time = object.getString("time_in_minutes");
                                if (nameslist.size() < 4) {
                                    nameslist.add(catname);
                                    timelist.add(time);
                                }

                            }

                        }
                     //   adapter = new TRUCKS(Home.this, integers, secondicon, nameslist, timelist);
                      //  recyclerView.setAdapter(adapter);

        /*
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    },10);
*/

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {

            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            int socketTimeout = 20000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            requestQueue.add(stringRequest);
            return null;
        }

    }

    public class DistanceMajor extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation

            Toast.makeText(Home.this, "Wait requesting...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = Constant.URL + Constant.ORIGIN + PickUp.getText().toString() + Constant.DESTINATION + DropCity.getText().toString() + Constant.KEY + Constant.DistanceMatrixApiKey;
            url = url.replaceAll(" ", "%20");
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("rows");
                        JSONObject object = jsonArray.getJSONObject(0);
                        JSONArray jsonArray1 = object.getJSONArray("elements");
                        JSONObject dataobj = jsonArray1.getJSONObject(0);
                        JSONObject distanceobj = dataobj.getJSONObject("distance");
                        String Distance = distanceobj.getString("text");
                        DistMesure = distanceobj.getString("value");
                        JSONObject durationobj = dataobj.getJSONObject("duration");
                        TimeDuration = durationobj.getString("text");
                        String durvalue = durationobj.getString("value");
                            if (distStatus==1){
                                new BookingPriceConfirm().execute();
                            }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    // Removed this line if you dont need it or Use application/json
                    // params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            int socketTimeout = 20000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            requestQueue.add(stringRequest);
            return null;
        }
    }


}



