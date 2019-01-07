package com.iyoyogo.android.ui.mine;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.mine.MineMessageBean;
import com.iyoyogo.android.contract.MineContract;
import com.iyoyogo.android.presenter.MineMessagePresenter;
import com.iyoyogo.android.ui.mine.collection.CollectionActivity;
import com.iyoyogo.android.ui.mine.draft.DraftActivity;
import com.iyoyogo.android.ui.mine.homepage.HisFansActivity;
import com.iyoyogo.android.ui.mine.homepage.Like_me_Activity;
import com.iyoyogo.android.ui.mine.homepage.Message_center_Activity;
import com.iyoyogo.android.ui.mine.homepage.MyFollowActivity;
import com.iyoyogo.android.ui.mine.homepage.Personal_homepage_Activity;
import com.iyoyogo.android.ui.mine.homepage.UserFansActivity;
import com.iyoyogo.android.ui.mine.homepage.VipCenterActivity;
import com.iyoyogo.android.utils.FastBlurUtil;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.icondottextview.IconDotTextView;
import com.iyoyogo.android.widget.CircleImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MineFragment extends BaseFragment<MineContract.Presenter> implements MineContract.View {


    @BindView(R.id.my_bgi_iv_id)
    ImageView myBgiIvId;
    @BindView(R.id.my_messge_im_id)
    IconDotTextView myMessgeImId;
    @BindView(R.id.my_dot_v_id)
    View myDotVId;
    @BindView(R.id.my_addfriend_im_id)
    IconDotTextView myAddfriendImId;
    @BindView(R.id.my_basic_headimg_iv_id)
    CircleImageView myBasicHeadimgIvId;
    @BindView(R.id.my_basic_name_tv_id)
    TextView myBasicNameTvId;
    @BindView(R.id.my_basic_name_iv_id)
    ImageView myBasicNameIvId;
    @BindView(R.id.my_basic_llone_id)
    LinearLayout myBasicLloneId;
    @BindView(R.id.my_basic_fans_tv_id)
    TextView myBasicFansTvId;
    @BindView(R.id.my_basic_fansnumber_tv_id)
    TextView myBasicFansnumberTvId;
    @BindView(R.id.my_basic_follow_tv_id)
    TextView myBasicFollowTvId;
    @BindView(R.id.my_basic_follownumber_tv_id)
    TextView myBasicFollownumberTvId;
    @BindView(R.id.vip_center_img)
    ImageView vipCenterImg;
    @BindView(R.id.my_rllayout1_id)
    RelativeLayout myRllayout1Id;
    @BindView(R.id.my_clock_iv_id)
    ImageView myClockIvId;
    @BindView(R.id.my_day_tv_id)
    TextView myDayTvId;
    @BindView(R.id.my_exceed_tv_id)
    TextView myExceedTvId;
    @BindView(R.id.my_clock_but_id)
    TextView myClockButId;
    @BindView(R.id.my_rllayout2_id)
    RelativeLayout myRllayout2Id;
    @BindView(R.id.my_option_home_id)
    LinearLayout myOptionHomeId;
    @BindView(R.id.my_option_draft_id)
    LinearLayout myOptionDraftId;
    @BindView(R.id.my_option_like_id)
    LinearLayout myOptionLikeId;
    @BindView(R.id.my_option_col_id)
    LinearLayout myOptionColId;
    @BindView(R.id.my_option_set_id)
    LinearLayout myOptionSetId;
    Unbinder unbinder;
    @BindView(R.id.img_vip_sign)
    ImageView imgVipSign;
    @BindView(R.id.img_level)
    ImageView imgLevel;
    Unbinder unbinder1;
    private String user_id;
    private String user_token;
    private Intent intent2;
    private Intent intent3;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected MineContract.Presenter createPresenter() {
        return new MineMessagePresenter(this);
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected void initView() {
        super.initView();
        final String pattern = "2";


        int scaleRatio = 0;
        if (TextUtils.isEmpty(pattern)) {
            scaleRatio = 0;
        } else if (scaleRatio < 0) {
            scaleRatio = 10;
        } else {
            scaleRatio = Integer.parseInt(pattern);
        }

        //        获取需要被模糊的原图bitmap
        Resources res = getResources();
        Bitmap scaledBitmap = BitmapFactory.decodeResource(res, R.mipmap.img_bj);

        //        scaledBitmap为目标图像，10是缩放的倍数（越大模糊效果越高）
        Bitmap blurBitmap = FastBlurUtil.toBlur(scaledBitmap, scaleRatio);
        myBgiIvId.setScaleType(ImageView.ScaleType.CENTER_CROP);
        myBgiIvId.setImageBitmap(blurBitmap);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
            Window window = getActivity().getWindow();
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
            Window window = getActivity().getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            attributes.flags |= flagTranslucentStatus;
            //int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            //attributes.flags |= flagTranslucentNavigation;
            window.setAttributes(attributes);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        user_id = SpUtils.getString(getContext(), "user_id", null);
        user_token = SpUtils.getString(getContext(), "user_token", null);
        mPresenter.getUserInfo(user_id, user_token);
    }

    @OnClick({R.id.my_basic_fans_tv_id, R.id.my_basic_fansnumber_tv_id, R.id.my_basic_follow_tv_id, R.id.my_basic_follownumber_tv_id,R.id.my_basic_headimg_iv_id, R.id.vip_center_img, R.id.my_basic_name_iv_id, R.id.my_messge_im_id, R.id.my_addfriend_im_id, R.id.my_clock_but_id, R.id.my_option_home_id, R.id.my_option_draft_id, R.id.my_option_like_id, R.id.my_option_col_id, R.id.my_option_set_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_basic_headimg_iv_id:
                Intent intent = new Intent(getContext(), Personal_homepage_Activity.class);
                intent.putExtra("yo_user_id", user_id);
                startActivity(intent);
                break;
            case R.id.vip_center_img:
                startActivity(new Intent(getContext(), VipCenterActivity.class));
                break;
            case R.id.my_messge_im_id:
                startActivity(new Intent(getContext(), Message_center_Activity.class));
                break;
            case R.id.my_addfriend_im_id:
                startActivity(new Intent(getContext(), AddCollectionActivity.class));
                break;
            case R.id.my_clock_but_id:
                mPresenter.punchClock(user_id, user_token);
                break;
            case R.id.my_option_home_id:
                Intent intent1 = new Intent(getContext(), Personal_homepage_Activity.class);
                intent1.putExtra("yo_user_id", user_id);
                startActivity(intent1);
                break;
            case R.id.my_option_draft_id:
                startActivity(new Intent(getContext(), DraftActivity.class));
                break;
            case R.id.my_option_like_id:
                startActivity(new Intent(getContext(), Like_me_Activity.class));
                break;
            case R.id.my_option_col_id:
                Intent intent2 = new Intent(getContext(), CollectionActivity.class);
                intent2.putExtra("collect", 2);
                startActivity(intent2);
//                startActivity(new Intent(getContext(), CollectionActivity.class));
                break;
            case R.id.my_option_set_id:
                startActivity(new Intent(getContext(), MineSettingActivity.class));
                break;
            case R.id.my_basic_name_iv_id:
                startActivity(new Intent(getContext(), EditPersonalMessageActivity.class));
                break;
            case R.id.my_basic_fans_tv_id:
                intent3 = new Intent(getContext(), HisFansActivity.class);
                intent3.putExtra("id", "2");
                intent3.putExtra("yo_user_id", user_id);
                startActivity(intent3);
                break;
            case R.id.my_basic_fansnumber_tv_id:
                intent3 = new Intent(getContext(), HisFansActivity.class);
                intent3.putExtra("id", "2");
                intent3.putExtra("yo_user_id", user_id);
                startActivity(intent3);
                break;
            case R.id.my_basic_follow_tv_id:
                intent2 = new Intent(getContext(), MyFollowActivity.class);
                intent2.putExtra("id", "1");
                intent2.putExtra("yo_user_id", user_id);
                startActivity(intent2);
                break;
            case R.id.my_basic_follownumber_tv_id:
                intent2 = new Intent(getContext(), MyFollowActivity.class);
                intent2.putExtra("id", "1");
                intent2.putExtra("yo_user_id", user_id);
                startActivity(intent2);
                break;
        }
    }


    @Override
    public void getUserInfoSuccess(MineMessageBean.DataBean data) {
        //获取用户等级
        int user_level = data.getUser_level();

        if (user_level == 1) {
            imgLevel.setVisibility(View.VISIBLE);
            imgVipSign.setImageResource(R.mipmap.level_one);
        } else if (user_level == 2) {
            imgLevel.setVisibility(View.VISIBLE);
            imgLevel.setImageResource(R.mipmap.lv2);
            imgVipSign.setImageResource(R.mipmap.level_two);
        } else if (user_level == 3) {
            imgLevel.setVisibility(View.VISIBLE);
            imgLevel.setImageResource(R.mipmap.lv3);
            imgVipSign.setImageResource(R.mipmap.level_three);
        } else if (user_level == 4) {
            imgLevel.setVisibility(View.VISIBLE);
            imgLevel.setImageResource(R.mipmap.lv4);
            imgVipSign.setImageResource(R.mipmap.level_four);
        } else if (user_level == 5) {
            imgLevel.setVisibility(View.VISIBLE);
            imgLevel.setImageResource(R.mipmap.lv5);
            imgVipSign.setImageResource(R.mipmap.level_five);
        } else {
            imgLevel.setVisibility(View.GONE);
            imgVipSign.setImageResource(R.mipmap.level_zero);
        }

        String user_nickname = data.getUser_nickname();
        myBasicNameTvId.setText(user_nickname);
        String user_logo = data.getUser_logo();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.mipmap.default_touxiang).placeholder(R.mipmap.default_touxiang);
        Glide.with(getContext()).load(data.getUser_logo()).apply(requestOptions).into(myBasicHeadimgIvId);

        int count_fans = data.getCount_fans();
        myBasicFansnumberTvId.setText(count_fans + "");
        int count_attention = data.getCount_attention();
        myBasicFollownumberTvId.setText(count_attention + "");
        int clock_days = data.getClock_days();
        myDayTvId.setText("累计打卡" + clock_days + "天");
        String clock_win = data.getClock_win();
        myExceedTvId.setText("超过了全国" + clock_win + "的用户哦！");
        int count_noread = data.getCount_noread();

//       BadgeViewPro bv1 = new BadgeViewPro(getContext());
//        bv1.setStrText(count_noread + "").setMargin(10, 3, 10, 0).setStrSize(10).setTargetView(myMessgeImId);
//        bv1.setStrText(count_noread + "").setMargin(10, 3, 10, 0).setStrSize(10).setTargetView(myMessgeImId);
        int today_isclock = data.getToday_isclock();
        if (today_isclock == 1) {
            myClockButId.setText("打卡成功");
            myClockButId.setClickable(false);
            myClockButId.setTextColor(Color.parseColor("#FA800A"));
            myClockButId.setBackgroundResource(R.drawable.punch_bg);

        } else {
            myClockButId.setVisibility(View.VISIBLE);
        }
        if (count_noread > 0) {
            myMessgeImId.setDotVisibility(true);
            myMessgeImId.setDotText(count_noread);
        } else {
            myMessgeImId.setDotVisibility(false);
        }
    }

    @Override
    public void punchClockSuccess(BaseBean baseBean) {
        mPresenter.getUserInfo(user_id, user_token);
    }
}
