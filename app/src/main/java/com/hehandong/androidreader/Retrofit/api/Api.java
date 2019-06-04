package com.hehandong.androidreader.Retrofit.api;


import com.hehandong.androidreader.Retrofit.module.MeiziModel;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Stay on 13/4/16.
 * Powered by www.stay4it.com
 */
public interface Api {
    @GET("api/data/福利/{pageCount}/{pageIndex}")
    Call<MeiziModel> defaultBenefits(
            @Path("pageCount") int pageCount,
            @Path("pageIndex") int pageIndex
    );

    @GET("api/data/福利/{pageCount}/{pageIndex}")
    Observable<MeiziModel> rxBenefits(
            @Path("pageCount") int pageCount,
            @Path("pageIndex") int pageIndex
    );
}
