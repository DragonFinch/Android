package com.iyoyogo.android.ui.home.yoji;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.MineFragmentAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.mine.center.UserCenterBean;
import com.iyoyogo.android.contract.PersonalCenterContract;
import com.iyoyogo.android.presenter.PersonalCenterPresenter;
import com.iyoyogo.android.ui.mine.homepage.UserFansActivity;
import com.iyoyogo.android.ui.mine.homepage.YoJiFragment;
import com.iyoyogo.android.ui.mine.homepage.YoXiuFragment;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户中心
 */
public class UserHomepageActivity extends BaseActivity<PersonalCenterContract.Presenter> implements PersonalCenterContract.View {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.personal_bg_rl_id)
    RelativeLayout personalBgRlId;
    @BindView(R.id.tv_attention_count)
    TextView tvAttentionCount;
    @BindView(R.id.tv_collection_count)
    TextView tvCollectionCount;
    @BindView(R.id.tv_fans_count)
    TextView tvFansCount;
    @BindView(R.id.img_user_icon)
    CircleImageView imgUserIcon;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_star)
    TextView tvStar;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.personal_vp_id)
    ViewPager personalVpId;
    @BindView(R.id.tv_guanzhu)
    TextView tvGuanzhu;
    private String user_id;
    private String user_token;
    private int age;
    private String yo_user_id;
    private String user_logo;
    private String user_nickname;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_homepage;
    }

    protected void initView() {
        super.initView();
        statusbar();
    }

    @Override
    protected PersonalCenterContract.Presenter createPresenter() {
        return new PersonalCenterPresenter(this);
    }

    public int getAge(Date birthDay) throws Exception {
        //获取当前系统时间
        Calendar cal = Calendar.getInstance();
        //如果出生日期大于当前时间，则抛出异常
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        //取出系统当前时间的年、月、日部分
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        //将日期设置为出生日期
        cal.setTime(birthDay);
        //取出出生日期的年、月、日部分
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        //当前年份与出生年份相减，初步计算年龄
        int age = yearNow - yearBirth;
        //当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
        if (monthNow <= monthBirth) {
            //如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        System.out.println("age:" + age);
        return age;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        user_id = SpUtils.getString(UserHomepageActivity.this, "user_id", null);
        user_token = SpUtils.getString(UserHomepageActivity.this, "user_token", null);
        Intent intent = getIntent();
        yo_user_id = intent.getStringExtra("yo_user_id");
        mPresenter.getPersonalCenter(user_id, user_token, yo_user_id);
    }

    public void share() {
        View view = getLayoutInflater().inflate(R.layout.popup_share, null);
        PopupWindow popup_share = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(getApplicationContext(), 220), true);
        popup_share.setBackgroundDrawable(new ColorDrawable());
        popup_share.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup_share.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        LinearLayout qq_layout = view.findViewById(R.id.qq_layout);
        LinearLayout comment_layout = view.findViewById(R.id.comment_layout);
        LinearLayout wechat_layout = view.findViewById(R.id.wechat_layout);
        LinearLayout sina_layout = view.findViewById(R.id.sina_layout);
        TextView tv_cancel = view.findViewById(R.id.cancel);
        ImageView img_close = view.findViewById(R.id.close_img);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popup_share.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
            }
        });
        comment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
                shareWeb(SHARE_MEDIA.WEIXIN_CIRCLE);
            }
        });
        wechat_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
                shareWeb(SHARE_MEDIA.WEIXIN);
            }
        });
        sina_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
                shareWeb(SHARE_MEDIA.SINA);
            }
        });
        qq_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWeb(SHARE_MEDIA.QQ);
                popup_share.dismiss();
            }
        });
        backgroundAlpha(0.6f);

        //添加pop窗口关闭事件
        popup_share.setOnDismissListener(new poponDismissListener());
        popup_share.showAtLocation(findViewById(R.id.activity_user_homepage), Gravity.BOTTOM, 0, 0);

    }

    private void shareWeb(SHARE_MEDIA share_media) {
        /*80002/yo_id/4143*/
        String url = "http://192.168.0.104/home/share/center_yoj/share_user_id/" + user_id + "/his_id/" + yo_user_id;
        UMWeb web = new UMWeb(url);
        web.setTitle(user_nickname);//标题
        UMImage thumb = new UMImage(getApplicationContext(), user_logo);
        web.setThumb(thumb);  //缩略图

        web.setDescription("用户主页");//描述

        new ShareAction(UserHomepageActivity.this)
                .withMedia(web)
                .setPlatform(share_media)
                .share();
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }

    //隐藏事件PopupWindow
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1.0f);
        }
    }

    @Override
    public void getPersonalCenterSuccess(UserCenterBean.DataBean data) {
        user_logo = data.getUser_logo();
        user_nickname = data.getUser_nickname();
        List<Fragment> fragments = new ArrayList<>();
        YoXiuFragment yoXiuFragment = new YoXiuFragment();
        YoJiFragment yoJiFragment = new YoJiFragment();
        Bundle bundle = new Bundle();
        bundle.putString("yo_user_id", yo_user_id);
        yoXiuFragment.setArguments(bundle);
        yoJiFragment.setArguments(bundle);
        fragments.add(yoJiFragment);
        fragments.add(yoXiuFragment);
        List<String> titles = new ArrayList<>();
        Glide.with(this).load(data.getUser_logo()).into(imgUserIcon);

        String user_sex = data.getUser_sex();
        String user_birthday = data.getUser_birthday();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = df.parse(user_birthday);
            age = getAge(date);
            tvAge.setText(age + "");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {


        }

        if (user_sex.equals("男")) {
            Drawable nan_xuanzhong = getResources().getDrawable(
                    R.mipmap.nan_xuanzhong);

            tvAge.setCompoundDrawablesWithIntrinsicBounds(nan_xuanzhong,
                    null, null, null);
        } else {
            Drawable nv_xz = getResources().getDrawable(
                    R.mipmap.nv_xz);

            tvAge.setCompoundDrawablesWithIntrinsicBounds(nv_xz,
                    null, null, null);
        }
        tvNickName.setText(data.getUser_nickname());
        tvAttentionCount.setText(data.getCount_attention() + "");
        tvCollectionCount.setText(data.getCount_collect() + "");
        tvFansCount.setText(data.getCount_fans() + "");
        tvCity.setText(data.getUser_city());
        titles.add(getResources().getString(R.string.yoji) + "  " + data.getCount_yoj());
        titles.add(getResources().getString(R.string.yoxiu) + "  " + data.getCount_yox());
        MineFragmentAdapter mineFragmentAdapter = new MineFragmentAdapter(getSupportFragmentManager(), fragments, titles);
        personalVpId.setAdapter(mineFragmentAdapter);
        tab.setupWithViewPager(personalVpId);
    }

    @Override
    public void addAttention1(AttentionBean.DataBean data) {

    }


    @OnClick({R.id.img_back, R.id.my_collection, R.id.get_hisFans, R.id.tv_guanzhu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.my_collection:
                Intent intent = new Intent(this, UserFansActivity.class);
                intent.putExtra("id", "1");
                intent.putExtra("yo_user_id", yo_user_id);
                startActivity(intent);
                break;
            case R.id.get_hisFans:
                intent = new Intent(this, UserFansActivity.class);
                intent.putExtra("id", "2");
                intent.putExtra("yo_user_id", yo_user_id);
                startActivity(intent);
                break;
            case R.id.tv_guanzhu:
                mPresenter.addAttention1(user_id, user_token, yo_user_id);
                tvGuanzhu.setText("已关注");
                tvGuanzhu.setBackgroundResource(R.drawable.bg_delete_yoji);
                tvGuanzhu.setTextColor(Color.parseColor("#888888"));
                break;
        }
    }
}
