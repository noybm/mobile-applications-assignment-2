package com.example.Ex2;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private static final String DB_FILE = "DB_FILE";

    private static MySharedPreferences instance = null;
    private final SharedPreferences preferences;

    private MySharedPreferences(Context context) {
        preferences = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
    }

    public static MySharedPreferences init(Context context) {
        if (instance == null)
            instance = new MySharedPreferences(context);

        return instance;
    }

    public static MySharedPreferences getInstance() {
        return instance;
    }

    public void putInt(String key, int value) {

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }
}
