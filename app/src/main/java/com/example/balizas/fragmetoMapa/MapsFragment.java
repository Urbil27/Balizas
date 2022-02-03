package com.example.balizas.fragmetoMapa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.balizas.communication.euskalmet.ApiConnection;
import com.example.balizas.fragmentoBalizas.BalizasViewModel;
import com.example.balizas.MainActivity;
import com.example.balizas.R;
import com.example.balizas.database.Baliza;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

public class MapsFragment extends Fragment {
    Context context;
    ApiConnection apiConnection;

    public MapsFragment(Context context) {
        this.context = context;
        apiConnection = new ApiConnection(context);
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        HashMap<Marker, Baliza> balizasGuardadas = new HashMap<Marker, Baliza>();

        @Override
        public void onMapReady(GoogleMap googleMap) {
            BalizasViewModel bvm = new BalizasViewModel();

            bvm.getBalizas().observe(getViewLifecycleOwner(), new Observer<List<Baliza>>() {
                @Override
                public void onChanged(List<Baliza> balizas) {

                    if (balizas != null) {
                        for (Baliza b : balizas) {
                            LatLng location = new LatLng(b.y, b.x);
                            if (b.activated) {
                                Marker marker = googleMap.addMarker(new MarkerOptions().position(location)
                                        .title(b.balizaName).icon(BitmapDescriptorFactory
                                                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                                balizasGuardadas.put(marker, b);

                            } else {
                                Marker marker = googleMap.addMarker(new MarkerOptions().position(location)
                                        .title(b.balizaName));
                                balizasGuardadas.put(marker, b);
                            }

                        }
                        LatLng loc = new LatLng(43.035827, -2.473771);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 8), 5000, null);
                        ;
                        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(@NonNull Marker marker) {
                                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                                HandlerThread htCargarBaliza = new HandlerThread("hiloCargarBaliza");
                                htCargarBaliza.start();
                                Handler cargadorBaliza = new Handler(htCargarBaliza.getLooper());
                                Baliza baliza = balizasGuardadas.get(marker);
                                if (baliza.activated) {
                                    baliza.activated = false;
                                } else {
                                    baliza.activated = true;
                                }
                                cargadorBaliza.post(new Runnable() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    @Override
                                    public void run() {
                                        MainActivity.db.balizaDao().update(baliza);

                                        apiConnection.getBalizaReading(baliza.id);
                                    }
                                });
                                return false;
                            }

                        });
                    }
                }
            });
        }

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_maps2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

    }
}