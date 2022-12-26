package com.example.Ex2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private Button menu_BTN_fast;
    private Button menu_BTN_sensor;
    private Button menu_BTN_slow;
    private Button menu_BTN_table;

    public static int inter = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        RecordsListDB.init(
                getApplicationContext(),
                getResources().getInteger(R.integer.MAX_RECORDS),
                getResources().getString(R.string.RECORDS_KEY));

        Log.d("RecordsDB", RecordsListDB.getInstance().getRecords().toString());

        findViews();
        initViews();
    }


    private void initViews() {

        menu_BTN_slow.setOnClickListener(view -> {
            inter = 1000;
            GameActivity.sensorsEnabled = false;
            Intent intent = new Intent(MenuActivity.this, GameActivity.class);
            startActivity(intent);
        });

        menu_BTN_fast.setOnClickListener(view -> {
            inter = 500;
            GameActivity.sensorsEnabled = false;
            Intent intent = new Intent(MenuActivity.this, GameActivity.class);
            startActivity(intent);
        });

        menu_BTN_sensor.setOnClickListener(View ->{
            GameActivity.sensorsEnabled = true;
            Intent intent = new Intent(MenuActivity.this, GameActivity.class);
            startActivity(intent);
        });

        menu_BTN_table.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, RecordsActivity.class);
            startActivity(intent);
        });
    }


    private void findViews() {

        menu_BTN_fast = findViewById(R.id.menu_BTN_start_fast);
        menu_BTN_slow = findViewById(R.id.menu_BTN_start_slow);
        menu_BTN_sensor = findViewById(R.id.menu_BTN_start_tilt);
        menu_BTN_table = findViewById(R.id.menu_BTN_table);

    }

    public static int getInter() {
        return inter;
    }


}