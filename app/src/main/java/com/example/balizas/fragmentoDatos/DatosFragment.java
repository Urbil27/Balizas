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

public class DatosFragment extends Fragment {


    private Context context;
    private ApiConnection apiConnection;
    public DatosFragment() {

    }

    public DatosFragment(Context context) {
        this.context = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datos, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerDatos);

        if(apiConnection == null){

            apiConnection = new ApiConnection(context);

        }

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
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

                recyclerAdapter.setReadings(readings);

            }
        });

        return view;
    }
}