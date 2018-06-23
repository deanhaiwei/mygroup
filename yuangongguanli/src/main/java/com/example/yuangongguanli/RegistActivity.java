package com.example.yuangongguanli;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yuangongguanli.Entity.User;
import com.example.yuangongguanli.manager.HttpManager;

public class RegistActivity extends AppCompatActivity {

    private EditText editText_loginname;
    private EditText editText_password;
    private EditText editText_enterpassword;
    private EditText editText_realname;
    private EditText editText_emial;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initialUi();
    }

    private void initialUi() {
        editText_loginname = findViewById(R.id.Regist_EditText_Usersname);
        editText_password = findViewById(R.id.Regist_EditText_Password);
        editText_enterpassword = findViewById(R.id.Regist_EditText_EnterPassword);
        editText_realname = findViewById(R.id.Regist_EditText_RealName);
        editText_emial = findViewById(R.id.Regist_EditText_Emial);
    }

    public void regist(View view){
        String loginname = editText_loginname.getText().toString();
        String password = editText_password.getText().toString();
        String enterpassword = editText_enterpassword.getText().toString();
        String realname = editText_realname.getText().toString();
        String emial = editText_emial.getText().toString();
        if (TextUtils.isEmpty(loginname)||TextUtils.isEmpty(password)||TextUtils.isEmpty(realname)
                ||TextUtils.isEmpty(emial)){
            Toast.makeText(this,"请填写完整的信息",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(enterpassword)){
            Toast.makeText(this,"密码和确认密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }
        user = new User(-1,loginname,password,realname,emial);
        asyncRegist(user);
    }
    @SuppressLint("HandlerLeak")
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Boolean flag = (Boolean) msg.obj;
            Log.i("TAG:flag", flag+"");
            if (flag){
                Toast.makeText(RegistActivity.this,"账号:"+user.getLoginname()+"密码:"
                +user.getPassword(),Toast.LENGTH_SHORT).show();
                ClearContent();
                startActivity(new Intent(RegistActivity.this,LoginActivity.class));
                finish();
            }else {
                Toast.makeText(RegistActivity.this,
                        "注册失败",
                        Toast.LENGTH_LONG).show();
            }
        }
    };
    private void asyncRegist(final User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = HttpManager.HttpRegistPost(user);
                Message msg = handler.obtainMessage();
                msg.obj = flag;
                handler.sendMessage(msg);
            }
        }).start();

    }
    public void  ClearContent(){
        editText_loginname.setText("");
        editText_password.setText("");
        editText_enterpassword.setText("");
        editText_realname.setText("");
        editText_emial.setText("");
    }
}
