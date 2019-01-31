package com.hehandong.androidreader.viewPage.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.hehandong.androidreader.R;
import com.hehandong.androidreader.viewPage.adapter.ViewPagerAdapter;
import com.hehandong.androidreader.viewPage.transformer.CardTransformer;


public class CardVPActivity extends AppCompatActivity {

    private RelativeLayout mRelativeLayout;
    private ViewPager mViewPager;
    private int[] mImages={R.drawable.b, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h};
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        mRelativeLayout= (RelativeLayout) findViewById(R.id.rl_main_content);
        mViewPager= (ViewPager) findViewById(R.id.vp_main_pager);
        mViewPagerAdapter=new ViewPagerAdapter(this,mImages);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setPageTransformer(true,new CardTransformer());
    }



}
