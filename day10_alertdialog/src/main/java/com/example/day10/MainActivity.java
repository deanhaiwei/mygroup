package com.example.day10;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.time.Month;
import java.time.Year;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher_round)
                .setTitle("提示")
                .setMessage("确定退出吗？")
                .setPositiveButton("确定",this)
                .setNegativeButton("取消",this)
                .show();

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
            super.onBackPressed();
                dialog.cancel();
            break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialog.cancel();
                break;
        }
    }
    private CharSequence[] items={"商务座","软座","硬座"};
    public void onClick(final View v){
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(items,-1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CharSequence item = items[which];
                        Button btn = (Button) v;
                        btn.setText(item);

                    }
                })

                .setNegativeButton("取消",this)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                dialog.cancel();
                                break;
                        }
                    }
                })
                .show();
    }
    public void onClick02(final View v){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int monthofYear = c.get(Calendar.MONTH);
        final int dayofMonth = c.get(Calendar.DAY_OF_MONTH);
          int date = c.get(Calendar.DAY_OF_WEEK);

        DatePickerDialog dialog =
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth ) {
                Calendar c = Calendar.getInstance();
                 c.set(Calendar.YEAR,year);
                 c.set(Calendar.MONTH,month);
                 c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                int date = c.get(Calendar.DAY_OF_WEEK);
                String [] dayofweeks={"周日","周一","周二","周三","周四","周五","周六"};
                String dayofweek = dayofweeks[date-1];
                String data = year+"/"+(month+1)+"/"+dayOfMonth+"("+dayofweek+")";
                Button btn = (Button) v;
                btn.setText(data);
            }
        }, year, monthofYear, dayofMonth);
               dialog .show();
    }
}
