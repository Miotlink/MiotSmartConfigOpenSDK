package com.mlink.android.iot.listener;

public interface ISmartConfigOnReceiver {
    /**
     * 配置返回SmartConnected 包
     * @param ipAddress 获取IP 地址
     * @param port 端口
     * @param result 数据包
     */
    public void onSmartConnected(String ipAddress, int port, String result) throws Exception;
}
