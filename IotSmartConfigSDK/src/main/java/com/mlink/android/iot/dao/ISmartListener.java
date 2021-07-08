package com.mlink.android.iot.dao;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.mlink.android.iot.bean.IDevice;
import com.mlink.android.iot.config.SmartConfigAndSmartConfigMulticase;
import com.mlink.android.iot.listener.ISmartConfigListener;
import com.mlink.android.iot.listener.ISmartConfigOnReceiver;
import com.mlink.android.iot.manager.SocketManager;
import com.mlink.android.iot.service.ISmart;
import com.mlink.android.iot.uitls.DeviceStore;
import com.mlink.android.iot.uitls.IR;
import com.mlink.android.iot.uitls.IotError;
import com.mlink.android.iot.uitls.Mlcc_ParseUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ISmartListener implements ISmart, ISmartConfigOnReceiver {

    private int timeOut=60;

    private Context mContext;

    private SmartConfigAndSmartConfigMulticase smartConfigAndSmartConfigMulticase=null;

    private ISmartConfigListener iSmartListener=null;

    private int errorCode= 0;
    private String errorMessage="";
    private String lastIpAddress="";
    private Map<String,Object> mapValue=new HashMap<>();
    private String macCode="";
    DeviceStore deviceStore=new DeviceStore();

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
        deviceStore.clear();
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
    }

    @Override
    public void onDestory() throws Exception {

        if (smartConfigAndSmartConfigMulticase!=null){
            smartConfigAndSmartConfigMulticase.isStop();
        }
        SocketManager.getInstance().onDestory();
    }

    @Override
    public void onSmartConnected(String ipAddress, int port, String result) throws Exception {
        if (TextUtils.isEmpty(result)
                ||TextUtils.isEmpty(ipAddress)
                ||TextUtils.equals("0.0.0.0",result)){
            return;
        }
        if(Mlcc_ParseUtils.isSmartConnected(result)){
           mapValue = Mlcc_ParseUtils.getParseObject(result);
            if (mapValue!=null&&mapValue.containsKey("mac")){
                macCode=mapValue.get("mac").toString();
                deviceStore.addDevice(macCode,mapValue);
                SocketManager.getInstance().send(ipAddress, "CodeName=fc_complete&mac="+macCode);
            }
            return;
        }
        if (result.startsWith("CodeName=fc_complete_ack")){
            Map<String, Object> fcComplete = Mlcc_ParseUtils.getParseObject(result);
            String fcmacCode="";
            if(fcComplete!=null&&fcComplete.containsKey("mac")){
                fcmacCode=fcComplete.get("mac").toString();
                SocketManager.getInstance().send(ipAddress, "CodeName=fc_complete_fin&mac="+fcmacCode);
            }
            onStop();
            handler.removeMessages(10001);
            errorCode=IotError.ERROR_0.getErrorCode();
            errorMessage=IotError.ERROR_0.getErrorMessage();
            if (mapValue!=null){
                mapValue.remove("CodeName");
            }
            if (iSmartListener==null){
                throw new Exception("iSmartListener is null");
            }
            Map<String, Object> device = deviceStore.getDevice(fcmacCode);


            if (device!=null){
                if (!deviceStore.getDeviceMap().containsKey(fcmacCode+"fin")){
                    iSmartListener.onSmartConfigListener(errorCode,errorMessage,JSON.toJSONString(IR.ok("success").put("data",device)));
                    deviceStore.addDevice(fcmacCode+"fin",device);
                }
            }
            return;
        }


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
