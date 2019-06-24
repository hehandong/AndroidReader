package com.hehandong.androidreader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hehandong.androidreader.ui.fragment.HomeFragment;
import com.hehandong.androidreader.ui.fragment.second.child.childpager.HomePagerFragment;


public class MyPagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles;

    public MyPagerFragmentAdapter(FragmentManager fm, String... titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return HomePagerFragment.newInstance();
        } else {
//            return OtherPagerFragment.newInstance(mTitles[position]);
            return HomeFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
