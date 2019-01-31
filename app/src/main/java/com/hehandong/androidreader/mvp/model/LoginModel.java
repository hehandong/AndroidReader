package com.hehandong.androidreader.mvp.model;


import com.hehandong.androidreader.mvp.bean.BaseObjectBean;
import com.hehandong.androidreader.mvp.bean.LoginBean;
import com.hehandong.androidreader.mvp.contract.LoginContract;
import com.hehandong.androidreader.mvp.net.RetrofitClient;

import io.reactivex.Flowable;

public class LoginModel implements LoginContract.Model {
    @Override
    public Flowable<BaseObjectBean<LoginBean>> login(String username, String password) {
        return RetrofitClient.getInstance().getApi().login(username,password);
    }
}
