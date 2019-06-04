package com.hehandong.androidreader.Retrofit.api;

import com.hehandong.androidreader.Retrofit.module.WxArticleListModel;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @Author dong
 * @Date 2019-06-04 15:40
 * @Description TODO
 * GitHub：https://github.com/hehandong
 * Email：hehandong@qq.com
 * @Version 1.0
 */
public interface WanAndroidAPI {
    @GET("wxarticle/chapters/json")
    Observable<WxArticleListModel> getWxArticleList();
}
