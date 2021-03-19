package com.example.sbeta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TrialAdapter extends ArrayAdapter<Trial> {

    private ArrayList<Trial> trials;
    private Context context;
    private ArrayList<Trial> ignoreTrials;

    public TrialAdapter(@NonNull Context context, ArrayList<Trial> trials, ArrayList<Trial> ignoreTrials) {
        super(context, 0, trials);
        this.trials = trials;
        this.context = context;
        this.ignoreTrials = ignoreTrials;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.trial_content, parent, false);
        }
        Trial trial = trials.get(position);

        TextView title = view.findViewById(R.id.trialNum);
        title.setText(trial.getTrialName());

        CheckBox ignoreBox = view.findViewById(R.id.check_to_ignore);


        if (ignoreBox.isChecked()) {
            ignoreTrials.add(trial);
            //title.setTextColor(Color.RED);
        }
        else {

            //if (ignoreTrials.contains(trial)) {
                //ignoreTrials.remove(trial);
                //view.setBackgroundColor(Color.WHITE);
            //}
        }






        return view;
    }


}
