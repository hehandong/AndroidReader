package com.hehandong.androidreader.ui;

import android.app.Application;

import butterknife.internal.Utils;


/**
 * Created by Stay on 2/2/16.
 * Powered by www.stay4it.com
 */
public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppStatusTracker.init(this);
//        Utils.init(this);
    }
}
