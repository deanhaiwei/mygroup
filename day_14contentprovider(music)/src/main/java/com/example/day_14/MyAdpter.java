package com.example.day_14;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

/**
 * Created by dell on 2018/5/8.
 */

public class MyAdpter extends RecyclerView.Adapter <MyAdpter.MyViewHolder>{
    private List<Songs> msongs;
    MyAdpter(List<Songs> songs){
        msongs=songs;
    }
    static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView artist;
         MyViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(android.R.id.text1);
             artist=itemView.findViewById(android.R.id.text2);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                android.R.layout.simple_list_item_2,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Songs songs = msongs.get(position);
        holder.title.setText(songs.getTitle());
        holder.artist.setText(songs.getArtist());
    }

    @Override
    public int getItemCount() {
        return msongs!=null?msongs.size():0;
    }
    public void addAll(List<Songs> songs){
       msongs.clear();
       msongs.addAll(songs);
    }
}
