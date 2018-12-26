package com.iyoyogo.android.ui.mine.homepage;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.MineFragmentAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.mine.center.UserCenterBean;
import com.iyoyogo.android.contract.PersonalCenterContract;
import com.iyoyogo.android.presenter.PersonalCenterPresenter;
import com.iyoyogo.android.ui.mine.AddCollectionActivity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class Personal_homepage_Activity extends BaseActivity<PersonalCenterContract.Presenter> implements PersonalCenterContract.View {


    @BindView(R.id.img_bg)
    ImageView imgBg;
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
    private String user_id;
    private String user_token;
    private int age;


    protected void initView() {
        super.initView();
        statusbar();


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
        user_id = SpUtils.getString(Personal_homepage_Activity.this, "user_id", null);
        user_token = SpUtils.getString(Personal_homepage_Activity.this, "user_token", null);
        mPresenter.getPersonalCenter(user_id, user_token, user_id);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_homepage;
    }

    @Override
    protected PersonalCenterContract.Presenter createPresenter() {
        return new PersonalCenterPresenter(this);
    }


    @OnClick({R.id.img_back, R.id.img_share,R.id.my_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_share:
                break;
            case R.id.my_collection:
                startActivity(new Intent(this,AddCollectionActivity.class));
                break;
        }
    }


    @Override
    public void getPersonalCenterSuccess(UserCenterBean.DataBean data) {
        List<Fragment> fragments = new ArrayList<>();
        YoXiuFragment yoXiuFragment = new YoXiuFragment();
        YoJiFragment yoJiFragment = new YoJiFragment();
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
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {


        }
        tvAge.setText(age + "");
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
}
