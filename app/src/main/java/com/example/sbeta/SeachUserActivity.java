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

public class SeachUserActivity extends AppCompatActivity {

    private String searchkey;
    private FirebaseFirestore db;
    final String TAG="user";
    ArrayAdapter<User> adapter;
    ListView DisplayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach_user);
        DisplayList=findViewById(R.id.display);

        Intent intent = getIntent();
        searchkey=intent.getStringExtra(); // fill it out from the previous activity

        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Users");
        ArrayList<User> users = new ArrayList<User>();

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                users.clear();

                for (QueryDocumentSnapshot doc : snapshots) {
                    Log.d(TAG, String.valueOf(doc.getData().get("username")));
                    String name = doc.getId();
                    if (name.toLowerCase().contains(searchkey.toLowerCase())){
                        String contact=(String) doc.getData().get(name);
                        users.add(new User(name,contact));
                    }
                }
            }
        });

        adapter=new SearchUserAdapter(this,users);
        DisplayList.setAdapter(adapter);

    }
}