package com.hehandong.androidreader.Retrofit.net;


import com.hehandong.androidreader.Retrofit.api.WanAndroidAPI;
import com.hehandong.androidreader.Retrofit.common.WanAndrodCons;
import com.hehandong.androidreader.Retrofit.costomCore.CustomApi;

public class WanAandroidManager {

    private static WanAndroidAPI sWanAndroidAPI;
    public static WanAndroidAPI getAPI() {
        if (sWanAndroidAPI == null){
            sWanAndroidAPI = CustomApi.getApiService(WanAndroidAPI.class, WanAndrodCons.BASE_URL);
        }
        return sWanAndroidAPI;
    }

}
