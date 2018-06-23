package com.tarena.karen.youlu03.manager;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;

import com.tarena.karen.youlu03.R;
import com.tarena.karen.youlu03.constant.IConstant;
import com.tarena.karen.youlu03.entity.Calllog;
import com.tarena.karen.youlu03.entity.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pjy on 2017/5/17.
 */

public class ContactsManager {
    //实现联系人的检索
    //创建查询联系人的方法

    public static List<Contact>
        getAllContacts(Context context){
        List<Contact> contacts=
                new ArrayList<Contact>();
        Uri contact_Uri=
                ContactsContract.
                Contacts.CONTENT_URI;
        //获得访问内容提供者的内容解析器
        ContentResolver resolver=
                context.
                getContentResolver();
        String[] selection=
                new String[]{"_id","photo_id"};
        Cursor cursor=resolver.query(
                contact_Uri,
                selection,
                null,null,null);
        //循环读取记录
        while(cursor.moveToNext()){
            Contact contact=new Contact();
            //把数据提取出来联系人的编号和该联系人
            //的帐户ID是一致的,所以我们可以把联系人
            //的编号当成是联系人帐户ID,以此为条件查
            //每一个帐户对应的数据
            int _id=cursor.
                    getInt(cursor.
                    getColumnIndex("_id"));
            int photoId=cursor.
                    getInt(cursor.
                    getColumnIndex("photo_id"));
            contact.set_id(_id);
            contact.setPhotoId(photoId);
            Uri data_Uri=ContactsContract.Data.CONTENT_URI;
            String[] dataProjection=
                    new String[]{
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.Data.DATA1};
            //设置查询的条件
            //根据帐户编号查询某一编号的帐户的所有的数据
            String where=ContactsContract.
                    Data.RAW_CONTACT_ID+"=?";
            String args[]=new String[]
                    {String.valueOf(_id)};
            Cursor dataCursor=resolver.query(
                    data_Uri,
                    dataProjection,
                    where,args,null);
            while(dataCursor.moveToNext()){
                String mimeType=dataCursor.getString(0);
                String data1=dataCursor.getString(1);
                if(mimeType.equals(IConstant.MIMETYPE_NAME)){
                    contact.setName(data1);
                }else if(mimeType.equals(IConstant.MIMETYPE_PHONE)){
                    contact.setPhone(data1);
                }else if(mimeType.equals(IConstant.MIMETYPE_ADDRESS)){
                    contact.setAddress(data1);
                }else if(mimeType.equals(IConstant.MIMETYPE_EMAIL)){
                    contact.setEmail(data1);
                }
            }
            dataCursor.close();
    //再把联系信息添加到集合中
            contacts.add(contact);
        }
        cursor.close();
        return contacts;
    }

    /**
     * 根据头像编号查询联系人的头像
     * @param context
     * @param photoId 联系人的头像编号
     * @return
     */
    public static Bitmap getPhotoByPhotoId(
            Context context,int photoId){
        Bitmap photo=null;
        if(photoId<=0){
            //没有为联系人提供头像信息
            //为联系人设置一个默认头像
            photo=BitmapFactory.decodeResource(
                    context.getResources(),
                    R.drawable.ic_contact);
        }else{
            ContentResolver resolver=
                    context.getContentResolver();
            Uri data_Uri=ContactsContract.Data.CONTENT_URI;
            String[] projection=new String[]{
                    ContactsContract.Data.DATA15};
            String selection=
                    ContactsContract.Data._ID+"=?";
            String[] args=
                    new String[]{String.valueOf(photoId)};
            Cursor cursor=resolver.query(
                    data_Uri,
                    projection,
                    selection,
                    args,null);
            if(cursor.moveToNext()){
                byte[] bytes=cursor.getBlob(0);
                //将字节数组解码成一个图片
                photo= BitmapFactory.
                        decodeByteArray(
                                bytes,0,bytes.length);
            }
        }

        return  photo;
    }

    /**
     *
     * @param context 删除联系人
     */
    public static void
        deleteContact(Context context,
                      Contact contact){
        ContentResolver resolver=
                context.getContentResolver();
        Uri uri=ContactsContract.
                RawContacts.CONTENT_URI;
        String where= ContactsContract.
                RawContacts.
                CONTACT_ID+"=?";
        String[] args=new String[]{
                String.valueOf(contact.get_id())};
        resolver.delete(uri,where,args);
    }

    /**
     * 实现通话记录的查询
     * @param context
     * @return
     */
    public static List<Calllog>
    getAllCalllogs(Context context){
        List<Calllog> calllogs=
                new ArrayList<Calllog>();
        try {
            ContentResolver resolver =
                    context.getContentResolver();
            Uri uri = CallLog.Calls.CONTENT_URI;
            String[] projection =
                    new String[]
                    {
                    CallLog.Calls._ID,
                    CallLog.Calls.NUMBER,
                    CallLog.Calls.TYPE,
                    CallLog.Calls.DATE,
                    };
            String order = CallLog.Calls.DATE + " desc";
            Cursor cursor=resolver.query(uri,
                    projection,
                    null, null, order);
            while(cursor.moveToNext()){
                Calllog calllog=new Calllog();
                int _id=cursor.getInt(0);
                String number=cursor.getString(1);
                int type=cursor.getInt(2);
                long date=cursor.getLong(3);
                int photoId=getPhotoIdByNumber(
                        context,number);
                String name=getNameByNumber(
                        context,number);

                calllog.set_id(_id);
                calllog.setPhone(number);
                calllog.setType(type);
                calllog.setDate(date);
                calllog.setPhotoId(photoId);
                calllog.setName(name);

                calllogs.add(calllog);
            }
        }catch (SecurityException ex){
            ex.printStackTrace();
        }
        return calllogs;
    }

    /**
     * 根据电话号码查询头像编号
     * @return
     */
    public static int
        getPhotoIdByNumber(Context context,
                           String number){
        int photoId=0;
            ContentResolver resolver=
                    context.getContentResolver();
        //创建查询的uri及条件
        Uri uri=Uri.withAppendedPath(
                ContactsContract.PhoneLookup.
                CONTENT_FILTER_URI,Uri.encode(number));
        //构建要查询的联系人的字段信息
        String[] projection=
                new String[]{
                ContactsContract.
                PhoneLookup.PHOTO_ID};

        Cursor cursor=resolver.query(
                uri,projection,
                null,null,null);
        if(cursor.moveToNext()){
            photoId=cursor.getInt(0);
        }
        cursor.close();
        return  photoId;
    }
    public static String getNameByNumber(Context context,String number){
        String name=null;
        ContentResolver resolver=context.getContentResolver();
        Uri uri=Uri.withAppendedPath(
                ContactsContract.
                PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        Cursor cursor=resolver.query(uri,
                new String[]{ContactsContract.
                PhoneLookup.DISPLAY_NAME},
                null,null,null);
        if(cursor.moveToNext()){
            name=cursor.getString(0);
        }
        cursor.close();
        return name;
    }
}
