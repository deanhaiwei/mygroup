package com.example.youlu1803.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youlu1803.Entity.Conversation;
import com.example.youlu1803.Manager.ContactsManager;
import com.example.youlu1803.Manager.ImageManager;
import com.example.youlu1803.R;

public class ConversationAdapter extends MyBaseAdpter<Conversation> {
    public ConversationAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.sms_item,null);
            holder = new ViewHolder();
            holder.Photo = convertView.findViewById(R.id.ImageView_SMS_Photo);
            holder.Warning = convertView.findViewById(R.id.ImageView_SMS_Warning);
            holder.Type = convertView.findViewById(R.id.ImageView_SMS_Type);
            holder.Name = convertView.findViewById(R.id.TextView_SMS_Name);
            holder.Body = convertView.findViewById(R.id.TextView_SMS_Body);
            holder.Date = convertView.findViewById(R.id.TextView_SMS_Date);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Conversation conversation = getItem(position);
        String name = conversation.getName();
        if (TextUtils.isEmpty(name)){
            holder.Name.setText(conversation.getAddress());
            holder.Name.setTextColor(Color.RED);
            holder.Warning.setVisibility(View.VISIBLE);
        }else {
            holder.Name.setText(name);
            holder.Name.setTextColor(Color.BLACK);
            holder.Warning.setVisibility(View.INVISIBLE);
        }
        holder.Body.setText(conversation.getBody());
        holder.Date.setText(conversation.getDateStr());
        int type = conversation.getRead();
        if (type==0){
            holder.Type.setVisibility(View.VISIBLE);
        }else {
            holder.Type.setVisibility(View.INVISIBLE);
        }
        Bitmap photo = ContactsManager.PhotobyPhotoId(context,conversation.getPhotoId());
        photo= ImageManager.formatBitMap(context,photo);
        holder.Photo.setImageBitmap(photo);
        return convertView;
    }
    class ViewHolder{
        ImageView Photo;
        ImageView Warning;
        ImageView Type;

        TextView Name;
        TextView Body;
        TextView Date;
    }
}
