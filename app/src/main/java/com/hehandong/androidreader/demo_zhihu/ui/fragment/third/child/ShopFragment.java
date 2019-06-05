package com.hehandong.androidreader.demo_zhihu.ui.fragment.third.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hehandong.androidreader.R;
import com.hehandong.androidreader.Retrofit.costomCore.CustomObserver;
import com.hehandong.androidreader.Retrofit.module.WxMenuListModel;
import com.hehandong.androidreader.Retrofit.net.WanAandroidManager;
import com.hehandong.androidreader.demo_zhihu.ui.fragment.third.child.child.ContentFragment;
import com.hehandong.androidreader.demo_zhihu.ui.fragment.third.child.child.MenuListFragment;
import com.hehandong.androidreader.demo_zhihu.ui.fragment.third.child.child.WxArticleListFragment;
import com.hehandong.androidreader.demo_zhihu.ui.fragment.third.child.child.WxMenuFragment;
import com.hehandong.retrofithelper.utils.LogUtils;
import com.hehandong.retrofithelper.utils.RxUtil;

import java.util.ArrayList;
import java.util.Arrays;

import me.yokeyword.fragmentation.SupportFragment;


/**
 * Created by YoKeyword on 16/2/4.
 */
public class ShopFragment extends SupportFragment {
    public static final String TAG = ShopFragment.class.getSimpleName();

    private Toolbar mToolbar;

    public static ShopFragment newInstance() {
        Bundle args = new Bundle();

        ShopFragment fragment = new ShopFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        initView(view, savedInstanceState);
        return view;
    }

    private void initView(View view, Bundle savedInstanceState) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);

        mToolbar.setTitle(R.string.public_mark);

        if (findChildFragment(MenuListFragment.class) == null) {
            ArrayList<String> listMenus = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.array_menu)));

//            MenuListFragment menuListFragment = MenuListFragment.newInstance(listMenus);
//            loadRootFragment(R.id.fl_list_container, menuListFragment);

            WanAandroidManager.getAPI()
                    .getWxMenuList()
                    .compose(RxUtil.<WxMenuListModel>rxSchedulerHelper2(this))
                    .subscribe(new CustomObserver<WxMenuListModel>() {
                        @Override
                        public void onSuccess(WxMenuListModel model) {
                            LogUtils.i("WanAandroidManager", "results.size：" + model.toString());

                            WxMenuFragment wxMenuFragment = WxMenuFragment.newInstance(model);
                            loadRootFragment(R.id.fl_list_container, wxMenuFragment);

                        }
                    });


            // false:  不加入回退栈;  false: 不显示动画
//            loadRootFragment(R.id.fl_content_container, ContentFragment.newInstance(listMenus.get(0)), false, false);
            loadRootFragment(R.id.fl_content_container, WxArticleListFragment.newInstance(null), false, false);
        }


    }

    @Override
    public boolean onBackPressedSupport() {
        // ContentFragment是ShopFragment的栈顶子Fragment,会先调用ContentFragment的onBackPressedSupport方法
        Toast.makeText(_mActivity, "onBackPressedSupport-->return false, " + getString(R.string.upper_process), Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 替换加载 内容Fragment
     *
     * @param fragment
     */
    public void switchContentFragment(ContentFragment fragment) {
        SupportFragment contentFragment = findChildFragment(ContentFragment.class);
        if (contentFragment != null) {
            contentFragment.replaceFragment(fragment, false);
        }
    }

    /**
     * 替换加载 内容Fragment
     *
     * @param fragment
     */
    public void switchWxArticleListFragment(WxArticleListFragment fragment) {
        SupportFragment contentFragment = findChildFragment(WxArticleListFragment.class);
        if (contentFragment != null) {
            contentFragment.replaceFragment(fragment, false);
        }
    }
}
