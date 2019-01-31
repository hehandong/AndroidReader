package com.hehandong.androidreader.Retrofit.net;


import com.hehandong.androidreader.Retrofit.api.Api;
import com.hehandong.androidreader.Retrofit.common.APIConstants;
import com.hehandong.androidreader.Retrofit.costomCore.CustomApi;

public class RetrofitHelper {
    private static Api sApi;
    private static IdeaApiService mIdeaApiService;

    public static Api getCustomApiService() {
        if (sApi == null){
            sApi = CustomApi.getApiService(Api.class, APIConstants.HOST);
        }
        return sApi;
    }

//    public static IdeaApiService getApiService() {
//        if (mIdeaApiService == null){
//            mIdeaApiService = IdeaApi.getApiService(IdeaApiService.class, Constants.API_SERVER_URL);
//        }
//        return mIdeaApiService;
//    }
}
