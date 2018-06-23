package com.example.mygroup.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mygroup.frament.FragmentA;
import com.example.mygroup.frament.FragmentB;
import com.example.mygroup.frament.FragmentC;
import com.example.mygroup.frament.FragmentD;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdpter extends FragmentPagerAdapter {
    List<Fragment> fragments = new ArrayList<>();
    public FragmentAdpter(FragmentManager fm) {
        super(fm);
        fragments.add(new FragmentA());
        fragments.add(new FragmentB());
        fragments.add(new FragmentC());
        fragments.add(new FragmentD());

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
