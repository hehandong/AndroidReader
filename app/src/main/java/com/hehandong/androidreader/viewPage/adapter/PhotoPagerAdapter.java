package com.hehandong.androidreader.viewPage.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hehandong.androidreader.Retrofit.module.Benefit;
import com.hehandong.androidreader.viewPage.utils.DensityUtil;

import java.util.ArrayList;


/**
 * ViewPager适配器
 * Create by: chenwei.li
 * Date: 2017/12/8
 * time: 15:17
 * Email: lichenwei.me@foxmail.com
 */

public class PhotoPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<Benefit> mImages;

    public PhotoPagerAdapter(Context context, ArrayList<Benefit> images) {
        this.mContext = context;
        this.mImages = images;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(DensityUtil.dip2px(mContext,200), DensityUtil.dip2px(mContext,400)));

        Glide.with(mContext)
                .load(mImages.get(position).url)
                .crossFade()
                .into(imageView);

        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
