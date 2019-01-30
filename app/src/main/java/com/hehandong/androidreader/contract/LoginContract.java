package com.hehandong.androidreader.contract;


import com.hehandong.androidreader.base.BaseView;
import com.hehandong.androidreader.bean.BaseObjectBean;
import com.hehandong.androidreader.bean.LoginBean;

import io.reactivex.Flowable;

public interface LoginContract {
    interface Model {
        Flowable<BaseObjectBean<LoginBean>> login(String username, String password);
    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        void onSuccess(BaseObjectBean<LoginBean> bean);
    }

    interface Presenter {
        /**
         * 登陆
         *
         * @param username
         * @param password
         */
        void login(String username, String password);
    }
}
