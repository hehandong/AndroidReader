package com.hehandong.androidreader.ui.fragment.second.child.childpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hehandong.androidreader.R;
import com.hehandong.androidreader.Retrofit.costomCore.CustomObserver;
import com.hehandong.androidreader.Retrofit.module.WanBaseModel;
import com.hehandong.androidreader.Retrofit.module.reponse.HomeListModel;
import com.hehandong.androidreader.Retrofit.net.WanAandroidManager;
import com.hehandong.androidreader.adapter.HomeListAdapter;
import com.hehandong.androidreader.event.TabSelectedEvent;
import com.hehandong.androidreader.listener.OnItemClickListener;
import com.hehandong.androidreader.ui.ZhiHuMainActivity;
import com.hehandong.androidreader.ui.fragment.third.child.child.ArticleFragment;
import com.hehandong.retrofithelper.utils.RxUtil;

import org.greenrobot.eventbus.Subscribe;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * @Author dong
 * @Date 2019-06-04 15:40
 * @Description 玩Android的首页
 * GitHub：https://github.com/hehandong
 * Email：hehandong@qq.com
 * @Version 1.0
 */
public class HomePagerFragment extends SupportFragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecy;
    private SwipeRefreshLayout mRefreshLayout;
    private HomeListAdapter mAdapter;
    private boolean mAtTop = true;
    private int mScrollTotal;

    public static HomePagerFragment newInstance() {

        Bundle args = new Bundle();
        HomePagerFragment fragment = new HomePagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_pager_first, container, false);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(this);

        mAdapter = new HomeListAdapter(_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);
        mRecy.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                // 这里的DetailFragment在flow包里
                // 这里是父Fragment启动,要注意 栈层级
//                ((SupportFragment) getParentFragment()).start(DetailFragment.newInstance(mAdapter.getItem(position).getTitle()));

                HomeListModel.DatasBean item = mAdapter.getItem(position);
                String title = item.getTitle();
                String link = item.getLink();

                ((SupportFragment) getParentFragment()).start(ArticleFragment.newInstance(title,link));
            }
        });

        // Init Datas
        WanAandroidManager.getAPI()
                .getHomeList(0)
                .compose(RxUtil.<WanBaseModel<HomeListModel>>rxSchedulerHelper2(this))
                .subscribe(new CustomObserver<WanBaseModel<HomeListModel>>() {
                    @Override
                    public void onSuccess(WanBaseModel<HomeListModel> model) {
                        mAdapter.setDatas(model);
                    }
                });


        mRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollTotal += dy;
                if (mScrollTotal <= 0) {
                    mAtTop = true;
                } else {
                    mAtTop = false;
                }
            }
        });
    }

    @Override
    public void onRefresh() {
//        mRefreshLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mRefreshLayout.setRefreshing(false);
//            }
//        }, 2000);
    }

    private void scrollToTop() {
        mRecy.smoothScrollToPosition(0);
    }

    /**
     * 选择tab事件
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != ZhiHuMainActivity.SECOND) {
            return;
        }

        if (mAtTop) {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        } else {
            scrollToTop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }

}
