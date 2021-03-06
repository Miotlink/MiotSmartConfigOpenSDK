package com.mlink.android.iot.network;



import com.mlink.android.iot.uitls.LogLinkUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * 此方法为UDP 组播功能发包协议
 */
public final class SmartMulticast {
	private static final String TAG = MulticastSocket.class.getName();
	private MulticastSocket multicastSocket;// 组播
	private InetAddress address;
	/**
	 * 向服务端发送广播
	 *
	 * @param port
	 *            服务端端口
	 * @param ip
	 *            服务端ip
	 * @param buffer
	 *            发送的byte信息
	 * @param len
	 *            byte 的 长度
	 */
	public void send(int port, String ip, byte[] buffer, int len) {
		try {
			address = InetAddress.getByName(ip);
			if (multicastSocket==null){
				multicastSocket = new MulticastSocket(port);
			}
			multicastSocket.setTimeToLive(1);
			multicastSocket.joinGroup(address);
			DatagramPacket sendDatagramPacket = new DatagramPacket(buffer, len,
					address, port);
			multicastSocket.send(sendDatagramPacket);
			LogLinkUtils.i("<www.miotlink.com>......... ");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (multicastSocket != null) {
				try {
					multicastSocket.leaveGroup(address);
					multicastSocket.close();
					address = null;
					multicastSocket = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


}
