package com.inti.ricoh.julfi.idap.Student;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inti.ricoh.julfi.idap.Helper.DBHelper;
import com.inti.ricoh.julfi.idap.Helper.HTTPHelper;
import com.inti.ricoh.julfi.idap.Helper.Helper;
import com.inti.ricoh.julfi.idap.MainActivity;
import com.inti.ricoh.julfi.idap.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.inti.ricoh.julfi.idap.Helper.Config.API_ERROR;
import static com.inti.ricoh.julfi.idap.Helper.Config.API_FAIL;
import static com.inti.ricoh.julfi.idap.Helper.Config.API_STUDENT_INFO;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_BACC;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_BHOLDER;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_BNAME;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_CITIZEN;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_CITY;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_COUNTRY;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_DOB;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_EMAIL;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_IC;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_MAJOR;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_MATRI;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_MOBILE;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_NAME;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_STATE;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_STREET;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_STYPE;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_TAG;
import static com.inti.ricoh.julfi.idap.Helper.Config.SHARED_PREFERENCE;
import static com.inti.ricoh.julfi.idap.Helper.Config.SP_INVALID;
import static com.inti.ricoh.julfi.idap.Helper.Config.SP_KEY;
import static com.inti.ricoh.julfi.idap.Helper.Config.SP_UPDATED;
import static com.inti.ricoh.julfi.idap.Helper.Config.SP_VALID;

public class StudentMainMenu extends AppCompatActivity {

    private MaterialProgressBar progressBar;
    private RelativeLayout layout_hide;
    private CoordinatorLayout coordinatorLayout;
    private TextView tv_loading;
    private ImageButton ib_apply,ib_track,ib_user,ib_logout;
    private boolean  flag;
    String CHECK;
    private String INTI_ID,
            I_NAME = "Not available",
            I_EMAIL = "Not available",
            I_MAJOR = "Not available",
            I_IC = "Not available",
            I_BNAME = "Not available",
            I_BACC = "Not available",
            I_BHOLDER = "Not available",
            I_MATRI = "Not available",
            I_STYPE = "Not available",
            I_MOBILE = "Not available",
            I_CITIZEN = "Not available",
            I_DOB = "Not available",
            I_ADDR = "Not available";

    private Context context = this;
    private Helper helper = new Helper(StudentMainMenu.this);
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        INTI_ID = sharedPreferences.getString(SP_KEY,"");
        CHECK = sharedPreferences.getString(SP_UPDATED,"");

        ib_apply = (ImageButton) this.findViewById(R.id.ib_apply);
        ib_track = (ImageButton) this.findViewById(R.id.ib_track);
        ib_user = (ImageButton) this.findViewById(R.id.ib_user);
        ib_logout = (ImageButton) this.findViewById(R.id.ib_logout);

        progressBar = (MaterialProgressBar) this.findViewById(R.id.progress);
        layout_hide = (RelativeLayout) this.findViewById(R.id.layout_hide);
        coordinatorLayout = (CoordinatorLayout) this.findViewById(R.id.layout_selection);

        tv_loading = (TextView) this.findViewById(R.id.tv_loading);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ib_apply.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("PrivateResource")
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, StudentOption.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                        ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
                        finish();
                    }
                });
                ib_user.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("PrivateResource")
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, StudentInfo.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                        ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
                        finish();
                    }
                });
                ib_track.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("PrivateResource")
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, TrackRequest.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                        ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
                        finish();
                    }
                });
                ib_logout.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("PrivateResource")
                    @Override
                    public void onClick(View view) {
                        SharedPreferences settings = context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
                        settings.edit().clear().apply();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                        ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
                        finish();
                    }
                });
            }
        });
        if(CHECK.equals(SP_INVALID) || TextUtils.isEmpty(CHECK)){
            GetInfo();
        }
    }
    public void GetInfo(){

        class Student extends AsyncTask<String, Void, String> {

            private HTTPHelper ruc = new HTTPHelper();

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("sid",params[0]);
                return ruc.sendPostRequest(API_STUDENT_INFO,map);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                ShowProgress(true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                ShowProgress(false);

                switch (s){
                    case API_FAIL:{
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "API Error. Please contact office", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        break;
                    }
                    case API_ERROR:{
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Something went wrong. Please try again later.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        break;
                    }
                    default:
                        parseJson(s);
                        break;
                }

                if(I_NAME.equals(I_EMAIL)){
                    GetInfo();
                    System.out.println("-----Going to fetch again------");
                }
                if(flag){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(SP_UPDATED,SP_VALID);
                    editor.apply();
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(SP_UPDATED,SP_INVALID);
                    editor.apply();
                }
            }
        }
        Student info = new Student();
        info.execute(INTI_ID);
    }

    public void parseJson(String json){

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray(INFO_TAG);

            for(int i = 0; i < array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                I_NAME = object.getString(INFO_NAME);
                I_EMAIL = object.getString(INFO_EMAIL);
                I_STYPE = object.getString(INFO_STYPE);
                I_MOBILE = object.getString(INFO_MOBILE);
                I_CITIZEN = object.getString(INFO_CITIZEN);
                I_DOB = object.getString(INFO_DOB);
                I_MAJOR = object.getString(INFO_MAJOR);
                I_IC = object.getString(INFO_IC);
                I_BNAME = object.getString(INFO_BNAME);
                I_BACC = object.getString(INFO_BACC);
                I_BHOLDER = object.getString(INFO_BHOLDER);
                I_MATRI = object.getString(INFO_MATRI);
                I_ADDR = object.getString(INFO_STREET)+ "," + object.getString(INFO_CITY) + "," + object.getString(INFO_STATE) + "," + object.getString(INFO_COUNTRY);
            }

            DBHelper dbHelper = new DBHelper(StudentMainMenu.this);
            flag = dbHelper.AddInfo(I_NAME,I_MATRI,I_MAJOR,I_IC,I_BNAME,I_BACC,I_BHOLDER,I_EMAIL,I_STYPE,I_MOBILE,I_CITIZEN,I_DOB,I_ADDR);

            System.out.println("DATA INSERTION RESULT - > "+flag);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void ShowProgress(boolean flag){
        if(!flag){
            layout_hide.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            tv_loading.setVisibility(View.INVISIBLE);
        }else{
            layout_hide.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            tv_loading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        helper.AlertExit();
    }
}
