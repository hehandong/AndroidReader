package com.hehandong.androidreader.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hehandong.androidreader.R;
import com.hehandong.androidreader.Retrofit.costomCore.CustomObserver;
import com.hehandong.androidreader.Retrofit.module.Benefit;
import com.hehandong.androidreader.Retrofit.module.MeiziModel;
import com.hehandong.androidreader.Retrofit.net.RetrofitHelper;
import com.hehandong.androidreader.utils.ConstantValues;
import com.hehandong.androidreader.widgets.pull.BaseViewHolder;
import com.hehandong.androidreader.widgets.pull.PullRecycler;
import com.hehandong.androidreader.widgets.pull.layoutmanager.ILayoutManager;
import com.hehandong.androidreader.widgets.pull.layoutmanager.MyGridLayoutManager;
import com.hehandong.retrofithelper.utils.LogUtils;
import com.hehandong.retrofithelper.utils.RxUtil;

import java.util.ArrayList;



public class PhotoListFragment extends BaseListFragment<Benefit> implements ITabFragment {
    private int page = 1;

    public static PhotoListFragment newInstance() {
        PhotoListFragment fragment = new PhotoListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static PhotoListFragment newInstance(int page) {
        PhotoListFragment fragment = new PhotoListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantValues.KEY_FRAGMENT_PAGE, page);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        enableLazyLoad();
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sample_list_item, parent, false);
        return new SampleViewHolder(view);
    }

    @Override
    protected ILayoutManager getLayoutManager() {
        return new MyGridLayoutManager(getContext(), 3);
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    @Override
    public void setUpData() {
        if (mDataList == null) {
            recycler.setRefreshing();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ConstantValues.KEY_SAVE_LIST, mDataList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mDataList = (ArrayList<Benefit>) savedInstanceState.getSerializable(ConstantValues.KEY_SAVE_LIST);
    }

    //    @Override
//    protected ILayoutManager getLayoutManager() {
//        return new MyStaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//    }

    @Override
    public void onRefresh(final int action) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }

        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            page = 1;
        }

        RetrofitHelper.getCustomApiService()
                .rxBenefits(12, page++)
                .compose(RxUtil.<MeiziModel>rxSchedulerHelper2(this))
                .subscribe(new CustomObserver<MeiziModel>() {
                    @Override
                    public void onSuccess(MeiziModel model) {
                        LogUtils.i("results.size：" + model.results.size());
                        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
                            mDataList.clear();
                        }
                        if (model.results == null || model.results.size() == 0) {
                            recycler.enableLoadMore(false);
                        } else {
                            recycler.enableLoadMore(true);
                            mDataList.addAll(model.results);
                            adapter.notifyDataSetChanged();
                        }
                        recycler.onRefreshCompleted();
                    }
                });

    }

    @Override
    public void onMenuItemClick() {

    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }

    class SampleViewHolder extends BaseViewHolder {

        ImageView mSampleListItemImg;
        TextView mSampleListItemLabel;

        public SampleViewHolder(View itemView) {
            super(itemView);
            mSampleListItemLabel = (TextView) itemView.findViewById(R.id.mSampleListItemLabel);
            mSampleListItemImg = (ImageView) itemView.findViewById(R.id.mSampleListItemImg);
        }

        @Override
        public void onBindViewHolder(int position) {
            mSampleListItemLabel.setVisibility(View.GONE);
            Glide.with(mSampleListItemImg.getContext())
                    .load(mDataList.get(position).url)
                    .centerCrop()
                    .placeholder(R.color.app_primary_color)
                    .error(R.mipmap.ic_launcher)
                    .crossFade()
                    .into(mSampleListItemImg);
        }

        @Override
        public void onItemClick(View view, int position) {
            if (getContext() instanceof HomeActivity) {
                ((HomeActivity) getContext()).showPhoteFragment(mDataList,position);
            }
        }

    }
}
