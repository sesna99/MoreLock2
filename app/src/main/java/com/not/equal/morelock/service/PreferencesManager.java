package com.not.equal.morelock.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by admin on 2016-05-27.
 */
public class PreferencesManager {
    public static PreferencesManager instance;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public PreferencesManager(Context mContext){
        instance = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        editor = preferences.edit();
    }

    public void setPassword(String password, int type){
        editor.putString("password"+type, password);
        editor.commit();
    }

    public String getPassword(int type){
        return preferences.getString("password"+type,"");
    }

    public void setType(int type){
        editor.putInt("type", type);
        editor.commit();
    }

    public int getType(){
        return preferences.getInt("type", 0);
    }

    public void setCurType(int type){
        editor.putInt("curType", type);
        editor.commit();
    }

    public int getCurType(){
        return preferences.getInt("curType", 0);
    }

    public void setActionbarSize(int height){
        editor.putInt("actionbar", height);
        editor.commit();
    }

    public int getActionbarSize(){
        return preferences.getInt("actionbar", 0);
    }

    public static PreferencesManager getInstance(Context mContext){
        if(instance == null){
            instance = new PreferencesManager(mContext);
        }
        return instance;
    }

    public void setIsRun(boolean isRun, int type){
        editor.putBoolean("run"+type, isRun);
        editor.commit();
    }

    public boolean isRun(int type){
        return preferences.getBoolean("run"+type, false);
    }

    public void buy(int type){
        editor.putBoolean("buy"+type, true);
        editor.commit();
    }

    public boolean isBuy(int type){
        return preferences.getBoolean("buy"+type, false);
    }
}
