package com.iyoyogo.android.ui.common;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.iyoyogo.android.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DingWei extends AppCompatActivity implements LocationSource, AMapLocationListener {
    private TextView currentText;
    private static final int WRITE_COARSE_LOCATION_REQUEST_CODE = 100;
    private MapView mapView = null;
    //AMap是地图对象
    private AMap aMap;
    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    private AMapLocationClientOption mLocationOption = null;
    //声明mListener对象，定位监听器
    private OnLocationChangedListener mListener = null;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    private double lat;
    private double lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ding_wei);
        //6.0定位权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
        }


        //获取地图引用控件
        mapView = (MapView) findViewById(R.id.map_dingwei);
        currentText = (TextView) findViewById(R.id.current_text);
        //在activity执行onCreate时执行mapView.onCreate(savedInstanceState),实现生命周期管理
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
            //设置显示定位按钮，并且可以点击
            UiSettings uiSettings = aMap.getUiSettings();
            //设置了定位的监听，这里要实现LocationSource接口
            aMap.setLocationSource(this);
            //设置是否显示定位按钮
            uiSettings.setMyLocationButtonEnabled(true);
            //显示定位层并且可以出发定位，默认是false
            aMap.setMyLocationEnabled(true);
        }

        //开始定位
        location();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。

    }

    private void location() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息(默认返回地址信息)
        mLocationOption.setNeedAddress(true);
        //设置是否定位定位一次，默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否允许模拟位置，默认为false,不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔，单位毫秒，默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestory时执行mapView.onDestory(),实现地图生命周期管理
        mapView.onDestroy();
        mLocationClient.stopLocation();//停止定位
        mLocationClient.onDestroy();//销毁定位客户端
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在Activity执行onResume时执行mapView.onResume()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mapView.onPause(),实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        //在activity执行onSaveInstanceState时执行mapView.onSaveInstanceState(outState),实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            //定位成功回调信息，设置相关消息
            aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果
            aMapLocation.getLatitude();//获取纬度
            aMapLocation.getLongitude();//获取经度
            aMapLocation.getAccuracy();//获取精度信息
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(aMapLocation.getTime());
            df.format(date);//定位时间
            aMapLocation.getAddress();//地址
            aMapLocation.getCountry();//国家
            aMapLocation.getProvince();//省信息
            aMapLocation.getCity();//城市信息
            aMapLocation.getDistrict();//城区信息
            aMapLocation.getStreet();//街道信息
            aMapLocation.getStreetNum();//街道门牌号信息
            aMapLocation.getCityCode();//城市编码
            aMapLocation.getAdCode();//地区编码

            StringBuffer stringBuffer1 = new StringBuffer();
            stringBuffer1.append(aMapLocation.getCountry() + ""
                    + aMapLocation.getProvince() + ""
                    + aMapLocation.getCity() + ""
                    + aMapLocation.getDistrict() + ""
                    + aMapLocation.getStreet() + ""
                    + aMapLocation.getStreetNum());
            Log.e("AmapSuccess", stringBuffer1.toString());
            currentText.setText("当前位置:" + stringBuffer1.toString());

            //设置当前地图 显示当前位置
            //经纬度
            lat = aMapLocation.getLatitude();//纬度
            lon = aMapLocation.getLongitude();//经度
            Log.e("amap经纬度", "lat=" + lat + ", lon=" + lon);
            //如果不设置标志位，此时在拖动地图时，它会不断将地图移动到当前的位置
            if (isFirstLoc) {
                //设置缩放级别
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 17));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(lat, lon));
                markerOptions.title("当前位置");
                markerOptions.visible(true);
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_location));
                markerOptions.icon(bitmapDescriptor);
                aMap.addMarker(markerOptions);

                //点击定位按钮 能够将地图的中心移动到定位点
                mListener.onLocationChanged(aMapLocation);

                //获取定位信息
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(aMapLocation.getCountry() + ""
                        + aMapLocation.getProvince() + ""
                        + aMapLocation.getCity() + ""
                        + aMapLocation.getDistrict() + ""
                        + aMapLocation.getStreet() + ""
                        + aMapLocation.getStreetNum());
                Log.e("AmapSuccess", stringBuffer.toString());
                Toast.makeText(getApplicationContext(), stringBuffer.toString(), Toast.LENGTH_SHORT).show();
                isFirstLoc = false;
            }
        } else {
            //显示错误信息   ErrCode是错误码，errInfo是错误信息
            Log.e("AmapError", "location Error,ErrCode:" + aMapLocation.getErrorCode() + ",errInfo"
                    + aMapLocation.getErrorInfo());
            Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_SHORT).show();
        }

    }
}

