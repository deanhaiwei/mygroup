package com.example.youlu1803;

import android.app.Activity;
import android.content.Intent;
import android.provider.Telephony;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.youlu1803.Adapter.MyFragmnetAdapter;
import com.example.youlu1803.Fragment.CalllogFragment;
import com.example.youlu1803.Fragment.ContactFragment;
import com.example.youlu1803.Fragment.DialpadFragment;
import com.example.youlu1803.Fragment.SMSFragment;

public class MainActivity extends FragmentActivity {
    Fragment colllog;
    Fragment contact;
    Fragment dialpad;
    Fragment SMS;
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private MyFragmnetAdapter adapter;
    private String defaultsmsApp=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defaultsmsApp= Telephony.Sms.getDefaultSmsPackage(this);
        initialFragment();
        setListener();
        setDefaultSMS(getPackageName());
    }

    private void setDefaultSMS(String packafeName) {
        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,packafeName);
        startActivity(intent);

    }

    private void setListener(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        radioGroup.check(R.id.calllog);
                        break;
                    case 1:
                        radioGroup.check(R.id.contact);
                        break;
                    case 2:
                        radioGroup.check(R.id.dailped);
                        break;
                    case 3:
                        radioGroup.check(R.id.sms);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.calllog:
                        viewPager.setCurrentItem(0,false);
                        break;
                    case R.id.contact:
                        viewPager.setCurrentItem(1,false);
                        break;
                    case R.id.dailped:
                        viewPager.setCurrentItem(2,false);
                        break;
                    case R.id.sms:
                        viewPager.setCurrentItem(3,false);
                        break;
                     default:
                         break;
                }
            }
        });
    }
    private void initialFragment(){
        viewPager = findViewById(R.id.ViewPager_Main);
        radioGroup = findViewById(R.id.main_RadioGroup);
        colllog = new CalllogFragment();
        contact = new ContactFragment();
        dialpad = new DialpadFragment();
        SMS = new SMSFragment();
        adapter = new MyFragmnetAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        adapter.addFragment(colllog);
        adapter.addFragment(contact);
        adapter.addFragment(dialpad);
        adapter.addFragment(SMS);
        viewPager.setCurrentItem(1,false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setDefaultSMS(defaultsmsApp);
    }
}
