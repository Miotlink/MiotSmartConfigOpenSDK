package com.mlink.android.iot.listener;

import com.mlink.android.iot.bean.IDevice;

public interface ISmartConfigListener {

    public void onSmartConfigListener(int errorCode, String errorMessage, IDevice iDevice)throws Exception;
}
