package com.example.youlu1803.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyFragmnetAdapter extends FragmentPagerAdapter {
    public MyFragmnetAdapter(FragmentManager fm) {
        super(fm);
    }
    private List<Fragment> fragments = new ArrayList<>();

    public void addFragment(Fragment fragment){
        if (fragment!=null) {
            fragments.add(fragment);
            notifyDataSetChanged();
        }
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
