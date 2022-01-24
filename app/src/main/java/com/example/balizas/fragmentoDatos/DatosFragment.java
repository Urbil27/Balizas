package com.example.balizas.fragmentoDatos;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.balizas.MainActivity;
import com.example.balizas.R;
import com.example.balizas.communication.ApiConnection;
import com.example.balizas.database.Baliza;

import java.util.ArrayList;
import java.util.Collections;
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
    private Context context;
    public DatosFragment() {
        // Required empty public constructor
    }
    public DatosFragment(Context context) {
        this.context = context;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_datos, container, false);
        System.out.println("Entro al oncreateview");
        RecyclerView recyclerView = view.findViewById(R.id.recyclerDatos);
        HandlerThread handlerThread = new HandlerThread("handlerThread");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        ApiConnection apiConnection = new ApiConnection(context,handler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        List<Baliza> balizasActivated = new ArrayList<Baliza>() ;


        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(context,balizasActivated,handler);

        MainActivity.db.balizaDao().getAll().observe(getViewLifecycleOwner(), new Observer<List<Baliza>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(List<Baliza> balizas) {
                balizasActivated.clear();
                for(Baliza baliza: balizas){
                    if(baliza.activated){
                        balizasActivated.add(baliza);
                        apiConnection.getBalizaReading(baliza);
                        recyclerView.setAdapter(recyclerAdapter);

                    }
                }
                recyclerAdapter.setBalizas(balizasActivated);
            }
        });
        return view;
    }
}