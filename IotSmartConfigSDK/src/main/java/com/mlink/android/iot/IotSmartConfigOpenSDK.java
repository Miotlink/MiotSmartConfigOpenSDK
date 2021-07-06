package com.mlink.android.iot;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.mlink.android.iot.dao.ISmartListener;
import com.mlink.android.iot.listener.ISmartConfigListener;
import com.mlink.android.iot.service.ISmart;
import com.mlink.android.iot.uitls.LogLinkUtils;

/**
 * 用于妙联WIFI 配网SDK
 */
public class IotSmartConfigOpenSDK {

    private static volatile IotSmartConfigOpenSDK instance=null;

    public static synchronized IotSmartConfigOpenSDK getInstance() {
        if (instance==null){
            synchronized (IotSmartConfigOpenSDK.class){
                if (instance==null){
                    instance=new IotSmartConfigOpenSDK();
                }
            }
        }
        return instance;
    }

    private Context mContext;

    private ISmart iSmart=null;

    public static void setDebug(boolean isDeubg){
        LogLinkUtils.isDebug=isDeubg;
    }

    /**
     * 初始化参数
     * @param mContext
     */
    public void init(String appKey,Context mContext){
        this.mContext=mContext;
    }
    private ISmartConfigListener iSmartConfigListener=null;
    public void setISmartConfigListener(ISmartConfigListener iSmartConfigListener){
        this.iSmartConfigListener=iSmartConfigListener;
        if (iSmart!=null){
            iSmart.setISmartListener(this.iSmartConfigListener);
        }
    }

    /**
     * 获取路由器名称
     * @return
     * @throws Exception
     */
    public String getSSID()throws Exception{
        if (mContext==null){
           throw new Exception("未初始化");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (PackageManager.PERMISSION_GRANTED !=
                    ActivityCompat.checkSelfPermission(mContext,
                            Manifest.permission.ACCESS_COARSE_LOCATION)) {
               throw new Exception("获取路由器热点需要开启定位权限");
            }
        }
        WifiManager wifiManager= (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager!=null){
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (TextUtils.isEmpty(connectionInfo.getSSID())){
                return "";
            }
            return connectionInfo.getSSID().replaceAll("\"", "");
        }
        return "";
    }


    /**
     *
     * @param mType 配置类型
     * @param routeName 路由器WIFI名称
     * @param password 路由器密码名称
     * @param timeOut 配置超时时间
     * @throws Exception
     */
    public void start(int mType,String routeName,String password,int timeOut)throws Exception{
        switch (mType){
            case 4:
                if (iSmart==null){
                    iSmart= new ISmartListener();
                }
                break;
        }
        iSmart.init(mContext);
        iSmart.start(routeName,password,timeOut);
        if (iSmartConfigListener!=null){
            iSmart.setISmartListener(iSmartConfigListener);
        }
    }

    public void onStop()throws Exception{

        if (iSmart!=null){
            iSmart.onStop();
        }
    }
    public void onDestory()throws Exception{
        if (iSmart!=null){
            iSmart.onDestory();
        }
    };
}
