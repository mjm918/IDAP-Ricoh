package com.inti.ricoh.julfi.idap.Employee;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.inti.ricoh.julfi.idap.Helper.HTTPHelper;
import com.inti.ricoh.julfi.idap.Helper.Helper;
import com.inti.ricoh.julfi.idap.Objects.NotificationLayer;
import com.inti.ricoh.julfi.idap.R;
import com.inti.ricoh.julfi.idap.ViewAdapter.NotificationCardAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.inti.ricoh.julfi.idap.Helper.Config.API_EMP_NOTIFICATION;
import static com.inti.ricoh.julfi.idap.Helper.Config.INFO_NOTIFICATION;
import static com.inti.ricoh.julfi.idap.Helper.Config.SHARED_PREFERENCE;
import static com.inti.ricoh.julfi.idap.Helper.Config.SP_KEY;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotificationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<NotificationLayer>list;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private AVLoadingIndicatorView avl;

    private OnFragmentInteractionListener mListener;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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

        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        final FragmentActivity activity = getActivity();

        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        String INTI_ID = sharedPreferences.getString(SP_KEY, "");

        list = new ArrayList<NotificationLayer>();

        avl = (AVLoadingIndicatorView) view.findViewById(R.id.avl);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        new Thread(new Runnable() {
            @Override
            public void run() {
                mAdapter = new NotificationCardAdapter(list,activity);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.setAdapter(mAdapter);
                    }
                });
            }
        }).start();

        GetNotifications(INTI_ID);

        return view;
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
            System.out.println("Notification fragment");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    public void GetNotifications(final String _ID){

        class Notification extends AsyncTask<String, Void, String>{

            private HTTPHelper ruc = new HTTPHelper();

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("id",params[0]);
                return ruc.sendPostRequest(API_EMP_NOTIFICATION,map);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                mRecyclerView.setVisibility(View.GONE);
                avl.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                mRecyclerView.setVisibility(View.VISIBLE);
                avl.hide();

                switch (s){
                    case "0":{
                        Toast.makeText(getActivity(),"No notification available",Toast.LENGTH_LONG).show();
                        avl.show();
                        avl.setIndicator("PacmanIndicator");
                        break;
                    }
                    case "":{
                        GetNotifications(_ID);
                    }
                    default:{
                        parseJSON(s);
                        break;
                    }
                }
            }
        }
        Notification notification = new Notification();
        notification.execute(_ID);
    }
    public void parseJSON(String json){

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray(INFO_NOTIFICATION);

            for(int i = 0; i < array.length(); i++){

                JSONObject object = array.getJSONObject(i);

                String message = object.getString("fullname")+ " (" + object.getString("sid") + ") has applied for " + object.getString("request") + " request";

                NotificationLayer layer = new NotificationLayer();
                layer.setId(object.getString("id"));
                layer.setTitle(object.getString("request"));
                layer.setDate(object.getString("day"));
                layer.setMessage(message);

                list.add(layer);

                System.out.println("this is message - "+ layer.getMessage());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
