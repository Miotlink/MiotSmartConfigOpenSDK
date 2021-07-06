package com.mlink.android.iot.uitls;

import android.util.Base64;

/**
 * Created by Administrator on 2017/9/7 0007.
 */
public class SmartConsts {
	public static final String WIFI_LOCK="mlink-wifi";
	public static final int SMARTCONFIG_CONFIG_UDP_TIME=10;
	public static final int SMARTCONFIG_FIRST_SIGN_LEN=30;
	public static final int SMARCONFIG_FIRST_SIGN=20;
	public static final int SMARCONFIG_THIRD_SIGN=22;
	public static final int SMARCONFIG_TWO_NUMBER_SGIN=3;
	public static final int SMARTCONFIG_TWO_SGIN=21;
	public static final String SMART_LOCAL_HOST="255.255.255.255";
	public static final int SMART_LOCAL_PORT=30000;
	public static final String SMART_FIRST_SGIN=HexString.randomNumStr(SMARCONFIG_FIRST_SIGN);
	public static final String SMART_TWO_SGIN=HexString.randomNumStr(SMARTCONFIG_TWO_SGIN);
	public static final String SMART_THIRD_SGIN=HexString.randomNumStr(SMARCONFIG_THIRD_SIGN);
	public static final String AP = "0";
	public static final String SA_4004_1 = "1";
	public static final String SA_7681 = "2";
	public static final String SA_HANFENG = "3";
	public static final String SA_4004_2 = "4";
	public static final String SIM = "5";
	public static final String SA_LWXIN = "6";
	public static final String MIOTLINK_IPC = "A";
	public static final String BLUETOOTH = "BLE";
	public static final String XIAOBAI = "655";      //机器人小白
	public static final String XIAOZHI = "554";      //小智机器人型号分配 kind:0354 model:0554
	public static final String AIR_FRUIT_1 = "676";  //空气果1
	public static final String AIR_FRUIT_1S = "683"; //空气果1S
	public static final String HK_IPC = "317";       //萤石摄像头   king:0316 model:0317
	public static final String GATEWAY = "471";      //智能网关     kind:0307 model:0471
	public static final String LANGFENG_284 = "284"; //朗风-284
	public static final String LANGFENG_285 = "285"; //朗风-285    原本是0模式，实际是1模式
	public static final String BG_MUSIC = "&mac=";   //背景音乐码标识
	/********************************汉枫调用*************************************/
	public static final String PLATFORM_CODENAME="CodeName";
	public static final int HANFENG_SMARCONNECTED=300001;
	public static final int HANFENG_SMARTCONFIG_TIMEOUT=300003;
	public static final int HANFENG_SMARTCONFIG_FINSH=300002;
	public static final int HANFENG_SMARTCONFIG_FAIL=300004;
//////////////////////////////////////////SA 7681//////////////////////////////////////////////////
	public static final String PLATFORM_COMPLETE = "CodeName=fc_complete";

	public static final String PLATFORM_PLATFORM_CONPLETE_ACK = "fc_complete_ack";

	public static final String PLATFORM_FC_COMPLETE_FIN="CodeName=fc_complete_fin&mac=";
	public static final String PLATFORM_FC_PLATFORM_ACK="fc_ml_platform_ack";
	public static final String PLATFORM_FC_PLATFORM="CodeName=fc_ml_platform&pf_url=www.51miaomiao.com&pf_port=28001&pf_ip1=118.190.67.214&pf_ip2=122.225.196.132&";

	public static final int DEVICE_LOCALHOST_PORT = 64535;
	public static final int DEVICE_VOIDE_LOCALHOST_PORT = 63541;


	public static final int SMARTCONFIH_AP_SUCCESS=5050;

	public static final int SMARTCONFIG_AP_FAIL=5000;

	public static final int SMARTCONFIG_AP_CONNECTED_WIFI=5011;


	public static String getBaseMessage(String msg){
		try {
			return new String(Base64.encode(msg.getBytes("UTF-8"),Base64.DEFAULT),"UTF-8");
		}catch (Exception e){

		}
		return ""
;	}



}
