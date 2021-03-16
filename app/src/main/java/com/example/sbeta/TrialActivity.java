package com.example.sbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class TrialActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    ListView trialList;
    ArrayAdapter<Trial> trialArrayAdapter;
    ArrayList<Trial> trialDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trial_list);
        Intent intent = getIntent();
        String trialListTittle = intent.getStringExtra("chosenExperiment");

        trialList = findViewById(R.id.trial_list);
        trialDataList = new ArrayList<>();

        trialArrayAdapter = new TrialAdapter(this, trialDataList);
        trialList.setAdapter(trialArrayAdapter);


        Button operationButton = (Button) findViewById(R.id.operation_button);
        Button addButton = (Button) findViewById(R.id.add_trial_button);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // place your code here
                switch (v.getId()) {
                    case R.id.operation_button:
                        PopupMenu popupMenu1 = new PopupMenu(TrialActivity.this, v);
                        popupMenu1.getMenuInflater().inflate(R.menu.operation_menu, popupMenu1.getMenu());
                        popupMenu1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Toast.makeText(TrialActivity.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                                switch (item.getItemId()) {
                                    case R.id.edit_trial:
                                        // do your code
                                        return true;
                                    case R.id.statistics:
                                        // do your code
                                        return true;
                                    case R.id.ignore:
                                        // do your code
                                        return true;
                                    case R.id.questions:
                                        Intent intent = new Intent(TrialActivity.this, showQuestion.class);
                                        intent.putExtra("chosenExperiment", trialListTittle);
                                        startActivity(intent);
                                        return true;
                                    case R.id.unpublish_exp:
                                        // do your code
                                        return true;
                                    case R.id.end_exp:
                                        // do your code
                                    case R.id.scan_barcode:
                                        //do your code
                                    default:
                                        return false;
                                }
                            }
                        });
                        popupMenu1.show();
                        break;

                    case R.id.add_trial_button:
                        // place your code here
                        PopupMenu popupMenu2 = new PopupMenu(TrialActivity.this, v);
                        popupMenu2.getMenuInflater().inflate(R.menu.add_option, popupMenu2.getMenu());
                        popupMenu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Toast.makeText(TrialActivity.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                                switch (item.getItemId()) {
                                    case R.id.manually_add:
                                        //do your code
                                        return true;
                                    case R.id.scan_qr_code:
                                        // do your code
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });
                        popupMenu2.show();
                        break;
                }
            }
        };

        operationButton.setOnClickListener(listener);
        addButton.setOnClickListener(listener);

        /*


        operationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(TrialActivity.this, v);
                popup.getMenuInflater().inflate(R.menu.operation_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return false;
                    }
                });
                //popup.setOnMenuItemClickListener(TrialActivity.this);
                //popup.inflate(R.menu.operation_menu);
                popup.show();
            }
        });


        Button addButton = (Button) findViewById(R.id.add_trial_button);

        operationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(TrialActivity.this, v);
                popup.setOnMenuItemClickListener(TrialActivity.this);
                popup.inflate(R.menu.add_option);
                popup.show();
            }
        });

         */


    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}