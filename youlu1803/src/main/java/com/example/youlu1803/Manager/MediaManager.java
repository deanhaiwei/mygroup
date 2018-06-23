package com.example.youlu1803.Manager;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class MediaManager {
    public  static  SoundPool soundPool = null;
    public static void PlayMusic(Context context,int musicId){
        if (soundPool==null){
            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC,0);
        }
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId,1.0f,1.0f,1,0,1.0f);
            }
        });
        soundPool.load(context,musicId,1);
    }
    public static void release(){
        if (soundPool!=null){
            soundPool.release();
            soundPool=null;
        }
    }

}
