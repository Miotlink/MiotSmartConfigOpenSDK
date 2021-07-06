package com.mlink.android.iot.uitls;

public enum  IotError {
    ERROR_0(40000,"配网成功"),
    ERROR_1(40001,"初始化成功"),
    ERROR_2(40002,"开始配网"),
    ERROR_3(40003,"配网超时，请检查路由器账户、密码是否错误或者确保设备是否复位"),
    ERROR_4(40004,"配网异常"),
    ;

    private int errorCode;
    private String errorMessage;
    IotError(int errorCode, String errorMessage) {
        this.errorCode=errorCode;
        this.errorMessage=errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
