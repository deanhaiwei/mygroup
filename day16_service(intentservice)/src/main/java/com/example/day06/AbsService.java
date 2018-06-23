package com.example.day06;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import java.util.ServiceLoader;

public abstract  class AbsService extends Service {
    public AbsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private HandlerThread handlerThread;
    private ServiceHandler serviceHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        handlerThread=new HandlerThread("workThread");
        handlerThread.start();
        serviceHandler=new ServiceHandler(handlerThread.getLooper());

    }
    class ServiceHandler extends Handler{
        ServiceHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            onHandleIntent((Intent)msg.obj);
            stopSelf(msg.arg1);
        }
    }

    public abstract void onHandleIntent(Intent intent);



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = Message.obtain();
        msg.obj=intent;
        msg.arg1=startId;
        serviceHandler.sendMessage(msg);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }
}
