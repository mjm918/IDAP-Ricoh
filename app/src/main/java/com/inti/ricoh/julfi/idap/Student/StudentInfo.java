package com.inti.ricoh.julfi.idap.Student;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inti.ricoh.julfi.idap.Helper.DBHelper;
import com.inti.ricoh.julfi.idap.Helper.HTTPHelper;
import com.inti.ricoh.julfi.idap.Helper.Helper;
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
import static com.inti.ricoh.julfi.idap.Helper.Config.SP_KEY;

public class StudentInfo extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private Context context = this;

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

    private TextView tv_name,tv_id,tv_stype,tv_email,tv_mobile,tv_cit,tv_dob,tv_addr;
    private RelativeLayout layout_hide;
    private CoordinatorLayout coordinatorLayout;
    private MaterialProgressBar progressBar;

    private Helper helper = new Helper(StudentInfo.this);

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        INTI_ID = sharedPreferences.getString(SP_KEY,"");

        tv_name = (TextView) this.findViewById(R.id.tv_name);
        tv_id = (TextView) this.findViewById(R.id.tv_sid);
        tv_stype = (TextView) this.findViewById(R.id.tv_stype);
        tv_email = (TextView) this.findViewById(R.id.tv_email);
        tv_mobile = (TextView) this.findViewById(R.id.tv_mobile);
        tv_cit = (TextView) this.findViewById(R.id.tv_cit);
        tv_dob = (TextView) this.findViewById(R.id.tv_dob);
        tv_addr = (TextView) this.findViewById(R.id.tv_addr);

        coordinatorLayout = (CoordinatorLayout) this.findViewById(R.id.layout_student);
        layout_hide = (RelativeLayout) this.findViewById(R.id.layout_hide);
        progressBar = (MaterialProgressBar) this.findViewById(R.id.progress);

        DBHelper dbHelper = new DBHelper(StudentInfo.this);
        Cursor cursor = dbHelper.GetAllData();

//        COLUMN_NAME + " TEXT, " +
//                COLUMN_ID + " TEXT, " +
//                COLUMN_MAJOR + " TEXT, " +
//                COLUMN_EMAIL + " TEXT, " +
//                COLUMN_STYPE + " TEXT, " +
//                COLUMN_MOBILE + " TEXT, " +
//                COLUMN_CITIZEN + " TEXT, " +
//                COLUMN_IC + " TEXT, " +
//                COLUMN_BNAME + " TEXT, " +
//                COLUMN_BACC + " TEXT, " +
//                COLUMN_BHOLDER + " TEXT, "+
//                COLUMN_DOB + " TEXT, " +
//                COLUMN_ADDRESS + " TEXT)";

        while(cursor.moveToNext()){

            I_NAME = "Name : \n"+cursor.getString(1);
            I_EMAIL = "Email : \n"+cursor.getString(4);
            I_STYPE = "Student Type : \n"+cursor.getString(5);
            I_MOBILE = "Mobile : \n"+cursor.getString(6).replace("+6","");
            I_CITIZEN = "Citizenship : \n"+cursor.getString(7);
            I_DOB = "Date of Birth : \n"+cursor.getString(12);
            INTI_ID = "INTI ID : \n"+cursor.getString(2);
            I_ADDR = cursor.getString(13);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_name.setText(I_NAME);
                    tv_id.setText(INTI_ID);
                    tv_email.setText(I_EMAIL);
                    tv_stype.setText(I_STYPE);
                    tv_mobile.setText(I_MOBILE);
                    tv_cit.setText(I_CITIZEN);
                    tv_dob.setText(I_DOB);
                    tv_addr.setText(I_ADDR);
                }
            });
            counter++;
        }

        if(counter == 0){
            GetInfo();
        }

    }

    public void GetInfo(){

        class Student extends AsyncTask<String, Void, String>{

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
                if(tv_name.getText().equals(context.getResources().getString(R.string.garbage))){
                    GetInfo();
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
                I_ADDR = "\n" + object.getString(INFO_STREET)+ ", " + object.getString(INFO_CITY) + ",\n" + object.getString(INFO_STATE) + ", " + object.getString(INFO_COUNTRY);
            }

            I_NAME = "Name : \n"+I_NAME;
            I_EMAIL = "Email : \n"+I_EMAIL;
            I_STYPE = "Student Type : \n"+I_STYPE;
            I_MOBILE = "Mobile : \n"+I_MOBILE.replace("+6","");
            I_CITIZEN = "Citizenship : \n"+I_CITIZEN;
            I_DOB = "Date of Birth : \n"+I_DOB;
            INTI_ID = "INTI ID : \n"+INTI_ID;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_name.setText(I_NAME);
                    tv_id.setText(INTI_ID);
                    tv_email.setText(I_EMAIL);
                    tv_stype.setText(I_STYPE);
                    tv_mobile.setText(I_MOBILE);
                    tv_cit.setText(I_CITIZEN);
                    tv_dob.setText(I_DOB);
                    tv_addr.setText(I_ADDR);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void ShowProgress(boolean flag){

        if(!flag){
            layout_hide.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }else{
            layout_hide.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("PrivateResource")
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context,StudentMainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();
        ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
        finish();
    }
}
