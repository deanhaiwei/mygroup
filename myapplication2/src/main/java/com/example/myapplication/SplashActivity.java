package com.example.myapplication;



import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setSplash();


    }

    private void setSplash() {
        //获取imageview对象
        ImageView ivSpalsh  = findViewById(R.id.im_splash);
        //创建一个淡入淡出动画对象
        AlphaAnimation a = new AlphaAnimation(0,1);
        //这只动画持续时间
        a.setDuration(5000);
        a.setAnimationListener(new base() {
            @Override
            public void onAnimationEnd(Animation animation) {
               // startActivity(new Intent(SplashActivity.this,BaseActivity.class));
            }
        });
        ivSpalsh.startAnimation(a);
        //构建一个淡入淡出的动画效果

        TextView tvverson = findViewById(R.id.tv_verson);
        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(getPackageName(),0);
        tvverson.setText(packageInfo.versionName);
    }

}
