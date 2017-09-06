package com.inti.ricoh.julfi.idap.Employee;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.inti.ricoh.julfi.idap.AppController;
import com.inti.ricoh.julfi.idap.Helper.HTTPHelper;
import com.inti.ricoh.julfi.idap.Helper.Helper;
import com.inti.ricoh.julfi.idap.R;
import com.inti.ricoh.julfi.idap.SQLite.EmployeeDB;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.inti.ricoh.julfi.idap.Helper.Config.API_EMPLOYEE_INFO;
import static com.inti.ricoh.julfi.idap.Helper.Config.API_ERROR;
import static com.inti.ricoh.julfi.idap.Helper.Config.API_FAIL;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_TAG;
import static com.inti.ricoh.julfi.idap.Helper.Config.SHARED_PREFERENCE;
import static com.inti.ricoh.julfi.idap.Helper.Config.SP_KEY;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private String INTI_ID,name,id,programme,working,email,phone;
    private AVLoadingIndicatorView avl;
    private RelativeLayout layout_hide;
    private TextView tv_name,tv_id,tv_programme,tv_working,tv_email,tv_phone;
    private FragmentActivity activity;
    private EmployeeDB employeeDB;
    private Helper helper;

    SharedPreferences sharedPreferences;

    int numOfData = 0;

    private OnFragmentInteractionListener mListener;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */

    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        activity = getActivity();
        sharedPreferences = activity.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        INTI_ID = sharedPreferences.getString(SP_KEY,"");
        helper = new Helper(activity);

        avl = (AVLoadingIndicatorView) view.findViewById(R.id.avl);
        layout_hide = (RelativeLayout) view.findViewById(R.id.layout_hide);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_email = (TextView) view.findViewById(R.id.tv_email);
        tv_id = (TextView) view.findViewById(R.id.tv_id);
        tv_programme = (TextView) view.findViewById(R.id.tv_dept);
        tv_working = (TextView) view.findViewById(R.id.tv_work);
        tv_phone = (TextView) view.findViewById(R.id.tv_phone);

        employeeDB = new EmployeeDB(activity);
        Cursor cursor = employeeDB.GetAllData();

        while(cursor.moveToNext()){
            numOfData++;
        }
        if(numOfData <= 0){
            GetEmployeeInfo();
        }else{
            loadUserData();
        }
        return view;
    }
    public void loadUserData(){
        Cursor cursor = employeeDB.GetAllData();
        while (cursor.moveToNext()){
            name = cursor.getString(1);
            id = cursor.getString(2);
            programme = cursor.getString(3);
            working = cursor.getString(6);
            email = cursor.getString(5);
            phone = cursor.getString(4);

            if(id.equals(INTI_ID)){
                activity.runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        tv_name.setText(name);
                        tv_id.setText("INTI ID : "+id);
                        tv_programme.setText("Programme Name : "+programme);
                        tv_working.setText("Working Hour : "+working);
                        tv_email.setText("Email : "+email);
                        tv_phone.setText("Phone : "+phone);
                    }
                });
            }else{
                boolean flag = employeeDB.DeleteAll();
                if(flag){
                    GetEmployeeInfo();
                }
            }
        }
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            System.out.println("Setting fragment");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void GetEmployeeInfo(){

        class downloadinfo extends AsyncTask<String,Void,String>{

            private HTTPHelper ruc = new HTTPHelper();

            @Override
            protected String doInBackground(String... strings) {
                HashMap<String,String>data = new HashMap<String, String>();
                data.put("id",strings[0]);
                return ruc.sendPostRequest(API_EMPLOYEE_INFO,data);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                layout_hide.setVisibility(View.GONE);
                avl.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                layout_hide.setVisibility(View.VISIBLE);
                avl.hide();

                switch (s){
                    case API_ERROR:{
                        helper.NormalDialog("IDAP","Something went wrong. Please try again later.");
                        break;
                    }
                    case API_FAIL:{
                        helper.NormalDialog("IDAP","API Error. Please contact office");
                        break;
                    }
                    case "":{
                        GetEmployeeInfo();
                        break;
                    }
                    default:{
                        ParseJSON(s);
                        break;
                    }
                }

            }
        }
        downloadinfo download = new downloadinfo();
        download.execute(INTI_ID);
    }
    public void ParseJSON(String json){
        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray(INFO_TAG);

            for (int i = 0; i < array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                name = object.getString("name");
                programme = object.getString("programme");
                id = object.getString("matri");
                working = object.getString("working");
                email = object.getString("email");
                phone = object.getString("phone");
            }

            boolean flag = employeeDB.AddInfo(name,id,programme,email,phone,working);
            if(flag){
                loadUserData();
            }else{
                GetEmployeeInfo();
                System.out.println("Insertion failed in employee database");
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
