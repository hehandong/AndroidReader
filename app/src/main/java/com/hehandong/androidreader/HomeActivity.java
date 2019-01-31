package com.hehandong.androidreader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.hehandong.androidreader.ui.SampleListRxJavaFragment;
import com.hehandong.androidreader.utils.LogUtil;
import com.hehandong.androidreader.widgets.BottomNavigationViewHelper;

public class HomeActivity extends AppCompatActivity {

    private SampleListRxJavaFragment mSampleListFragment;

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
                    hideSampleListFragment();
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

        BottomNavigationView navigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    public void showSampleListFragment() {
        if (mSampleListFragment != null) {
            mSampleListFragment = (SampleListRxJavaFragment) getSupportFragmentManager().findFragmentByTag("SampleListRxJavaFragment");
            LogUtil.e(mSampleListFragment.toString());
        } else {
            mSampleListFragment = new SampleListRxJavaFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mSampleListFragmentLayout, mSampleListFragment, "SampleListRxJavaFragment").commit();
        }
    }

    public void hideSampleListFragment() {
        if (mSampleListFragment != null) {
            mSampleListFragment = (SampleListRxJavaFragment) getSupportFragmentManager().findFragmentByTag("SampleListRxJavaFragment");
            getSupportFragmentManager().beginTransaction().remove(mSampleListFragment).commit();
            mSampleListFragment = null;
        }
    }

}
