package com.example.balizas.fragmentoBalizas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balizas.R;
import com.example.balizas.database.Baliza;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Baliza> balizas = new ArrayList<Baliza>();


    private Context context;
    private LayoutInflater mInflater;
    public RecyclerAdapter(Context context, List<Baliza> balizas){
        this.balizas = balizas;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
       private final TextView tvBaliza;
        public View view;
        public ViewHolder(View view) {
            super(view);
            this.view = view;
            tvBaliza = view.findViewById(R.id.textView);
        }
        //Devuelve el textview del nombre de la baliza
        public TextView getTVBaliza(){
            return tvBaliza;
        }
    }
    public void setBalizas(List<Baliza> balizas){
        this.balizas = balizas;
    }
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_balizas, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Baliza baliza = balizas.get(position);
        holder.getTVBaliza().setText(baliza.balizaName);
    }
    @Override
    public int getItemCount() {
        System.out.println(balizas.size());
        return balizas.size();
    }
}