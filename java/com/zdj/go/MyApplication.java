package com.zdj.go;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 */

public class MyApplication extends Application {
    private final String APPID = "5988041f";
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(MyApplication.this);
        SDKInitializer.setCoordType(CoordType.BD09LL);
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=" + APPID);
    }
}
