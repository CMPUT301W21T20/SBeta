package com.example.sbeta;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String searchkey;
        FirebaseFirestore db;
        final String TAG="user";
        ArrayAdapter<User> adapter;
        ListView DisplayList;

        setContentView(R.layout.activity_seach_user);
        DisplayList=(ListView)findViewById(R.id.display_username);

        Intent intent = getIntent();
        searchkey=intent.getStringExtra("username"); // fill it out from the previous activity
        // searchkey="con";
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("users");
        ArrayList<User> users = new ArrayList<User>();

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {

                users.clear();

                if (snapshots.size()==0) throw new IllegalArgumentException("wrong database");

                for (QueryDocumentSnapshot doc : snapshots) {
                    Log.d(TAG, String.valueOf(doc.getData().get("userName")));
                    String name = (String)doc.getData().get("userName");

                    if (name.toLowerCase().contains(searchkey.toLowerCase())) {
                        String contact = (String) doc.getData().get("contact");
                        users.add(new User(name, contact));
                    }
                }
            }

        });

        adapter = new SearchUserAdapter(this, users);
        DisplayList.setAdapter(adapter);
    }
}