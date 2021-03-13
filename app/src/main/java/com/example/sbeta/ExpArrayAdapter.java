package com.example.sbeta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import org.w3c.dom.Text;

import java.util.ArrayList;

public class ExpArrayAdapter extends ArrayAdapter<Experiment>{


    private ArrayList<Experiment> experiments;
    private Context context;

    public ExpArrayAdapter(Context context, ArrayList<Experiment> experiments) {
        super(context,0,experiments);
        this.experiments = experiments;
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.experiment_content,parent,false);
        }

        Experiment experiment = experiments.get(position);

        TextView content_name = view.findViewById(R.id.experiment_name_text);

        content_name.setText(experiment.description);

        return view;
    }


}