package com.example.day10;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        drawerLayout = findViewById(R.id.activity_main);
        drawerToggle = new ActionBarDrawerToggle(
                this,drawerLayout, R.string.open_drawer,R.string.close_drawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                setTitle("Open");
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                setTitle("Close");
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
       Boolean flog =  super.onPrepareOptionsMenu(menu);
       drawerToggle.syncState();
       return  flog;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        drawerToggle.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id==R.id.list_item01){
            Toast.makeText(this,item.getTitle(),Toast.LENGTH_SHORT).show();
        }else  if (id==R.id.list_item02){
            Toast.makeText(this,item.getTitle(),Toast.LENGTH_SHORT).show();
        }else if (id==android.R.id.home){
            //setLogo();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setLogo() {
        Boolean open = drawerLayout.isDrawerOpen(GravityCompat.START);
        if (open) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }
    public void onClick(View v){
        startActivity(new Intent(this,MusicActivity.class));
    }
}
