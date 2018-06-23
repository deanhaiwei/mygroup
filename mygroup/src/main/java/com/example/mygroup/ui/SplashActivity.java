package com.example.mygroup.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mygroup.R;
import com.example.mygroup.uitl.SPuitl;

public class SplashActivity extends AppCompatActivity {
    SPuitl sPuitl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sPuitl = new SPuitl(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;

               if (true){
                intent = new Intent(SplashActivity.this,GuideActivity.class);
               sPuitl.setFrist(false);
               }else {
                intent = new Intent(SplashActivity.this,MainActivity.class);

               }
                startActivity(intent);
                SplashActivity.this.finish();

            }
        },1500);
    }
}
