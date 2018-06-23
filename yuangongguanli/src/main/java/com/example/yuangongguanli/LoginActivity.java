package com.example.yuangongguanli;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yuangongguanli.manager.HttpManager;

public class LoginActivity extends AppCompatActivity {

    private EditText editText_username;
    private EditText editText_password;
    private EditText editText_code;
    private ImageView imageView_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialUi();
        LoadCode();
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                Bitmap bitmap = (Bitmap) msg.obj;
                imageView_code.setImageBitmap(bitmap);
            }else if (msg.what==2){
                Boolean flag = (Boolean) msg.obj;
                if (flag){
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    private void LoadCode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = HttpManager.HttpCodeGet();
                Message msg = handler.obtainMessage();
                msg.what=1;
                msg.obj = bitmap;
                handler.sendMessage(msg);

            }
        }).start();

    }

    private void initialUi() {
        editText_username = findViewById(R.id.Login_EditText_Usersname);
        editText_password = findViewById(R.id.Login_EditText_Password);
        editText_code = findViewById(R.id.Login_EditText_Code);
        imageView_code = findViewById(R.id.Login_ImageView_Code);
    }
    public void login(View view){
        String username = editText_username.getText().toString();
        String password = editText_password.getText().toString();
        String code = editText_code.getText().toString();
        asyncLogin(username,password,code);
    }

    private void asyncLogin(final String username, final String password, final String code) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = HttpManager.HttpLoginPost(username,password,code);
                Message msg = handler.obtainMessage();
                msg.what=2;
                msg.obj = flag;
                handler.sendMessage(msg);
            }
        }).start();
    }

    public void refrashCode(View view){
        LoadCode();
    }
}
