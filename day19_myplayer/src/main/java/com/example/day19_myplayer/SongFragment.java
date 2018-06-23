package com.example.day19_myplayer;



import android.content.Intent;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.day19_myplayer.Util.Song;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongFragment extends Fragment implements SimplaSongAdpter.OnSongItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private List<Song> songs;
    private SimplaSongAdpter adpter;


    public SongFragment() {
        songs=new ArrayList<>();
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_song, container, false);
        setRecyclerView(view);
        setSwipeRefreshLayout(view);
        return view;
    }
    public void setRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recyclerviewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        //songs.add(new Song(1,"Taitle01","Artist01","/mnt/sdcard/..."));
        //songs.add(new Song(2,"Taitle02","Artist02","/mnt/sdcard/..."));
        adpter = new SimplaSongAdpter(songs, R.layout.song_list);
        recyclerView.setAdapter(adpter);
        adpter.setOnSongItemClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(1,null,this);
    }
    public void setSwipeRefreshLayout(View view){
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.SRLid);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                songs.clear();
                getLoaderManager().restartLoader(1,null,SongFragment.this);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void OnItemClick(RecyclerView parent, View View, int positoin) {
        Song song = songs.get(positoin);
        Intent intent = new Intent(getActivity(),SongService.class);
        intent.putExtra("actionKey",SongService.PLAY);
        intent.putExtra("dateKey",song.getData());
        getActivity().startService(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id==1){
            return new CursorLoader(getActivity(), MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    null,null,null, MediaStore.Audio.Media.DATE_ADDED +" desc ");
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data==null)return;
        while (data.moveToNext()){
            songs.add(new Song(data.getInt(data.getColumnIndex(MediaStore.Audio.Media._ID)),
                    data.getString(data.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                    data.getString(data.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                    data.getString(data.getColumnIndex(MediaStore.Audio.Media.DATA))));

        }

        adpter.notifyDataSetChanged();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getActivity(),SongService.class);
        getActivity().stopService(intent);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        songs.clear();
    }
}
