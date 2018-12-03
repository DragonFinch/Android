package com.iyoyogo.android.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.bean.mine.MineMessageBean;
import com.iyoyogo.android.contract.MineContract;
import com.iyoyogo.android.presenter.MineMessagePresenter;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;
import com.iyoyogo.android.widget.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MineFragment extends BaseFragment<MineContract.Presenter> implements MineContract.View {


    @BindView(R.id.my_bgi_iv_id)
    ImageView myBgiIvId;
    @BindView(R.id.my_messge_im_id)
    ImageView myMessgeImId;
    @BindView(R.id.my_dot_v_id)
    View myDotVId;
    @BindView(R.id.my_addfriend_im_id)
    ImageView myAddfriendImId;
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
        StatusBarUtils.setWindowStatusBarColor(getActivity(), R.color.white);
        String user_id = SpUtils.getString(getContext(), "user_id", null);
        String user_token = SpUtils.getString(getContext(), "user_token", null);
        mPresenter.getUserInfo(user_id, user_token);
    }


    @OnClick({R.id.my_basic_name_iv_id, R.id.my_messge_im_id, R.id.my_addfriend_im_id, R.id.my_clock_but_id, R.id.my_option_home_id, R.id.my_option_draft_id, R.id.my_option_like_id, R.id.my_option_col_id, R.id.my_option_set_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_messge_im_id:

                break;
            case R.id.my_addfriend_im_id:

                break;
            case R.id.my_clock_but_id:

                break;
            case R.id.my_option_home_id:

                break;
            case R.id.my_option_draft_id:

                break;
            case R.id.my_option_like_id:

                break;
            case R.id.my_option_col_id:

                break;
            case R.id.my_option_set_id:

                break;
            case R.id.my_basic_name_iv_id:
                startActivity(new Intent(getContext(), EditPersonalMessageActivity.class));
                break;
        }
    }

    @Override
    public void getUserInfoSuccess(MineMessageBean.DataBean data) {
        String user_nickname = data.getUser_nickname();
        myBasicNameTvId.setText(user_nickname);
        String user_logo = data.getUser_logo();
        Glide.with(getContext()).load(user_logo).into(myBasicHeadimgIvId);
        int count_fans = data.getCount_fans();
        myBasicFansnumberTvId.setText(count_fans + "");
        int count_attention = data.getCount_attention();
        myBasicFollownumberTvId.setText(count_attention + "");
        int clock_days = data.getClock_days();
        myDayTvId.setText("累计打卡" + clock_days + "天");
        String clock_win = data.getClock_win();
        myExceedTvId.setText("超过了全国" + clock_win + "的用户哦！");
        int count_noread = data.getCount_noread();
    }
}
