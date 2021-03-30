package com.example.sbeta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.xml.namespace.QName;

public class showReply extends AppCompatActivity {
    ArrayList<String> replyData;
    ListView replyList;
    TextView replyListTittle;
    ArrayAdapter<String> replyAdapter;
    FloatingActionButton addReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reply);
        final String TAG = "Sample";

        Intent intent = getIntent();
        String questionName = intent.getStringExtra("QuestionName");
        String experimentName = intent.getStringExtra("experimentName");
        String questionContent = intent.getStringExtra("QuestionContent");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("experiments");
        final DocumentReference experiment = collectionReference.document(experimentName);
        final CollectionReference questions = experiment.collection("questions");
        final DocumentReference question = questions.document(questionName);

//        question.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        String Qname = document.getId();
//                        String Qcontent = (String) document.getData().get("content");
//                        titleContent.add(Qname);
//                        titleContent.add(Qcontent);
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });
//
//        String title = titleContent.get(0) + "\n\n" + titleContent.get(1);
//        if (titleContent.get(0) == null) {
//            title = "sfavfsv";
//        }
        String title = "Title: " + questionName + "\n\nBody: " + questionContent;

        replyListTittle = findViewById(R.id.reply_to);
        replyListTittle.setText(title);
        replyList = findViewById(R.id.reply_list);
        addReply = findViewById(R.id.add_reply_button);

        replyData = new ArrayList<>();
        replyAdapter = new CustomReplyList(this, replyData);
        replyList.setAdapter(replyAdapter);

        // get data from firebase
        final CollectionReference replies = question.collection("replies");
        replies.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                replyData.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                    Log.d(TAG, String.valueOf(doc.getData().get("content")));
                    //String name = doc.getId();
                    String replyContent = (String) doc.getData().get("content");
                    replyData.add(replyContent);
                }
                replyAdapter.notifyDataSetChanged();
            }
        });

        // add reply
        addReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showReply.this, addReply.class);
                intent.putExtra("chosenExperiment", experimentName);
                intent.putExtra("QuestionName", questionName);
                intent.putExtra("QuestionContent", questionContent);
                startActivity(intent);
            }
        });

        // show detail of the reply
        replyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String contentDetail = replyData.get(position);
                Intent intent = new Intent(showReply.this, replyContent.class);
                intent.putExtra("replyContent", contentDetail);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });
    }
}