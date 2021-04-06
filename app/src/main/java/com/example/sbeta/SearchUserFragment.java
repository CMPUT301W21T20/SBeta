package com.example.sbeta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SearchUserFragment extends DialogFragment {

    private EditText searchkeyText;

    @Override
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle bundle){

        View v= LayoutInflater.from(getActivity()).inflate(R.layout.search_user_frag, null);
        searchkeyText=v.findViewById(R.id.searchkey);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(v)
                .setTitle("Search User")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String key=searchkeyText.getText().toString();
                        Intent intent=new Intent(getActivity(),SearchUserActivity.class);
                        intent.putExtra("username",key);
                        startActivity(intent);
                    }
                }).create();
    }
}
