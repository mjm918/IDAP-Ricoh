package com.inti.ricoh.julfi.idap.Student;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.inti.ricoh.julfi.idap.Helper.HTTPHelper;
import com.inti.ricoh.julfi.idap.Helper.Helper;
import com.inti.ricoh.julfi.idap.R;

import java.util.HashMap;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.inti.ricoh.julfi.idap.Helper.Config.API_TRACK;
import static com.inti.ricoh.julfi.idap.Helper.Config.REQ_ELIGBLE;
import static com.inti.ricoh.julfi.idap.Helper.Config.REQ_WITHDRAW;
import static com.inti.ricoh.julfi.idap.Helper.Config.SHARED_PREFERENCE;
import static com.inti.ricoh.julfi.idap.Helper.Config.SP_KEY;

public class StudentOption extends AppCompatActivity {

    private Helper helper = new Helper(StudentOption.this);
    private Button btn_withdraw,btn_service,btn_trans;
    private MaterialProgressBar progressBar;
    private RelativeLayout relativeLayout;
    private Context context = this;
    SharedPreferences sharedPreferences;
    private String INTI_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_option);

        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        INTI_ID = sharedPreferences.getString(SP_KEY,"");

        btn_withdraw = (Button) this.findViewById(R.id.btn_withdraw);
        btn_service = (Button) this.findViewById(R.id.btn_service);
        btn_trans = (Button) this.findViewById(R.id.btn_trans);

        relativeLayout = (RelativeLayout) this.findViewById(R.id.layout_hide);
        progressBar = (MaterialProgressBar) this.findViewById(R.id.progress);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btn_withdraw.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("PrivateResource")
                    @Override
                    public void onClick(View v) {
                        CheckRequest(REQ_WITHDRAW);
                    }
                });
                btn_service.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("PrivateResource")
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,SSDActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                        ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
                        finish();
                    }
                });
                btn_trans.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("PrivateResource")
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,TranscriptActvity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                        ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
                        finish();
                    }
                });
            }
        });
    }

    public void CheckRequest(final String request){
        class Trackuser extends AsyncTask<String,Void,String>{

            private HTTPHelper ruc = new HTTPHelper();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                ShowProgress(true);
                if(!helper.IsNetworkAvailable()){
                    helper.Alert();
                }
            }

            @SuppressLint("PrivateResource")
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                System.out.println("TRACKING RESULT - >"+s);

                if(s.equals("")){
                    CheckRequest(request);
                }

                if(s.equals(REQ_ELIGBLE)){
                    Intent intent = new Intent(context,WithdrawActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                    ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
                    finish();
                }else{
                    helper.CustomDialog("Attention!","You have already requested for withdrawal. Please have patience and you will be notified shortly. Thank you");
                }
            }

            @Override
            protected String doInBackground(String... strings) {
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("sid",strings[0]);
                map.put("request",strings[1]);
                return ruc.sendPostRequest(API_TRACK,map);
            }
        }
        Trackuser user = new Trackuser();
        user.execute(INTI_ID,request);
    }

    public void ShowProgress(boolean flag){

        if(!flag){
            relativeLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }else{
            relativeLayout.setVisibility(View.INVISIBLE);
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
