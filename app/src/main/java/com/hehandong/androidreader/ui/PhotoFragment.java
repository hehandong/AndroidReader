package com.hehandong.androidreader.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hehandong.androidreader.R;
import com.hehandong.androidreader.Retrofit.module.Benefit;
import com.hehandong.androidreader.viewPage.adapter.PhotoPagerAdapter;
import com.hehandong.androidreader.viewPage.transformer.GalleryTransformer;


import java.util.ArrayList;


public class PhotoFragment extends BaseFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String POSITION = "position";
    private static final String LIST = "list";
    private int mPosition;
    private ArrayList<Benefit> mImages;
    private FrameLayout mFrameLayout;
    private ViewPager mViewPager;
    private PhotoPagerAdapter mViewPagerAdapter;

    public static PhotoFragment newInstance(ArrayList<Benefit> images, int position) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        args.putSerializable(LIST, images);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(POSITION);
            mImages = (ArrayList<Benefit>) getArguments().getSerializable(LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        return view;
    }

    public PhotoFragment() {
        // Required empty public constructor
    }




    @Override
    public void setUpView(View view) {
        mFrameLayout = view.findViewById(R.id.layout_photo);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager_photo);
        mViewPagerAdapter = new PhotoPagerAdapter(getContext(), mImages);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageMargin(30);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setPageTransformer(false, new GalleryTransformer());


        mViewPager.setCurrentItem(mPosition,true);

        //事件分发，处理页面滑动问题
        mFrameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });
    }

    @Override
    public void setUpData() {

    }

}
