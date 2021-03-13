package com.example.sbeta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TrialAdapter extends ArrayAdapter<Trial> {
    private int resourceId;
    public TrialAdapter(Context context, int textViewResourceId, ArrayList<Trial> objects)
    {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;

    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Trial trial = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        return view;
    }




}