package com.hehandong.androidreader.model;


import com.hehandong.androidreader.bean.BaseObjectBean;
import com.hehandong.androidreader.bean.LoginBean;
import com.hehandong.androidreader.contract.LoginContract;
import com.hehandong.androidreader.net.RetrofitClient;

import io.reactivex.Flowable;

public class LoginModel implements LoginContract.Model {
    @Override
    public Flowable<BaseObjectBean<LoginBean>> login(String username, String password) {
        return RetrofitClient.getInstance().getApi().login(username,password);
    }
}
