package com.iyoyogo.android.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.mine.setting.MineSettingBean;
import com.iyoyogo.android.contract.MineSettingContract;
import com.iyoyogo.android.presenter.MineSettingPresenter;
import com.iyoyogo.android.ui.common.LoginActivity;
import com.iyoyogo.android.utils.CleanDataUtils;
import com.iyoyogo.android.utils.DataCleanManager;
import com.iyoyogo.android.utils.SpUtils;
import com.suke.widget.SwitchButton;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineSettingActivity extends BaseActivity<MineSettingContract.Presenter> implements MineSettingContract.View, SwitchButton.OnCheckedChangeListener {

    @BindView(R.id.back_iv_id)
    ImageView backIvId;
    //    @BindView(R.id.new_message_remind)
//    ImageView newMessageRemind;
//    @BindView(R.id.mail_list)
//    ImageView mailList;
//    @BindView(R.id.imgg_auto_play)
//    ImageView imggAutoPlay;
//    @BindView(R.id.auto_play)
//    RelativeLayout autoPlay;
    @BindView(R.id.user_security)
    RelativeLayout userSecurity;
    @BindView(R.id.feed_back)
    RelativeLayout feedBack;
    @BindView(R.id.about_me)
    RelativeLayout aboutMe;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;
    @BindView(R.id.version_name)
    RelativeLayout versionName;
    @BindView(R.id.tv_clear_cache)
    TextView tvClearCache;
    @BindView(R.id.clear_cache)
    RelativeLayout clearCache;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.switch_button1)
    SwitchButton switchButton1;
    @BindView(R.id.switch_button2)
    SwitchButton switchButton2;
    @BindView(R.id.switch_button3)
    SwitchButton switchButton3;
    //    @BindView(R.id.switch1)
//    Switch aSwitch;
//    @BindView(R.id.switch2)
//    Switch bSwitch;
//    @BindView(R.id.switch3)
//    Switch cSwitch;
    private String user_id;
    private String user_token;
    private String address;
    private String phone_type;
    private String localVersion;
    private int wifi_auto_play_video;
    private int notice;
    private int address_list;
    private int is_notice = 0;
    private int is_autoPlay = 0;
    private int is_mail = 0;
    private String totalCacheSize;


    @Override
    protected void initView() {
        super.initView();
        File file = new File(this.getCacheDir().getPath());
        try {
            Log.d("MineSettingActivity", DataCleanManager.getCacheSize(file));
            tvClearCache.setText(DataCleanManager.getCacheSize(file));

        } catch (Exception e) {
            e.printStackTrace();
        }
        user_id = SpUtils.getString(MineSettingActivity.this, "user_id", null);
        user_token = SpUtils.getString(MineSettingActivity.this, "user_token", null);
        address = SpUtils.getString(MineSettingActivity.this, "address", null);
        phone_type = SpUtils.getString(MineSettingActivity.this, "phone_type", null);
        localVersion = packageName(MineSettingActivity.this);
        tvVersionName.setText(localVersion + "");
        try {
            totalCacheSize = CleanDataUtils.getTotalCacheSize(this);
            tvClearCache.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvVersionName.setText("v." + localVersion + "");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mPresenter.getMineSetting(user_id, user_token);
        switchButton1.setOnCheckedChangeListener(this);
        switchButton2.setOnCheckedChangeListener(this);
        switchButton3.setOnCheckedChangeListener(this);
    }

    public String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }

    //R.id.switch1, R.id.switch2, R.id.switch3,
