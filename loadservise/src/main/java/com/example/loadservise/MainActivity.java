package com.example.loadservise;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private MyConnection con;
    IStudent iStudent=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialUI();
        Intent intent = new Intent(this,StudentService.class);
        bindService(intent,con, Service.BIND_AUTO_CREATE);
    }
    class MyConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iStudent= (IStudent) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iStudent=null;
        }
    }


    private void initialUI() {
        editText = findViewById(R.id.editTextnum);
        textView = findViewById(R.id.textView_name);
    }
    public void Query(View view){
        String numStr = editText.getText().toString();
        int num = Integer.parseInt(numStr);
        String name = iStudent.QueryStudent(num);
        if (!TextUtils.isEmpty(name)){
            textView.setText(name);
        }else {
            Toast.makeText(this,"该学生不存在",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(con);
    }
}
