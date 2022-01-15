package com.example.balizas.fragmentoDatos;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.balizas.ApiConnection;
import com.example.balizas.BalizasViewModel;
import com.example.balizas.MainActivity;
import com.example.balizas.R;
import com.example.balizas.database.Baliza;
import com.example.balizas.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context context;
    public DatosFragment(Context context){
        this.context = context;
    }
    public DatosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DatosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DatosFragment newInstance(String param1, String param2) {
        DatosFragment fragment = new DatosFragment();
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
        //Volley
        BalizasViewModel balizasViewModel = new BalizasViewModel();

        LiveData<List<Baliza>> balizasLiveData = balizasViewModel.getBalizas();
        balizasLiveData.observe(getViewLifecycleOwner(), new Observer<List<Baliza>>() {
            @Override
            public void onChanged(List<Baliza> balizas) {
                if(balizas != null){
                    for(Baliza baliza : balizas){
                        if(baliza.activated){
                            System.out.println("ID DE LA BALIZA: "+baliza.id);
                            RequestQueue queue = Volley.newRequestQueue(context);
                            String url = "https://euskalmet.euskadi.eus/vamet/stations/readings/"+baliza.id+"/2022/01/05/readingsData.json";

                            JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                                    new Response.Listener<JSONArray>() {
                                        @Override
                                        public void onResponse(JSONArray jsonArray) {
                                            // textView.setText("Response is: "+ response.substring(0,500));
                                            try {
                                                System.out.println(jsonArray.get(0));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });


                            queue.add(stringRequest);
                        }
                    }
                }
            }
        });


        // Inflate the layout for this fragment
        ApiConnection connection = new ApiConnection(context);
        return inflater.inflate(R.layout.fragment_datos, container, false);
    }

}