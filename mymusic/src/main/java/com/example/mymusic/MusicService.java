package com.example.mymusic;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.mymusic.IContact.IURL;

import java.io.IOException;
import java.net.URL;

public class MusicService extends Service {

    private MediaPlayer player;
    private boolean isPause = false;
    private int seekTo;
    private MusicReceiver receiver;
    String pauseURl="";
    private Thread progressThread;
    boolean progressFlag = true;
    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("TAG:service", "音乐播放服务已启动");
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                player.start();
            }
        });
        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.i("TAG:error","播放器出错:what" + what + "extra:" + extra);
                return true;
            }
        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                Intent intent = new Intent(IURL.PLAYNEXT_ACTION);
                sendBroadcast(intent);
            }
        });
        registMusicReceiver();
        progressThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                while (progressFlag){
                    int cp = player.getCurrentPosition();
                    int duration = player.getDuration();
                    Intent intent = new Intent(IURL.UPDATEPROGRESS_ACTION);
                    intent.putExtra("cp",cp);
                    intent.putExtra("duration",duration);
                    sendBroadcast(intent);
                }

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        progressThread.start();

    }

    private void registMusicReceiver() {
        receiver = new MusicReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(IURL.PLAYMUSIC_ACTION);
        filter.addAction(IURL.PUASEMUSIC_ACTION);
        filter.addAction(IURL.SEEKUPDATE_ACTION);
        filter.addAction(IURL.UPDATENEXTWIDGET_ACTION);

        registerReceiver(receiver,filter);
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public void play(String musicPath){

            try {
                if (isPause&&pauseURl.equals(musicPath)){
                    isPause = false;
                    player.seekTo(seekTo);
                    player.start();
                    seekTo = 0;
                }else {
                    player.reset();
                    player.setDataSource(musicPath);
                    player.prepareAsync();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(IURL.UPDATEPAUSEWIDGET_ACTION);
            intent.putExtra("musicpath",musicPath);
            sendBroadcast(intent);
    }

    public void pause(String musicpath){
        if (player.isPlaying()){
            isPause =true;
            pauseURl = musicpath;
            seekTo = player.getCurrentPosition();
            player.pause();
            Intent intent = new Intent(IURL.UPDATEPLAYWIDGET_ACTION);
            intent.putExtra("musicpath",musicpath);
            sendBroadcast(intent);
        }

    }


    public class MusicReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (IURL.PLAYMUSIC_ACTION.equals(action)){
                String musicPath = intent.getStringExtra("musicpath");
                play(musicPath);
            }else if (IURL.PUASEMUSIC_ACTION.equals(action)){
                String musicPath = intent.getStringExtra("musicpath");
                pause(musicPath);
            }else if (IURL.SEEKUPDATE_ACTION.equals(action)){
                int progress = intent.getIntExtra("progress",0);
                seekToTime(progress);
            }
        }
    }
    private void seekToTime(int progress) {
        seekTo=player.getDuration()*progress/100;
        //改变播放器的进度
        player.seekTo(seekTo);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


        unregisterReceiver(receiver);

        progressFlag = false;
    }
}
