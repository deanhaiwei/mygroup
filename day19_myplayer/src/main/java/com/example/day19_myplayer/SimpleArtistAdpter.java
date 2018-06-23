package com.example.day19_myplayer;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.day19_myplayer.Util.Artist;


import java.util.List;

public class SimpleArtistAdpter extends RecyclerView.Adapter <SimpleArtistAdpter.ArtistViewHolder>{
    private List<Artist> mObject;
    private int mResource;
    private OnArtistItemClickListener onArtistItemClickListener;
    public void setOnArtistItemClickListener (OnArtistItemClickListener onArtistItemClickListener){
        this.onArtistItemClickListener = onArtistItemClickListener;
    }
    private RecyclerView mRecyclerView;

    SimpleArtistAdpter(List<Artist> Object, int resource){
        mObject=Object;
        mResource=resource;
    }
    static class ArtistViewHolder extends RecyclerView.ViewHolder{
        TextView titleTv;
        ArtistViewHolder(View itemView) {
            super(itemView);
            titleTv=itemView.findViewById(R.id.artistTextId);


        }
    }
    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(mResource,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mRecyclerView.getChildAdapterPosition(v);
                onArtistItemClickListener.OnItemClick(mRecyclerView,v,position);
            }
        });



        return new ArtistViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        Artist artist =  mObject.get(position);
        holder.titleTv.setText(artist.getName()+"("+artist.getCount()+")");



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
    public interface OnArtistItemClickListener{
        public void OnItemClick(RecyclerView parent,View View, int positoin);


    }
}
