package com.mlink.android.iot.network;

/**
 * Created by Administrator on 2019/12/3 0003.
 */

public interface IReceiver {

    /**
     * 监听UDP广播 获取的数据
     * @param localPort 监听本地的端口
     * @param IpAddress 返回IP
     * @param port 监听的端口
     * @param bs 数据
     * @param len 数据长度
     */
    public void onReceive(int localPort, String IpAddress, int port, byte[] bs, int len);
}
