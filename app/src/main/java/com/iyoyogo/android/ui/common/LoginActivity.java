package com.iyoyogo.android.ui.common;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.login.SendMessageBean;
import com.iyoyogo.android.bean.login.login.LoginBean;
import com.iyoyogo.android.bean.login.login.MarketBean;
import com.iyoyogo.android.contract.LoginContract;
import com.iyoyogo.android.presenter.LoginPresenter;
import com.iyoyogo.android.utils.SpUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View {
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 0;
    private final String LOGIN_TYPE_YZ = "YZ";
    private final String LOGIN_TYPE_QQ = "QQ";
    private final String LOGIN_TYPE_WB = "WB";
    private final String LOGIN_TYPE_WX = "WX";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION_CODE = 1;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 2;
    String login_Type = LOGIN_TYPE_YZ;
    public static final int CAPTURE_TYPE_ZOOM = 2;
    public static final int CAPTURE_TYPE_EXPOSE = 3;
    private static final int FILTERREQUESTLIST = 110;
    private static final int ARFACEERREQUESTLIST = 111;
    private static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION_CODE = 203;
    private final int MIN_RECORD_DURATION = 1000000;
    public int TYPE = 0;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.img_vip)
    ImageView imgVip;
    private UMShareAPI mShareAPI;
    @BindView(R.id.app_icon)
    ImageView appIcon;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_ver_code)
    EditText etVerCode;
    TextView tvCode;
    @BindView(R.id.img_btn)
    ImageButton imgBtn;
    @BindView(R.id.fwtk)
    TextView fwtk;
    @BindView(R.id.yszc)
    TextView yszc;
    @BindView(R.id.ll_reg_desc)
    LinearLayout llRegDesc;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.ll_reg_content)
    LinearLayout llRegContent;
    @BindView(R.id.third_desc)
    TextView thirdDesc;
    @BindView(R.id.rl_third_desc)
    RelativeLayout rlThirdDesc;
    @BindView(R.id.login_wechat)
    ImageView loginWechat;
    @BindView(R.id.login_weibo)
    ImageView loginWeibo;
    @BindView(R.id.login_qq)
    ImageView loginQq;
    private String dateTime;
    private String sign;
    private boolean runningOne;
    private boolean isAgree = false;
    //    Handler handler = new MyHandler(LoginActivity.this);
    private Map<String, String> para = new HashMap<>();
    private Intent intent;
    private String iconurl;
    private String name;
    private String uid;
    private int type;
    String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};

    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //初始化AMapLocationClientOption对象
    private int status;
    private int have_interest;
    private String user_id;
    private String user_token;
    private String registrationID;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
//可在其中解析amapLocation获取相应内容。
                    mLocationOption.setNeedAddress(true);
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    //地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    address = amapLocation.getAddress();
                    SpUtils.putString(getApplicationContext(), "address", address);
                    String systemVersion = Build.VERSION.RELEASE;
                    SpUtils.putString(getApplicationContext(), "phone_type", android.os.Build.BRAND + "_" + android.os.Build.MODEL);
                    SpUtils.putString(getApplicationContext(), "system_version", systemVersion);
                    amapLocation.getCountry();//国家信息
                    amapLocation.getProvince();//省信息
                    String city = amapLocation.getCity();//城市信息
//                    bar.setLocationResult(city);
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                    amapLocation.getAoiName();//获取当前定位点的AOI信息
                    amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                    amapLocation.getFloor();//获取当前室内定位的楼层
                    amapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
