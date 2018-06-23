package com.example.loadservise;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class StudentService extends Service {
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
    public StudentService() {
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
    class MyBinder extends Binder implements IStudent{

        @Override
        public String QueryStudent(int num) {

            return Query(num);
        }
    }
}
