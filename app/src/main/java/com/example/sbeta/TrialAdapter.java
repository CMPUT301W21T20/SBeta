package com.example.sbeta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TrialAdapter extends ArrayAdapter<Trial> {

    private final ArrayList<Trial> trials;
    private final Context context;

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
        return view;
    }


}
