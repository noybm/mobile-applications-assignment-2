package com.example.Ex2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class SaveScoreActivity extends AppCompatActivity {
    private Button loose_BTN_end;
    private EditText loose_LBL_insert_name;
    private TextView loose_LBL_show_score;
    private String name;
    private int score;
    private Record record;
    private Bundle bundle;

    private FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_score);
        findViews();
        this.bundle = getIntent().getBundleExtra("BUNDLE");
        score = bundle.getInt("SCORE");
        loose_LBL_show_score.setText("" + score);
        initViews();
        requestLocationPermission();
    }

    private void initViews() {
        loose_BTN_end.setOnClickListener(view -> {
            Record record = new Record();
            setRecordLocation(record);
            name = loose_LBL_insert_name.getText().toString();
            bundle.putString("PLAYER NAME", name);
            bundle.putInt("SCORE", score);
            bundle.putDouble("LAT", record.getLat());
            bundle.putDouble("LNG", record.getLon());
            Intent intent = new Intent(SaveScoreActivity.this, RecordsActivity.class);
            intent.putExtra("BUNDLE", bundle);
            Log.d("SaveScoreActivity", String.format("name: %s, score: %d", name, score));
            startActivity(intent);
            finish();
        });
    }

    private void findViews() {
        loose_LBL_insert_name = (EditText) findViewById(R.id.loose_LBL_insert_name);
        loose_LBL_show_score = (TextView) findViewById(R.id.loose_LBL_show_score);
        loose_BTN_end = findViewById(R.id.loose_BTN_end);
    }

    private void requestLocationPermission() {
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted)
                                Log.d("LocationPermission", "FINE location permission granted");

                            else if(coarseLocationGranted!= null && coarseLocationGranted)
                                Log.d("LocationPermission", "COARSE location permission granted");

                            else
                                Log.d("LocationPermission", "NO location permission granted");

                        }
                );
        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });

    }

    private void setRecordLocation(Record record) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION ,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("", "");
            record.setLat(0).setLon(0);
        }

        }

        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        record.setLat(location.getLatitude()).setLon(location.getLongitude());
                    }
                });


    }




}