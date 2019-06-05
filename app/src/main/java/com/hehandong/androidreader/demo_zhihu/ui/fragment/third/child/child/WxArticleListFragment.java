package com.hehandong.androidreader.demo_zhihu.ui.fragment.third.child.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hehandong.androidreader.R;
import com.hehandong.androidreader.Retrofit.module.WanBaseModel;
import com.hehandong.androidreader.Retrofit.module.WxArticleListModel;
import com.hehandong.androidreader.demo_zhihu.ZhiHuMainActivity;
import com.hehandong.androidreader.demo_zhihu.adapter.WxArticleListAdapter;
import com.hehandong.androidreader.demo_zhihu.event.TabSelectedEvent;
import com.hehandong.androidreader.demo_zhihu.listener.OnItemClickListener;
import com.hehandong.androidreader.ui.webview.WebViewActivity;

import org.greenrobot.eventbus.Subscribe;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;


public class WxArticleListFragment extends SupportFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String WX_ARTICLE_LIST = "wx_article_list";
    private RecyclerView mRecy;
    private SwipeRefreshLayout mRefreshLayout;
    private WxArticleListAdapter mAdapter;
    private boolean mAtTop = true;
    private int mScrollTotal;
    private WanBaseModel<WxArticleListModel> mWanBaseModel;

    public static WxArticleListFragment newInstance(WanBaseModel<WxArticleListModel> data) {

        Bundle args = new Bundle();
        args.putSerializable(WX_ARTICLE_LIST, data);
        WxArticleListFragment fragment = new WxArticleListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mWanBaseModel = (WanBaseModel<WxArticleListModel>) args.getSerializable(WX_ARTICLE_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhihu_fragment_second_pager_first, container, false);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(this);

        mAdapter = new WxArticleListAdapter(_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);

        mRecy.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                // 这里的DetailFragment在flow包里
                // 这里是父Fragment启动,要注意 栈层级
//                ((SupportFragment) getParentFragment()).start(WxArticleFragment.newInstance(mAdapter.getItem(position).getTitle()));

//                openDetail(mAdapter.getItem(position));

                WxArticleListModel.DatasBean item = mAdapter.getItem(position);
                String title = item.getTitle();
                String link = item.getLink();

                ((SupportFragment) getParentFragment()).start(WxArticleFragment.newInstance(title,link));

            }
        });

        mAdapter.setDatas(mWanBaseModel);

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
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 2000);
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


    public void openDetail(WxArticleListModel.DatasBean bean) {
        WebViewActivity.loadUrl(_mActivity, bean.getLink(), bean.getTitle());
    }

}
