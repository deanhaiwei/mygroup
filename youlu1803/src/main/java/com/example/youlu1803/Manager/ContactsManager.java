package com.example.youlu1803.Manager;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.youlu1803.Constant.IConstant;
import com.example.youlu1803.Entity.Colllog;
import com.example.youlu1803.Entity.Contact;
import com.example.youlu1803.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ContactsManager {
    public static List<Contact> getAllcontacts(Context context) {
        List<Contact> contacts = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        Uri contact_uri = ContactsContract.Contacts.CONTENT_URI;

        String[] projection = new String[]{"_id", "photo_id"};
        Cursor cursor = resolver.query(contact_uri, projection,
                null, null, null);
        while (cursor.moveToNext()) {
            Contact contact = new Contact();
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            Log.i("TAG", "getAllcontacts: " + contact.get_id());
            int photo_id = cursor.getInt(cursor.getColumnIndex("photo_id"));
            Log.i("TAG", "getAllcontacts: " + contact.getPhotoId());
            contact.set_id(_id);
            contact.setPhotoId(photo_id);
            Uri dataUri = ContactsContract.Data.CONTENT_URI;
            String[] dataprojection = new String[]{ContactsContract.Contacts.Data.MIMETYPE,
                    ContactsContract.Contacts.Data.DATA1};
            String where = ContactsContract.Contacts.Data.RAW_CONTACT_ID + "=?";
            String args[] = new String[]{String.valueOf(_id)};
            Cursor dataCursor = resolver.query(dataUri, dataprojection,
                    where, args, null);
            while (dataCursor.moveToNext()) {
                String minType = dataCursor.getString(0);
                String data1 = dataCursor.getString(1);
                if (minType.equals(IConstant.MIMETYPE_NAME)) {
                    contact.setName(data1);
                    Log.i("TAG", "getAllcontacts: " + contact.getName());
                } else if (minType.equals(IConstant.MIMETYPE_EMAIL)) {
                    contact.setEmail(data1);
                    Log.i("TAG", "getAllcontacts: " + contact.getEmail());
                } else if (minType.equals(IConstant.MIMETYPE_ADDRESS)) {
                    contact.setAddress(data1);
                    Log.i("TAG", "getAllcontacts: " + contact.getAddress());
                } else if (minType.equals(IConstant.MIMETYPE_PHONE)) {
                    contact.setPhone(data1);
                    Log.i("TAG", "getAllcontacts: " + contact.getPhone());
                }

            }
            contacts.add(contact);
            Log.i("TAG", "contacts: " + contacts.get(0));


        }
        return contacts;

    }

    public static Bitmap PhotobyPhotoId(Context context, int photoId) {
        Bitmap photo = null;
        if (photoId <= 0) {
            photo = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_contact);
        } else {
            ContentResolver resolver = context.getContentResolver();
            Uri uri = ContactsContract.Data.CONTENT_URI;
            String[] projiection = new String[]{ContactsContract.Data.DATA15};
            String selection = ContactsContract.Data._ID + "=?";
            String[] args = new String[]{String.valueOf(photoId)};
            Cursor cursor = resolver.query(uri, projiection, selection, args, null);
            if (cursor.moveToNext()) {
                byte[] bytes = cursor.getBlob(0);
                photo = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        }

        return photo;
    }

    public static void DeleteContact(Context context, Contact contact) {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = ContactsContract.RawContacts.CONTENT_URI;
        String where = ContactsContract.RawContacts._ID + "=?";
        String args[] = new String[]{String.valueOf(contact.get_id())};
        resolver.delete(uri, where, args);

    }

    public static List<Colllog> AddAllCalllog(Context context) {
        List<Colllog> calllogs = new ArrayList();
        try {


            ContentResolver resolver = context.getContentResolver();
            Uri uri = CallLog.Calls.CONTENT_URI;
            String[] projection = new String[]{
                    CallLog.Calls._ID,
                    CallLog.Calls.NUMBER,
                    CallLog.Calls.TYPE,
                    CallLog.Calls.DATE
            };
            String selection = null;
            String[] args = null;
            String order = CallLog.Calls.DATE + " desc ";

            Cursor cursor = resolver.query(uri, projection, selection, args, order);
            while (cursor.moveToNext()) {
                Colllog colllog = new Colllog();
                int _id = cursor.getInt(0);
                String number = cursor.getString(1);
                int type = cursor.getInt(2);
                long date = cursor.getLong(3);
                int photoId = getPhotoIdByNumber(context, number);
                String name = getNameByNumber(context, number);
                String datesre = formatDate(date);
                colllog.set_id(_id);
                colllog.setPhone(number);
                colllog.setType(type);
                colllog.setDate(date);
                colllog.setDateSre(datesre);
                colllog.setPhotoId(photoId);
                colllog.setName(name);
                calllogs.add(colllog);
            }
        }catch (SecurityException se){
            se.printStackTrace();
        }
        return calllogs;
    }
    public static  int Daydiff(Long date){
        int daydiff=0;
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date);
        daydiff = calendar.get(Calendar.DAY_OF_YEAR)-calendar1.get(Calendar.DAY_OF_YEAR);
        return daydiff;

    }
    public static String formatDate(long date){
        String dateStr = null;
        int diff = Daydiff(date);
        if (diff==0){
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            dateStr=format.format(new Date(date));
        }else if (diff==1){
            SimpleDateFormat format = new SimpleDateFormat("昨天  HH:mm:ss");
            dateStr=format.format(new Date(date));
        }else if (diff<=7){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date);
            int weekday = calendar.get(Calendar.DAY_OF_WEEK);
            switch (weekday){
                case Calendar.MONDAY:
                    dateStr = "周一";
                    break;
                case Calendar.TUESDAY:
                    dateStr = "周二";
                    break;
                case Calendar.WEDNESDAY:
                    dateStr = "周三";
                    break;
                case Calendar.THURSDAY:
                    dateStr = "周四";
                    break;
                case Calendar.FRIDAY:
                    dateStr = "周五";
                    break;
                case Calendar.SATURDAY:
                    dateStr = "周六";
                    break;
                case Calendar.SUNDAY:
                    dateStr = "周日";
                    break;
                 default:
                     break;
            }
        }else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            dateStr = format.format(new Date(date));
        }

        return dateStr;
    }
    public static int getPhotoIdByNumber(Context context,String number){
        int photoId=0;
        ContentResolver resolver = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String[]projection = new String[]{
                ContactsContract.PhoneLookup.PHOTO_ID
        };
        Cursor cursor = resolver.query(uri,projection,null,null,null);
        if (cursor.moveToNext()){
            photoId = cursor.getInt(0);
        }
        return photoId;

    }
    public static String getNameByNumber(Context context,String number){
        String name=null;
        ContentResolver resolver = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String[]projection = new String[]{
                ContactsContract.PhoneLookup.DISPLAY_NAME
        };
        Cursor cursor = resolver.query(uri,projection,null,null,null);
        if (cursor.moveToNext()){
            name = cursor.getString(0);
        }
        return name;

    }
    public static void DeleteCalllog(Context context , Colllog colllog){
        try {


        ContentResolver resolver =context.getContentResolver();
        Uri uri = CallLog.Calls.CONTENT_URI;
        String where = CallLog.Calls._ID+"=?";
        String args[] = new String[]{String.valueOf(colllog.get_id())};
        resolver.delete(uri,where,args);
        }catch (SecurityException se){
            se.printStackTrace();
        }

    }


}
