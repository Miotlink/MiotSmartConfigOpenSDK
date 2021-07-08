package com.mlink.android.iot.uitls;



import java.util.HashMap;
import java.util.Map;

public class IR extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public IR() {
        put("code", 40000);
        put("msg", "success");
    }

    public static IR error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static IR error(String msg) {
        return error(501, msg);
    }

    public static IR error(int code, String msg) {
        IR r = new IR();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static IR ok(String msg) {
        IR r = new IR();
        r.put("msg", msg);
        return r;
    }

    public static IR ok(Map<String, Object> map) {
        IR r = new IR();
        r.putAll(map);
        return r;
    }

    public static IR ok() {
        return new IR();
    }

    @Override
    public IR put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}

