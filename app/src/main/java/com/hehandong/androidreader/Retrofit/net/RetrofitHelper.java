package com.hehandong.androidreader.Retrofit.net;


import com.hehandong.androidreader.Retrofit.api.Api;
import com.hehandong.androidreader.Retrofit.api.WanAndroidAPI;
import com.hehandong.androidreader.Retrofit.common.APIConstants;
import com.hehandong.androidreader.Retrofit.common.WanAndrodCons;
import com.hehandong.androidreader.Retrofit.costomCore.CustomApi;

public class RetrofitHelper {
    private static Api sAPI;
    private static IdeaApiService mIdeaApiService;
    private static WanAndroidAPI sWanAndroidAPI;

    public static Api getCustomApiService() {
        if (sAPI == null){
            sAPI = CustomApi.getApiService(Api.class, APIConstants.HOST);
        }
        return sAPI;
    }

    public static WanAndroidAPI getWanAndroidAPI() {
        if (sWanAndroidAPI == null){
            sWanAndroidAPI = CustomApi.getApiService(WanAndroidAPI.class, WanAndrodCons.BASE_URL);
        }
        return sWanAndroidAPI;
    }

//    public static IdeaApiService getApiService() {
//        if (mIdeaApiService == null){
//            mIdeaApiService = IdeaApi.getApiService(IdeaApiService.class, Constants.API_SERVER_URL);
//        }
//        return mIdeaApiService;
//    }
}
