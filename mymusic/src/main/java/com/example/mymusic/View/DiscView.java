package com.example.mymusic.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.mymusic.R;


public class DiscView extends RelativeLayout {

    FrameLayout frameLayout;
    ImageView imageView_Pin;
    CircleImageView Album;
    public DiscView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context,R.layout.discview_layout,this);
        frameLayout = view.findViewById(R.id.framelayout_Disc);
        imageView_Pin = view.findViewById(R.id.ImageView_Disc_Pin);
        Album = view.findViewById(R.id.Album_Disc);
    }
    public void startRotation(){
        frameLayout.clearAnimation();
        imageView_Pin.clearAnimation();
        RotateAnimation Pinanimation = new RotateAnimation(0,25,
                RotateAnimation.RELATIVE_TO_SELF,0.0f,RotateAnimation.RELATIVE_TO_SELF,
                0.0f);
        Pinanimation.setDuration(2000);
        Pinanimation.setFillAfter(true);
        imageView_Pin.setAnimation(Pinanimation);

        RotateAnimation Discanimation = new RotateAnimation(0,359,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,
                0.5f
                );
        Discanimation.setDuration(10000);
        Discanimation.setRepeatCount(Animation.INFINITE);
        Discanimation.setInterpolator(new LinearInterpolator());
        frameLayout.setAnimation(Discanimation);

    }
    public void stopRotation(){
        frameLayout.clearAnimation();
        imageView_Pin.clearAnimation();

        RotateAnimation Pinanimation = new RotateAnimation(25,0,
                RotateAnimation.RELATIVE_TO_SELF,0.0f,RotateAnimation.RELATIVE_TO_SELF,
                0.0f);
        Pinanimation.setDuration(2000);
        Pinanimation.setFillAfter(true);
        imageView_Pin.setAnimation(Pinanimation);
    }
    public void setAlbumpic(Bitmap bitmap){
        Album.setImageBitmap(bitmap);
    }
    public void setAlbumpic(int resource){
        Album.setImageResource(resource);
    }

    public CircleImageView getAlbumpic() {
        return Album;
    }
}
