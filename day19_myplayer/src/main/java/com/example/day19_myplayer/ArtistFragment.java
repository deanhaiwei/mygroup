package com.example.day19_myplayer;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.day19_myplayer.Util.Artist;

import java.util.ArrayList;
import java.util.List;



public class ArtistFragment extends Fragment implements SimpleArtistAdpter.OnArtistItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private List<Artist> artists = new ArrayList<>();
    private SimpleArtistAdpter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist, container, false);
        setRceyclerView(view);
        setSwipeRefreshLayout(view);
        return view;

    }

    public void setRceyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.ArtistRecyclerviewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new SimpleArtistAdpter(artists, R.layout.artist_text01);
        recyclerView.setAdapter(adapter);
        adapter.setOnArtistItemClickListener(this);

    }
    public void setSwipeRefreshLayout(View view){
        final SwipeRefreshLayout layout = view.findViewById(R.id.SRLArtistid);
        layout.setColorSchemeResources(android.R.color.holo_green_dark,android.R.color.holo_green_light,android.R.color.holo_blue_light);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                artists.clear();
                getLoaderManager().initLoader(2,null,ArtistFragment.this);
                layout.setRefreshing(false);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(2,null,this);
    }

    @Override
    public void OnItemClick(RecyclerView parent, View View, int positoin) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id ==2){
            return new CursorLoader(getActivity(),
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Audio.Media.ARTIST,
                    "count(*) as ct "},
                    MediaStore.Audio.Media.ARTIST+" is not null ) GROUP BY ("+ MediaStore.Audio.Media.ARTIST,
                    null,null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor==null)return;
        while (cursor.moveToNext()){
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            int count = cursor.getInt(cursor.getColumnIndex("ct"));
            artists.add(new Artist(artist,count));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        artists.clear();
    }
}
