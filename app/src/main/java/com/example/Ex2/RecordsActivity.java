package com.example.Ex2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class RecordsActivity extends AppCompatActivity {

    private Bundle bundle;
    private Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        this.bundle = getIntent().getBundleExtra("BUNDLE");
        if (bundle != null)
            updateData();

        Context context = this;

        Button records_BTN_back = findViewById(R.id.records_BTN_back);
        records_BTN_back.setOnClickListener(v -> startActivity(new Intent(context, MenuActivity.class)));

        MapsFragment mapsFragment = new MapsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.records_LAY_map, mapsFragment).commit();

        CallBack_list onItemClicked = record -> {
            Log.d("Item Clicked", record.toString());
            mapsFragment.zoomOnLocation(record);
        };

        RecordFragment recordFragment = RecordFragment.newInstance(onItemClicked);
        getSupportFragmentManager().beginTransaction().replace(R.id.records_LAY_list, recordFragment).commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        RecordsListDB.getInstance().saveState();
    }

    public void updateData() {
        Record record = new Record().setName(bundle.getString("PLAYER NAME"))
                .setScore(bundle.getInt("SCORE"))
                .setLat(bundle.getDouble("LAT"))
                .setLon(bundle.getDouble("LNG"));

        Log.d("RecordsActivity", String.format("received record - %s", record));
        RecordsListDB.getInstance().addRecord(record);
    }
}