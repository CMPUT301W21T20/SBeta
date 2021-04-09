package com.example.sbeta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DisplayPersonalAdapter extends ArrayAdapter<Experiment> {

    private ArrayList<Experiment> List;
    private Context context;

    public DisplayPersonalAdapter(@NonNull Context context,@Nullable ArrayList<Experiment> List) {
        super(context,0,List);
        this.List=List;
        this.context=context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.personal_adapter,parent, false);
        }

        Experiment to_show = List.get(position);
        TextView expname=view.findViewById(R.id.expname);

        expname.setText(to_show.getName());

        return view;
    }
}
