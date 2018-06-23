package com.example.yuangongguanli;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StarActivity extends AppCompatActivity {

    private Button btn_regist;
    private Button btn_login;
    private Button btn_add;
    private Button btn_browse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        initialUi();
    }

    private void initialUi() {
        btn_regist = findViewById(R.id.Star_btn_Regist);
        btn_login = findViewById(R.id.Star_btn_Login);
        btn_add = findViewById(R.id.Star_btn_Add);
        btn_browse = findViewById(R.id.Star_btn_Browse);
    }
    public void Regist(View view){
        startActivity(new Intent(this,RegistActivity.class));
    }
    public void Login(View view){
        startActivity(new Intent(this,LoginActivity.class));
    }
    public void Add(View view){
        startActivity(new Intent(this,AddActivity.class));
    }
    public void Browse(View view){
        startActivity(new Intent(this,BrowseActivity.class));
    }
}
