package com.ceyizlistesi.ceyizlistesi.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionHelper {

    private SharedPreferences prefs;

    public SessionHelper(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setusename(String usename) {
        prefs.edit().putString("usename", usename).commit();
    }

    public String getusename() {
        String usename = prefs.getString("usename","");
        return usename;
    }
}