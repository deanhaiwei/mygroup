package com.example.day19_myplayer;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolBar();
        setDrawerLayout();
        /*
        FragmentManager fragmentManager=
        getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=
        fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayoutId, new SongFragment(),"framt_Songs_list");
        fragmentTransaction.commit();
        */
    }
    public void setDrawerLayout(){
        drawerLayout = findViewById(R.id.drawerlayoutId);
        navigationView = findViewById(R.id.navigationviewId);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);
        TextView headerText = headerview.findViewById(R.id.headerTextViewId);
        headerText.setText(MyApplication.user!=null?MyApplication.user:"游客");
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.drawer_open,R.string.drawer_colse);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        drawerToggle.onOptionsItemSelected(item);
        if (item.getItemId()==R.id.nav_sub_2) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbarId);
        setSupportActionBar(toolbar);
        RadioGroup radioGroup = (RadioGroup) View.inflate(this,R.layout.toolbar_center,null);
        radioGroup.setOnCheckedChangeListener(this);
        toolbar.addView(radioGroup,new Toolbar.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT,
                Toolbar.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
        ));
        RadioButton radioButton = radioGroup.findViewById(R.id.radio_0);
        radioButton.setChecked(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentManager fragmentManager=
                getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        if (checkedId==R.id.radio_0) {

            fragmentTransaction.replace(R.id.framelayoutId, new Fragment_menu(), "framt_menu");
        }else if (checkedId==R.id.radio_1){

        }else if (checkedId==R.id.radio_2){

        }

        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.nav_item_1){
            startActivity(new Intent(this,HistoryActivity.class));
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if (item.getItemId()==R.id.nav_sub_2){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }
}
