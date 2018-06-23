package com.example.yuangongguanli;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.yuangongguanli.Entity.Employee;
import com.example.yuangongguanli.manager.HttpManager;

public class AddActivity extends AppCompatActivity {

    private EditText editText_name;
    private EditText editText_salary;
    private EditText editText_age;
    private RadioButton radioButton_m;
    private RadioButton radioButton_f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initialUi();
    }

    private void initialUi() {
        editText_name = findViewById(R.id.Add_EditText_Name);
        editText_salary = findViewById(R.id.Add_EditText_Salary);
        editText_age = findViewById(R.id.Add_EditText_Age);
        radioButton_m = findViewById(R.id.Add_RadioButton_M);
        radioButton_f = findViewById(R.id.Add_RadioButton_F);
    }
    public void add (View view){
        String name = editText_name.getText().toString();
        double salary = Double.parseDouble(editText_salary.getText().toString());
        int age = Integer.parseInt(editText_age.getText().toString());
        String gender="";
        if (radioButton_m.isChecked()){
            gender="m";
        }else if (radioButton_f.isChecked()){
            gender="f";
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(AddActivity.this,
                    "员工姓名不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        if(age<=0||salary<=0){
            Toast.makeText(AddActivity.this,
                    "请填写有效的年龄或薪水",
                    Toast.LENGTH_LONG).show();
            return;
        }
        Employee employee = new Employee(-1,name,salary,age,gender);
        asyncAddEmployee(employee);

    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Boolean flag = (Boolean) msg.obj;
            if(flag){
                Toast.makeText(AddActivity.this,
                        "添加成功!",
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(AddActivity.this,BrowseActivity.class));
                finish();
            }else{
                Toast.makeText(
                        AddActivity.this,
                        "添加失败!",Toast.LENGTH_LONG).show();
            }
        }
    };
    private void asyncAddEmployee(final Employee employee) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag=HttpManager.HttpAddPost(employee);
                Message msg = handler.obtainMessage();
                msg.obj = flag;
                handler.sendMessage(msg);
            }
        }).start();
    }
}
