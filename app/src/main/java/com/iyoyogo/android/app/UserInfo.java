package com.iyoyogo.android.app;


import com.iyoyogo.android.utils.SpUtils;

/**
 * Created by wgheng on 2018/7/20.
 * Description : 保存登录用户信息（修改时需同时修改内存和sp）
 */
public class UserInfo {

    private static final String KEY_USER_TOKEN = "userToken";
    private static final String KEY_HEADER_URL = "headerUrl";
    private static final String KEY_WEDDING_PROGRESS = "weddingProgress";
    private static volatile UserInfo instance;
    private String userToken;
    private String headerUrl;
    private int weddingProgress;
    private int hasInit;//是否初始化

    private UserInfo() {
        //TODO
        userToken = SpUtils.getString(App.context, KEY_USER_TOKEN, null);
        headerUrl = SpUtils.getString(App.context, KEY_HEADER_URL, null);
        weddingProgress = SpUtils.getInt(App.context, KEY_WEDDING_PROGRESS, 0);
    }

    public static UserInfo getInstance() {

        if (instance == null) {
            synchronized (UserInfo.class) {
                if (instance == null) {
                    instance = new UserInfo();
                }
            }
        }
        return instance;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
        SpUtils.putString(App.context, KEY_USER_TOKEN, userToken);
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
        SpUtils.putString(App.context, KEY_HEADER_URL, headerUrl);
    }

    public int getWeddingProgress() {
        return weddingProgress;
    }

    public void setWeddingProgress(int weddingProgress) {
        this.weddingProgress = weddingProgress;
        SpUtils.putInt(App.context, KEY_WEDDING_PROGRESS, weddingProgress);
    }

    public int getHasInit() {
        return hasInit;
    }

    public void setHasInit(int hasInit) {
        this.hasInit = hasInit;
    }
}
