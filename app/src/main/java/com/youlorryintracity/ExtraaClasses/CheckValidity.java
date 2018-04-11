package com.youlorryintracity.ExtraaClasses;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user_adnig on 6/6/16.
 */

public class CheckValidity {
    public static boolean isBlank(String name, EditText edt){
        boolean status=false;
        if(name.equalsIgnoreCase(" ")||name.length()<1){
            status=false;
            edt.setError("Can't be blank!");
        }else{
            status=true;
        }
        return status;
    }

    public static boolean isValidEmail(String email, EditText edt) {
        boolean status =false;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches()==false){
            status=false;
            edt.setError("Enter valid Email Id");
        }else{
            status=true;
        }
        return status;
    }

    public static boolean isContactValid(String contact){
        boolean status=false;
        return status;
    }
    public static boolean isUserNameValid(String username, EditText edt){
        boolean status=false;
        if(username.equalsIgnoreCase("")||username.length()<2){
            status=false;
            edt.setError("Enter Valid User Name");
        }else{
            status=true;
        }
        return status;
    }
    public static boolean isMisslaneousValid(String email, EditText edt){
        boolean status=false;
        if(email.equalsIgnoreCase("")||email.length()<10){
            status=false;
            edt.setError("Write at least 10 words");
        }else{
            status=true;
        }
        return status;
    }

    public static boolean isvalidphone(String phone, EditText edt){
        boolean status=false;
        if(phone.equalsIgnoreCase("")||phone.length()<10){
            status=false;
            edt.setError("Enter 10 digit number");
        }else{
            status=true;
        }
        return status;
    }


    public static boolean checkPasswordLength(String pass, EditText edt){
        boolean status=false;

        if(pass.length()<5){
            status=false;
            edt.setError("Password must be 5 digit.");
        }else
        {


            status=true;
        }



        return status;
    }
    public static boolean validOTPcode(String pass, EditText edt){
        boolean status=false;

        if(pass.length()<3){
            status=false;
            edt.setError("OTP Code must be 4 digit.");
        }else{
            status=true;
        }



        return status;
    }


    public static boolean isPasswordMatched(String pass, String repass, EditText edt){
        boolean status=false;

        if(pass.equals(repass)){
            status=true;

        }else{
            status=false;
            edt.setError("Password Missmatch");
        }
        if(pass.length()<1){
            status=false;
            edt.setError("Password Required");
        }



        return status;
    }




    public static boolean isName(String s, EditText edt) {
        boolean status=false;
        if(s.equalsIgnoreCase("")||s.length()<2){
            status=false;
            edt.setError("Enter Valid First Name");
        }else{
            status=true;
        }
        return status;
    }
}