package com.example.yuangongguanli;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yuangongguanli.Adapter.EmployeeAdapter;
import com.example.yuangongguanli.Entity.Employee;
import com.example.yuangongguanli.manager.HttpManager;

import java.util.List;

public class BrowseActivity extends AppCompatActivity {

    private TextView browse_add;
    private ListView listView;
    private EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        initialUi();
        asyncLoadEmps();
    }
    @SuppressLint("HandlerLeak")
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            List<Employee> employees = (List<Employee>) msg.obj;
           if (employees!=null){
               adapter.addEmployees(employees);
           }


        }
    };
    private void asyncLoadEmps() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Employee> employees = HttpManager.queryEmployeesHttpGet();
                Message msg = handler.obtainMessage();
                msg.obj = employees;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void initialUi() {
        browse_add = findViewById(R.id.Browse_Add);
        listView = findViewById(R.id.Browse_listView);
        adapter = new EmployeeAdapter(this);
        listView.setAdapter(adapter);
    }
    public void Browse_add(View view){
        startActivity(new Intent(this,AddActivity.class));
        finish();
    }
}
