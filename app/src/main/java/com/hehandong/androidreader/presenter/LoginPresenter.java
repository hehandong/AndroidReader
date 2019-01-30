package com.hehandong.androidreader.presenter;



import com.hehandong.androidreader.base.BasePresenter;
import com.hehandong.androidreader.bean.BaseObjectBean;
import com.hehandong.androidreader.bean.LoginBean;
import com.hehandong.androidreader.contract.LoginContract;
import com.hehandong.androidreader.model.LoginModel;
import com.hehandong.androidreader.net.RxScheduler;
import com.hehandong.androidreader.util.LogUtil;

import io.reactivex.functions.Consumer;


public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private LoginContract.Model model;

    public LoginPresenter() {
        model = new LoginModel();
    }

    @Override
    public void login(String username, String password) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        LogUtil.i("login--username:" + username + "--password: "+ password);
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        model.login(username, password)
                .compose(RxScheduler.<BaseObjectBean<LoginBean>>Flo_io_main())
                .as(mView.<BaseObjectBean<LoginBean>>bindAutoDispose())
                .subscribe(new Consumer<BaseObjectBean<LoginBean>>() {
                    @Override
                    public void accept(BaseObjectBean<LoginBean> bean) throws Exception {
                        LogUtil.i("accept:" + bean.toString());
                        mView.onSuccess(bean);
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.i("login--Throwable" );
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });

        LogUtil.i("login2--username:" + username + "--password: "+ password);
    }
}
