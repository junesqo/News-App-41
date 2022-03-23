package com.example.newsapp41;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

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
        preferences.edit().putBoolean("isProfileImageChanged", true).apply();
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

    public void clearPreferences(){
        preferences.edit().clear().apply();
    }
}
