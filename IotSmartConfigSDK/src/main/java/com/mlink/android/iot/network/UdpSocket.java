package com.mlink.android.iot.network;

import android.content.Context;
import android.net.wifi.WifiManager;


import com.mlink.android.iot.uitls.LogLinkUtils;
import com.mlink.android.iot.uitls.SmartConsts;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class UdpSocket implements Runnable {

    public static String TAG = UdpSocket.class.getName();

    private IReceiver receiver = null;

    private DatagramSocket socket = null;

    private Thread thread = null;

    private int localPort = 0;



    private WifiManager.MulticastLock lock = null;

    public void init(Context context){
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        lock = manager.createMulticastLock(SmartConsts.WIFI_LOCK);
        acquire();
    }
    public boolean startRecv(int port, IReceiver lrs) {
        try {
            LogLinkUtils.e("receiver port"+port);
            this.receiver = lrs;
            needStop = false;
            localPort = port;
            socket = new DatagramSocket(null);
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress(localPort));
            thread = new Thread(this);
            thread.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void onStop() {
        if (!needStop) {
            needStop = true;
        }
        release();
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                socket.disconnect();
                socket = null;
            }
        }
        release();
    }

    public boolean needStop = false;

    private InetAddress address = null;

    private DatagramPacket dPacket = null;

    public boolean send(String ip, int port, byte[] bs, int len) {
        try {
            if(socket==null){
                return false;
            }
            address = InetAddress.getByName(ip);
            dPacket = new DatagramPacket(bs, len, address, port);
            acquire();
            socket.send(dPacket);
        } catch (UnknownHostException e) {
            e.printStackTrace();
             release();
            return false;
        }catch (IOException e) {
            e.printStackTrace();
            release();
            return false;
        }
        return true;

    }

    private  void acquire(){
        if(lock!=null){
            lock.acquire();
        }
    }

    private  void release(){
        try {
            if(lock!=null&&lock.isHeld()){
                lock.release();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (lock!=null&&lock.isHeld()){
                    lock.release();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void run() {
        if (socket == null || receiver == null) {
            return;
        }
        while (!needStop) {
            try {
                byte data[] = new byte[1024];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                acquire();
                socket.receive(packet);
                release();
                byte[] bs = new byte[packet.getLength()];
                System.arraycopy(packet.getData(), packet.getOffset(), bs, 0, packet.getLength());
                String host = packet.getAddress().getHostAddress();
                int port = packet.getPort();
                if (receiver != null) {
                    receiver.onReceive(localPort, host, port, bs, bs.length);
                }
            } catch (IOException e) {
                e.printStackTrace();
              release();
            }

        }
    }

}
