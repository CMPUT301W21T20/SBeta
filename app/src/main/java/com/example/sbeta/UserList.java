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

public class UserList extends ArrayAdapter<User> {

    private ArrayList<User> users;
    private Context context;

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content,parent,false);
        }

        User user = users.get(position);

        TextView cityName = view.findViewById(R.id.user_name);
        TextView provinceName = view.findViewById(R.id.contact);

        cityName.setText(user.getname());
        provinceName.setText(user.getcontact());

        return view;
    }

}
