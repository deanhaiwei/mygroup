package com.example.youlu1803.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.youlu1803.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class MyBaseFragment extends Fragment {
    protected View contentView=null;
    protected LinearLayout actionBar;
    public void InitialActionBar(int LeftId , String Titile  ,int RightId){
        if (actionBar==null)return;
        ImageView imageLeft = actionBar.findViewById(R.id.ImageView_ActionBar_left);
        TextView title = actionBar.findViewById(R.id.TextView_ActionBar_Title);
        ImageView imageRight = actionBar.findViewById(R.id.ImageView_ActionBar_Right);
        if (LeftId<=0){
            imageLeft.setVisibility(View.INVISIBLE);
        }else {
            imageLeft.setVisibility(View.VISIBLE);
            imageLeft.setImageResource(LeftId);
        }
        if (TextUtils.isEmpty(Titile)){
            title.setVisibility(View.INVISIBLE);
        }else {
            title.setVisibility(View.VISIBLE);
            title.setText(Titile);
        }
        if (RightId<=0){
            imageRight.setVisibility(View.INVISIBLE);
        }else {
            imageRight.setVisibility(View.VISIBLE);
            imageRight.setImageResource(RightId);
        }
    }
    public abstract void InitialUI();






}