//获取定位时间
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };
    private String address;

    public String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initLocation() {
        String s = sHA1(getApplicationContext());
        Log.d("LocationActivity", s);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        builder.detectFileUriExposure();

        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE};
            ActivityCompat.requestPermissions(LoginActivity.this, mPermissionList, 123);
        }


        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();

        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //判断权限是否被允许

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginContract.Presenter createPresenter() {
        return new LoginPresenter(LoginActivity.this,this);
    }

    public void qqLogin() {
        type = 2;
        authorization(SHARE_MEDIA.QQ, 2);
    }

    public void weiXinLogin() {
        type = 1;
        authorization(SHARE_MEDIA.WEIXIN, 1);
    }

    public void sinaLogin() {

        type = 3;
        authorization(SHARE_MEDIA.SINA, 3);
    }

    //授权
    private void authorization(SHARE_MEDIA share_media, int type) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d("LoginActivity", "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.d("LoginActivity", "onComplete " + "授权完成");

                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                uid = map.get("uid");

                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                name = map.get("name");
                String gender = map.get("gender");
                iconurl = map.get("iconurl");
                Log.d("LoginActivity", "uid=" + uid + "name=" + name + ",gender=" + gender + "iconurl=" + iconurl);
//                Toast.makeText(getApplicationContext(), "uid=" + uid + "name=" + name + ",gender=" + gender, Toast.LENGTH_SHORT).show();

                SpUtils.putString(LoginActivity.this, "openid", uid);
                SpUtils.putString(LoginActivity.this, "nickname", name);
                SpUtils.putString(LoginActivity.this, "logo", iconurl);
                //拿到信息去请求登录接口。。。
                if (address != null) {
                    mPresenter.login(LoginActivity.this,address, android.os.Build.BRAND + "_" + android.os.Build.MODEL, Build.VERSION.RELEASE, type, "", "", uid, name, iconurl);
                } else {
                    mPresenter.login(LoginActivity.this,"", android.os.Build.BRAND + "_" + android.os.Build.MODEL, Build.VERSION.RELEASE, type, "", "", uid, name, iconurl);
                }


            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d("LoginActivity", "onError " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d("LoginActivity", "onCancel " + "授权取消");
            }
        });
    }

    //删除授权
    @Override
    protected void initView() {
        super.initView();
        isAgree = true;
        registrationID = JPushInterface.getRegistrationID(this);
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
            Window window = getWindow();
            View decorView = window.getDecorView();
            //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //导航栏颜色也可以正常设置
            //window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            attributes.flags |= flagTranslucentStatus;
            //int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            //attributes.flags |= flagTranslucentNavigation;
            window.setAttributes(attributes);
        }
        boolean isLogin = SpUtils.getBoolean(LoginActivity.this, "isLogin", false);


        tvCode = (TextView) findViewById(R.id.tv_code);
        mShareAPI = UMShareAPI.get(this);
        long l = System.currentTimeMillis();
        dateTime = String.valueOf(l);
        if (isLogin) {
            intent = new Intent();
            intent.setClass(LoginActivity.this, MainActivity.class);
            finish();
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        initLocation();
        mPresenter.market();
        String user_name = SpUtils.getString(getApplicationContext(), "user_name", null);
        if (user_name != null) {
            etPhoneNum.setText(user_name);

        } else {
            etPhoneNum.setText("");
        }
        etPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SpUtils.putString(getApplicationContext(), "user_name", s.toString().trim());
            }
        });
    }

    /*
        * ity: 0391fc32376b15fc1e9c788922c6e097
        1542013504760
        * */
    private int count = 60;


    //    private static class MyHandler extends Handler {
