package com.mlink.android.iot.uitls;

import android.util.Log;

/**
 * Created by Administrator on 2019/12/3 0003.
 */

public class LogLinkUtils {

    public static boolean isDebug=false;

    public static void i(String e){
        if (isDebug){
            Log.i("MLink",e);
        }
    }

    public static void e(String e){
        if (isDebug){
            Log.e("MLink",e);
        }
    }
    public static void w(String e){
        if (isDebug){
            Log.w("MLink",e);
        }
    }
    public static void d(String e){
        if (isDebug){
            Log.d("MLink",e);
        }
    }
}
