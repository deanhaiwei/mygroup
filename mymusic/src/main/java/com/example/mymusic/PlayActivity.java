package com.example.mymusic;


import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mymusic.Entity.Music;
import com.example.mymusic.IContact.IURL;
import com.example.mymusic.Manager.DonloadMusi;
import com.example.mymusic.Manager.MyImageLoader;
import com.example.mymusic.View.DiscView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView_favo;
    private ImageView back;
    private TextView title;
    private ImageView statics;
    private ImageView imageView_downLoad;
    private TextView textView_current;
    private TextView textView_duration;
    private SeekBar seekBar;
    private ImageButton imageButton_previous;
    private ImageButton imageButton_pause;
    private ImageButton imageButton_next;
    private DiscView diskView;
    private List<Music> musics;
    private int currentPosition;
    private Music music;
    private boolean isPlay = false;
    private int seekBarProgress;
    private ProgressReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        checkPermission();
        initialDatas();
        initialUI();
        setListener();
    }
    private void checkPermission(){
        if(ContextCompat.
                checkSelfPermission(
                        this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,new String[]{Manifest.
                            permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    private void downLoadMusic() {
        AlertDialog.Builder builder=
                new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.favo);
        builder.setTitle("系统提示");
        builder.setMessage("确定要下载当前歌曲吗?");
        builder.setNegativeButton("再想想",null);
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String musicpath = IURL.ROOT+music.getMusicpath();
                String musicname = musicpath.substring(musicpath.lastIndexOf("/")+1);
                DonloadMusi.downLoadFile(PlayActivity.this,musicpath,musicname);
            }
        });
        builder.create().show();
    }


    private void setListener() {
        imageButton_next.setOnClickListener(this);
        imageButton_previous.setOnClickListener(this);
        imageButton_pause.setOnClickListener(this);
        imageView_downLoad.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new OnMySeekBarChangeListener());
    }
    public class OnMySeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {



        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            seekBarProgress = progress;
            String duration = music.getDurationtime();
            try {
                Date time = new SimpleDateFormat("mm:ss").parse(duration);
                long currentTime = time.getTime()*progress/100;
                textView_current.setText(new SimpleDateFormat("mm:ss").format(currentTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Intent intent = new Intent(IURL.SEEKUPDATE_ACTION);
            intent.putExtra("progress",seekBarProgress);
            sendBroadcast(intent);
        }
    }

    private void initialDatas() {
        Intent intent = getIntent();

        musics = (ArrayList<Music>) intent.getSerializableExtra("musics");
        currentPosition = intent.getIntExtra("position",0);
        music = musics.get(currentPosition);
    }

    private void initialUI() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.Play_Title);
        title.setText(music.getName());
        statics = findViewById(R.id.statics);
        imageView_favo = findViewById(R.id.imageView_Play_Favo);
        imageView_downLoad = findViewById(R.id.imageView_Play_DownLoad);
        textView_current = findViewById(R.id.textView_Play_Current);
        textView_duration = findViewById( R.id.textView_Play_Duration);
        seekBar = findViewById(R.id.seekBar_Play);
        imageButton_previous = findViewById(R.id.imageButton_Play_Previous);
        imageButton_pause = findViewById(R.id.imageButton_Play_Pause);
        imageButton_next = findViewById(R.id.imageButton_Play_Next);
        diskView = findViewById(R.id.Play_DiscView);
        imageView_favo.setColorFilter(Color.RED,PorterDuff.Mode.SRC_ATOP);
        imageView_downLoad.setColorFilter(Color.WHITE,PorterDuff.Mode.SRC_ATOP);
        textView_current.setTextColor(Color.WHITE);
        textView_duration.setTextColor(Color.WHITE);
        textView_duration.setText(music.getDurationtime());
    }
    public void back(View view){
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButton_Play_Pause:
                if (isPlay){
                    pause();
                }else {
                    play();
                }
                break;
            case R.id.imageButton_Play_Previous:
                previous();
                break;
            case R.id.imageButton_Play_Next:
                next();
                break;
            case R.id.imageView_Play_DownLoad:
                downLoadMusic();
                break;
        }
    }

    private void previous() {
        if (currentPosition>0){
            currentPosition--;
        }else {
            currentPosition = musics.size()-1;
        }

        play();
    }

    private void next() {
        if (currentPosition<9){
            currentPosition++;
        }else {
            currentPosition = 0 ;
        }

        play();
    }

    @Override
    protected void onResume() {
        super.onResume();
        play();
        registProgressReceiver();
    }

    private void registProgressReceiver() {
        receiver = new ProgressReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(IURL.UPDATEPROGRESS_ACTION);
        filter.addAction(IURL.PLAYNEXT_ACTION);
        registerReceiver(receiver,filter);


    }
    public class ProgressReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(IURL.PLAYNEXT_ACTION)){
                next();
            }else if (action.equals(IURL.UPDATEPROGRESS_ACTION)){
                int cp = intent.getIntExtra("cp",0);
                int duration = intent.getIntExtra("duration",0);
                int progress = cp*100/duration;
                seekBar.setProgress(progress);
                textView_current.setText(new SimpleDateFormat("mm:ss").format(cp));
                textView_duration.setText(new SimpleDateFormat("mm:ss").format(duration));

            }
        }
    }
    private void play(){
        isPlay =true;
        imageButton_pause.setImageResource(R.drawable.pause);
        diskView.startRotation();
        music = musics.get(currentPosition);
        title.setText(music.getName());
        String imageURL = IURL.ROOT+music.getAlbumpic();
        MyImageLoader.setBitmapFromCache(this,diskView.getAlbumpic(),imageURL);
        String musicpath = IURL.ROOT+music.getMusicpath();
        Intent intent = new Intent(IURL.PLAYMUSIC_ACTION);
        intent.putExtra("musicpath",musicpath);
        sendBroadcast(intent);
    }
    private void pause(){
        isPlay = false;
        imageButton_pause.setImageResource(R.drawable.play);
        diskView.stopRotation();

        Intent intent = new Intent(IURL.PUASEMUSIC_ACTION);
        String musicpath = IURL.ROOT+music.getMusicpath();

        intent.putExtra("musicpath",musicpath);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
