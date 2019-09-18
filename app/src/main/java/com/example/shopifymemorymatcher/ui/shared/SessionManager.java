package com.example.shopifymemorymatcher.ui.shared;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class SessionManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "AndroidHivePref";
    private static final String KEY_THEME = "isDark";

    // Constructor
    public SessionManager(Context context){
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void switchTheme() {
        boolean isDark = this.isDark();
        editor.putBoolean(KEY_THEME, !isDark);
        editor.commit();
    }

    public boolean isDark() {
        return pref.getBoolean(KEY_THEME, false);
    }

    public void clearSession(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    public Map getPref() {
        return pref.getAll();
    }
}

