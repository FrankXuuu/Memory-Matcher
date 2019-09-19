package com.example.shopifymemorymatcher.ui.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

public class SessionManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "AndroidHivePref";
    private static final String KEY_THEME = "isDark", KEY_COLUMNS = "columns",
            KEY_MATCHES = "matches", KEY_ROWS = "rows", KEY_MAX_SCORE = "maxScore";

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

    public int getUniques() {
        int columns = pref.getInt(KEY_COLUMNS, 4);
        int rows = pref.getInt(KEY_ROWS, 6);
        int matches = pref.getInt(KEY_MATCHES, 2);

        return (columns * rows) / matches;
    }

    public void setColumns(int columns) {
        editor.putInt(KEY_COLUMNS, columns);
        editor.commit();
    }

    public void setRows(int rows) {
        editor.putInt(KEY_ROWS, rows);
        editor.commit();
    }

    public int getColumns() {
        return pref.getInt(KEY_COLUMNS, 4);
    }

    public void setMatches(int matches) {
        editor.putInt(KEY_MATCHES, matches);
        editor.commit();
    }

    public int getMatches() {
        return pref.getInt(KEY_MATCHES, 2);
    }

    public void clearSession(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    // For testing purposes
    public void hardSetMaxScore(int score) {
        editor.putInt(KEY_MAX_SCORE, score);
        editor.commit();
    }

    public void setMaxScore(int score) {
        int currentScore = pref.getInt(KEY_MAX_SCORE, 0);
        int maxScore = score > currentScore ? score : currentScore;
        editor.putInt(KEY_MAX_SCORE, maxScore);
        editor.commit();
    }

    public int getMaxScore() {
        return pref.getInt(KEY_MAX_SCORE, 0);
    }

    public Map getPref() {
        return pref.getAll();
    }
}

