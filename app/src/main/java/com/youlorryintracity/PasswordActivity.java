package com.youlorryintracity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.youlorryintracity.ExtraaClasses.CheckValidity;
import com.youlorryintracity.ExtraaClasses.Constant;
import com.youlorryintracity.ExtraaClasses.Global;
import com.youlorryintracity.ExtraaClasses.Helper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.Gravity.RIGHT;

public class PasswordActivity extends AppCompatActivity {


    @BindView(R.id.fabProgressCircle)
    FABProgressCircle fabProgressCircle;

    @BindView(R.id.rootFrame)
    FrameLayout rootFrame;

    @BindView(R.id.etPass)
    EditText etPass;
    String phoneNumber;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        sharedPreferences= Helper.getSharedPref(PasswordActivity.this);
Bundle bundle=getIntent().getExtras();
phoneNumber=bundle.getString("phone");

        ButterKnife.bind(this);

        Slide enterSlide = new Slide(RIGHT);
        enterSlide.setDuration(400);
        enterSlide.addTarget(R.id.llphone);
        enterSlide.setInterpolator(new DecelerateInterpolator(2));
        getWindow().setEnterTransition(enterSlide);

        Slide returnSlide = new Slide(RIGHT);
        returnSlide.setDuration(400);
        returnSlide.addTarget(R.id.llphone);
        returnSlide.setInterpolator(new DecelerateInterpolator());
        getWindow().setReturnTransition(returnSlide);
    }

    @OnClick(R.id.fabProgressCircle)
    void nextActivity() {
        etPass.setCursorVisible(false);
        rootFrame.setAlpha(0.4f);
        fabProgressCircle.show();

        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etPass.getWindowToken(), 0);

if (CheckValidity.validOTPcode(etPass.getText().toString(),etPass))
{
    VerifyOTP();
}
else {
    rootFrame.setAlpha(1f);
    fabProgressCircle.hide();
}

     }

    @OnClick(R.id.ivback)
    void back() {
        onBackPressed();
    }
    public void VerifyOTP()
    {
        final ProgressDialog progressDialog=new ProgressDialog(PasswordActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://youlorry.com/wp-json/intracity/login-send-otp", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(PasswordActivity.this, response, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(Constant.KEY_USER_LOGIN,phoneNumber);
                editor.commit();
                Global.USER=phoneNumber;
                String token=sharedPreferences.getString(Constant.AUTH_VALUE,"");
                new UpdateToken().execute(token,phoneNumber );
                Intent intent = new Intent(PasswordActivity.this, Home.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(PasswordActivity.this);
                startActivity(intent, options.toBundle());
                finish();

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
                hashMap.put("otp_pin",etPass.getText().toString().trim());

                return hashMap;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(PasswordActivity.this);
        requestQueue.add(stringRequest);

    }
    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(this, "You Need to verify to login!", Toast.LENGTH_SHORT).show();
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
         //   Toast.makeText(context, "Sending....", Toast.LENGTH_SHORT).show();
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                etPass.setText(message);

            }
        }
    };
    protected class UpdateToken extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(final String... strings) {
            StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://youlorry.com/wp-json/device_token_id/save", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> stringMap=new HashMap<String, String>();
                    stringMap.put("token",strings[0]);
                    stringMap.put("username",strings[1]);
                    stringMap.put("intracity_app_type","customer");
                    return stringMap;

                }
            };

            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
            int socketTimeout = 20000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            requestQueue.add(stringRequest);
            return null;
        }

    }

}
