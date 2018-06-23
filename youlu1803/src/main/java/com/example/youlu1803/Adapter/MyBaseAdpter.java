package com.example.youlu1803.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.youlu1803.Entity.Conversation;

import java.util.ArrayList;
import java.util.List;

public abstract class MyBaseAdpter<T> extends BaseAdapter{
    protected LayoutInflater layoutInflater = null;
    protected Context context;
    public MyBaseAdpter(Context context) {
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
    }
    private List<T> datas=new ArrayList<>();

    public void addData(List<T> list ,boolean isClear){

        if (isClear){
        datas.clear();
        }
        if (list!=null){
        datas.addAll(list);
        notifyDataSetChanged();
        }
    }
    public void RemoveData(){
        datas.clear();
    }
    public void RemoveData(T t){
        datas.remove(t);
        notifyDataSetChanged();
    }
    public List<T> getData(){
        return datas;
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent) ;


}
