package com.example.youlu1803.Manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import android.util.TypedValue;


public class ImageManager {
    public static Bitmap formatBitMap(Context context , Bitmap bitmap){
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        Bitmap bakeBitmap= Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bakeBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        int radios=Math.min(width,height)/2;
        canvas.drawCircle(width/2,height/2,radios,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap,0,0,paint);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        float storokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                1,context.getResources().getDisplayMetrics());
        paint.setStrokeWidth(storokeWidth);
        canvas.drawCircle(width/2,height/2,radios-storokeWidth/2,paint);

        return bakeBitmap;
    }
}
