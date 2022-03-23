package com.example.newsapp41;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void saveBoardState(){
        preferences.edit().putBoolean("isBoardShown", true).apply();
    }

    public boolean isBoardShown(){
        return preferences.getBoolean("isBoardShown", false);
    }

    public void saveImageUri(String uriString) {
        preferences.edit().putString("profileImage", uriString).apply();
        Log.e("Prefs", uriString);
    }

    public String getImageUri(){
        return preferences.getString("profileImage", null );
    }

    public void saveProfileEditText(String etText){
        preferences.edit().putString("profileET", etText).apply();
        Log.e("Prefs", etText);
    }

    public String getProfileEditText(){
        return preferences.getString("profileET", "");
    }
}
