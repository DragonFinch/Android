package com.iyoyogo.android.ui.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.contract.ReplacePhoneContract;
import com.iyoyogo.android.presenter.ReplacePhonePresenter;
import com.iyoyogo.android.utils.SpUtils;

import java.security.MessageDigest;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 替换手机号
 */
public class ReplacePhoneActivity extends BaseActivity<ReplacePhoneContract.Presenter> implements ReplacePhoneContract.View {

    @BindView(R.id.back_iv_id)
    ImageView backIvId;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_code)
    EditText editCode;

    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.replacer_phone)
    Button replacerPhone;
    private String dateTime;
    private String user_token;
    private String user_id;
    private String sign;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_replace_phone;
    }

    @Override
    protected ReplacePhoneContract.Presenter createPresenter() {
        return new ReplacePhonePresenter(ReplacePhoneActivity.this,this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
//        StatusBarCompat.setStatusBarColor(this, Color.WHITE);

    }

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

    @Override
    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        long l = System.currentTimeMillis();
        dateTime = String.valueOf(l);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacksAndMessages(null);
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

    @OnClick({R.id.back_iv_id, R.id.tv_code, R.id.replacer_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv_id:
                finish();
                break;
            case R.id.tv_code:
                Log.d("ReplacePhoneActivity", "dateTime=" + dateTime);
                sign = MD5(editPhone.getText().toString().trim() + "" + dateTime + "yoyogo");
                Log.d("ReplacePhoneActivity", "sign=" + sign);
                if (editPhone.getText().toString().trim().startsWith("1") && editPhone.getText().toString().trim().length() == 11) {

                    mPresenter.sendMessage(ReplacePhoneActivity.this,editPhone.getText().toString().trim(), "", dateTime, sign);
                } else {
                    Toast.makeText(this, "手机格式不正确", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.replacer_phone:
                user_id = SpUtils.getString(ReplacePhoneActivity.this, "user_id", null);
                user_token = SpUtils.getString(ReplacePhoneActivity.this, "user_token", null);
                mPresenter.replacePhone(ReplacePhoneActivity.this,user_id, user_token, editPhone.getText().toString().trim(), editCode.getText().toString().trim());
                break;
        }
    }

    @Override
    public void replacePhoneSuccess(BaseBean baseBean) {
        Toast.makeText(this, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendMessageSuccess(BaseBean data) {
        startTime1();
        Toast.makeText(this, data.getMsg(), Toast.LENGTH_SHORT).show();
    }
}
