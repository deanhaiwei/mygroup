package com.example.text;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private EditText etphone;
    private ImageView ivcleanphone;
    private EditText etpassword;
    private CheckBox cbshow;
    private CheckBox cbmarkphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setphone();
        setpassword();
        setlogin();

    }



    private void setpassword() {
        cbshow = findViewById(R.id.cb_show);
        etpassword = findViewById(R.id.et_passwrod);
        cbshow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                etpassword.setInputType(isChecked?0x91:0x81);
            }
        });
        etpassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO){
                    setlogin();
                }

                return true;
            }
        });
    }

    private void setphone() {
        etphone = findViewById(R.id.et_phone);
        ivcleanphone = findViewById(R.id.iv_cleanphone);
        etphone.addTextChangedListener(new EtphoneTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            String phone =etphone.getText().toString();
            if (phone.length()>0){
                ivcleanphone.setVisibility(View.VISIBLE);
            }else {
                ivcleanphone.setVisibility(View.GONE);
            }
            }
        });
        ivcleanphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               etphone.setText("");
            }
        });
        SharedPreferences sp = getSharedPreferences("user",Context.MODE_PRIVATE);
        String phone =sp.getString("phonekey","");
        if (!TextUtils.isEmpty(phone)){
            etphone.setText(phone);
        }
    }
    private void setlogin() {

        Button btnlogin = findViewById(R.id.btn_login);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbmarkphone = findViewById(R.id.cb_markphone);
                String phone = etphone.getText().toString();
                String password = etpassword.getText().toString();
                if (phone.length() > 11||phone.length() < 11 && password.length() > 6) {
                    Toast.makeText(LoginActivity.this, "手机号必须为11位,密码最少6位数", Toast.LENGTH_SHORT).show();
                }  else {
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                }
                if (cbmarkphone.isChecked()) {
                    SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("phonekey", phone);
                    editor.commit();
                }

            }

        });



    }
}

