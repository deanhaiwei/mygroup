package com.example.day06;

import android.content.Intent;
import android.util.Log;

public class DonwService extends AbsService {
    @Override
    public void onHandleIntent(Intent intent) {
        String tname=Thread.currentThread().getName();
        Log.i("TAG","thread.name="+tname);
        Log.i("TAG","down start");
        try{Thread.sleep(5000);}catch (Exception e){}
        Log.i("TAG","down start end");
    }
}
