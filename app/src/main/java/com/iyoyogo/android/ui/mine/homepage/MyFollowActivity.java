package com.iyoyogo.android.ui.mine.homepage;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.ui.mine.AddCollectionActivity;
import com.iyoyogo.android.utils.SystemBarTintManager;
import com.iyoyogo.android.utils.Util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity.MIUISetStatusBarLightMode;

/***
 * 关注
 */
public class MyFollowActivity extends BaseActivity {

    @BindView(R.id.message_center_back_im_id)
    ImageView messageCenterBackImId;
    @BindView(R.id.like_me_title_tv_id)
    TextView likeMeTitleTvId;
    @BindView(R.id.rb_yoji)
    RadioButton rbYoji;
    @BindView(R.id.rb_yoxiu)
    RadioButton rbYoxiu;
    @BindView(R.id.group)
    RadioGroup group;
    private FollowFragment followFragment = new FollowFragment();//关注的人
    private RecommendFragment recommendFragment = new RecommendFragment();//推荐话题
    private Fragment currentFragment = new Fragment();

    private boolean mIsFirstIn = true;

    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white));

        switchContent(followFragment,recommendFragment);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_yoji:
                        switchContent(followFragment,recommendFragment);
                        break;
                    case R.id.rb_yoxiu:
                        switchContent(recommendFragment,followFragment);
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_follow;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */

    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }
    //白色可以替换成其他浅色系
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void myStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(activity.getWindow(), true)) {//MIUI
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
                    activity.getWindow().setStatusBarColor(Color.WHITE);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4
                    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    SystemBarTintManager tintManager = new SystemBarTintManager(activity);
                    tintManager.setStatusBarTintEnabled(true);
                    tintManager.setStatusBarTintResource(android.R.color.white);
                }
            } else if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {//Flyme
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
                    activity.getWindow().setStatusBarColor(Color.WHITE);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4
                    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    SystemBarTintManager tintManager = new SystemBarTintManager(activity);
                    tintManager.setStatusBarTintEnabled(true);
                    tintManager.setStatusBarTintResource(android.R.color.white);
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0
                activity.getWindow().setStatusBarColor(Color.WHITE);
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }



    private void switchContent(Fragment from, Fragment to) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            // 先判断是否被add过
            if (mIsFirstIn) {

                mIsFirstIn = false;
                ft.add(R.id.fragment, to).commit();
            } else {
                ft.hide(from).add(R.id.fragment, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            }
        } else {
            ft.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
    }

    @OnClick({R.id.add_collection, R.id.message_center_back_im_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_collection://添加关注
                startActivity(new Intent(this, AddCollectionActivity.class));
                break;
            case R.id.message_center_back_im_id://finsh
                finish();
                break;
        }
    }
}
