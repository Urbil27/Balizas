package com.example.balizas.fragmentoDatos;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.balizas.R;
import com.example.balizas.communication.euskalmet.ApiConnection;
import com.example.balizas.database.Baliza;
import com.example.balizas.database.Reading;
import com.example.balizas.fragmentoBalizas.BalizasViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatosFragment extends Fragment {


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;
    private List<Reading> readings;

    public DatosFragment() {
        // Required empty public constructor
    }

    public DatosFragment(Context context) {
        this.context = context;
    }

    public static DatosFragment newInstance(String param1, String param2) {
        DatosFragment fragment = new DatosFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_datos, container, false);
        System.out.println("Entro al oncreateview");
        RecyclerView recyclerView = view.findViewById(R.id.recyclerDatos);
        HandlerThread handlerThread = new HandlerThread("handlerThread");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        ApiConnection apiConnection = new ApiConnection(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        List<Baliza> balizasActivated = new ArrayList<>();
        DatosRecyclerAdapter recyclerAdapter = new DatosRecyclerAdapter(context);
        ReadingsViewModel readingsViewModel = new ReadingsViewModel();
        BalizasViewModel balizasViewModel = new BalizasViewModel();
        recyclerView.setAdapter(recyclerAdapter);
        balizasViewModel.getActivatedBalizas().observe(getViewLifecycleOwner(), new Observer<List<Baliza>>() {

            @Override
            public void onChanged(List<Baliza> balizas) {


                recyclerAdapter.setBalizas(balizas);

            }
        });
        readingsViewModel.getReadings().observe(getViewLifecycleOwner(), new Observer<List<Reading>>() {
            @Override
            public void onChanged(List<Reading> readings) {
                System.out.println("Observer readings");
                recyclerAdapter.setReadings(readings);
            }
        });

        return view;
    }
}