package com.example.day_14;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/5/8.
 */

public class LoadMuiscAsynch extends AsyncTask<Void,Void,List<Songs>> {
    private WeakReference<View> weakReference;
    private Context mContext;
    public LoadMuiscAsynch(Context cxt,View view){
        weakReference = new WeakReference<>(view);
        mContext = cxt;
    }
    @Override
    protected List<Songs> doInBackground(Void... params) {
        View view = weakReference.get();
        if (view==null)return null;
        Cursor cursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.DATA},
                MediaStore.Audio.Media.IS_MUSIC+"=?",
                new String[]{"1"},
                MediaStore.Audio.Media.DATE_ADDED+" desc "
                );
        List<Songs> songs = new ArrayList<>();
        while (cursor.moveToNext()){
            songs.add(new Songs(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    ));

        }
        cursor.close();
        return songs;
    }

    @Override
    protected void onPostExecute(List<Songs> songs) {
        super.onPostExecute(songs);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) weakReference.get();
        if (swipeRefreshLayout!=null) {
            RecyclerView recyclerView = swipeRefreshLayout.findViewById(R.id.recyclerviewId);
            MyAdpter adpter = (MyAdpter) recyclerView.getAdapter();
            adpter.addAll(songs);
            adpter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }


    }
}
