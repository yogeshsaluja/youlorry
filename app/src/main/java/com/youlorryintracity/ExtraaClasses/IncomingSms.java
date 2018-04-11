package com.youlorryintracity.ExtraaClasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;

/**
 * Created by hp on 7/20/2017.
 */
public class IncomingSms extends BroadcastReceiver
{
    @Override
    public void onReceive(final Context context, Intent intent)
    {
      //  Toast.makeText(context, "Sending....", Toast.LENGTH_SHORT).show();
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null)
            {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj .length; i++)
                {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[])                                                                                                    pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber ;
                    String message = currentMessage .getDisplayMessageBody();
                   //Toast.makeText(context, "Fetch...."+currentMessage, Toast.LENGTH_SHORT).show();
                    try
                    {
                        if (!senderNum .equals(""))
                        {

                             final String numberOnly= message.replaceAll("[^0-9]", "");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                   // Toast.makeText(context, "Sending....", Toast.LENGTH_SHORT).show();
                                    Intent myIntent = new Intent("otp");
                                    myIntent.putExtra("message",numberOnly);
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(myIntent);
                                    // Show Alert
                                   /* OTPNumber Sms = new OTPNumber();
                                    Sms.recivedSms(numberOnly );
                                   */
                                }
                            },4000);


                        }
                    }
                    catch(Exception e){}

                }
            }

        } catch (Exception e)
        {

        }
    }
 }