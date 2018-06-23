package com.example.day19_myplayer;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {
    private int images[]={R.mipmap.aha,R.mipmap.ahb,R.mipmap.ahc};
    private ViewPager viewPager;
    private RadioGroup Mgroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        setLogonBtn();
        setViewPager();
        setRadioGroup();

    }
    private void setLogonBtn(){
        Button logon =findViewById(R.id.logonbtnId);
        logon.setOnClickListener(this);
        Button enter = findViewById(R.id.enterbtnId);
        enter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.logonbtnId){
           // startActivity(new Intent(this,));
            startActivity(new Intent(this,LoginActivity.class));
        }else if (v.getId()==R.id.enterbtnId){
            startActivity(new Intent(this,MainActivity.class));
        }
    }
    private void setRadioGroup(){
        Mgroup = findViewById(R.id.guideRadioGroupId);
        Mgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton btn = group.findViewById(checkedId);
                int position = group.indexOfChild(btn);
                btn.setChecked(true);
                viewPager.setCurrentItem(position);

            }
        });

    }
    private void setViewPager(){
        viewPager = findViewById(R.id.guideViewPagerId);
        PagerAdapter adapter = new ImageAdpter();
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new BaseOnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (Mgroup!=null){
                    RadioButton btn = (RadioButton) Mgroup.getChildAt(position);
                    btn.setChecked(true);

                }
            }
        });
    }
    class ImageAdpter extends PagerAdapter{

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            int ImageId= images[position];
            imageView.setImageResource(ImageId);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }
    }


}
