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

/**
 * An ArrayAdapter for trials
 * It also enables igonring some trials
 */
public class TrialAdapter extends ArrayAdapter<Trial> {


    private ArrayList<Trial> trials;
    private Context context;



    public TrialAdapter(@NonNull Context context, ArrayList<Trial> trials) {
        super(context, 0, trials);
        this.trials = trials;
        this.context = context;
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
        ignoreBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ignoreBox.isChecked()) {
                    trials.get(position).setIgnored(true);
                }
                else {
                    trials.get(position).setIgnored(false);
                }
            }
        });

        return view;
    }


}
