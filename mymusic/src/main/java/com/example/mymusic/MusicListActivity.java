package com.example.mymusic;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mymusic.Adapter.MusicListAdapter;
import com.example.mymusic.Entity.Music;
import com.example.mymusic.IContact.IURL;
import com.example.mymusic.Manager.HttpManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MusicListActivity extends AppCompatActivity implements HttpManager.LoadMusicListener {

    private List<Music> musics = null;
    private ListView listView;
    private MusicListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        HttpManager.AsyncLoadMusic(this);
        initialUI();
    }

    private void initialUI() {
        listView = findViewById(R.id.MusicList_ListView);
        adapter = new MusicListAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MusicListActivity.this,PlayActivity.class);
                intent.putExtra("musics", (ArrayList)musics);
                intent.putExtra("position",position);

                startActivity(intent);
            }
        });
    }

    @Override
    public void onLoadMusicEnd(List<Music> music) {
        musics = music;
        adapter.addMusic(musics);
        startService(new Intent(this, MusicService.class));
        Intent intent = new Intent(IURL.MUSICSLOAD_ACTION);
        intent.putExtra("musics",(ArrayList<Music>)musics);
        sendBroadcast(intent);

    }
}
