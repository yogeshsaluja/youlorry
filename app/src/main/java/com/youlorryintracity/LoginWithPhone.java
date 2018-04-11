package com.youlorryintracity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.transition.Transition;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.youlorryintracity.ExtraaClasses.CheckValidity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.Gravity.LEFT;

public class LoginWithPhone extends AppCompatActivity {

    @BindView(R.id.ivback)
    ImageView ivBack;

    @BindView(R.id.etPhoneNo)
    EditText etPhoneNo;

    @BindView(R.id.tvMoving)
    TextView tvMoving;

    @BindView(R.id.ivFlag)
    ImageView ivFlag;

    @BindView(R.id.tvCode)
    TextView tvCode;

    @BindView(R.id.fabProgressCircle)
    FABProgressCircle fabProgressCircle;

    @BindView(R.id.rootFrame)
    FrameLayout rootFrame;

    @BindView(R.id.llphone)
    LinearLayout llPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_phone);

        ButterKnife.bind(this);
        setupWindowAnimations();

    }

    private void setupWindowAnimations() {

        ChangeBounds enterTransition = new ChangeBounds();
        enterTransition.setDuration(1000);
        enterTransition.setInterpolator(new DecelerateInterpolator(4));
        enterTransition.addListener(enterTransitionListener);
        getWindow().setSharedElementEnterTransition(enterTransition);

        ChangeBounds returnTransition = new ChangeBounds();
        returnTransition.setDuration(1000);
        returnTransition.addListener(returnTransitionListener);
        getWindow().setSharedElementReturnTransition(returnTransition);

        Slide exitSlide = new Slide(LEFT);
        exitSlide.setDuration(700);
        exitSlide.addListener(exitTransitionListener);
        exitSlide.addTarget(R.id.llphone);
        exitSlide.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(exitSlide);

        Slide reenterSlide = new Slide(LEFT);
        reenterSlide.setDuration(700);
        reenterSlide.addListener(reenterTransitionListener);
        reenterSlide.setInterpolator(new DecelerateInterpolator(2));
        reenterSlide.addTarget(R.id.llphone);
        getWindow().setReenterTransition(reenterSlide);
        if (ContextCompat.checkSelfPermission(LoginWithPhone.this,
                android.Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginWithPhone.this,
                    android.Manifest.permission.READ_SMS)) {


            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(LoginWithPhone.this,
                        new String[]{Manifest.permission.READ_SMS},
                        123);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }

    Transition.TransitionListener enterTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {
            ivBack.setImageAlpha(0);
        }

        @Override
        public void onTransitionEnd(Transition transition) {

            ivBack.setImageAlpha(255);
            Animation animation = AnimationUtils.loadAnimation(LoginWithPhone.this, R.anim.slide_right);
            ivBack.startAnimation(animation);

        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    };


    Transition.TransitionListener returnTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {

            InputMethodManager imm = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etPhoneNo.getWindowToken(), 0);
            tvMoving.setText(null);
            tvMoving.setHint(getString(R.string.enter_no));
            ivFlag.setImageAlpha(0);
            tvCode.setAlpha(0);
            etPhoneNo.setVisibility(View.GONE);
            etPhoneNo.setCursorVisible(false);
            etPhoneNo.setBackground(null);
            Animation animation = AnimationUtils.loadAnimation(LoginWithPhone.this, R.anim.slide_left);
            ivBack.startAnimation(animation);
        }

        @Override
        public void onTransitionEnd(Transition transition) {


        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    };

    Transition.TransitionListener exitTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {

            rootFrame.setAlpha(1f);
            fabProgressCircle.hide();
            llPhone.setBackgroundColor(Color.parseColor("#EFEFEF"));
        }

        @Override
        public void onTransitionEnd(Transition transition) {


        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    };


    Transition.TransitionListener reenterTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {


        }

        @Override
        public void onTransitionEnd(Transition transition) {

            llPhone.setBackgroundColor(Color.parseColor("#FFFFFF"));
            etPhoneNo.setCursorVisible(true);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    };


    @OnClick(R.id.fabProgressCircle)
    void nextPager() {

        etPhoneNo.setCursorVisible(false);
        rootFrame.setAlpha(0.4f);
        fabProgressCircle.show();

        if (CheckValidity.isvalidphone(etPhoneNo.getText().toString(),etPhoneNo))
        {
            SendOtpData();

        }else {
            etPhoneNo.setCursorVisible(true);
            rootFrame.setAlpha(1f);
            fabProgressCircle.hide();
            Toast.makeText(this, "Enter Mobile  Number", Toast.LENGTH_SHORT).show();

        }


     }

    @OnClick(R.id.ivback)
    void startReturnTransition() {
        super.onBackPressed();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 123: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Allowed", Toast.LENGTH_SHORT).show();

                } else {

                    ActivityCompat.requestPermissions(LoginWithPhone.this,
                            new String[]{Manifest.permission.READ_SMS},
                            123);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    public void SendOtpData()
    {
        final ProgressDialog progressDialog=new ProgressDialog(LoginWithPhone.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();


        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://youlorry.com/wp-json/intracity/login-send-otp", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Boolean result=jsonObject.getBoolean("success");

                    if (result)
                    {
                        Toast.makeText(LoginWithPhone.this, "Done", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginWithPhone.this, PasswordActivity.class);
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(LoginWithPhone.this);
                        intent.putExtra("phone",etPhoneNo.getText().toString());
                        startActivity(intent, options.toBundle());
                        finish();
                    }else {
                        String errormsg=jsonObject.getString("error");
                        onBackPressed();
                        Toast.makeText(LoginWithPhone.this, errormsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginWithPhone.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public HashMap<String,String> getParams()
            {
                HashMap<String,String> hashMap=new HashMap<String, String>();
                hashMap.put("phone",etPhoneNo.getText().toString().trim());
                hashMap.put("intracity_app_type","customer");

                return hashMap;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(LoginWithPhone.this);
        requestQueue.add(stringRequest);

    }

}
