package com.inti.ricoh.julfi.idap.Student;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inti.ricoh.julfi.idap.SQLite.StudentDB;
import com.inti.ricoh.julfi.idap.Helper.HTTPHelper;
import com.inti.ricoh.julfi.idap.Helper.Helper;
import com.inti.ricoh.julfi.idap.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.TimeZone;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.inti.ricoh.julfi.idap.Helper.Config.API_ERROR;
import static com.inti.ricoh.julfi.idap.Helper.Config.API_FAIL;
import static com.inti.ricoh.julfi.idap.Helper.Config.API_VALID;
import static com.inti.ricoh.julfi.idap.Helper.Config.API_WITHDRAW_INSERT;
import static com.inti.ricoh.julfi.idap.Helper.Config.SHARED_PREFERENCE;
import static com.inti.ricoh.julfi.idap.Helper.Config.SP_KEY;

public class WithdrawActivity extends AppCompatActivity {

    private EditText et_fullname,et_matri,et_bname,et_bacc,et_bholder;
    private TextView tv_date;
    private CheckBox cb_accept;
    private Button btn_submit;
    private MaterialProgressBar progressBar;
    private RelativeLayout relativeLayout;
    private CoordinatorLayout coordinatorLayout;

    private Context context = this;
    SharedPreferences sharedPreferences;

    private String INTI_ID,fullname,ic,contact,matri,programme,email,addr,bname,bacc,bholder,sdate,ERROR;
    private int checked = -1;

    private Helper helper = new Helper(WithdrawActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_a);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        StudentDB dbhelper = new StudentDB(WithdrawActivity.this);
        final Cursor cursor = dbhelper.GetAllData();

        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        INTI_ID = sharedPreferences.getString(SP_KEY,"");

        tv_date = (TextView) this.findViewById(R.id.tv_date);
        et_bacc = (EditText) this.findViewById(R.id.et_bacc);
        et_bholder = (EditText) this.findViewById(R.id.et_bholder);
        et_bname = (EditText) this.findViewById(R.id.et_bank);
        et_bacc = (EditText) this.findViewById(R.id.et_bacc);
        et_fullname = (EditText) this.findViewById(R.id.et_fullname);
        et_matri = (EditText) this.findViewById(R.id.et_matri);
        cb_accept = (CheckBox) this.findViewById(R.id.cb_agree);
        btn_submit = (Button) this.findViewById(R.id.btn_submit);
        relativeLayout = (RelativeLayout) this.findViewById(R.id.layout_hide);
        progressBar = (MaterialProgressBar) this.findViewById(R.id.progress);
        coordinatorLayout = (CoordinatorLayout) this.findViewById(R.id.layout_withdraw);

        ERROR = String.valueOf(context.getResources().getString(R.string.empty));

        try{
            while (cursor.moveToNext()){
                fullname = cursor.getString(1);
                programme = cursor.getString(3);
                ic = cursor.getString(8);
                bname = cursor.getString(9);
                bacc = cursor.getString(10);
                bholder = cursor.getString(11);
                contact = cursor.getString(6);
                email = cursor.getString(4);
                addr = cursor.getString(13);
                matri = cursor.getString(2);
            }
        }finally {
            dbhelper.close();
        }

        try {
            long time = getTime();
            Date date = new Date(time);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy");
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            sdate = format.format(date);
            System.out.println("Current time - > "+format.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                et_fullname.setText(fullname);
                et_matri.setText(matri);
                et_bname.setText(bname);
                et_bacc.setText(bacc);
                et_bholder.setText(bholder);

                try {
                    long time = getTime();
                    Date date = new Date(time);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy");
                    format.setTimeZone(TimeZone.getTimeZone("GMT"));

                    tv_date.setText(tv_date.getText()+ " " + format.format(date));

                    System.out.println("Current time - > "+format.format(date));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cb_accept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            checked = 1;
                            cb_accept.setText(context.getResources().getString(R.string.accepted));
                        }else{
                            checked = -1;
                            cb_accept.setText(context.getResources().getString(R.string.agree));
                        }
                    }
                });

                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fullname = et_fullname.getText().toString().trim();
                        matri = et_matri.getText().toString().trim();
                        bname = et_bname.getText().toString().trim();
                        bacc = et_bacc.getText().toString().trim();
                        bholder = et_bholder.getText().toString().trim();

                        if(!validate(fullname)){
                            et_fullname.setError(ERROR);
                            return;
                        }
                        if(!validate(matri)){
                            et_matri.setError(ERROR);
                            return;
                        }
                        if(!validate(bname)){
                            et_bname.setError(ERROR);
                            return;
                        }
                        if(!validate(bacc)){
                            et_bacc.setError(ERROR);
                            return;
                        }
                        if(!validate(bholder)){
                            et_bholder.setError(ERROR);
                            return;
                        }
                        if(checked == -1){
                            cb_accept.setError("Please accept the terms");
                            return;
                        }
                        SubmitForm();
                    }
                });

            }
        });
    }

    public void SubmitForm(){
        class Submit extends AsyncTask<String,Void,String>{

            private HTTPHelper ruc = new HTTPHelper();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                ShowProgress(true);
                if(!helper.IsNetworkAvailable()){
                    helper.Alert();
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                ShowProgress(false);

                switch (s){
                    case API_VALID:{
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Login Failed. Please Check INTI ID or Password", Snackbar.LENGTH_LONG);
                        snackbar.show();

                        helper.CustomDialog("Successful!","Your request has been submitted successfully. Please wait for office approval");

                        break;
                    }
                    case API_FAIL:{
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Something went wrong. Please try again.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        break;
                    }
                    case API_ERROR:{
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Something went wrong. Please contact back office", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        break;
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("name",params[0]);
                map.put("ic",params[1]);
                map.put("contact",params[2]);
                map.put("matri",params[3]);
                map.put("pro",params[4]);
                map.put("email",params[5]);
                map.put("addr",params[6]);
                map.put("bname",params[7]);
                map.put("bacc",params[8]);
                map.put("bholder",params[9]);
                map.put("date",params[10]);
                map.put("sid",params[11]);
                return ruc.sendPostRequest(API_WITHDRAW_INSERT,map);
            }
        }
        Submit submit = new Submit();
        submit.execute(fullname,ic,contact,matri,programme,email,addr,bname,bacc,bholder,sdate,INTI_ID);
    }

    public Boolean validate(String content){
        return !Objects.equals(content, "");
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

    private long getTime() throws Exception {
        String url = "https://time.is/Unix_time_now";
        Document doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
        String[] tags = new String[] {
                "div[id=time_section]",
                "div[id=clock0_bg]"
        };
        Elements elements= doc.select(tags[0]);
        for (String tag : tags) {
            elements = elements.select(tag);
        }
        return Long.parseLong(elements.text() + "000");
    }

    @Override
    public void onBackPressed() {
        helper.GoBackForStudent();
    }
}
