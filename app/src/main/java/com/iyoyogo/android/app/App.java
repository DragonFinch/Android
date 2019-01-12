package com.iyoyogo.android.app;


import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.iyoyogo.android.bean.yoji.publish.MessageBean;
import com.iyoyogo.android.camera.utils.CrashHandler;
import com.iyoyogo.android.camera.utils.asset.NvAssetManager;
import com.iyoyogo.android.ui.common.MainActivity;
import com.iyoyogo.android.utils.GdLocationUtil;
import com.meicam.sdk.NvsStreamingContext;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;
import zhanghuan.cn.emojiconlibrary.FaceConversionUtil;

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
 public  static    List<MessageBean> list = new ArrayList<>();

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        FaceConversionUtil.getInstace().getFileText(context);

        //umeng的初始化
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
        //全局不活一场
        CrashHandler.getInstance().init(mContext);
        //防止方法数
        MultiDex.install(this);

        //初始化
        String licensePath = "assets:/meishe.lic";
        NvsStreamingContext.init(this, licensePath, NvsStreamingContext.STREAMING_CONTEXT_FLAG_SUPPORT_4K_EDIT);
        NvAssetManager.init(this);//素材管理器初始化
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
            //激光的初始化
            JPushInterface.setDebugMode(BuildConfig.DEBUG);
            JPushInterface.init(this);
//            initImageLoader();
//初始化用户西你想
            initUserInfo();
//            initBaiduMap();
//            initUmeng();
            //屏幕适配
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
//        DisplayAdapter.setDensity(this);
    /*    AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(false)
                .setSupportSP(false)
                .setSupportSubunits(Subunits.MM);*/
        //AndroidAutoSize 默认开启对 dp 的支持, 调用 UnitsManager.setSupportDP(false); 可以关闭对 dp 的支持
        //主单位 dp 和 副单位可以同时开启的原因是, 对于旧项目中已经使用了 dp 进行布局的页面的兼容
        //让开发者的旧项目可以渐进式的从 dp 切换到副单位, 即新页面用副单位进行布局, 然后抽时间逐渐的将旧页面的布局单位从 dp 改为副单位
        //最后将 dp 全部改为副单位后, 再使用 UnitsManager.setSupportDP(false); 将 dp 的支持关闭, 彻底隔离修改 density 所造成的不良影响
        //如果项目完全使用副单位, 则可以直接以像素为单位填写 AndroidManifest 中需要填写的设计图尺寸, 不需再把像素转化为 dp
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(false)

                //当使用者想将旧项目从主单位过渡到副单位, 或从副单位过渡到主单位时
                //因为在使用主单位时, 建议在 AndroidManifest 中填写设计图的 dp 尺寸, 比如 360 * 640
                //而副单位有一个特性是可以直接在 AndroidManifest 中填写设计图的 px 尺寸, 比如 1080 * 1920
                //但在 AndroidManifest 中却只能填写一套设计图尺寸, 并且已经填写了主单位的设计图尺寸
                //所以当项目中同时存在副单位和主单位, 并且副单位的设计图尺寸与主单位的设计图尺寸不同时, 可以通过 UnitsManager#setDesignSize() 方法配置
                //如果副单位的设计图尺寸与主单位的设计图尺寸相同, 则不需要调用 UnitsManager#setDesignSize(), 框架会自动使用 AndroidManifest 中填写的设计图尺寸
//                .setDesignSize(2160, 3840)

                //AndroidAutoSize 默认开启对 sp 的支持, 调用 UnitsManager.setSupportSP(false); 可以关闭对 sp 的支持
                //如果关闭对 sp 的支持, 在布局时就应该使用副单位填写字体的尺寸
                //如果开启 sp, 对其他三方库控件影响不大, 也可以不关闭对 sp 的支持, 这里我就继续开启 sp, 请自行斟酌自己的项目是否需要关闭对 sp 的支持
//                .setSupportSP(false)

                //AndroidAutoSize 默认不支持副单位, 调用 UnitsManager#setSupportSubunits() 可选择一个自己心仪的副单位, 并开启对副单位的支持
                //只能在 pt、in、mm 这三个冷门单位中选择一个作为副单位, 三个单位的适配效果其实都是一样的, 您觉的哪个单位看起顺眼就用哪个
                //您选择什么单位就在 layout 文件中用什么单位进行布局, 我选择用 mm 为单位进行布局, 因为 mm 翻译为中文是妹妹的意思
                //如果大家生活中没有妹妹, 那我们就让项目中最不缺的就是妹妹!
                .setSupportSubunits(Subunits.MM);
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