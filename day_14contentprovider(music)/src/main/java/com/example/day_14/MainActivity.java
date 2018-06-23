package com.example.day_14;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private List<Songs> songs = new ArrayList<>();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRecyclerView();
        setSwipeRefreshLayout();
        checkauthorities();

    }

    private void setSwipeRefreshLayout() {
        swipeRefreshLayout = findViewById(R.id.swiperefreshlayputId);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_dark );
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.recyclerviewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdpter adpter = new MyAdpter(songs);
        recyclerView.setAdapter(adpter);
    }

    @Override
    public void onRefresh() {
        loadMediaSongs();
    }
    public void checkauthorities(){
        int sor =ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
       if (sor!=PackageManager.PERMISSION_GRANTED) {
           requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
           }, 101);
       }else {
           loadMediaSongs();
       }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode==101){
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            loadMediaSongs();
            }
        }
    }
    public void loadMediaSongs(){
        new LoadMuiscAsynch(getApplicationContext(),swipeRefreshLayout).execute();
    }
}
