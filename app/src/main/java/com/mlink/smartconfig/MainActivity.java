package com.mlink.smartconfig;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mlink.android.iot.IotSmartConfigOpenSDK;

public class MainActivity extends AppCompatActivity {

    private TextView userEt;
    private EditText passEt;
    private Button startButton;
    private boolean isStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_wifi);
        userEt = findViewById(R.id.name_tv);
        passEt = findViewById(R.id.password_et);
        if (Build.VERSION.SDK_INT >= 29) {
            if (PackageManager.PERMISSION_GRANTED !=
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)) {
                int hasWriteStoragePermission = ContextCompat.checkSelfPermission(getApplication(),
                        Manifest.permission.ACCESS_COARSE_LOCATION);


                if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
                    //拥有权限，执行操作
                    try {
                        userEt.setText(IotSmartConfigOpenSDK.getInstance().getSSID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    //没有权限，向用户请求权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 10001);
                }
            }
        }else {
            try {
                userEt.setText(IotSmartConfigOpenSDK.getInstance().getSSID());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        startButton = findViewById(R.id.start_btn);
        startButton.setOnClickListener((view)->{
            String user=userEt.getText().toString();
            String pass=passEt.getText().toString();
            if (isStart){
                Toast.makeText(this,"配置中......", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(user)){
                Toast.makeText(this,"WIFI 名称不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                isStart=true;
                IotSmartConfigOpenSDK.getInstance().setISmartConfigListener(((errorCode, errorMessage, iDevice) -> {

                }));
                IotSmartConfigOpenSDK.getInstance().start(4,user,pass,60);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        findViewById(R.id.stop_btn).setOnClickListener((view)->{
            try {
                isStart=false;
                IotSmartConfigOpenSDK.getInstance().onStop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //通过requestCode来识别是否同一个请求
        if (requestCode == 10001){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                try {
                    userEt.setText(IotSmartConfigOpenSDK.getInstance().getSSID());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else{
                //用户不同意，向用户展示该权限作用
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    new AlertDialog.Builder(this)
                            .setMessage("前往设置权限")
                            .setPositiveButton("OK", (dialog1, which) ->
                                    ActivityCompat.requestPermissions(this,
                                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                            10001))
                            .setNegativeButton("Cancel", null)
                            .create()
                            .show();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isStart=false;
        try {
            IotSmartConfigOpenSDK.getInstance().onStop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}