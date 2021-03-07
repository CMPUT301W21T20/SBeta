package com.example.sbeta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CustomMainList extends ArrayAdapter<Experiment> {

    private ArrayList<Experiment> experiments;
    private Context context;

    public CustomMainList(Context context, ArrayList<Experiment> experiments) {
        super(context, 0, experiments);
        this.experiments = experiments;
        this.context = context;
    }

    @SuppressLint("DefaultLocate")
    public View getView(int position, @Nullable View convertView, @Nonnull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.exper_name,parent, false);
        }

        Experiment experiment = experiments.get(position);
        TextView experName = view.findViewById(R.id.exper_name);
        experName.setText(experiment.getName());

        return view;
    }
}
