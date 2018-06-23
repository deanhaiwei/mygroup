package com.example.youlu1803.Adapter;


import android.content.Context;
import android.graphics.Bitmap;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youlu1803.Entity.Contact;
import com.example.youlu1803.Manager.ContactsManager;
import com.example.youlu1803.Manager.ImageManager;
import com.example.youlu1803.R;

public class ContactAdapter extends MyBaseAdpter<Contact> {
    public ContactAdapter(Context context) {
        super(context);
    }


    @Override
    public View getView(
            int position, View view, ViewGroup parent) {


        ViewHolder viewHolder = null;
        if (view==null){

            view = layoutInflater.inflate(R.layout.contact_item,null);


            viewHolder = new ViewHolder();
            viewHolder.imageView_Header=view.findViewById(R.id.ImageView_Contact_Header);
            viewHolder.textView_Name = view.findViewById(R.id.TextView_Name);
            view.setTag(viewHolder);
        }else {

            viewHolder = (ViewHolder) view.getTag();
        }
        Contact contact = getItem(position);
        if (position==0){
            viewHolder.imageView_Header.setImageResource(R.mipmap.ic_add_contact);
        }else {
            Bitmap photos = ContactsManager.PhotobyPhotoId(context,contact.getPhotoId());
            photos= ImageManager.formatBitMap(context,photos);
            viewHolder.imageView_Header.setImageBitmap(photos);
        }

        viewHolder.textView_Name.setText(contact.getName());
        Log.i("TAG","getView.convertView="+view);
        return view;
    }
    private class ViewHolder{
        ImageView imageView_Header;
        TextView textView_Name;
    }
}
