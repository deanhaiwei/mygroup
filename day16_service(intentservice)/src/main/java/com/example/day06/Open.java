package com.example.day06;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class Open extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public Open() {
        this("workThread");

    }
    public Open(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String tname=Thread.currentThread().getName();
        Log.i("TAG","thread.name="+tname);
        Log.i("TAG","down start");
        try{Thread.sleep(5000);}catch (Exception e){}
        Log.i("TAG","down start end");
    }
}
