package com.example.Ex2;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class RecordsListDB {

    private static RecordsListDB db;
    private final int maxSize;
    private final ArrayList<Record> records;
    private final String recordsKey;
    private final MySharedPreferences mySP;

    private RecordsListDB(Context context, int maxSize, String recordsKey) {
        this.maxSize = maxSize;
        this.recordsKey = recordsKey;
        mySP = MySharedPreferences.init(context);

        String recordsJson = mySP.getString(recordsKey, "");
        if (recordsJson.isEmpty())
            records = new ArrayList<>();
        else
            records = new ArrayList<>(Arrays.asList(new Gson()
                    .fromJson(recordsJson, Record[].class)));

        Log.d("RecordsDB", "init records - " + records.toString());
    }

    public static RecordsListDB init(Context context, int maxSize, String recordsKey) {
        if (db == null)
            db = new RecordsListDB(context, maxSize, recordsKey);

        return db;
    }

    public static RecordsListDB getInstance() {
        return db;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public void saveState() {
        mySP.putString(recordsKey, new Gson()
                .toJson(records.toArray(new Record[]{})));
    }

    public void addRecord(Record record) {
        records.add(record);
        records.sort(null); // records are sorted from high to low
        if (records.size() > maxSize)
            records.remove(maxSize);

        saveState();
    }
}


