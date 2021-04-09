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

/**
 * ArrayAdapter to display the search result for searching for users
 */
public class SearchUserAdapter extends ArrayAdapter<User> {

    private ArrayList<User> users;
    private Context context;

    public SearchUserAdapter(@NonNull Context context,ArrayList<User> users) {
        super(context,0,users);
        this.users=users;
        this.context=context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.show_user,parent, false);
        }

        User to_show = users.get(position);

        TextView name = view.findViewById(R.id.UserName);
        TextView contact=view.findViewById(R.id.UserContact);

        name.setText(to_show.getUserID());
        contact.setText(to_show.getContactInfo());

        return view;
    }
}

