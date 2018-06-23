package com.example.day19_myplayer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},100);

        }
        setSplashImage();
        setSplashVersion();
    }
    public void setSplashImage(){
        ImageView imageView = findViewById(R.id.splashImageId);
        AlphaAnimation a = new AlphaAnimation(0,1);
        a.setDuration(2000);
        a.setAnimationListener(new BaseAnimationListener(){
            @Override
            public void onAnimationEnd(Animation animation) {
                setSplashTime();
            }
        });
        imageView.startAnimation(a);


    }
    int count = 3;
    public void setSplashTime(){
        final TextView textView = findViewById(R.id.textId);
        textView.setText(String.valueOf(3));
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                count--;
                if (count>=0){
                    textView.setText(String.valueOf(count));
                    handler.postDelayed(this,1000);

                }else {
                   startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                   finish();
                }
            }
        },1000);
    }
    public void setSplashVersion(){
        TextView textView = findViewById(R.id.versionId);
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),0);
            textView.setText("我的音乐盒 ( V"+packageInfo.versionName+" )");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

}
