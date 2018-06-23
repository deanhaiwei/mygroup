package com.example.day12;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;


/**
 * Created by dell on 2018/5/1.
 */

public class MyButton extends android.support.v7.widget.AppCompatButton {

    private  Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private float progress;
    public void setProgress (float progress){
        this.progress=progress;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        float left=getLeft()+15;
        float top =getTop()+20;
        float right = getWidth()*progress-15;
        float bottom = getBottom() -20;
        paint.setColor(Color.GREEN);
        canvas.drawRect(left, top, right, bottom, paint);
        super.onDraw(canvas);
    }
}
