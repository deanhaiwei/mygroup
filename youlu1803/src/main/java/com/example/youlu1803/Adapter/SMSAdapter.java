package com.example.youlu1803.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youlu1803.Entity.SMS;
import com.example.youlu1803.Manager.ContactsManager;
import com.example.youlu1803.Manager.ImageManager;
import com.example.youlu1803.R;

public class SMSAdapter extends MyBaseAdpter<SMS> {
    public static final int LEFT_TYPE=0;
    public static final int RIGHT_TYPE=1;
    public SMSAdapter(Context context) {
        super(context);
    }
    @Override
    public int getItemViewType(int position) {
        int type = getItem(position).getType();
        return type-1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int type = getItemViewType(position);
        if (convertView==null){
            if (type==LEFT_TYPE){
                convertView = layoutInflater.inflate(R.layout.smsleft_item,null);

            }else if (type==RIGHT_TYPE){
                convertView = layoutInflater.inflate(R.layout.smsright_item,null);

            }
            holder = new ViewHolder();
            holder.imageView_photo=convertView.findViewById(R.id.SMS_Message_Photo);
            holder.textView_date = convertView.findViewById(R.id.SMS_Message_Date);
            holder.textView_body = convertView.findViewById(R.id.SMS_Message_Body);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        SMS sms=getItem(position);
        holder.textView_body.setText(sms.getBody());
        holder.textView_date.setText(sms.getDateStr());
        if (type==LEFT_TYPE){
        Bitmap photo = ContactsManager.PhotobyPhotoId(context,sms.getPhotoId());
        photo = ImageManager.formatBitMap(context,photo);
        holder.imageView_photo.setImageBitmap(photo);
        }else if (type==RIGHT_TYPE){
            Bitmap photo = BitmapFactory.decodeResource(context.getResources(),
                    R.mipmap.ic_contact_selected);
            photo = ImageManager.formatBitMap(context,photo);
            holder.imageView_photo.setImageBitmap(photo);
        }
        return convertView;
    }
    class ViewHolder{
        TextView textView_date;
        ImageView imageView_photo;
        TextView textView_body;
    }


}
