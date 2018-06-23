package com.example.day10;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private NotificationManager myManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }
    public void onClick01(View v){
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("大神来了")
                .setContentText("我的爱人")
                .setTicker("您与新消息")
                .setContentIntent(PendingIntent.getActivity(this,100,
                        new Intent(this,OtherActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT))
                .setAutoCancel(true)
                .build();
        myManager.notify(1,notification);
    }
}
