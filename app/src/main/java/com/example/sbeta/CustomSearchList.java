package com.example.sbeta;

// custom search results list

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * ArrayAdapter for displaying search results of experiments
 */
public class CustomSearchList extends ArrayAdapter<Experiment> {

    private final ArrayList<Experiment> searchResults;
    private final Context context;

    public CustomSearchList(Context context, ArrayList<Experiment> searchResults) {
        super(context, 0, searchResults);
        this.searchResults = searchResults;
        this.context = context;
    }

    @SuppressLint("DefaultLocale")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.exper_info,parent, false);
        }
        FirebaseFirestore db =  FirebaseFirestore.getInstance();;
        Experiment result = searchResults.get(position);

        TextView experName = view.findViewById(R.id.experName);
        TextView description = view.findViewById(R.id.experDesc);
        TextView experOwner = view.findViewById(R.id.experOwner);
        TextView experStatus = view.findViewById(R.id.experStatus);

        experName.setText(result.getName());

        description.setText(result.getExperimentType());


        DocumentReference userDocReference = db.collection("users").document(result.getUserId());

        //get experiment owner name
        userDocReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String usern = (String) document.get("userName");
                        experOwner.setText(usern);

                    }
                }
            }
        });
        experStatus.setText(result.getStatus());

        return view;
    }
}
