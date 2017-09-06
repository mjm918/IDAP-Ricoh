package com.inti.ricoh.julfi.idap.Student;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.inti.ricoh.julfi.idap.Helper.HTTPHelper;
import com.inti.ricoh.julfi.idap.Helper.Helper;
import com.inti.ricoh.julfi.idap.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.inti.ricoh.julfi.idap.Helper.Config.API_INVALID;
import static com.inti.ricoh.julfi.idap.Helper.Config.API_TRACK_PROGRESS;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_DATE;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_ID;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_STATUS;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_TAG;
import static com.inti.ricoh.julfi.idap.Helper.Config.SHARED_PREFERENCE;
import static com.inti.ricoh.julfi.idap.Helper.Config.SP_KEY;

public class TrackRequest extends AppCompatActivity {

    private String id,date,current,status,INTI_ID;

    private Context context = this;
    SharedPreferences sharedPreferences;

    private TextView tv_id,tv_date,tv_current;
    private MaterialProgressBar progressBar;
    private RoundCornerProgressBar progressBarL;
    private RelativeLayout layout_hide;

    private Helper helper = new Helper(TrackRequest.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_request);

        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        INTI_ID = sharedPreferences.getString(SP_KEY,"");

        tv_id = (TextView) this.findViewById(R.id.tv_id);
        tv_date = (TextView) this.findViewById(R.id.tv_date);
        tv_current = (TextView) this.findViewById(R.id.tv_current);
        progressBar = (MaterialProgressBar) this.findViewById(R.id.progressl);
        progressBarL = (RoundCornerProgressBar) this.findViewById(R.id.progress);
        layout_hide = (RelativeLayout) this.findViewById(R.id.layout_hide);

        progressBarL.setProgressColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
        progressBarL.setProgressBackgroundColor(ContextCompat.getColor(context,R.color.grey));

        getProgress();

    }
    public void getProgress(){
        class Progress extends AsyncTask<String,Void,String>{

            private HTTPHelper ruc = new HTTPHelper();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                ShowProgress(true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                ShowProgress(false);

                if(s.equals("")){
                    getProgress();
                }

                if(!s.equals("")){
                    parseJson(s);
                }
                if (s.equals(API_INVALID)){
                    layout_hide.setVisibility(View.GONE);
                    helper.CustomDialog("Message","No result found");
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> map = new HashMap<String, String>();
                map.put("sid",params[0]);
                return ruc.sendPostRequest(API_TRACK_PROGRESS,map);
            }
        }
        Progress progress = new Progress();
        progress.execute(INTI_ID);
    }

    public void parseJson(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray(INFO_TAG);

            for(int i = 0; i < array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                id = object.getString(INFO_ID);
                date = object.getString(INFO_DATE);
                status = object.getString(INFO_STATUS);
            }

            current = helper.GetDepartmentName(status);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int progress = Integer.parseInt(status);
                    progress = progress*20;

                    if(progress == 0){
                        progress = 10;
                    }

                    tv_id.setText(id);
                    tv_date.setText(date);
                    tv_current.setText(current);
                    progressBarL.setProgress(progress);
                    progressBarL.setMax(100);
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
