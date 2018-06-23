package com.example.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class StidentService extends Service {
    String[] names=new String[]{
            "kitty",
            "jerry",
            "mary",
            "tom",
            "lily"};
    public String Query (int num){
        if (num>0&&num<6){
            return names[num-1];
        }
        return null;

    }
    public StidentService() {
    }
    @Override
    public void onCreate() {

        super.onCreate();
        Log.i("TAG:", "onCreate: 服务已启动");

    }
    MyBinder binder = new MyBinder();
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    class MyBinder extends IStudent.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String queryStudent(int num) throws RemoteException {
            return Query(num);
        }
    }
}
