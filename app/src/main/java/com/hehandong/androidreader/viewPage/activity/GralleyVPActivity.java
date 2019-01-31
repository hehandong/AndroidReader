package com.hehandong.androidreader.viewPage.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.hehandong.androidreader.R;
import com.hehandong.androidreader.viewPage.adapter.ViewPagerAdapter;
import com.hehandong.androidreader.viewPage.transformer.GalleryTransformer;


public class GralleyVPActivity extends AppCompatActivity {

    private RelativeLayout mRelativeLayout;
    private ViewPager mViewPager;
    private int[] mImages = {R.drawable.b, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h};
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl_main_content);
        mViewPager = (ViewPager) findViewById(R.id.vp_main_pager);
        mViewPagerAdapter = new ViewPagerAdapter(this, mImages);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageMargin(30);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setPageTransformer(false, new GalleryTransformer());


        //事件分发，处理页面滑动问题
        mRelativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });

    }


}
