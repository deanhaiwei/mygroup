package com.example.mynetlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mynetlogin.manage.HttpManage;

public class LoginActivity extends AppCompatActivity {

    private EditText editText_username;
    private EditText editText_passwrod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InintialUI();
    }

    private void InintialUI() {
        editText_username = findViewById(R.id.editText_username);
        editText_passwrod = findViewById(R.id.editText_password);
    }
    public void login(View view ){
        final String username = editText_username.getText().toString();
        final String password = editText_passwrod.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final boolean flag = HttpManage.LoginPost(username,password);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (flag) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                }

        }).start();
    }
    public void regist(View view){
        Intent intent = new Intent(this,RegistActivity.class);
        startActivity(intent);
    }
}
