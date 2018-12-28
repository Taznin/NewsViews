package com.example.taznin.newsviews.Manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.taznin.newsviews.R;

public class PreferenceManager {
    Context context;
    SharedPreferences sharedPreferences;

    public PreferenceManager(Context context) {
        this.context = context;
        getSharedPreference();
    }

    public void getSharedPreference(){
        sharedPreferences= context.getSharedPreferences(context.getString(R.string.myPreference),Context.MODE_PRIVATE);
    }
    public void writePref(){
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(context.getString(R.string.myPreferenceKey),"INIT_OK");
        editor.commit();

    }
    public boolean checkPref(){
        boolean flag=false;
        if(sharedPreferences.getString(context.getString(R.string.myPreferenceKey),"null").equals("null")){
            flag=false;
        }else{
            flag=true;
        }
        return flag;
    }
    public void clearPref(){
        sharedPreferences.edit().clear().commit();
    }
}
