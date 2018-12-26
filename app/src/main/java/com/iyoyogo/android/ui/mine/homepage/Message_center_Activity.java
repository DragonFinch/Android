package com.iyoyogo.android.ui.mine.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Message_center_Activity extends BaseActivity<MessageCenterContract.Presenter> implements MessageCenterContract.View {


    @BindView(R.id.message_center_back_im_id)
    ImageView messageCenterBackImId;
    @BindView(R.id.message_system)
    BadgeButton messageSystem;
    @BindView(R.id.message_center_system_message_rl_id)
    RelativeLayout messageCenterSystemMessageRlId;
    @BindView(R.id.like_message)
    BadgeButton likeMessage;
    @BindView(R.id.message_center_like_me_rl_id)
    RelativeLayout messageCenterLikeMeRlId;
    @BindView(R.id.discuss_message)
    BadgeButton discussMessage;
    @BindView(R.id.message_center_commentary_message_rl_id)
    RelativeLayout messageCenterCommentaryMessageRlId;
    @BindView(R.id.attention_message)
    BadgeButton attentionMessage;
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
                intent = new Intent(this, MessageDetailActivity.class);
                string = "系统消息";
                intent.putExtra("title", string);
                startActivity(intent);
                break;
            case R.id.message_center_like_me_rl_id:
                intent = new Intent(this, MessageDetailActivity.class);
                string = "喜欢我的";
                intent.putExtra("title", string);
                startActivity(intent);
                break;
            case R.id.message_center_commentary_message_rl_id:
                intent = new Intent(this, MessageDetailActivity.class);
                string = "评论消息";
                intent.putExtra("title", string);
                startActivity(intent);
                break;
            case R.id.message_center_focus_on_news_rl_id:
                intent = new Intent(this, MessageDetailActivity.class);
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
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
        user_token = SpUtils.getString(getApplicationContext(), "user_token", null);
        mPresenter.getMessageCenter(user_id, user_token);
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
        Toast.makeText(this, type1, Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                likeMessage.setBadgeText(type2);
                discussMessage.setBadgeText(type3);
                attentionMessage.setBadgeText(type4);
                if (Integer.parseInt(type1) > 0) {
                    messageSystem.setBadgeText(type1);
                    messageSystem.setBadgeVisible(true);
                }
                if (Integer.parseInt(type2) > 0) {
                    likeMessage.setBadgeText(type2);
                    likeMessage.setBadgeVisible(true);
                }
                if (Integer.parseInt(type3) > 0) {
                    discussMessage.setBadgeVisible(true);
                    discussMessage.setBadgeText(type3);
                }
                if (Integer.parseInt(type4) > 0) {
                    attentionMessage.setBadgeVisible(true);
                    attentionMessage.setBadgeText(type3);
                }
            }
        }).start();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
