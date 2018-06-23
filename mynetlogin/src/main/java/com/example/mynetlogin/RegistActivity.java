package com.example.mynetlogin;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mynetlogin.manage.HttpManage;
import com.example.mynetlogin.entity.User;

public class RegistActivity extends AppCompatActivity {

    private EditText editText_username;
    private EditText editText_passwrod;
    private EditText editText_emial;
    private EditText editText_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        InitialUI();
    }

    private void InitialUI() {
        editText_username = findViewById(R.id.editText_Username);
        editText_passwrod = findViewById(R.id.editText_PassWord);
        editText_emial = findViewById(R.id.editText_Emial);
        editText_phone = findViewById(R.id.editText_Phone);
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            User resultUser = (User) msg.obj;
            if (resultUser!=null){
                Toast.makeText(RegistActivity.this,"注册成功",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(RegistActivity.this,"注册失败！",Toast.LENGTH_SHORT).show();
            }
        }
    };
    public void Regist(View view){
        String username = editText_username.getText().toString();
        String passwrod = editText_passwrod.getText().toString();
        String emial = editText_emial.getText().toString();
        String phone = editText_phone.getText().toString();
        final User user = new User(username,passwrod,emial,phone);
        new Thread(new Runnable() {
            @Override
            public void run() {
                User registUser = HttpManage.RegistPost(user);
                Message message = handler.obtainMessage();
                message.obj = registUser;
                handler.sendMessage(message);
            }
        }).start();
    }
    public void Back(View view){
        finish();
    }
}
