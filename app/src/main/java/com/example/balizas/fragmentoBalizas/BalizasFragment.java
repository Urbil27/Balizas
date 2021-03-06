package com.example.balizas.fragmentoBalizas;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balizas.MainActivity;
import com.example.balizas.R;
import com.example.balizas.communication.euskalmet.ApiConnection;
import com.example.balizas.database.Baliza;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;


public class BalizasFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private JSONArray jsonArray;

    private List<Baliza> balizas = new ArrayList<>();
    private Context context;
    private ApiConnection euskalmet;

    public BalizasFragment() {
        // Required empty public constructor
    }

    public BalizasFragment(Context context) {

        this.context = context;
    }


    public BalizasFragment newInstance(String param1, String param2) {

        BalizasFragment fragment = new BalizasFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        HandlerThread ht = new HandlerThread("BalizasSetter");
        ht.start();

        Handler handler = new Handler(ht.getLooper());
        if (euskalmet == null) {
            euskalmet = new ApiConnection(context);
        }

        euskalmet.getBalizas();

        View view = inflater.inflate(R.layout.fragment_balizas, container, false);
        RecyclerView rv = view.findViewById(R.id.rv);
        BalizasViewModel bvm = new BalizasViewModel();
        RecyclerAdapter ra = new RecyclerAdapter(context, balizas, handler);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(ra);

        bvm.getBalizas().observe(getViewLifecycleOwner(), new Observer<List<Baliza>>() {
            @Override
            public void onChanged(List<Baliza> balizas) {
                if (balizas != null) {
                    ra.setBalizas(balizas);
                    ra.notifyDataSetChanged();
                }
            }
        });


        return view;
    }


}
