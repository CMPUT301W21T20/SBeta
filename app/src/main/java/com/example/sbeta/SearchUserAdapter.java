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

public class SearchUserAdapter extends ArrayAdapter<User> {

    private Context context;
    private ArrayList<User> users;

    public SearchUserAdapter(@NonNull Context context,@NonNull ArrayList<User> users) {
        super(context,0,users);
        this.context=context;
        this.users=users;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content,parent,false);
        }

        User user = users.get(position);

        TextView username = view.findViewById(R.id.user_id);
        TextView contact = view.findViewById(R.id.user_contact);

        username.setText(user.userID);
        contact.setText(user.contactInfo);

        return view;
    }
}
