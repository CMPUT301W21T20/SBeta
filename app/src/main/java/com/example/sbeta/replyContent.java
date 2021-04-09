package com.example.sbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * This activity shows the content of a reply to a question
 */
public class replyContent extends AppCompatActivity {
    TextView replyContentTittle;
    TextView replyDetail;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_content);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String detail = intent.getStringExtra("replyContent");

        replyContentTittle = findViewById(R.id.reply_content_tittle);
        replyDetail =  findViewById(R.id.reply_detail);
        backButton = findViewById(R.id.back_reply_list);

        replyContentTittle.setText(title);
        replyDetail.setText(detail);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}