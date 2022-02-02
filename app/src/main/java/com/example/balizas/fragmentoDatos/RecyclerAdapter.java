package com.example.balizas.fragmentoDatos;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balizas.MainActivity;
import com.example.balizas.R;
import com.example.balizas.database.Baliza;
import com.example.balizas.database.Reading;
import com.example.balizas.etc.BalizaAndLastReading;
import com.example.balizas.etc.LastReading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Baliza> balizas;


    private Context context;
    private LayoutInflater mInflater;

    private List<Reading> readings;

    public RecyclerAdapter(Context context){
        this.balizas = balizas;

        this.context = context;
        this.mInflater = LayoutInflater.from(context);

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvBaliza;
        private final TextView tvTemperature;
        private final TextView tvHumidity;
        private final TextView tvIrradiance;
        private final TextView tvPrecipitation;
        private final Switch switch1;
        public View view;
        public ViewHolder(View view) {
            super(view);
            this.view = view;
            tvBaliza = view.findViewById(R.id.textView);
            tvTemperature = view.findViewById(R.id.cifraTemperatura);
            tvHumidity = view.findViewById(R.id.cifraHumedad);
            tvIrradiance = view.findViewById(R.id.cifraTemperatura);
            tvPrecipitation = view.findViewById(R.id.cifraPrecipitacion);
            switch1 = view.findViewById(R.id.switch1);
        }
        //Devuelve el textview del nombre de la baliza
        public TextView getTVBaliza(){
            return tvBaliza;
        }
        public TextView getTvTemperatura(){return tvTemperature;}
        public TextView getTvHumidity(){return tvHumidity;}
        public TextView getTvIrradiance(){return tvIrradiance;}
        public TextView getTvPrecipitation(){return tvPrecipitation;}
        public Switch getSwitchBaliza(){return switch1;}

    }
    public void setBalizas(List<Baliza> balizas){

        this.balizas = balizas;
        notifyDataSetChanged();
        System.out.println("entro en setbalizas");
    }
    public void setReadings(List<Reading> readings){
        this.readings = readings;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_datos, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        System.out.println("on bind view holder");
        Baliza baliza = balizas.get(position);
        TextView textViewNombreBaliza = holder.getTVBaliza();
        textViewNombreBaliza.setText(baliza.balizaName);


    }
    @Override public int getItemViewType(int position) { return position; }


    @Override
    public int getItemCount() {
        return balizas.size();
    }
}