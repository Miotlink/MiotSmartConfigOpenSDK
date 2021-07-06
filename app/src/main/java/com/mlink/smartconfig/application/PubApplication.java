package com.mlink.smartconfig.application;

import android.app.Application;

import com.mlink.android.iot.IotSmartConfigOpenSDK;

public class PubApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        IotSmartConfigOpenSDK.setDebug(true);
        IotSmartConfigOpenSDK.getInstance().init("", this);
    }
}
