package com.example.day16;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;





public class MusicSevice extends Service implements MediaPlayer.OnCompletionListener,MediaPlayer.OnPreparedListener{
    private MediaPlayer mediaPlayer;
    private int currentPositoin;
    public  MusicSevice (){}
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return new InnerBind();
    }
    class InnerBind extends Binder implements IPlayer{

        @Override
        public void PlayMusic() {
            Play();
        }

        @Override
        public void PauseMusic() {
            Pause();
        }
    }
    public void Play(){
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource("/storage/emulated/0/Music/孙露 - 愿得一人心.mp3");
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Pause(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            currentPositoin = mediaPlayer.getCurrentPosition();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;

        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Play();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.seekTo(currentPositoin);
        mp.start();
    }
}
