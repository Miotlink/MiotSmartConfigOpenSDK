package com.mlink.android.iot.dao;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.mlink.android.iot.bean.IDevice;
import com.mlink.android.iot.config.SmartConfigAndSmartConfigMulticase;
import com.mlink.android.iot.listener.ISmartConfigListener;
import com.mlink.android.iot.listener.ISmartConfigOnReceiver;
import com.mlink.android.iot.manager.SocketManager;
import com.mlink.android.iot.service.ISmart;
import com.mlink.android.iot.uitls.IotError;



public class ISmartListener implements ISmart, ISmartConfigOnReceiver {

    private int timeOut=60;

    private Context mContext;

    private SmartConfigAndSmartConfigMulticase smartConfigAndSmartConfigMulticase=null;

    private ISmartConfigListener iSmartListener=null;

    private int errorCode= 0;
    private String errorMessage="";

    @Override
    public void init(Context mContext) throws Exception {
        this.mContext=mContext;
        errorCode=IotError.ERROR_1.getErrorCode();
        errorMessage=IotError.ERROR_1.getErrorMessage();
    }

    @Override
    public void setISmartListener(ISmartConfigListener iSmartListener) {
        this.iSmartListener=iSmartListener;
    }

    @Override
    public void start( String routeName, String password,int timeOut) throws Exception {
        if (timeOut>60){
            this.timeOut=timeOut;
        }
        errorCode=IotError.ERROR_2.getErrorCode();
        errorMessage=IotError.ERROR_2.getErrorMessage();
        SocketManager.getInstance().init(mContext);
        SocketManager.getInstance().start(SocketManager.Search_Device_Port);
        SocketManager.getInstance().setISmartConfigOnReceiver(this);
        smartConfigAndSmartConfigMulticase=SmartConfigAndSmartConfigMulticase.getInstance();
        smartConfigAndSmartConfigMulticase.sendData(routeName,password);
        handler.sendEmptyMessageDelayed(10001, timeOut*1000);
    }

    @Override
    public void onStop() throws Exception {
        handler.removeMessages(10001);
        if (smartConfigAndSmartConfigMulticase!=null){
            smartConfigAndSmartConfigMulticase.isStop();
        }
        SocketManager.getInstance().onDestory();
    }

    @Override
    public void onDestory() throws Exception {
        handler.removeMessages(10001);
        if (smartConfigAndSmartConfigMulticase!=null){
            smartConfigAndSmartConfigMulticase.isStop();
        }
        SocketManager.getInstance().onDestory();
    }

    @Override
    public void onSmartConnected(String ipAddress, int port, String result) throws Exception {
        handler.removeMessages(10001);
        if (iSmartListener==null){
            throw new Exception("iSmartListener is null");
        }
        errorCode=IotError.ERROR_0.getErrorCode();
        errorMessage=IotError.ERROR_0.getErrorMessage();
        IDevice iDevice=new IDevice(ipAddress,port,result);
        iSmartListener.onSmartConfigListener(errorCode,errorMessage,iDevice);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==10001){
                errorCode=IotError.ERROR_3.getErrorCode();
                errorMessage=IotError.ERROR_3.getErrorMessage();
            }
        }
    };
}
