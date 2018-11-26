package com.iyoyogo.android.model.en;


import com.iyoyogo.android.app.App;
import com.iyoyogo.android.app.AppInfo;
import com.iyoyogo.android.utils.DisplayUtils;
import com.iyoyogo.android.utils.NetWorkUtils;

/**
 * Created by wgheng on 2018/7/18.
 * Description :
 */
public class PlatformInfo {


    /**
     * w : 720
     * product : 8
     * phonetype : Honor
     * fid : 10000
     * platform : 3
     * netType : 2
     * systemVersion : 4.4.4
     * mobileIP : 192.168.97.89
     * pid : A000004FFF9138
     * imsi :
     * release : 2015623
     * h : 1280
     * version : 1
     */

    private int w;
    private int h;
    private String product;
    private String phonetype;
    private String fid;
    private String platform;
    private int netType;
    private String systemVersion;
    private String mobileIP;
    private String pid;
    private String imsi;
    private String release;
    private String version;

    private static PlatformInfo instance;

    private PlatformInfo() {
        w = DisplayUtils.getScreenWidth();
        h = DisplayUtils.getScreenHeight();
        product = "1";
        phonetype = android.os.Build.BRAND + "_" + android.os.Build.MODEL;//手机型号
        fid = AppInfo.getChannelId();//渠道号
        platform = "3";
        systemVersion = android.os.Build.VERSION.RELEASE;
        pid = AppInfo.getDeviceID();//设备唯一标识
//        imsi = ;//sim卡信息（需动态权限）
        release = AppInfo.getReleaseTime(App.context);
        version = AppInfo.getVersionName(App.context);
    }

    public static PlatformInfo getInstance() {
        if (instance == null) {
            synchronized (PlatformInfo.class) {
                if (instance == null) {
                    instance = new PlatformInfo();
                }
            }
        }
        return instance;
    }

    public PlatformInfo init() {
        netType = NetWorkUtils.getNetworkType(App.context);
        mobileIP = NetWorkUtils.getIP();
        return this;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPhonetype() {
        return phonetype;
    }

    public void setPhonetype(String phonetype) {
        this.phonetype = phonetype;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getNetType() {
        return netType;
    }

    public void setNetType(int netType) {
        this.netType = netType;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getMobileIP() {
        return mobileIP;
    }

    public void setMobileIP(String mobileIP) {
        this.mobileIP = mobileIP;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
