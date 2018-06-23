package com.example.day12;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private HandlerThread handlerThread;
    private WorkHandler workHandler;
    private MainHandler mainHandler;
    private MyButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btnId);
        handlerThread = new HandlerThread("work");
        handlerThread.start();
        workHandler = new WorkHandler(handlerThread.getLooper());
        mainHandler = new MainHandler(Looper.getMainLooper());
    }
    class WorkHandler extends Handler{
        public WorkHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            for (int i=1;msg.arg1>=i;i++){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message NewMsg = Message.obtain();
                NewMsg.arg1=i;
                mainHandler.sendMessage(NewMsg);
            }
        }
    }
    class MainHandler extends Handler{
        public MainHandler (Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            btn.setProgress(msg.arg1*1.0f/100);

            btn.setText(msg.arg1+"%");
            btn.invalidate();
        }
    }
    public void onClick(View v){
        Message msg = Message.obtain();
        msg.arg1 = 100;
        workHandler.sendMessage(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }
}
