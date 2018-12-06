package com.iyoyogo.android.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.utils.DataCleanManager;

import butterknife.BindView;
import butterknife.OnClick;

public class MineSettingActivity extends BaseActivity {

    @BindView(R.id.back_iv_id)
    ImageView backIvId;
    @BindView(R.id.new_message_remind)
    ImageView newMessageRemind;
    @BindView(R.id.mail_list)
    ImageView mailList;
    @BindView(R.id.imgg_auto_play)
    ImageView imggAutoPlay;
    @BindView(R.id.auto_play)
    RelativeLayout autoPlay;
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

    @Override
    protected void initView() {
        super.initView();
        String localVersion = packageName(MineSettingActivity.this);
        tvVersionName.setText(localVersion+"");
        try {
            String totalCacheSize = DataCleanManager.getInstance().getTotalCacheSize(MineSettingActivity.this);
            tvClearCache.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  String packageName(Context context) {
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

    @OnClick({R.id.back_iv_id, R.id.new_message_remind, R.id.mail_list, R.id.imgg_auto_play, R.id.user_security, R.id.feed_back, R.id.about_me, R.id.version_name, R.id.clear_cache, R.id.btn_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv_id:
                finish();
                break;
            case R.id.new_message_remind:

                break;
            case R.id.mail_list:

                break;
            case R.id.imgg_auto_play:

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

                break;
            case R.id.btn_logout:

                break;
        }
    }
    public  int getLocalVersion(Context ctx) {
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
    protected IBasePresenter createPresenter() {
        return null;
    }
}
