package com.hehandong.androidreader.Retrofit.api;

import com.hehandong.androidreader.Retrofit.module.WanBaseModel;
import com.hehandong.androidreader.Retrofit.module.WxArticleListModel;
import com.hehandong.androidreader.Retrofit.module.WxMenuListModel;
import com.hehandong.androidreader.Retrofit.module.reponse.HomeListModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

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
    Observable<WxMenuListModel> getWxMenuList();

    //https://wanandroid.com/wxarticle/list/408/2/json
    @GET("wxarticle/list/{chapterId}/{pageIndex}/json")
    Observable<WanBaseModel<WxArticleListModel>> getWxArticleList(
            @Path("chapterId") int chapterId,
            @Path("pageIndex") int pageIndex
    );

    //首页文章列表
    //https://www.wanandroid.com/article/list/0/json
    @GET("article/list/{pageIndex}/json")
    Observable<WanBaseModel<HomeListModel>> getHomeList(
            @Path("pageIndex") int pageIndex
    );
}
