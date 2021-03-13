package com.example.sbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.VerifiedInputEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EditText userNamePrompt;
    Button loginButton, registerButton;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNamePrompt = findViewById(R.id.userNameField);
        loginButton = findViewById(R.id.loginbutton);
        registerButton = findViewById(R.id.registerbutton);


        db = FirebaseFirestore.getInstance();
        final CollectionReference usersReference = db.collection("users");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = userNamePrompt.getText().toString();
                HashMap<String, String> data = new HashMap<>();
                DocumentReference docRef = db.collection("users").document(userName);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
                                intent.putExtra("userName", userName);
                                MainActivity.this.startActivity(intent);
                            }
                            else {
                                Toast.makeText(MainActivity.this, "User name does not exist", Toast.LENGTH_LONG).show();
                                userNamePrompt.setText("");
                            }
                        }
                    }
                });

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = userNamePrompt.getText().toString();
                HashMap<String, String> data = new HashMap<>();
                DocumentReference docRef = db.collection("users").document(userName);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Toast.makeText(MainActivity.this, "User name already used", Toast.LENGTH_LONG).show();
                                userNamePrompt.setText("");
                            }
                            else {
                                data.put("userName", userName);
                                usersReference
                                        .document(userName)
                                        .set(data);
                                Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
                                intent.putExtra("userName", userName);
                                MainActivity.this.startActivity(intent);
                            }
                        }
                    }
                });
            }
        });
    }}