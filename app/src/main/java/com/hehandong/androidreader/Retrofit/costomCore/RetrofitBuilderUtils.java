package com.hehandong.androidreader.Retrofit.costomCore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hehandong.retrofithelper.net.common.Constants;
import com.hehandong.retrofithelper.net.interceptor.HttpHeaderInterceptor;
import com.hehandong.retrofithelper.net.interceptor.LoggingInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhpan on 2018/3/21.
 */

public class RetrofitBuilderUtils {

    public static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(new HttpHeaderInterceptor()).build();
    }

    public static Retrofit getRetrofit(String baseUrl) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        OkHttpClient okHttpClient = getOkHttpClient();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl).build();
    }

}
