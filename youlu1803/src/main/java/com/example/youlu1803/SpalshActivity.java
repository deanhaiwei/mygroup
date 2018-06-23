package com.example.youlu1803;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SpalshActivity extends Activity {

    private ImageView imageView_spalsh;
    private Animation animation_spalsh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
       setSpalsh();
    }
    private void setSpalsh(){
        imageView_spalsh = findViewById(R.id.splashimageId);
        animation_spalsh = AnimationUtils.loadAnimation(this, R.anim.splash);
        imageView_spalsh.setAnimation(animation_spalsh);
        animation_spalsh.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SpalshActivity.this,MainActivity.class));
                overridePendingTransition(R.anim.enter_anim,R.anim.out_anim);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
