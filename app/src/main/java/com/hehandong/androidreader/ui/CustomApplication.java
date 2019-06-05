package com.hehandong.androidreader.ui;

import android.support.multidex.MultiDexApplication;

import com.tencent.bugly.crashreport.CrashReport;


/**
 * Created by Stay on 2/2/16.
 * Powered by www.stay4it.com
 */
public class CustomApplication extends MultiDexApplication {

    private static CustomApplication sCustomApplication;

    public static CustomApplication getInstance() {
        return sCustomApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sCustomApplication = this;

        AppStatusTracker.init(this);

        CrashReport.initCrashReport(getApplicationContext(), "e12152e218", false);
//        Utils.init(this);
    }
}
