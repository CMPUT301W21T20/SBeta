package com.example.sbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView experList;
    ArrayAdapter<Experiment> experAdapter;
    ArrayList<Experiment> dataList;
    Button searchButton;
    EditText searchWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseFirestore db;

        experList = findViewById(R.id.exper_list);
        searchButton = findViewById(R.id.search_button);
        searchWord = findViewById(R.id.searchKeyWord);
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Experiments");

        dataList = new ArrayList<>();

        User testUser = new User("123456");
        Experiment testExper1 = new Experiment(testUser, "this is test", "published", "Experiment A");
        Experiment testExper2 = new Experiment(testUser, "URUS", "published", "Car");
        Experiment testExper3 = new Experiment(testUser, "GALAXY s31", "published", "Phone");
        Experiment testExper4 = new Experiment(testUser, "GALAXY note40", "end", "Samsung");
        Experiment []experiments = {testExper1, testExper2, testExper3, testExper4};
        dataList.addAll(Arrays.asList(experiments));

        experAdapter = new CustomMainList(this, dataList);
        experList.setAdapter(experAdapter);

        // search
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchWord.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Cannot search empty string!");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
                    builder.create().show();
                } else {
                    Intent intent = new Intent(MainActivity.this, SearchDisplay.class);
                    intent.putExtra("keyword", searchWord.getText().toString());
                    searchWord.setText("");
                    startActivity(intent);
                }
            }
        });


    }
}