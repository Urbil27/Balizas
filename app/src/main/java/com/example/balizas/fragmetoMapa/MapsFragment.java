package com.example.balizas.fragmetoMapa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.balizas.BalizasViewModel;
import com.example.balizas.MainActivity;
import com.example.balizas.R;
import com.example.balizas.database.Baliza;
import com.example.balizas.fragmentoBalizas.RecyclerAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        HashMap<Marker, Baliza> balizasGuardadas;
        @Override
        public void onMapReady(GoogleMap googleMap) {
            BalizasViewModel bvm = new BalizasViewModel();

            bvm.getBalizas().observe(getViewLifecycleOwner(), new Observer<List<Baliza>>() {
                @Override
                public void onChanged(List<Baliza> balizas) {

                    if(balizas != null){
                        for(Baliza b : balizas){
                            LatLng sydney = new LatLng(b.y, b.x);
                            if(b.activated){
                                Marker marker = googleMap.addMarker(new MarkerOptions().position(sydney)
                                        .title(b.balizaName).icon(BitmapDescriptorFactory
                                                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                                balizasGuardadas.put(marker,b);

                            }
                            else{
                                Marker marker = googleMap.addMarker(new MarkerOptions().position(sydney)
                                        .title(b.balizaName));
                                balizasGuardadas.put(marker,b);
                            }
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        }
                        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(@NonNull Marker marker) {
                                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                                HandlerThread hiloCargarBaliza = new HandlerThread("hiloCargarBaliza");
                                hiloCargarBaliza.start();
                                Handler cargadorBaliza = new Handler(hiloCargarBaliza.getLooper());
                                Baliza baliza = balizasGuardadas.get(marker);
                                if(baliza.activated){
                                    baliza.activated = false;
                                }
                                else{
                                    baliza.activated=true;
                                }
                                cargadorBaliza.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        MainActivity.db.balizaDao().update(baliza);
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