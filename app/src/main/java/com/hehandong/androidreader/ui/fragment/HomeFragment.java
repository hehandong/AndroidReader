package com.hehandong.androidreader.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hehandong.androidreader.R;
import com.hehandong.androidreader.Retrofit.costomCore.CustomObserver;
import com.hehandong.androidreader.Retrofit.module.WanBaseModel;
import com.hehandong.androidreader.Retrofit.module.reponse.ListModel;
import com.hehandong.androidreader.Retrofit.net.WanAandroidManager;
import com.hehandong.androidreader.adapter.PullToRefreshAdapter;
import com.hehandong.androidreader.bean.DataServer;
import com.hehandong.androidreader.bean.Status;
import com.hehandong.androidreader.event.TabSelectedEvent;
import com.hehandong.androidreader.ui.ZhiHuMainActivity;
import com.hehandong.androidreader.ui.view.CustomLoadMoreView;
import com.hehandong.androidreader.utils.ToastUtil;
import com.hehandong.retrofithelper.utils.RxUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

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
public class HomeFragment extends SupportFragment implements SwipeRefreshLayout.OnRefreshListener {

    private boolean mAtTop = true;

    private int mScrollTotal;

    private static final int PAGE_SIZE = 6;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PullToRefreshAdapter mAdapter;

    private int mNextRequestPage = 1;


    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pull, container, false);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        initAdapter();
        addHeadView();
        initRefreshLayout();
        mSwipeRefreshLayout.setRefreshing(true);
        refresh();

    }


    private void addHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.head_view, (ViewGroup) mRecyclerView.getParent(), false);
        headView.findViewById(R.id.iv).setVisibility(View.GONE);
        ((TextView) headView.findViewById(R.id.tv)).setText("change load view");
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.setNewData(null);
                mAdapter.setLoadMoreView(new CustomLoadMoreView());
                mRecyclerView.setAdapter(mAdapter);
                ToastUtil.showToastLong("change complete");
                mSwipeRefreshLayout.setRefreshing(true);
                refresh();
            }
        });
        mAdapter.addHeaderView(headView);
    }

    private void initAdapter() {
        mAdapter = new PullToRefreshAdapter();
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        mAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                ToastUtil.showToastLong(Integer.toString(position));
            }
        });
    }

    private void initRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    @Override
    public void onRefresh() {
//        mSwipeRefreshLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mSwipeRefreshLayout.setRefreshing(false);
//            }
//        }, 2000);
    }

    private void scrollToTop() {
        mRecyclerView.smoothScrollToPosition(0);
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
            mSwipeRefreshLayout.setRefreshing(true);
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


    private void refresh() {
        mNextRequestPage = 1;
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        new Request(mNextRequestPage, new RequestCallBack() {
            @Override
            public void success(List<Status> data) {
                setData(true, data);
                mAdapter.setEnableLoadMore(true);
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void fail(Exception e) {
                ToastUtil.showToastLong("ERROR");
                mAdapter.setEnableLoadMore(true);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }).start();

        // Init Datas
        WanAandroidManager.getAPI()
                .getProjectList(0)
                .compose(RxUtil.<WanBaseModel<ListModel>>rxSchedulerHelper2(this))
                .subscribe(new CustomObserver<WanBaseModel<ListModel>>() {
                    @Override
                    public void onSuccess(WanBaseModel<ListModel> model) {

                    }
                });
    }


    private void loadMore() {
        new Request(mNextRequestPage, new RequestCallBack() {
            @Override
            public void success(List<Status> data) {
                /**
                 * fix https://github.com/CymChad/BaseRecyclerViewAdapterHelper/issues/2400
                 */
                boolean isRefresh = mNextRequestPage == 1;
                setData(isRefresh, data);
            }

            @Override
            public void fail(Exception e) {
                mAdapter.loadMoreFail();
                ToastUtil.showToastLong("ERROR");
            }
        }).start();
    }


    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
            ToastUtil.showToastLong("no more data");
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    interface RequestCallBack {
        void success(List<Status> data);

        void fail(Exception e);
    }

    static class Request extends Thread {
        private static final int PAGE_SIZE = 6;
        private int mPage;
        private RequestCallBack mCallBack;
        private Handler mHandler;

        private static boolean mFirstPageNoMore;
        private static boolean mFirstError = true;

        public Request(int page, RequestCallBack callBack) {
            mPage = page;
            mCallBack = callBack;
            mHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }

            if (mPage == 2 && mFirstError) {
                mFirstError = false;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallBack.fail(new RuntimeException("fail"));
                    }
                });
            } else {
                int size = PAGE_SIZE;
                if (mPage == 1) {
                    if (mFirstPageNoMore) {
                        size = 1;
                    }
                    mFirstPageNoMore = !mFirstPageNoMore;
                    if (!mFirstError) {
                        mFirstError = true;
                    }
                } else if (mPage == 4) {
                    size = 1;
                }

                final int dataSize = size;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallBack.success(DataServer.getSampleData(dataSize));
                    }
                });
            }
        }
    }

}
