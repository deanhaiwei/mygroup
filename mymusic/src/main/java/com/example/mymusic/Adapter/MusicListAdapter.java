package com.example.mymusic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mymusic.Entity.Music;
import com.example.mymusic.IContact.IURL;
import com.example.mymusic.Manager.MyImageLoader;
import com.example.mymusic.R;

import java.util.ArrayList;
import java.util.List;

public class MusicListAdapter extends BaseAdapter {
    List<Music> musics = new ArrayList<>();
    Context context;
    public MusicListAdapter (Context context){
        this.context = context;
    }
    public void addMusic(List<Music> emps){
        if(emps!=null){
            musics.addAll(emps);
            notifyDataSetChanged();
        }
    }
    @Override
    public int getCount() {
        return musics.size();
    }

    @Override
    public Music getItem(int position) {
        return musics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.music_list_item,null);
            viewHolder.Album = convertView.findViewById(R.id.Item_Album);
            viewHolder.SongName = convertView.findViewById(R.id.Item_SongName);
            viewHolder.Singer = convertView.findViewById(R.id.Item_Singer);
            viewHolder.Author = convertView.findViewById(R.id.Item_Author);
            viewHolder.Composer = convertView.findViewById(R.id.Item_Composer);
            viewHolder.Duration = convertView.findViewById(R.id.Item_Duration);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Music music = getItem(position);
        String path = IURL.ROOT+music.getAlbumpic();
        MyImageLoader.setBitmapFromCache(context,viewHolder.Album,path);
        viewHolder.SongName.setText(music.getName());
        viewHolder.Singer.setText(music.getSinger());
        viewHolder.Composer.setText(music.getComposer());
        viewHolder.Author.setText(music.getAuthor());
        viewHolder.Duration.setText(music.getDurationtime());
        return convertView;
    }
    class ViewHolder{
        ImageView Album;
        TextView SongName;
        TextView Singer;
        TextView Author;
        TextView Composer;
        TextView Duration;
    }
}
