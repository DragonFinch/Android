package com.iyoyogo.android.ui.home.yoji;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.YoJiDetailCommentAdapter;
import com.iyoyogo.android.adapter.YoJiDetailAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.yoji.detail.YoJiDetailBean;
import com.iyoyogo.android.contract.YoJiDetailContract;
import com.iyoyogo.android.presenter.YoJiDetailPresenter;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;
import com.iyoyogo.android.widget.MyNestedScrollView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YoJiDetailActivity extends BaseActivity<YoJiDetailContract.Presenter> implements YoJiDetailContract.View {


    public static int expendedtag = 2;
    List<String> mList = new ArrayList<>();
    @BindView(R.id.bg)
    ImageView bg;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_head)
    CircleImageView imgHead;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.img_message)
    RelativeLayout imgMessage;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.coll)
    CollapsingToolbarLayout coll;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time_create)
    TextView tvTimeCreate;
    @BindView(R.id.tv_create)
    TextView tvCreate;
    @BindView(R.id.tv_count_see)
    TextView tvCountSee;
    @BindView(R.id.tv_see)
    TextView tvSee;
    @BindView(R.id.user_icon)
    CircleImageView userIcon;
    @BindView(R.id.tv_yoji_count)
    TextView tvYojiCount;
    @BindView(R.id.tv_yoxiu_count)
    TextView tvYoxiuCount;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.tv_address_start)
    TextView tvAddressStart;
    @BindView(R.id.tv_address_end)
    TextView tvAddressEnd;
    @BindView(R.id.tv_address_spot)
    TextView tvAddressSpot;
    @BindView(R.id.tv_spot_time)
    TextView tvSpotTime;
    @BindView(R.id.tv_money_pay)
    TextView tvMoneyPay;
    @BindView(R.id.realtive)
    RelativeLayout realtive;
    @BindView(R.id.tv_address_start_fold)
    TextView tvAddressStartFold;
    @BindView(R.id.tv_address_end_fold)
    TextView tvAddressEndFold;
    @BindView(R.id.tv_address_spot_fold)
    TextView tvAddressSpotFold;
    @BindView(R.id.tv_spot_time_fold)
    TextView tvSpotTimeFold;
    @BindView(R.id.tv_money_pay_fold)
    TextView tvMoneyPayFold;
    @BindView(R.id.message_trip)
    RelativeLayout messageTrip;
    @BindView(R.id.user_layouts)
    RelativeLayout userLayouts;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.describe_relative)
    RelativeLayout describeRelative;
    @BindView(R.id.recycler_yoji)
    RecyclerView recyclerYoji;
    @BindView(R.id.tv_load_more)
    TextView tvLoadMore;
    @BindView(R.id.comment_view)
    ImageView commentView;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.recycler_comment)
    RecyclerView recyclerComment;
    @BindView(R.id.tv_more_comment)
    TextView tvMoreComment;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.edit_comment)
    EditText editComment;
    @BindView(R.id.img_brow)
    ImageView imgBrow;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.send_emoji)
    ImageView sendEmoji;
    @BindView(R.id.comment_layout)
    RelativeLayout commentLayout;
    @BindView(R.id.tv_user_nickname)
    TextView tvUserNickname;
    @BindView(R.id.nested)
    MyNestedScrollView nested;


    private String user_token;
    private String user_id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yo_ji_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        for (int i = 0; i < 10; i++) {
            mList.add("aaa");
        }
        setSupportActionBar(toolbar);
        appbar.setExpanded(true);
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            //verticalOffset是当前appbarLayout的高度与最开始appbarlayout高度的差，向上滑动的话是负数
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //通过日志得出活动启动是两次，由于之前有setExpanded所以三次
                Log.d("启动活动调用监听次数", "几次");
                if (getSupportActionBar().getHeight() - appBarLayout.getHeight() == verticalOffset) {
                    //折叠监听
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(getResources().getColor(android.R.color.white));
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }
                    MIUISetStatusBarLightMode(getWindow(), true);
                    imgBack.setImageResource(R.mipmap.fanhui_black);
                    imgShare.setImageResource(R.mipmap.fenxiang_hei);
                    imgMessage.setVisibility(View.VISIBLE);
                    messageTrip.setVisibility(View.VISIBLE);
                    realtive.setVisibility(View.GONE);
