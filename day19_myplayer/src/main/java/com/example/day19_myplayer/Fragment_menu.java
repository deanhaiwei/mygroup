package com.example.day19_myplayer;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_menu extends Fragment {


    private FragmentPagerAdapter mAdpter;
    private ViewPager viewPager;

    public Fragment_menu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_menu, container, false);
        setViewPager(view);
        setTabLayout(view);
        return view;

    }
    public void setViewPager(View view){
        viewPager = view.findViewById(R.id.musicFragmentViewPagerId);
        mAdpter = new MyFraAdpter(getChildFragmentManager());
        viewPager.setAdapter(mAdpter);
    }
    public void setTabLayout(View view){
        TabLayout tabLayout = view.findViewById(R.id.tablayoutId);
        for (int i=0; i<mAdpter.getCount();i++){
            tabLayout.addTab(tabLayout.newTab());
        }
        tabLayout.setupWithViewPager(viewPager);
    }
    class MyFraAdpter extends FragmentPagerAdapter{

         MyFraAdpter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position==0){
                return "歌手";
            }else if (position == 1 ){
                return "歌手";
            }
            return null;
        }

        @Override
        public Fragment getItem(int position) {
            if (position==0){
                return new SongFragment();
            }else if (position==1){
                return new ArtistFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
