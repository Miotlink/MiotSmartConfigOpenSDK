package com.mlink.android.iot.network;

/**
 * Created by Administrator on 2017/9/25 0025.
 */
public interface TcpIReciver {

	public void onReceive(byte[] bytes, int len)throws Exception;
}
