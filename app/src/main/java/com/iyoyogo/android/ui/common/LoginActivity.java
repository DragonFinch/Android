package com.iyoyogo.android.ui.common;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;


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


    private int status;
    private int have_interest;
    private String user_id;
    private String user_token;
    private String registrationID;


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
        return new LoginPresenter(this);
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
                mPresenter.login("", "", "", type, "", "", uid, name, iconurl);


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
        mPresenter.market();
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


    @OnClick({R.id.tv_code, R.id.login_btn, R.id.login_wechat, R.id.login_weibo, R.id.login_qq, R.id.img_btn, R.id.ll_reg_content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
                            mPresenter.login("", "", "", 4, etPhoneNum.getText().toString().trim(), etVerCode.getText().toString().trim(), "", "", "");
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


                Toast.makeText(this, "微信登陆", Toast.LENGTH_SHORT).show();

                break;
            case R.id.login_qq:
                qqLogin();
//                mPresenter.login("", "", "", 2, "", "", uid, name, iconurl);

                break;
            case R.id.login_weibo:
                sinaLogin();
//                mPresenter.login("", "", "", 3, "", "", uid, name, iconurl);

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
        mPresenter.push(user_id, user_token, "and", registrationID);
        SpUtils.putBoolean(LoginActivity.this, "isLogin", true);
        SpUtils.putString(LoginActivity.this, "user_id", user_id);
        SpUtils.putString(LoginActivity.this, "user_token", user_token);
        if (status == 2) {
            Intent intent = new Intent(LoginActivity.this, BindPhoneActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
            finish();

        } else {
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
