package com.iyoyogo.android.model.en;


import android.util.Log;


/**
 * Created by wgheng on 2018/7/19.
 * Description :
 */
public class BaseRequest {

    public PlatformInfo platformInfo;

    public BaseRequest() {
        this.platformInfo = PlatformInfo.getInstance().init();
        Log.i("BaseRequest", platformInfo.toString());
    }
}
