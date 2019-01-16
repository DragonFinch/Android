package com.iyoyogo.android.utils;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.iyoyogo.android.app.App;

/**
 * 高德定位工具类
 * Created by zhuhui on 2018/01/26.
 */

public class AMapLocationUtils implements AMapLocationListener {

    private LocationListener mLocationListener;

    public static AMapLocationUtils getInstance() {
        return new AMapLocationUtils();
    }

    private AMapLocationUtils() {
        init();
    }

    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    /**
     * 初始化定位
     */
    private void init() {
        //初始化定位
        mLocationClient = new AMapLocationClient(App.getInstance().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(7200000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(false);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(false);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        mLocationOption.setOnceLocation(true);

    }

    /**
     * 开始定位
     */
    public void startLocation() {
        if (mLocationClient != null) {
            mLocationClient.startLocation();
        }
    }

    /**
     * 结束定位
     */
    public void stopLocation() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
        }
    }


    /**
     * 定位成功回调
     *
     * @param aMapLocation 定位信息
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation.getErrorCode()==0) {
            Log.d("AMapLocationUtils", aMapLocation.getProvince());
            Log.d("AMapLocationUtils", aMapLocation.getCity());
            Log.d("AMapLocationUtils", aMapLocation.getDistrict());
            Log.d("AMapLocationUtils", "---" + aMapLocation.getAddress());
            Log.d("AMapLocationUtils", "aMapLocation.getLatitude():" + aMapLocation.getLatitude());
            Log.d("AMapLocationUtils", "aMapLocation.getLongitude():" + aMapLocation.getLongitude());
//        String location = aMapLocation.getCountry() + aMapLocation.getProvince() + aMapLocation.getCity();

//            SPUtils.getInstance(Constant.LOCATION).put(Constant.LATITUDE, aMapLocation.getLatitude() + "");
//            SPUtils.getInstance(Constant.LOCATION).put(Constant.LONGITUDE, aMapLocation.getLongitude() + "");
//            SPUtils.getInstance(Constant.LOCATION).put(Constant.POINAME, aMapLocation.getPoiName() + "");
            if (mLocationListener != null) {
                mLocationListener.onLocation(aMapLocation);
            }
            stopLocation();
        }
    }

    /**
     * 设置定位监听
     *
     * @param mLocationListener 监听对象
     * @return 当前实例
     */
    public AMapLocationUtils setOnLocationListener(LocationListener mLocationListener) {
        this.mLocationListener = mLocationListener;
        return this;
    }

    public interface LocationListener {
        void onLocation(AMapLocation aMapLocation);
    }
}