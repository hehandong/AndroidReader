package com.hehandong.androidreader.ui;

import android.os.Bundle;

import com.hehandong.androidreader.R;
import com.hehandong.androidreader.Retrofit.module.Benefit;
import com.hehandong.androidreader.utils.LogUtil;

import java.util.ArrayList;


/**
 * Created by Stay on 2/2/16.
 * Powered by www.stay4it.com
 */
public class SampleListRxJavaActivity extends BaseActivity {

    private PhotoListFragment mSampleListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_sample_list_1, R.string.title_recycler_fragment);
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            LogUtil.e("onCreate(savedInstanceState);" + savedInstanceState);
            mSampleListFragment = (PhotoListFragment) getSupportFragmentManager().findFragmentByTag("PhotoList2Fragment");
            LogUtil.e(mSampleListFragment.toString());
        } else {
            mSampleListFragment = new PhotoListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mSampleListFragmentLayout, mSampleListFragment, "PhotoList2Fragment").commit();
        }

    }


    public void showPhoteFragment(ArrayList<Benefit> images, int position){
        PhotoFragment photoFragment = PhotoFragment.newInstance(images,position);
        getSupportFragmentManager().beginTransaction().replace(R.id.mSampleListFragmentLayout, photoFragment, "photoFragment").commit();
    }
}
