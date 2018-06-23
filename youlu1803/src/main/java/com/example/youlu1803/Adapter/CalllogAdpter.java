package com.example.youlu1803.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.CallLog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youlu1803.Entity.Colllog;
import com.example.youlu1803.Manager.ContactsManager;
import com.example.youlu1803.Manager.ImageManager;
import com.example.youlu1803.R;

public class CalllogAdpter extends MyBaseAdpter<Colllog> {
    public CalllogAdpter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.calllog_item,null);
            holder = new ViewHolder();
            holder.Name_TextView=convertView.findViewById(R.id.TextView_Calllog_Name);
            holder.Photo_ImageView=convertView.findViewById(R.id.ImageView_Calllog_Photo);
            holder.Warning_ImageView=convertView.findViewById(R.id.ImageView_Calllog_Warning);
            holder.Phone_TextView = convertView.findViewById(R.id.TextView_Calllog_Phone);
            holder.Type_ImageView = convertView.findViewById(R.id.ImageView_Calllog_Type);
            holder.Date_TextView = convertView.findViewById(R.id.TextView_Calllog_Date);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        Colllog colllog = getItem(position);
        String name = colllog.getName();
        if (TextUtils.isEmpty(name)){
            holder.Name_TextView.setText("陌生号码");
            holder.Name_TextView.setTextColor(Color.RED);
            holder.Warning_ImageView.setVisibility(View.VISIBLE);
        }else {
            holder.Name_TextView.setText(name);
            holder.Name_TextView.setTextColor(Color.BLACK);
            holder.Warning_ImageView.setVisibility(View.INVISIBLE);
        }
        holder.Phone_TextView.setText(colllog.getPhone());
        holder.Date_TextView.setText(colllog.getDateSre());
        Bitmap photo = ContactsManager.PhotobyPhotoId(context,colllog.getPhotoId());
        photo = ImageManager.formatBitMap(context,photo);
        holder.Photo_ImageView.setImageBitmap(photo);
        if (colllog.getType()== CallLog.Calls.OUTGOING_TYPE){
            holder.Type_ImageView.setVisibility(View.VISIBLE);
        }else {
            holder.Type_ImageView.setVisibility(View.INVISIBLE);
        }


        return convertView;
    }
    public class ViewHolder{
        //1
        ImageView Photo_ImageView;
        //2
        ImageView Warning_ImageView;
        //3
        TextView Name_TextView;
        //4
        ImageView Type_ImageView;
        //5
        TextView Phone_TextView;
        //6
        TextView Date_TextView;
    }
}
