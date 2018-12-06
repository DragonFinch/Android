package com.iyoyogo.android.app;


import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.iyoyogo.android.camera.utils.CrashHandler;
import com.iyoyogo.android.utils.DisplayAdapter;
import com.iyoyogo.android.utils.GdLocationUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import static com.iyoyogo.android.app.AppInfo.getCurProcessName;

/**
 * Created by wgheng on 2018/7/9.
 * Description :
 */
public class App extends Application {
//5bea3cb0f1f5565cc1000170
    public static Context mContext;
    public static Context context;
    public static boolean isLogin;
    public static UserInfo userInfo;


    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        UMShareAPI.get(this);
        UMConfigure.init(this,"5bea3cb0f1f5565cc1000170"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        mContext = getApplicationContext();
        //
        PlatformConfig.setSinaWeibo("3163111192", "d6a50a54a967198b5e58e01fa92d448d", "http://www.iyoyogo.com:8090/api/1.0/about/us/wbAuth");
        PlatformConfig.setWeixin("wxa79424f9b261fbea", "b31df002153d0897cc5de08be3d72cfd");
        PlatformConfig.setQQZone("1107050658", "MPqlCLruUzjIUAAu");
        init();



        if (BuildConfig.DEBUG) {
            UMConfigure.setLogEnabled(true);
        } else {
            UMConfigure.setLogEnabled(false);
        }
        CrashHandler.getInstance().init(mContext);
        MultiDex.install(this);
    }
    public static Context getmContext() {
        return context;
    }
    /**
     * 第三方库及工具的初始化操作
     */
    private void init() {
        //主进程执行初始化
        if (getPackageName().equals(getCurProcessName(this))) {
//            initDebugTools();
            initLog();
//            initImageLoader();

            initUserInfo();
//            initBaiduMap();
//            initUmeng();
            initDisplayAdapter();
        }
    }

    /***
     * 初始化友盟
     *//*
    private void initUmeng() {
        String name = "umeng";
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, BuildConfig.UMENG_APP_KEY, name, UMConfigure.DEVICE_TYPE_PHONE, "");
        // TODO: 2018/8/27 设置各个平台的 appkey  微信 QQ
        PlatformConfig.setQQZone(BuildConfig.QQ_APP_ID, BuildConfig.QQ_APP_KEY);


    }

    *//***
     * 百度地图初始化
     *//*
    private void initBaiduMap() {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    /**
     * 初始化用户信息
     */
    private void initUserInfo() {
        userInfo = UserInfo.getInstance();
        if (!TextUtils.isEmpty(userInfo.getUserToken())) {
            isLogin = true;
        }
    }

    /**
     * 初始化调试工具
     *//*
    private void initDebugTools() {
        Stetho.initializeWithDefaults(this);
    }

    */
    /**
     * 配置屏幕适配
     */
    private void initDisplayAdapter() {
        DisplayAdapter.setDensity(this);
    }

    /**
     * 初始化日志库
     */
    private void initLog() {

        //处理 3.0 以上 AS bug，
        PrettyFormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .logStrategy(new LogStrategy() {
                    private int last;
                    @Override
                    public void log(int priority, @Nullable String tag, @NonNull String message) {
                        int random = (int) (10 * Math.random());
                        if (random == last) {
                            random = (random + 1) % 10;
                        }
                        last = random;
                        Log.println(priority, String.valueOf(random) + tag, message);
                    }
                })
                .methodCount(0)
                .methodOffset(7)
//                .tag("tag")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    /**
     * 初始化图片加载库
     *//*
    private void initImageLoader() {
        ImageLoader.getInstance().setGlobalImageLoader(new GlideLoader());
    }

    *//**
     * 64K配置
     *//*
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }*/

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i("mzh", "onTerminate");
        //停止定位服务
        GdLocationUtil.getInstance().destoryLocationService();
    }
}