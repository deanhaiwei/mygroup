package com.example.youlu1803.Manager;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.youlu1803.Constant.IConstant;
import com.example.youlu1803.Entity.Conversation;
import com.example.youlu1803.Entity.SMS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.youlu1803.Manager.ContactsManager.Daydiff;

public class SMSManager {


    public static final Uri CONVERSATION_URI = Uri
            .parse("content://mms-sms/conversations");
    // 短信Uri 对应的ContentProvider会协调处理短信的收件箱和发件箱
    public static final Uri SMS_URI = Uri.parse("content://sms");
    // 短信发件箱:
    public static final Uri SMS_SEND_URI = Uri.parse("content://sms/sent");
    // 短信收件箱:
    public static final Uri SMS_INBOX_URI = Uri.parse("content://sms/inbox");
    public static void getConversation(Context context){
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(CONVERSATION_URI,null,null,
                null,null);

        if (cursor.moveToNext()){
            int count = cursor.getColumnCount();
            for (int i =0;i<count;i++){
                Log.i("TAG:", cursor.getColumnName(i)+"-->"+cursor.getString(i));
            }
        }
    }
    public static List<Conversation> getAllConversation(Context context){
        List<Conversation> conversations = new ArrayList<>();
        ContentResolver resolver =context.getContentResolver();
        String[]projection = new String[]{
                "thread_id",
                "address",
                "read",
                "date",
                "body",
        };
        String selection = null;
        String [] args = null;
        String order = "date desc ";
        Cursor cursor = resolver.query(CONVERSATION_URI,projection,selection,args,order);
        while (cursor.moveToNext()){
            Conversation conversation = new Conversation();
            int thread_id = cursor.getInt(0);
            String address = cursor.getString(1);
            int read = cursor.getInt(2);
            long date = cursor.getLong(3);
            String body = cursor.getString(4);
            conversation.setThread_id(thread_id);
            conversation.setAddress(address);
            conversation.setRead(read);
            conversation.setDate(date);
            conversation.setBody(body);
            conversation.setName(ContactsManager.getNameByNumber(context,address));
            conversation.setPhotoId(ContactsManager.getPhotoIdByNumber(context,address));
            conversation.setDateStr(ContactsManager.formatDate(date));
            conversations.add(conversation);
        }

        return conversations;
    }
    public static void UpdataConversation(Context context , int threadId){
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("read",1);
        String where = "thread_id=?";
        String[]args = new String[]{String.valueOf(threadId)};
        resolver.update(SMS_INBOX_URI,values,where,args);
    }
    public static void DeleteConversation(Context context , int threadId){
        ContentResolver resolver = context.getContentResolver();
        String where = "thread_id=?";
        String[]args = new String[]{String.valueOf(threadId)};
        resolver.delete(CONVERSATION_URI,where,args);
    }
    public static List<SMS>  getAllSMSES(Context context , int threadId){
        List<SMS> smses = new ArrayList<>();
        ContentResolver resolver =context.getContentResolver();

        String[]projection = new String[]{
                "_id","body","address","type","date"
        };
        String selection = "thread_id=?";
        String [] args = new String[]{String.valueOf(threadId)};
        String order = "date asc";
        Cursor cursor = resolver.query(SMS_URI,projection,selection,args,order);
        while (cursor.moveToNext()){
            SMS sms = new SMS();
            int _id=cursor.getInt(0);
            String body = cursor.getString(1);
            String address=cursor.getString(2);
            int type = cursor.getInt(3);
            long date = cursor.getLong(4);
            sms.set_id(_id);
            sms.setBody(body);
            sms.setAddress(address);
            sms.setDate(date);
            sms.setType(type);
            sms.setPhotoId(ContactsManager.getPhotoIdByNumber(context,address));
            sms.setDateStr(SMSformatDate(date));
            smses.add(sms);
        }

        return smses;
    }
    public static String SMSformatDate(long date){
        String dateStr = null;
        int diff = Daydiff(date);
        if (diff==0){
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            dateStr=format.format(new Date(date));
        }else if (diff==1){
            SimpleDateFormat format = new SimpleDateFormat("昨天  HH:mm:ss");
            dateStr=format.format(new Date(date));
        }else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            dateStr = format.format(new Date(date));
        }

        return dateStr;
    }
    public static SMS onReceiverSMS(Context context, Intent intent){
        SMS sms = new SMS();
        Bundle bundle = intent.getExtras();

        Object [] pdus = (Object[]) bundle.get("pdus");
        SmsMessage [] messages = new SmsMessage[pdus.length];
        String format = intent.getStringExtra("format");
        for(int i =0;i<pdus.length;i++){
            SmsMessage smsMessage = null;
            if (Build.VERSION.SDK_INT<23){
                smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }else {
                smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i],format);
            }
            messages[i]=smsMessage;
        }
        StringBuilder builder = new StringBuilder();
        String address=null;
        long date=0;
        for (int i= 0;i<messages.length;i++){
            if (i==0){
                address = messages[i].getOriginatingAddress();
                date = messages[i].getTimestampMillis();
            }
            builder.append(messages[i].getDisplayMessageBody());

        }
        sms.setAddress(address);
        sms.setDate(date);
        sms.setType(1);
        sms.setBody(builder.toString());

        return sms;
    }
    public static void SaveOnReceiverSMS(Context context,SMS sms , int threadId){
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("thread_id",threadId);
        values.put("body",sms.getBody());
        values.put("address",sms.getAddress());
        values.put("type",sms.getType());
        values.put("date",sms.getDate());
        values.put("read",1);
        resolver.insert(SMS_INBOX_URI,values);

    }
    public static void SendSMS(Context context,String message,String address){
        SmsManager manager = SmsManager.getDefault();
        ArrayList<String> messages = manager.divideMessage(message);
        for (int i=0;i<messages.size();i++){
            String body = messages.get(i);
            Intent intent = new Intent();
            intent.putExtra("body",body);
            intent.putExtra("address",address);
            intent.setAction(IConstant.SEND_SMS);
            PendingIntent sendIntent = PendingIntent.getBroadcast(context,100,
                    intent,PendingIntent.FLAG_UPDATE_CURRENT);
            manager.sendTextMessage(address,null,body,sendIntent,null);
        }
    }
    public static void SaveSendSMS(Context context,String body , String address){
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("body",body);
        values.put("address",address);
        values.put("type",2);
        values.put("date",System.currentTimeMillis());
        resolver.insert(SMS_SEND_URI,values);

    }

}
