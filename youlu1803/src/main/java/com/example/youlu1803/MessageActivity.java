package com.example.youlu1803;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.youlu1803.Adapter.SMSAdapter;
import com.example.youlu1803.Constant.IConstant;
import com.example.youlu1803.Entity.SMS;

import com.example.youlu1803.Manager.SMSManager;

import java.util.List;

public class MessageActivity extends Activity {

    private String name;
    private static String address;
    private static int threadId;
    private static ListView listView;
    private static SMSAdapter adapter;
    private ImageView imageView_left;
    private TextView textView_title;
    private ImageView imageVie_right;
    private EditText editText_body;
    static  Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        context = getApplicationContext();
        initialData();
        setMessageActivity();
        setListView();
    }
    private void initialData() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        threadId = intent.getIntExtra("thread_Id",0);
    }

    private void setMessageActivity() {
        editText_body = findViewById(R.id.EditText_SendBody);
        imageView_left = findViewById(R.id.ImageView_ActionBar_left);
        textView_title = findViewById(R.id.TextView_ActionBar_Title);
        imageVie_right = findViewById(R.id.ImageView_ActionBar_Right);

       imageView_left.setImageResource(R.mipmap.ic_back);
       imageVie_right.setVisibility(View.INVISIBLE);
       if (TextUtils.isEmpty(name)){
           textView_title.setText(address);
       }else{
           textView_title.setText(name);
       }
       imageView_left.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });

    }

    private void setListView() {
        listView = findViewById(R.id.MessageActivity_ListView);
        adapter = new SMSAdapter(this);
        listView.setAdapter(adapter);
        refreshSMS();
    }

    private static void refreshSMS() {
        List<SMS> smses = SMSManager.getAllSMSES(context,threadId);
        adapter.addData(smses,true);
        listView.setSelection(adapter.getCount()-1);
    }


    public static  class SMSReceiver extends BroadcastReceiver{


        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (IConstant.SMS_RECEIVED.equals(action)){
                SMS sms = SMSManager.onReceiverSMS(context,intent);
                if (sms.getAddress().equals(address)){
                    SMSManager.SaveOnReceiverSMS(context,sms,threadId);
                    refreshSMS();
                }
            }else if (IConstant.SEND_SMS.equals(action)){
                String body = intent.getStringExtra("body");
                String address = intent.getStringExtra("address");
                SMSManager.SaveSendSMS(context,body,address);
                refreshSMS();
            }
        }
    }
    public void send(View view){
        String message = editText_body.getText().toString();
        SMSManager.SendSMS(this,message,address);
        editText_body.setText("");
    }
}
