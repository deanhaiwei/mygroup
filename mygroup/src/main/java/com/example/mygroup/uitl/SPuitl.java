package com.example.mygroup.uitl;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.mygroup.contants.Contants;


public class SPuitl {
    SharedPreferences sp=null;
    SPuitl(Context context,String name){
        sp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
    }
    public SPuitl(Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public boolean isFrist(){
        return sp.getBoolean(Contants.FRIST,true);
    }
    public void setFrist(boolean flag){
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Contants.FRIST,flag);
        editor.commit();
    }
}
