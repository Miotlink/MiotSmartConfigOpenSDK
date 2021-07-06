package com.mlink.android.iot.bean;

import java.io.Serializable;

public class IDevice implements Serializable {

    private String ipAddress="";
    private int port=0;
    private String data="";

    public IDevice(String ipAddress, int port, String data) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.data = data;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "IDevice{" +
                "ipAddress='" + ipAddress + '\'' +
                ", port=" + port +
                ", data='" + data + '\'' +
                '}';
    }
}
