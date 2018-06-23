package com.example.day10;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends AppCompatActivity {

    private LinearLayout bottomLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        //初始化RecyclerView
        setRecyclerView();
        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        AppBarLayout layout= (AppBarLayout)
                findViewById(R.id.appBarLayoutId);
        bottomLayout = findViewById(R.id.bottomLayoutId);
        layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                bottomLayout.setTranslationY(-verticalOffset);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(Menu.NONE,
                101,
                100,
                "list"
        );
        item.setIcon(R.mipmap.lianxi_numlist_on);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == 101) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager)
                recyclerView.setLayoutManager(linearLayoutManager);
            else {
                recyclerView.setLayoutManager(gridLayoutManager);
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void setRecyclerView() {
        //1.获得RecyclerView并进行简单设置
        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        //2.构建并关联layoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //3.构建并关联适配器
        List<News> mObjects=loadObjects();
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(mObjects);
        recyclerView.setAdapter(adapter);
        //4.添加事件监听

    }


    private List<News> loadObjects(){
        List<News> mObjects=new ArrayList<>();
        mObjects.add(new News(R.mipmap.png_01,"News-title-01"));
        mObjects.add(new News(R.mipmap.png_02,"News-title-02"));
        mObjects.add(new News(R.mipmap.png_03,"News-title-03"));
        mObjects.add(new News(R.mipmap.png_04,"News-title-04"));
        mObjects.add(new News(R.mipmap.png_05,"News-title-05"));
        return mObjects;
    }
}
