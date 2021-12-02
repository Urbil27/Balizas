package com.example.balizas.fragmentoBalizas;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balizas.MainActivity;
import com.example.balizas.R;
import com.example.balizas.database.AppDatabase;
import com.example.balizas.database.Baliza;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BalizasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BalizasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private JSONArray jsonArray;

    private ArrayList<Baliza> balizas;
    private Context context;
    public BalizasFragment() {
        // Required empty public constructor
    }

    public BalizasFragment(Context context, JSONArray jsonArray) {

        this.jsonArray = jsonArray;

        this.context = context;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BalizasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public BalizasFragment newInstance(String param1, String param2) {

        BalizasFragment fragment = new BalizasFragment();
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
        View view = inflater.inflate(R.layout.fragment_balizas, container, false);
        //System.out.println(response);
        RecyclerView rv = view.findViewById(R.id.rv);
        //rv.setAdapter(new RecyclerAdapter(response));
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = (JSONObject) jsonArray.get(i);
                System.out.println(object.toString());
                Baliza baliza = new Baliza();
                baliza.id = object.getString("id");
                baliza.balizaName = object.getString("name");
                baliza.nameEus = object.getString("nameEus");
                baliza.municipality = object.getString("municipality");
                baliza.altitude = Double.parseDouble(object.getString("altitude"));
                baliza.x = Double.parseDouble(object.getString("x"));
                baliza.y = Double.parseDouble(object.getString("y"));
                baliza.altitude = Double.parseDouble(object.getString("altitude"));
                System.out.println("----------------------------------------------------");
             //  balizas.add(baliza);
                MainActivity.db.balizaDao().insertAll(baliza);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        BalizasViewModel bvm = new BalizasViewModel();
        RecyclerAdapter ra = new RecyclerAdapter(context,balizas);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(ra);
        bvm.getBalizas().observe(getViewLifecycleOwner(), new Observer<List<Baliza>>() {
            @Override
            public void onChanged(List<Baliza> balizas) {

                ra.setBalizas(balizas);
                ra.notifyDataSetChanged();
            }
        });
        return view;
    }


}
