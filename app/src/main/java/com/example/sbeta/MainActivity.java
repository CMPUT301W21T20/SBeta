package com.example.sbeta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.UUID;

/**
 * Serve as launching activity of our program
 * It checks for a local config.txt file and compares it with the database
 */
public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db;
    Button goToMenu;

    /**
     * Read the userID from the local config.txt file
     *
     * @param context
     * @return return the userID (String) read from the config file, empty string if config.txtt does not exist
     */
    private String readConfigFile(Context context) {
        String userID = "";
        try {
            InputStream inputStream = context.openFileInput("config.txt");
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String s = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((s = bufferedReader.readLine()) != null) {
                    stringBuilder.append(s);
                }
                inputStream.close();
                userID = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            return "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userID;
    }

    /**
     * Make a config file and assign a new userID
     *
     * @param context
     * @return return the newly generated userID (String)
     */
    private String makeConfigFile(Context context) {
        String userID = UUID.randomUUID().toString();
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(userID);
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", "make config file failed: " + e.toString());
        }
        return userID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        goToMenu = findViewById(R.id.menu_button);
        final CollectionReference usersReference = db.collection("users");


        goToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = readConfigFile(getApplicationContext());
                if (userID != "") {
                    DocumentReference docRef = db.collection("users").document(userID);
                    String finalUserID = userID;
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    String userName = (String) document.getData().get("userName");
                                    Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
                                    intent.putExtra("userID", finalUserID);
                                    intent.putExtra("userName", userName);
                                    MainActivity.this.startActivity(intent);
                                }
                            }
                        }
                    });
                } else {
                    userID = makeConfigFile(getApplicationContext());
                    HashMap<String, String> data = new HashMap<>();
                    DocumentReference docRef = db.collection("users").document(userID);
                    String finalUserID1 = userID;
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                data.put("userName", "New User");
                                data.put("contact", "unknown");
                                usersReference
                                        .document(finalUserID1)
                                        .set(data);
                                Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
                                intent.putExtra("userID", finalUserID1);
                                intent.putExtra("userName", "New User");
                                MainActivity.this.startActivity(intent);
                            }
                        }
                    });
                }

            }
        });
    }
}