//        WeakReference<LoginActivity> mActivity;
//
//        public MyHandler(LoginActivity activity) {
//            mActivity = new WeakReference<>(activity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//// TODO Auto-generated method stub
//            final LoginActivity mf = mActivity.get();
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 0:
//                    mf.tvCode.setText("重新发送");
//                    mf.count = 60;
//                    mf.tvCode.setEnabled(true);
//                    break;
//                case 1:
//                    mf.tvCode.setEnabled(false);
//                    mf.tvCode.setText(mf.count + "秒");
//                    break;
//            }
//        }
//
//    }
    private int time = 60;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    setResult(RESULT_OK);
                    break;
                case 4:
                    tvCode.setEnabled(false);
                    tvCode.setTextColor(Color.parseColor("#888888"));
                    tvCode.setText("已发送(" + String.valueOf(time) + ")");
                    break;
                case 5:
                    tvCode.setEnabled(true);
                    tvCode.setText("重新获取验证码");

                    time = 60;
                    break;
            }

        }
    };

    public void startTime1() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                time--;
                if (time <= 0) {
                    mHandler.sendEmptyMessage(5);
                } else {
                    mHandler.sendEmptyMessage(4);
                    mHandler.postDelayed(this, 1000);
                }
            }
        };
        new Thread(runnable).start();
    }


    @OnClick({R.id.fwtk, R.id.yszc, R.id.tv_code, R.id.login_btn, R.id.login_wechat, R.id.login_weibo, R.id.login_qq, R.id.img_btn, R.id.ll_reg_content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fwtk:
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra("title", "服务条款")
                        .putExtra("url", "http://app.iyoyogo.com/index.php/home/article/details?id=21"));
                break;
            case R.id.yszc:
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra("title", "隐私政策")
                        .putExtra("url", "  http://app.iyoyogo.com/index.php/home/article/details?id=20"));
                break;
            case R.id.ll_reg_content:

                break;
            case R.id.img_btn:

                if (!isAgree) {
                    imgBtn.setImageResource(R.mipmap.log_select);
                    isAgree = true;
                } else {
                    isAgree = false;
                    imgBtn.setImageResource(R.mipmap.log_unselect);
                }
                break;
            case R.id.tv_code:
                Log.d("LoginActivity", etPhoneNum.getText().toString().trim());
                sign = MD5(etPhoneNum.getText().toString().trim() + "" + dateTime + "yoyogo");
                Log.d("LoginActivity", "sign是" + sign);

                Log.d("LoginActivity", "时间戳" + dateTime);
                if (etPhoneNum.getText().toString().length() == 11 && !TextUtils.isEmpty(etPhoneNum.getText().toString()) && etPhoneNum.getText().toString().startsWith("1")) {

                    mPresenter.sendMessage(etPhoneNum.getText().toString(), "", dateTime, sign);

                } else {
                    Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_btn:
                if (isAgree) {
                    if (etPhoneNum.getText().toString().length() == 11 && !TextUtils.isEmpty(etPhoneNum.getText().toString()) && etPhoneNum.getText().toString().startsWith("1")) {
                        if (!TextUtils.isEmpty(etVerCode.getText().toString().trim())) {
                            if (address != null) {
                                mPresenter.login(LoginActivity.this,address, android.os.Build.BRAND + "_" + android.os.Build.MODEL, Build.VERSION.RELEASE, 4, etPhoneNum.getText().toString().trim(), etVerCode.getText().toString().trim(), "", "", "");
                            } else {
                                mPresenter.login(LoginActivity.this,"", android.os.Build.BRAND + "_" + android.os.Build.MODEL, Build.VERSION.RELEASE, 4, etPhoneNum.getText().toString().trim(), etVerCode.getText().toString().trim(), "", "", "");
                                loginBtn.setClickable(false);
                            }
                        } else {
                            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "您还没有同意", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_wechat:
                weiXinLogin();
                loginWechat.setEnabled(false);
                loginWechat.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(loginWechat!=null)
                        loginWechat.setEnabled(true);
                    }
                },2000);
                break;
            case R.id.login_qq:
                qqLogin();
//                mPresenter.login("", "", "", 2, "", "", uid, name, iconurl);
                loginQq.setEnabled(false);
                loginQq.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(loginQq!=null)
                        loginQq.setEnabled(true);
                    }
                },2000);
                break;
            case R.id.login_weibo:
                sinaLogin();
                View btnWeibo = findViewById(R.id.login_weibo);
                btnWeibo.setEnabled(false);
                loginWeibo.setEnabled(false);
                loginWeibo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(loginWeibo!=null)
                        loginWeibo.setEnabled(true);
                    }
                },2000);
                break;
        }
    }


    public String MD5(String sourceStr) {
        try {
            // 获得MD5摘要算法的 MessageDigest对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(sourceStr.getBytes());
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < md.length; i++) {
                int tmp = md[i];
                if (tmp < 0)
                    tmp += 256;
                if (tmp < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(tmp));
            }
            //return buf.toString().substring(8, 24);// 16位加密
            return buf.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void sendMessageSuccess(SendMessageBean.DataBean data) {
        String yzm = data.getYzm();
//        etVerCode.setText(yzm);
        startTime1();
//        if (runningOne) {
//        } else {
//            sendCode();
//        }
    }

    @Override
    public void loginSuccess(LoginBean.DataBean data) {
        status = data.getStatus();
        user_id = data.getUser_id();
        user_token = data.getUser_token();

        SpUtils.putBoolean(LoginActivity.this, "isLogin", true);
        SpUtils.putString(LoginActivity.this, "user_id", user_id);
        SpUtils.putString(LoginActivity.this, "user_token", user_token);
        if (status == 2) {
            Intent intent = new Intent(LoginActivity.this, BindPhoneActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
            finish();

        } else {
            mPresenter.push(LoginActivity.this,user_id, user_token, "and", registrationID);
            have_interest = data.getHave_interest();
            if (have_interest == 1) {
                intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            } else {
                intent = new Intent();
                intent.setClass(LoginActivity.this, LikePrefencesActivity.class);
                startActivity(intent);
                finish();
            }

        }
    }

    @Override
    public void marketSuccess(MarketBean.DataBean data) {
        String content = data.getInfo().getContent();
        String logo = data.getInfo().getLogo();
        tvVip.setText(content);
        Glide.with(this).load(logo).into(imgVip);


    }

    @Override
    public void pushSuccess(BaseBean baseBean) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}