//    R.id.switch_button1, R.id.switch_button2, R.id.switch_button3,
    @OnClick({R.id.back_iv_id, R.id.user_security, R.id.feed_back, R.id.about_me, R.id.version_name, R.id.clear_cache, R.id.btn_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv_id:
                finish();
                break;
            case R.id.switch_button1:
               /* if (notice==1){
                    mPresenter.setMineSetting(user_id,user_token,is_autoPlay,is_notice,is_mail);
                    mPresenter.getMineSetting(user_id,user_token);
                }else {
                    mPresenter.setMineSetting(user_id,user_token,wifi_auto_play_video,notice,address_list);
                    mPresenter.getMineSetting(user_id,user_token);
                }*/
                if (is_notice == 1) {
                    mPresenter.setMineSetting(user_id, user_token, is_autoPlay, 0, is_mail);
                    mPresenter.getMineSetting(user_id, user_token);
                    Log.e("is_notice", "选中");
                } else {
                    mPresenter.setMineSetting(user_id, user_token, is_autoPlay, 1, is_mail);
                    mPresenter.getMineSetting(user_id, user_token);
                    Log.e("is_notice", "no选中");
                }
                break;
            case R.id.switch_button2:
                if (is_mail == 1) {
                    mPresenter.setMineSetting(user_id, user_token, is_autoPlay, is_notice, 0);
                    mPresenter.getMineSetting(user_id, user_token);
                    Log.e("is_mail", "选中");
                } else {
                    mPresenter.setMineSetting(user_id, user_token, is_autoPlay, is_notice, 1);
                    mPresenter.getMineSetting(user_id, user_token);
                    Log.e("is_mail", "no选中");
                }
                break;
            case R.id.switch_button3:
                if (is_autoPlay == 1) {
                    mPresenter.setMineSetting(user_id, user_token, 0, is_notice, is_mail);
                    mPresenter.getMineSetting(user_id, user_token);
                    Log.e("is_autoPlay", "选中");
                } else {
                    mPresenter.setMineSetting(user_id, user_token, 1, is_notice, is_mail);
                    mPresenter.getMineSetting(user_id, user_token);
                    Log.e("is_autoPlay", "no选中");
                }
                break;

            case R.id.user_security:
                startActivity(new Intent(MineSettingActivity.this, UserAndSecurityActivity.class));
                break;
            case R.id.feed_back:
                startActivity(new Intent(MineSettingActivity.this, FeedBackActivity.class));
                break;
            case R.id.about_me:
                startActivity(new Intent(MineSettingActivity.this, AboutMeActivity.class));
                break;
            case R.id.version_name:

                break;
            case R.id.clear_cache:

                try {
                    CleanDataUtils.clearAllCache(this);
                    String size = CleanDataUtils.getTotalCacheSize(this);
                    tvClearCache.setText(size);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btn_logout:
                mPresenter.logout(user_id, user_token, address, phone_type, localVersion);
                break;
        }
    }

    public int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
            Log.d("TAG", "本软件的版本号。。" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_setting;
    }

    @Override
    protected MineSettingContract.Presenter createPresenter() {
        return new MineSettingPresenter(this);
    }

    @Override
    public void logoutSuccess(BaseBean baseBean) {
        SpUtils.remove(MineSettingActivity.this, "user_id");
        SpUtils.remove(MineSettingActivity.this, "user_token");
        SpUtils.remove(MineSettingActivity.this, "isLogin");
        startActivity(new Intent(MineSettingActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void getMineSettingSuccess(MineSettingBean.DataBean data) {
        wifi_auto_play_video = data.getWifi_auto_play_video();
        notice = data.getNotice();
        address_list = data.getAddress_list();
        is_autoPlay = wifi_auto_play_video;
        is_notice = notice;
        is_mail = address_list;
        if (notice == 1) {
            switchButton1.setChecked(true);
        } else {
            switchButton1.setChecked(false);
        }
        if (address_list == 1) {
            switchButton2.setChecked(true);
        } else {
            switchButton2.setChecked(false);
        }

        if (wifi_auto_play_video == 1) {
            switchButton3.setChecked(true);
        } else {
            switchButton3.setChecked(false);
        }
    }

    @Override
    public void setMineSettingSuccess(BaseBean baseBean) {
        mPresenter.getMineSetting(user_id, user_token);
    }

    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.switch_button1:
                if (is_notice == 1) {
                    mPresenter.setMineSetting(user_id, user_token, is_autoPlay, 0, is_mail);
//                    mPresenter.getMineSetting(user_id, user_token);
                    Log.e("is_notice", "选中");
                } else {
                    mPresenter.setMineSetting(user_id, user_token, is_autoPlay, 1, is_mail);
//                    mPresenter.getMineSetting(user_id, user_token);
                    Log.e("is_notice", "no选中");
                }
                break;
            case R.id.switch_button2:
                if (is_mail == 1) {
                    mPresenter.setMineSetting(user_id, user_token, is_autoPlay, is_notice, 0);
//                    mPresenter.getMineSetting(user_id, user_token);
                    Log.e("is_mail", "选中");
                } else {
                    mPresenter.setMineSetting(user_id, user_token, is_autoPlay, is_notice, 1);
//                    mPresenter.getMineSetting(user_id, user_token);
                    Log.e("is_mail", "no选中");
                }
                break;
            case R.id.switch_button3:
                if (is_autoPlay == 1) {
                    mPresenter.setMineSetting(user_id, user_token, 0, is_notice, is_mail);
//                    mPresenter.getMineSetting(user_id, user_token);
                    Log.e("is_autoPlay", "选中");
                } else {
                    mPresenter.setMineSetting(user_id, user_token, 1, is_notice, is_mail);
//                    mPresenter.getMineSetting(user_id, user_token);
                    Log.e("is_autoPlay", "no选中");
                }
                break;

        }
    }
}
