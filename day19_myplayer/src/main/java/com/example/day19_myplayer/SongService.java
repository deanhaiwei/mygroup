package com.example.day19_myplayer;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

public class SongService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    public static final String PLAY="action.PLAY";
    public static final String PAUSE="action.PAUSE";
    public static final String STOP="action.STOP";

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        mState=States.PLAY;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.start();
    }

    enum States {PLAY,PAUSE,STOP}
    private States mState=States.STOP;
    private NotificationManager notificationManager;
    private MediaPlayer mPlayer;
    public SongService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       String action =  intent.getStringExtra("actionKey");
       if (PLAY.equals(action)){
           processPlayRequest(intent);
       }else if (PAUSE.equals(action)){
           processPauseRequest(intent);
       }else if (STOP.equals(action)){
           processStopRequest(intent);
       }else {
           stopSelf();
       }
        return super.onStartCommand(intent, flags, startId);
    }
     private void processPlayRequest(Intent intent){
        if (mPlayer==null){
            mPlayer = new MediaPlayer();
            mPlayer.setOnPreparedListener(this);
            mPlayer.setOnCompletionListener(this);
        }else {
            mPlayer.reset();
        }
        if (mState==States.STOP){
            String date = intent.getStringExtra("dateKey");
            try {
                mPlayer.setDataSource(date);
                mPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (mState == States.PAUSE){
            mPlayer.seekTo(currentPlayPostion);
            mPlayer.start();
            mState=States.PLAY;
        }
     }
     private int currentPlayPostion;
     private void processPauseRequest(Intent intent){
         if (mPlayer.isPlaying()){
             currentPlayPostion=mPlayer.getCurrentPosition();
             mPlayer.pause();
             mState=States.PAUSE;
         }
     }
     private void processStopRequest(Intent intent){
         stopSelf();
     }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mState=States.STOP;
        if (mPlayer!=null){
            mPlayer.release();
            mPlayer=null;
        }
    }
}

