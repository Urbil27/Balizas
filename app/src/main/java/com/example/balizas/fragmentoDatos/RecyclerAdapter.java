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

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Baliza> balizas;


    private Context context;
    private LayoutInflater mInflater;
    private Handler handler;

    public RecyclerAdapter(Context context, List<Baliza> balizas, Handler handler){
        this.balizas = balizas;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.handler = handler;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvBaliza;
        private final Switch switch1;
        public View view;
        public ViewHolder(View view) {
            super(view);
            this.view = view;
            tvBaliza = view.findViewById(R.id.textView);
            switch1 = view.findViewById(R.id.switch1);
        }
        //Devuelve el textview del nombre de la baliza
        public TextView getTVBaliza(){
            return tvBaliza;
        }
        public Switch getSwitchBaliza(){return switch1;}
    }
    public void setBalizas(List<Baliza> balizas){
        this.balizas = balizas;
    }
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_datos, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Baliza baliza = balizas.get(position);
        holder.getTVBaliza().setText(baliza.balizaName);

    }
    @Override public int getItemViewType(int position) { return position; }


    @Override
    public int getItemCount() {
        return balizas.size();
    }
}