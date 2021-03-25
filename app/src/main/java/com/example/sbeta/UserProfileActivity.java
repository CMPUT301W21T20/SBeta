package com.example.sbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This activity is the page for user profile
 * the user can edit their user name and contact info here.
 * the user can also search for other users in this activity.
 */
public class UserProfileActivity extends AppCompatActivity {
    TextView userNameText, contactText;
    Button editUser;

    /**
     * Used to update the user profile activity after the user changed their profile
     * @param userName (String)
     * @param contact (String)
     */
    public void updateData(String userName, String contact) {
        userNameText.setText("User Name: " + userName);
        contactText.setText("Contact: " + contact);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        userNameText = findViewById(R.id.userProfileName);
        contactText = findViewById(R.id.userProfileContact);
        editUser = findViewById(R.id.editUserButton);
        final String[] userName = {""};
        final String[] contact = { "" };


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        userName[0] = (String) document.getData().get("userName");
                        contact[0] = (String) document.getData().get("contact");
                        userNameText.setText("User Name: " + userName[0]);
                        contactText.setText("Contact: " + contact[0]);
                    }
                }
            }
        });
        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle =new Bundle();
                bundle.putString("userName", userName[0]);
                bundle.putString("contact", contact[0]);
                bundle.putString("userID", userID);
                EditUserProfileFragment editUserProfileFragment = new EditUserProfileFragment();
                editUserProfileFragment.setArguments(bundle);
                editUserProfileFragment.show(getSupportFragmentManager(), "Edit");
            }
        });

    }


}