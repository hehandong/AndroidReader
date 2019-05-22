package com.hehandong.androidreader.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.hehandong.androidreader.R;
import com.hehandong.androidreader.Retrofit.module.Benefit;
import com.hehandong.androidreader.widgets.BottomNavigationViewHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    private Fragment mFragment;

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    hideSampleListFragment();
                    return true;
                case R.id.navigation_dashboard:
                    hideSampleListFragment();
                    return true;
                case R.id.navigation_notifications:
                    showLoginFragment();
                    return true;
                case R.id.navigation_serve:
                    showSampleListFragment();
                    return true;
            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        mNavigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(mNavigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }



    public void showSampleListFragment() {
        if (mFragment != null && mFragment instanceof PhotoListFragment) {
            mFragment = (PhotoListFragment) getSupportFragmentManager().findFragmentByTag("PhotoList2Fragment");
        } else {
            mFragment = new PhotoListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mSampleListFragmentLayout, mFragment, "PhotoList2Fragment").commit();
        }
    }

    public void hideSampleListFragment() {
        if (mFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(mFragment).commit();
            mFragment = null;
        }
    }

    public void showPhoteFragment(ArrayList<Benefit> images, int position){
        mFragment = PhotoFragment.newInstance(images,position);
        getSupportFragmentManager().beginTransaction().replace(R.id.mSampleListFragmentLayout, mFragment, "photoFragment").commit();
    }

    public void showLoginFragment(){
        mFragment = LoginFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.mSampleListFragmentLayout, mFragment, "LoginFragment").commit();
    }

}
