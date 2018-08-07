package com.example.saint.bitcoinprice.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SecurityPreferences {
    private SharedPreferences preferences;

    public SecurityPreferences(Context context) {
        this.preferences = context.getSharedPreferences("BitcoinPrice",Context.MODE_PRIVATE);
    }


    public void saveFloatValue(String key, Float value) {
        this.preferences.edit().putFloat(key,value).apply();
    }

    public Float getFloatValue(String key) {
        return this.preferences.getFloat(key,-1);
    }

}
