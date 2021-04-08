package com.example.sbeta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.LatLng;

import java.util.ArrayList;

public class MapsMarkerActivity extends AppCompatActivity implements OnMapReadyCallback {
    private String experimentID;
    private GoogleMap mMap;
    FirebaseFirestore db;
    ArrayList<LatLng> markers = new ArrayList<>();
    ArrayList<String> trialNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_marker);
        experimentID = getIntent().getStringExtra("chosenExperiment");
        db = FirebaseFirestore.getInstance();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        CollectionReference trials = db.collection("experiments").document(experimentID).collection("trials");
        trials.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                markers.clear();
                trialNames.clear();
                for (QueryDocumentSnapshot doc: value) {
                    String title = doc.getId();
                    Log.i("Trial title", title);
                    String latString = (String) doc.getData().get("lat");
                    String lngString = (String) doc.getData().get("lng");
                    if (latString.equals("null") || lngString.equals("null")) {
                        continue;
                    }
                    Double lat = Double.parseDouble(latString);
                    Double lng = Double.parseDouble(lngString);
                    com.google.android.gms.maps.model.LatLng newMarker = new com.google.android.gms.maps.model.LatLng(lat, lng);
                    googleMap.addMarker(new MarkerOptions()
                    .position(newMarker)
                    .title(title));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(newMarker));

                }
            }
        });

    }
}