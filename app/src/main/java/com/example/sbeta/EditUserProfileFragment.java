package com.example.sbeta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This fragment enables the user to edit their profile (both user name and contact info
 * It updates the user info in the database, and calls the userProfileActivity for updates
 */
public class EditUserProfileFragment extends DialogFragment {
    private EditText userNameField, contactInfoField;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.edit_user_fragment, null);
        userNameField = view.findViewById(R.id.userNameEdit);
        contactInfoField = view.findViewById(R.id.userContactEdit);
        userNameField.setText(this.getArguments().getString("userName"));
        contactInfoField.setText(this.getArguments().getString("contact"));
        String userID = this.getArguments().getString("userID");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit User")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newUserName = userNameField.getText().toString();
                        String newContact = contactInfoField.getText().toString();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        DocumentReference docRef = db.collection("users").document(userID);
                        docRef.update("userName", newUserName);
                        docRef.update("contact", newContact);
                        ((UserProfileActivity) getActivity()).updateData(newUserName, newContact);
                        Toast.makeText(view.getContext(), "User profile successfully edited",
                                Toast.LENGTH_LONG).show();
                    }
                }).create();
    }
}