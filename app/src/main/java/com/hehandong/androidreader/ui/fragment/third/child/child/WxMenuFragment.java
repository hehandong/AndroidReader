package com.hehandong.androidreader.ui.fragment.third.child.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hehandong.androidreader.R;
import com.hehandong.androidreader.Retrofit.costomCore.CustomObserver;
import com.hehandong.androidreader.Retrofit.module.WanBaseModel;
import com.hehandong.androidreader.Retrofit.module.WxArticleListModel;
import com.hehandong.androidreader.Retrofit.module.WxMenuListModel;
import com.hehandong.androidreader.Retrofit.net.WanAandroidManager;
import com.hehandong.androidreader.adapter.WxListAdapter;
import com.hehandong.androidreader.listener.OnItemClickListener;
import com.hehandong.androidreader.ui.fragment.third.child.WxFragment;
import com.hehandong.retrofithelper.utils.LogUtils;
import com.hehandong.retrofithelper.utils.RxUtil;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @Author dong
 * @Date 2019-06-04 15:40
 * @Description TODO
 * GitHub：https://github.com/hehandong
 * Email：hehandong@qq.com
 * @Version 1.0
 */
public class WxMenuFragment extends SupportFragment {
    private static final String WX_MENUS = "wx_menus";
    private static final String SAVE_STATE_POSITION = "save_state_position";

    private RecyclerView mRecy;
    private WxListAdapter mAdapter;

    private WxMenuListModel mWxMenuListModel;
    private int mCurrentPosition = -1;

    public static WxMenuFragment newInstance(WxMenuListModel model) {

        Bundle args = new Bundle();
        args.putSerializable(WX_MENUS, model);

        WxMenuFragment fragment = new WxMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mWxMenuListModel = (WxMenuListModel) args.getSerializable(WX_MENUS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_menu, container, false);
        initView(view);
        return view;
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultNoAnimator();
    }

    private void initView(View view) {
        mRecy = (RecyclerView) view.findViewById(R.id.recy);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);
        mAdapter = new WxListAdapter(_mActivity);
        mRecy.setAdapter(mAdapter);
        mAdapter.setDatas(mWxMenuListModel);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                showContent(position, false);
            }
        });

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(SAVE_STATE_POSITION);
            mAdapter.setItemChecked(mCurrentPosition);
        } else {
            mCurrentPosition = 0;
            mAdapter.setItemChecked(0);
        }

        showContent(0, true);
    }

    private void showContent(final int position, final boolean isFirst) {

        if (!isFirst && position == mCurrentPosition) {
            return;
        }

        mCurrentPosition = position;

        mAdapter.setItemChecked(position);

        if (mWxMenuListModel != null && mWxMenuListModel.getData() != null) {
            WxMenuListModel.DataBean dataBean = mWxMenuListModel.getData().get(position);
            WanAandroidManager.getAPI()
                    .getWxArticleList(dataBean.getId(), 1)
                    .compose(RxUtil.<WanBaseModel<WxArticleListModel>>rxSchedulerHelper2(this))
                    .subscribe(new CustomObserver<WanBaseModel<WxArticleListModel>>() {
                        @Override
                        public void onSuccess(WanBaseModel<WxArticleListModel> model) {
                            LogUtils.i("WanAandroidManager", "results.size：" + model.toString());


                            ((WxFragment) getParentFragment()).switchWxArticleListFragment(model, isFirst);
                        }
                    });

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_STATE_POSITION, mCurrentPosition);
    }
}
