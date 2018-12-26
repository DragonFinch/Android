package com.iyoyogo.android.ui.mine.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.mine.message.MessageCenterBean;
import com.iyoyogo.android.contract.MessageCenterContract;
import com.iyoyogo.android.presenter.MessageCenterPresenter;
import com.iyoyogo.android.ui.mine.message.MessageDetailActivity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;
import com.iyoyogo.android.widget.BadgeButton;

import butterknife.BindView;
import butterknife.OnClick;


public class Message_center_Activity extends BaseActivity<MessageCenterContract.Presenter>implements MessageCenterContract.View {


    @BindView(R.id.message_center_back_im_id)
    ImageView messageCenterBackImId;
    @BindView(R.id.message_center_system_message_im_id)
    BadgeButton messageCenterSystemMessageImId;
    @BindView(R.id.message_center_system_message_rl_id)
    RelativeLayout messageCenterSystemMessageRlId;
    @BindView(R.id.message_center_like_me_im_id)
    BadgeButton messageCenterLikeMeImId;
    @BindView(R.id.message_center_like_me_rl_id)
    RelativeLayout messageCenterLikeMeRlId;
    @BindView(R.id.message_center_commentary_message_iv_id)
    BadgeButton messageCenterCommentaryMessageIvId;
    @BindView(R.id.message_center_commentary_message_rl_id)
    RelativeLayout messageCenterCommentaryMessageRlId;
    @BindView(R.id.message_center_focus_on_news_im_id)
    BadgeButton messageCenterFocusOnNewsImId;
    @BindView(R.id.message_center_focus_on_news_rl_id)
    RelativeLayout messageCenterFocusOnNewsRlId;
    private String string;
    private Intent intent;
    private String user_id;
    private String user_token;


    @OnClick({R.id.message_center_back_im_id,
            R.id.message_center_system_message_rl_id,
            R.id.message_center_like_me_rl_id,
            R.id.message_center_commentary_message_rl_id,
            R.id.message_center_focus_on_news_rl_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.message_center_back_im_id:
                finish();
                break;
            case R.id.message_center_system_message_rl_id:

                string = "系统消息";
                intent.putExtra("title", string);
                startActivity(intent);
                break;
            case R.id.message_center_like_me_rl_id:

                string = "喜欢我的";
                intent.putExtra("title", string);
                startActivity(intent);
                break;
            case R.id.message_center_commentary_message_rl_id:

                string = "评论消息";
                intent.putExtra("title", string);
                startActivity(intent);
                break;
            case R.id.message_center_focus_on_news_rl_id:

                string = "关注消息";
                intent.putExtra("title", string);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtils.setWindowStatusBarColor(Message_center_Activity.this, R.color.white);
        intent = new Intent(this, MessageDetailActivity.class);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
        user_token = SpUtils.getString(getApplicationContext(), "user_token", null);
        mPresenter.getMessageCenter(user_id,user_token);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    protected MessageCenterContract.Presenter createPresenter() {
        return new MessageCenterPresenter(this);
    }


    @Override
    public void getMessageCenterSuccess(MessageCenterBean.DataBean data) {
        String type1 = data.getType1();
        String type2 = data.getType2();
        String type3 = data.getType3();
        String type4 = data.getType4();
        if (type1.equals("0")){
            messageCenterSystemMessageImId.setBadgeVisible(false);
        }else if (type2.equals("0")){
            messageCenterSystemMessageImId.setBadgeVisible(false);
        }else if (type3.equals("0")){
            messageCenterCommentaryMessageIvId.setBadgeVisible(false);
        }else if (type4.equals("0")){
            messageCenterFocusOnNewsImId.setBadgeVisible(false);
        }
        messageCenterSystemMessageImId.setBadgeText(type1);
        messageCenterLikeMeImId.setBadgeText(type2);
        messageCenterCommentaryMessageIvId.setBadgeText(type3);
        messageCenterFocusOnNewsImId.setBadgeText(type4);
    }
}
