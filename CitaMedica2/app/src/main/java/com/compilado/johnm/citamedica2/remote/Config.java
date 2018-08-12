package com.compilado.johnm.citamedica2.remote;

import android.content.Context;
import android.content.SharedPreferences;

public class Config {

    private final String SHARED_PREFS_FILE = "HMPrefs";
    private final String KEY_TIME = "time";

    private Context mContext;


    public Config(Context context) {

        mContext = context;

    }

    private SharedPreferences getSettings(){
        return mContext.getSharedPreferences(SHARED_PREFS_FILE, 0);
    }

    public int getTime(){
        return getSettings().getInt(KEY_TIME, 0);
    }

    public void setTime(int time){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putInt(KEY_TIME, time );
        editor.commit();
    }
}
