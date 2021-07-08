package com.mlink.android.iot.uitls;



import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceStore {

    private final Map<String, Map<String,Object>> mDeviceMap;

    public DeviceStore() {
        mDeviceMap = new HashMap<>();
    }

    public void addDevice(String macCode,Map<String,Object> device) {
        if (device == null) {
            return;
        }
        if (!TextUtils.isEmpty(macCode)){
            if (!mDeviceMap.containsKey(macCode)) {
                mDeviceMap.put(macCode, device);
            }
        }
    }



    public Map<String,Object> getDevice(String macCode){
        if (TextUtils.isEmpty(macCode)){
            return null;
        }
        if (mDeviceMap!=null&&mDeviceMap.containsKey(macCode)){
            return mDeviceMap.get(macCode);
        }
        return null;
    }

    public void removeDevice(String deviceMac) {
        if (mDeviceMap.containsKey(deviceMac)) {
            mDeviceMap.remove(deviceMac);
        }
    }

    public void clear() {
        mDeviceMap.clear();
    }

    public Map<String, Map<String,Object>> getDeviceMap() {
        return mDeviceMap;
    }

    public List<Map<String,Object>> getDeviceList() {
        final List<Map<String,Object>> methodResult = new ArrayList<>(mDeviceMap.values());
        return methodResult;
    }


}
