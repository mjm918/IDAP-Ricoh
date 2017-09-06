package com.inti.ricoh.julfi.idap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.inti.ricoh.julfi.idap.Employee.EmployeeActivity;
import com.inti.ricoh.julfi.idap.Helper.HTTPHelper;
import com.inti.ricoh.julfi.idap.Helper.Helper;
import com.inti.ricoh.julfi.idap.Student.StudentMainMenu;
import com.onesignal.OSPermissionObserver;
import com.onesignal.OSPermissionStateChanges;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;

import java.util.HashMap;

import static com.inti.ricoh.julfi.idap.Helper.Config.API_ERROR;
import static com.inti.ricoh.julfi.idap.Helper.Config.API_FAIL;
import static com.inti.ricoh.julfi.idap.Helper.Config.API_INVALID;
import static com.inti.ricoh.julfi.idap.Helper.Config.API_TOKEN_REG;
import static com.inti.ricoh.julfi.idap.Helper.Config.API_VALID;
import static com.inti.ricoh.julfi.idap.Helper.Config.API_isStaff;
import static com.inti.ricoh.julfi.idap.Helper.Config.API_isStudent;
import static com.inti.ricoh.julfi.idap.Helper.Config.SHARED_PREFERENCE;
import static com.inti.ricoh.julfi.idap.Helper.Config.SP_KEY;
import static com.inti.ricoh.julfi.idap.Helper.Config.SP_STATUS;

public class SplashScreen extends AppCompatActivity implements OSPermissionObserver {

    private Context context = this;

    String INTI_ID,S_STATUS,ONESIGNAL_PLAYERID,ONESIGNAL_PUSHTOKEN;
    int SPLASH_TIMEOUT = 3000;

    private Helper helper = new Helper(SplashScreen.this);
    SharedPreferences sharedPreferences;

    @SuppressLint("PrivateResource")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        OneSignal.addPermissionObserver(this);
        OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();
        status.getPermissionStatus().getEnabled();

        status.getSubscriptionStatus().getSubscribed();
        status.getSubscriptionStatus().getUserSubscriptionSetting();
        ONESIGNAL_PLAYERID = status.getSubscriptionStatus().getUserId();
        ONESIGNAL_PUSHTOKEN = status.getSubscriptionStatus().getPushToken();

        System.out.println("One Signal Player ID - "+ONESIGNAL_PLAYERID+" Push Token - "+ONESIGNAL_PUSHTOKEN);

        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        INTI_ID = sharedPreferences.getString(SP_KEY,"");
        S_STATUS = sharedPreferences.getString(SP_STATUS,"");

        if(!helper.isInternetAvailble()){
            helper.Alert();
        }else{
            if(!TextUtils.isEmpty(INTI_ID)){
                switch (S_STATUS){
                    case API_isStaff:{
                        Intent intent = new Intent(context, EmployeeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                        ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
                        finish();
                        break;
                    }
                    case API_isStudent:{
                        Intent intent = new Intent(context, StudentMainMenu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                        ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
                        finish();
                        break;
                    }
                }
                RegisterDeviceWithUser();
            }else{
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                        ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
                        finish();
                    }
                }, SPLASH_TIMEOUT);
            }
        }
    }
    public void RegisterDeviceWithUser(){

        class RegisterDevice extends AsyncTask<String,Void,String>{

            private HTTPHelper ruc = new HTTPHelper();

            @Override
            protected String doInBackground(String... strings) {
                HashMap<String,String>map = new HashMap<String,String>();
                map.put("matri",strings[0]);
                map.put("token",strings[1]);
                map.put("playerid",strings[2]);
                return ruc.sendPostRequest(API_TOKEN_REG,map);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                switch (s){
                    case API_INVALID:{
                        RegisterDeviceWithUser();
                        break;
                    }
                    case API_ERROR:{
                        RegisterDeviceWithUser();
                        break;
                    }
                    case API_VALID:{
                        System.out.println("Successfully registered device with player id and push token in backend");
                        break;
                    }
                    case API_FAIL:{
                        System.out.println("Device already registered with this matriculation id. updated info");
                        break;
                    }
                    case "":{
                        RegisterDeviceWithUser();
                        break;
                    }
                }
            }
        }
        RegisterDevice rd = new RegisterDevice();
        rd.execute(INTI_ID,ONESIGNAL_PUSHTOKEN,ONESIGNAL_PLAYERID);
    }
    public void onOSPermissionChanged(OSPermissionStateChanges stateChanges) {
        if (stateChanges.getFrom().getEnabled() &&
                !stateChanges.getTo().getEnabled()) {
            new AlertDialog.Builder(this)
                    .setMessage("Your Notifications Service is disabled! Please change from mobile phone settings")
                    .show();
        }

        System.out.println("Debug onOSPermissionChanged for OneSignal: " + stateChanges);
    }
    @Override
    public void onBackPressed() {
        helper.AlertExit();
    }
}
