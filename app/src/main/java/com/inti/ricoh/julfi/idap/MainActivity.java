package com.inti.ricoh.julfi.idap;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.inti.ricoh.julfi.idap.Employee.EmployeeActivity;
import com.inti.ricoh.julfi.idap.Helper.Config;
import com.inti.ricoh.julfi.idap.Helper.ConvertToHash;
import com.inti.ricoh.julfi.idap.Helper.HTTPHelper;
import com.inti.ricoh.julfi.idap.Helper.Helper;
import com.inti.ricoh.julfi.idap.Student.StudentMainMenu;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Objects;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.inti.ricoh.julfi.idap.Helper.Config.API_INVALID;
import static com.inti.ricoh.julfi.idap.Helper.Config.API_isStaff;
import static com.inti.ricoh.julfi.idap.Helper.Config.API_isStudent;
import static com.inti.ricoh.julfi.idap.Helper.Config.SHARED_PREFERENCE;
import static com.inti.ricoh.julfi.idap.Helper.Config.SP_KEY;
import static com.inti.ricoh.julfi.idap.Helper.Config.SP_STATUS;

public class MainActivity extends AppCompatActivity {

    private EditText et_id,et_pass;
    private Button btn_login;
    private CoordinatorLayout coordinatorLayout;
    private RelativeLayout layout_hide;
    private MaterialProgressBar progressBar;

    private String USER_ID,USER_PASSWORD;
    private String API_LOGIN = Config.API_LOGIN;

    private Context context = this;
    private SharedPreferences sharedPreferences;

    private Helper helper = new Helper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE,Context.MODE_PRIVATE);

        et_id = (EditText) this.findViewById(R.id.et_id);
        et_pass = (EditText) this.findViewById(R.id.et_pass);
        btn_login = (Button) this.findViewById(R.id.btn_login);
        coordinatorLayout = (CoordinatorLayout)this.findViewById(R.id.layout_main);
        layout_hide = (RelativeLayout)this.findViewById(R.id.layout_hide);
        progressBar = (MaterialProgressBar) this.findViewById(R.id.progress);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                btn_login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        USER_ID = et_id.getText().toString().toUpperCase().trim();
                        USER_PASSWORD = et_pass.getText().toString().trim();

                        if(!validate(USER_ID)){

                            et_id.setError("Do Not Leave INTI ID Blank");

                            return;
                        }
                        if(!validate(USER_PASSWORD)){

                            et_pass.setError("Do Not Leave Password Blank");

                            return;
                        }else{
                            try {
                                USER_PASSWORD = ConvertToHash.SHA1(USER_PASSWORD);
                            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

                        RunAsync(USER_ID,USER_PASSWORD);
                    }
                });

            }
        });
    }

    public void RunAsync(final String user, String pass){

        class LoginUser extends AsyncTask<String, Void, String> {

            private HTTPHelper ruc = new HTTPHelper();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                ShowProgress(true);
            }

            @SuppressLint("PrivateResource")
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                ShowProgress(false);

                if(!helper.IsNetworkAvailable()){
                    helper.Alert();
                }

                switch (s){

                    case API_isStudent:{
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(SP_KEY,user);
                        editor.putString(SP_STATUS,API_isStudent);
                        editor.apply();

                        Intent intent = new Intent(context,StudentMainMenu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                        ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
                        finish();
                        break;
                    }
                    case API_isStaff:{
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(SP_KEY,user);
                        editor.putString(SP_STATUS,API_isStaff);
                        editor.apply();

                        Intent intent = new Intent(context,EmployeeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                        ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
                        finish();
                        break;
                    }
                    case API_INVALID:{
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Login Failed. Please Check INTI ID or Password", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        break;
                    }
                    default:
                        break;

                }
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("username",params[0]);
                data.put("password",params[1]);

                return ruc.sendPostRequest(API_LOGIN,data);
            }
        }

        LoginUser ru = new LoginUser();
        ru.execute(user,pass);
    }

    public Boolean validate(String content){
        return !Objects.equals(content, "");
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

    @Override
    public void onBackPressed() {
        helper.AlertExit();
    }
}
