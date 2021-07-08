package com.mlink.android.iot.manager;

import android.content.Context;
import android.text.TextUtils;


import com.mlink.android.iot.dao.ISmartListener;
import com.mlink.android.iot.listener.ISmartConfigOnReceiver;
import com.mlink.android.iot.network.IReceiver;
import com.mlink.android.iot.network.UdpSocket;
import com.mlink.android.iot.uitls.LogLinkUtils;
import com.mlink.android.iot.uitls.SmartConsts;
import com.mlink.android.iot.uitls.VspBuildAndParseUtils;

import java.util.Random;

/**
 * Created by Administrator on 2019/12/3 0003.
 */

public class SocketManager implements IReceiver {
    private static volatile SocketManager instance=null;
    public static int Search_Cu_Port = new Random().nextInt(3000) + 30000;
    public static int Search_Device_Port = 64535;
    public static final String DEFAULT_CHARSET = "ISO-8859-1";
    public static synchronized SocketManager getInstance() {
        if (instance==null){
            synchronized (SocketManager.class){
                if (instance==null){
                    instance=new SocketManager();
                }
            }
        }
        return instance;
    }

    private Context mContext;

    private static UdpSocket udpSocket = new UdpSocket();

    public void init(Context mContext)throws Exception{
        udpSocket.init(mContext);
    }


    private ISmartConfigOnReceiver iSmartConfigOnReceiver=null;

    public void setISmartConfigOnReceiver(ISmartConfigOnReceiver iSmartConfigOnReceiver) {
        this.iSmartConfigOnReceiver = iSmartConfigOnReceiver;
    }



    public void start(final int port){
        new Thread(new Runnable() {
            @Override
            public void run() {
                udpSocket.startRecv(port,SocketManager.this);
            }
        }).start();
    }

    public void send(String address,String message){
        try {
            if (TextUtils.isEmpty(address)){
                address= VspBuildAndParseUtils.LOCALHOST_DEVICE;
            }
            byte[] bytes  = message.getBytes(DEFAULT_CHARSET);
            VspBuildAndParseUtils.send(udpSocket,address,VspBuildAndParseUtils.Search_Pu_Port,bytes);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public void onReceive(int localPort, String ipAddress, int port, byte[] bs, int len) {
        try {
            byte[] bytes = VspBuildAndParseUtils.encrypt(bs);
            String msg = VspBuildAndParseUtils.getMlccContent(bytes, len);
            LogLinkUtils.i("{address:"+ipAddress+",port:"+port+"}"+msg);
            if (iSmartConfigOnReceiver == null) {
                throw new Exception("not init interface ISmartListener");
            }
            if (localPort == Search_Device_Port) {
               iSmartConfigOnReceiver.onSmartConnected(ipAddress,localPort,msg);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void onDestory(){
        if (udpSocket!=null){
            udpSocket.onStop();
        }
    }
}
