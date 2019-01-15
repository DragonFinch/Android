package com.iyoyogo.android.ui.common;


import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.login.SendMessageBean;
import com.iyoyogo.android.bean.login.login.LoginBean;
import com.iyoyogo.android.contract.BindPhoneContract;
import com.iyoyogo.android.presenter.BindPresenter;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;

import java.lang.ref.WeakReference;
import java.security.MessageDigest;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 绑定手机号
 */
public class BindPhoneActivity extends BaseActivity<BindPhoneContract.Presenter> implements BindPhoneContract.View {

    @BindView(R.id.mBinding_Back_ImgeView_id)
    ImageView mBindingBackImgeViewId;
    @BindView(R.id.mBinding_toobar_id)
    LinearLayout mBindingToobarId;
    @BindView(R.id.mBinding_imageview_id)
    ImageView mBindingImageviewId;
    @BindView(R.id.img_bind_phone)
    ImageView imgBindPhone;
    @BindView(R.id.bind_line)
    View bindLine;
    @BindView(R.id.edit_bind)
    EditText editBind;
    @BindView(R.id.my_rl_phone_id)
    RelativeLayout myRlPhoneId;
    @BindView(R.id.img_bind_code)
    ImageView imgBindCode;
    @BindView(R.id.line_bind_code)
    View lineBindCode;
    @BindView(R.id.edit_bind_code)
    EditText editBindCode;
    @BindView(R.id.my_test_id)
    RelativeLayout myTestId;
    TextView tvBindCode;
    @BindView(R.id.bind_service)
    TextView bindService;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.btn_bind)
    Button btnBind;
    @BindView(R.id.radio_check_img)
    ImageView radioCheckImg;
    private String logo;
    private String nickname;
    private String openid;
    private String dateTime;
    private String sign;
    private boolean isAgree;
    private boolean runningOne;
    private int type;
    private int time = 60;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    setResult(RESULT_OK);

                    break;
                case 4:
                    tvBindCode.setEnabled(false);
                    tvBindCode.setText("已发送(" + String.valueOf(time) + ")");
                    break;
                case 5:
                    tvBindCode.setEnabled(true);
                    tvBindCode.setText("重新获取验证码");

                    time = 10;
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

    @Override
    protected void initView() {
        super.initView();
        statusbar();
        tvBindCode = (TextView) findViewById(R.id.tv_bind_code);
        long l = System.currentTimeMillis();
        dateTime = String.valueOf(l);
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);

        openid = SpUtils.getString(BindPhoneActivity.this, "openid", null);
        nickname = SpUtils.getString(BindPhoneActivity.this, "nickname", null);
        logo = SpUtils.getString(BindPhoneActivity.this, "logo", null);

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
    protected int getLayoutId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected BindPhoneContract.Presenter createPresenter() {
        return new BindPresenter(this);
    }

    @Override
    public void sendMessageSuccess(SendMessageBean.DataBean data) {

        String yzm = data.getYzm();
//        editBindCode.setText(yzm);
        startTime1();
//        if (runningOne) {
//        } else {
//            sendCode();
//        }
    }

    private int count = 60;

    private static class MyHandler extends Handler {
        WeakReference<BindPhoneActivity> mActivity;

        public MyHandler(BindPhoneActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            final BindPhoneActivity mf = mActivity.get();
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mf.tvBindCode.setText("重新发送");
                    mf.count = 60;
                    mf.tvBindCode.setEnabled(true);
                    break;
                case 1:
                    mf.tvBindCode.setEnabled(false);
                    mf.tvBindCode.setText(mf.count + "秒");
                    break;
            }
        }

    }

    //    private void sendCode() {
//        final Runnable r = new Runnable() {
//
//
//            public void run() {
//                runningOne = true;
//
//                while (count > 0) {
//                    count = count - 1;
//                    handler.sendEmptyMessage(1);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//
//                        e.printStackTrace();
//                    }
//                }
//                runningOne = false;
//                handler.sendEmptyMessage(0);
//            }
//        };
//        Thread s = new Thread(r);
//        s.start();
//    }
    @Override
    public void loginSuccess(LoginBean.DataBean data) {
        Toast.makeText(this, "绑定完成", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(BindPhoneActivity.this, MainActivity.class);
        SpUtils.putString(BindPhoneActivity.this, "user_token", data.getUser_token());
        SpUtils.putString(BindPhoneActivity.this, "user_id", data.getUser_id());
        if (data.getStatus() == 2) {
            startActivity(new Intent(BindPhoneActivity.this, BindPhoneActivity.class));
        } else if (data.getStatus() == 1) {
            int have_interest = data.getHave_interest();
            if (have_interest == 1) {
                intent = new Intent();
                intent.setClass(BindPhoneActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            } else {
                intent = new Intent();
                intent.putExtra("user_id", data.getUser_id());
                intent.putExtra("user_token", data.getUser_token());
                intent.setClass(BindPhoneActivity.this, LikePrefencesActivity.class);
                startActivity(intent);
                finish();
            }

        }

    }


    @OnClick({R.id.tv_bind_code, R.id.btn_bind, R.id.radio_check_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_check_img:
                if (!isAgree) {
                    radioCheckImg.setImageResource(R.mipmap.log_select);
                    isAgree = false;
                } else {
                    radioCheckImg.setImageResource(R.mipmap.log_unselect);
                    isAgree = false;
                }
                break;
            case R.id.tv_bind_code:
                if (editBind.getText().toString().length() > 0) {

                    sign = MD5(editBind.getText().toString().trim() + "" + dateTime + "yoyogo");
                    mPresenter.sendMessage(editBind.getText().toString().trim(), "", dateTime, sign);
                } else {
                    Toast.makeText(this, "手机号必须填", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_bind:
                if (type == 0) {
                    mPresenter.login(
                            "",
                            "",
                            "",
                            4,
                            editBind.getText().toString(),
                            editBindCode.getText().toString(),
                            openid,
                            nickname,
                            logo);

                } else {
                    mPresenter.login("", "", "", type, editBind.getText().toString(), editBindCode.getText().toString(), openid, nickname, logo);

                }
                break;
        }
    }
}
