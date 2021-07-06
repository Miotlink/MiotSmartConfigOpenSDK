package com.mlink.android.iot.service;

import android.content.Context;

import com.mlink.android.iot.listener.ISmartConfigListener;

public interface ISmart {

    public void init(Context mContext)throws Exception;

    public void setISmartListener(ISmartConfigListener iSmartListener);

    public void start(String routeName,String password,int timeOut)throws Exception;

    public void onStop()throws Exception;

    public void onDestory()throws Exception;


}
