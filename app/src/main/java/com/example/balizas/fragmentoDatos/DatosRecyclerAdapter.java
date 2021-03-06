package com.example.balizas.fragmentoDatos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balizas.R;
import com.example.balizas.database.Baliza;
import com.example.balizas.database.Reading;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DatosRecyclerAdapter extends RecyclerView.Adapter<DatosRecyclerAdapter.ViewHolder> {

    private List<Baliza> balizas = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;
    private List<Reading> readings;

    public DatosRecyclerAdapter(Context context) {

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
        public View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            tvBaliza = view.findViewById(R.id.textView);
            tvTemperature = view.findViewById(R.id.cifraTemperatura);
            tvHumidity = view.findViewById(R.id.cifraHumedad);
            tvIrradiance = view.findViewById(R.id.cifraIrradiance);
            tvPrecipitation = view.findViewById(R.id.cifraPrecipitacion);

        }

        public TextView getTVBaliza() {
            return tvBaliza;
        }

        public TextView getTvTemperatura() {
            return tvTemperature;
        }

        public TextView getTvHumidity() {
            return tvHumidity;
        }

        public TextView getTvIrradiance() {
            return tvIrradiance;
        }

        public TextView getTvPrecipitation() {
            return tvPrecipitation;
        }

    }

    public void setBalizas(List<Baliza> balizas) {

        this.balizas = balizas;
        notifyDataSetChanged();
    }

    public void setReadings(List<Reading> readings) {
        this.readings = readings;
        notifyDataSetChanged();
    }

    @Override
    public DatosRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_datos, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DatosRecyclerAdapter.ViewHolder holder, int position) {

        Baliza baliza = balizas.get(position);
        TextView textViewNombreBaliza = holder.getTVBaliza();
        textViewNombreBaliza.setText(baliza.balizaName);
        TextView tvTemperature = holder.getTvTemperatura();
        TextView tvHumidity = holder.getTvHumidity();
        TextView tvPrecipitation = holder.getTvPrecipitation();
        TextView tvIrradiance = holder.getTvIrradiance();

        for (Reading reading : readings) {
            if (reading.balizaId.equals(baliza.id)) {
                tvTemperature.setText(reading.temperature + "??C");
                tvHumidity.setText(reading.humidity + "%");
                tvPrecipitation.setText(reading.precipitation + "");
                tvIrradiance.setText(reading.irradiance + "");
            }
        }


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return balizas.size();
    }
}