//                    StatusBarUtil.setStatusBarColor(YoJiDetailActivity.this,Color.parseColor("#ffffff"));
                    Toast.makeText(YoJiDetailActivity.this, "折叠了", Toast.LENGTH_SHORT).show();
                }
                if (expendedtag == 2 && verticalOffset == 0) {
                    statusbar();
                    //展开监听
                    MIUISetStatusBarLightMode(getWindow(), true);
                    imgBack.setImageResource(R.mipmap.back_icon);
                    imgShare.setImageResource(R.mipmap.fenxiang_bai);
                    imgMessage.setVisibility(View.GONE);
                    messageTrip.setVisibility(View.GONE);
                    realtive.setVisibility(View.VISIBLE);
//                    StatusBarUtil.setStatusBarColor(YoJiDetailActivity.this,Color.parseColor("#00000000"));
                    Toast.makeText(YoJiDetailActivity.this, "展开了", Toast.LENGTH_SHORT).show();
                }
                if (expendedtag != 2 && verticalOffset == 0) {
                    expendedtag++;
                }
            }
        });


    }

    @Override
    protected YoJiDetailContract.Presenter createPresenter() {
        return new YoJiDetailPresenter(this);
    }

    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Intent intent = getIntent();
        int yo_id = intent.getIntExtra("yo_id", 0);
        user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
        user_token = SpUtils.getString(getApplicationContext(), "user_token", null);
        mPresenter.getYoJiDetail(user_id, user_token, yo_id);


    }

    @OnClick({R.id.img_back, R.id.img_share, R.id.tv_attention, R.id.tv_load_more, R.id.tv_comment, R.id.tv_like, R.id.tv_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_share:

                break;
            case R.id.tv_attention:

                break;
            case R.id.tv_load_more:

                break;
            case R.id.tv_comment:

                break;
            case R.id.tv_like:

                break;
            case R.id.tv_collection:

                break;
        }
    }

    @Override
    public void getYoJiDetailSuccess(YoJiDetailBean.DataBean data) {
        String logo = data.getLogo();
        Glide.with(this).load(data.getUser_logo()).into(userIcon);
        Glide.with(this).load(data.getUser_logo()).into(imgHead);
        Glide.with(this).load(logo).into(bg);
        tvUserName.setText(data.getUser_nickname());
        tvUserNickname.setText(data.getUser_nickname());
        tvTitle.setText(data.getTitle());
        tvYojiCount.setText(data.getCount_yoj() + "");
        tvYoxiuCount.setText(data.getCount_yox() + "");
        tvTimeCreate.setText(data.getCreate_time());
        tvCountSee.setText(data.getCount_view() + "人");
        tvMoneyPay.setText(data.getCost() + "人/天");
        tvMoneyPayFold.setText(data.getCost() + "人/天");
        tvSpotTime.setText(data.getCount_dates() + "天");
        tvSpotTimeFold.setText(data.getCount_dates() + "天");
        tvAddressStart.setText(data.getP_start());
        tvAddressStartFold.setText(data.getP_start());
        tvAddressSpot.setText(data.getList().size() + "个地点");
        tvAddressSpotFold.setText(data.getList().size() + "个地点");
        tvAddressEnd.setText(data.getP_end());
        tvAddressEndFold.setText(data.getP_end());


//        Glide.with(this).load(data.get)

        List<YoJiDetailBean.DataBean.ListBean> list = data.getList();
        YoJiDetailAdapter yoJiDetailAdapter = new YoJiDetailAdapter(YoJiDetailActivity.this, list);
        YoJiDetailCommentAdapter yoJiDetailCommentAdapter = new YoJiDetailCommentAdapter(YoJiDetailActivity.this, mList);
        recyclerYoji.setAdapter(yoJiDetailAdapter);
        recyclerYoji.setLayoutManager(new LinearLayoutManager(YoJiDetailActivity.this));
        recyclerComment.setLayoutManager(new LinearLayoutManager(YoJiDetailActivity.this));
        recyclerComment.setAdapter(yoJiDetailCommentAdapter);
        yoJiDetailAdapter.setOnItemClickListener(new YoJiDetailAdapter.OnClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        });
        yoJiDetailAdapter.setOnItemDataListener(new YoJiDetailAdapter.OnPlayListener() {
            @Override
            public void getData(YoJiDetailAdapter.Holder holder, int position) {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
