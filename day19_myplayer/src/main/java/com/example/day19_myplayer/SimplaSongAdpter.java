package com.example.day19_myplayer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.day19_myplayer.Util.Song;

import org.w3c.dom.Text;

import java.util.List;

public class SimplaSongAdpter extends RecyclerView.Adapter <SimplaSongAdpter.SongViewHolder>{
    private List<Song> mObject;
    private int mResource;
    private OnSongItemClickListener onSongItemClickListener;
    public void setOnSongItemClickListener(OnSongItemClickListener onSongItemClickListener){
        this.onSongItemClickListener=onSongItemClickListener;
    }
    private RecyclerView mRecyclerView;

    SimplaSongAdpter(List<Song> Object,int resource){
        mObject=Object;
        mResource=resource;
    }
    static class SongViewHolder extends RecyclerView.ViewHolder{
         TextView titleTv;
         TextView artistTv;
         ImageView delet;
         SongViewHolder(View itemView) {
            super(itemView);
            titleTv=itemView.findViewById(R.id.titleId);
            artistTv=itemView.findViewById(R.id.artistId);
            delet = itemView.findViewById(R.id.deletId);

        }
    }
    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(mResource,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mRecyclerView.getChildAdapterPosition(v);
                onSongItemClickListener.OnItemClick(mRecyclerView,v,position);
            }
        });



        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
       Song song =  mObject.get(position);
       holder.titleTv.setText(song.getTitle());
       holder.artistTv.setText(song.getArtist());
       holder.delet.setTag(position);
       holder.delet.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mObject.remove(mObject.get((Integer) v.getTag()));
               notifyDataSetChanged();
           }
       });

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView=recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView=null;
    }

    @Override
    public int getItemCount() {
        return mObject!=null?mObject.size():0;
    }
    public interface OnSongItemClickListener{
        public void OnItemClick(RecyclerView parent,View View, int positoin);


    }
}
