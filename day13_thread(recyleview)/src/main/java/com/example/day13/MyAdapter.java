package com.example.day13;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * Created by dell on 2018/5/7.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    private List<String> mObjects;
    MyAdapter(Context context, List<String> objects){
        layoutInflater = LayoutInflater.from(context);
        mObjects=objects;
    }
    static class  MyViewHolder extends RecyclerView.ViewHolder{
        private View itemview;
         MyViewHolder(View itemView) {

             super(itemView);
             this.itemview=itemView;
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(android.R.layout.simple_list_item_1,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
     if (holder.itemview!=null&&holder.itemview instanceof TextView) {
         ((TextView) holder.itemview).setText(mObjects.get(position));
     }
    }

    @Override
    public int getItemCount() {
        return mObjects!=null?mObjects.size():0;
    }
    public void AddAll(List<String> objects){
        mObjects.addAll(objects);
    }
}
