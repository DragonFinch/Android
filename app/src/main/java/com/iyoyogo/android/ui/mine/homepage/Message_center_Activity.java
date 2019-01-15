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
import com.iyoyogo.android.utils.icondottextview.IconDotTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 消息中心
 */
public class Message_center_Activity extends BaseActivity<MessageCenterContract.Presenter> implements MessageCenterContract.View {


    @BindView(R.id.message_center_back_im_id)
    ImageView messageCenterBackImId;
    @BindView(R.id.message_system)
    IconDotTextView messageSystem;
    @BindView(R.id.message_center_system_message_rl_id)
    RelativeLayout messageCenterSystemMessageRlId;
    @BindView(R.id.like_message)
    IconDotTextView likeMessage;
    @BindView(R.id.message_center_like_me_rl_id)
    RelativeLayout messageCenterLikeMeRlId;
    @BindView(R.id.discuss_message)
    IconDotTextView discussMessage;
    @BindView(R.id.message_center_commentary_message_rl_id)
    RelativeLayout messageCenterCommentaryMessageRlId;
    @BindView(R.id.attention_message)
    IconDotTextView attentionMessage;
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
//        statusbar();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
        user_token = SpUtils.getString(getApplicationContext(), "user_token", null);
        mPresenter.getMessageCenter(user_id, user_token);


    }

    @Override
    protected void onResume() {
        super.onResume();
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
        int type_system = Integer.parseInt(type1);
        int type_like = Integer.parseInt(type2);
        int type_discuss = Integer.parseInt(type3);
        int type_attention = Integer.parseInt(type4);
        if (type_system > 0) {
            messageSystem.setDotText(type_system);
            messageSystem.setDotVisibility(true);
        } else {
            messageSystem.setDotVisibility(false);
        }
        if (type_like > 0) {
            likeMessage.setDotText(type_like);
            likeMessage.setDotVisibility(true);
        } else {
            likeMessage.setDotVisibility(false);
        }
        if (type_discuss > 0) {
            discussMessage.setDotText(type_discuss);
            discussMessage.setDotVisibility(true);
        } else {
            discussMessage.setDotVisibility(false);
        }
        if (type_attention > 0) {
            attentionMessage.setDotText(type_attention);
            attentionMessage.setDotVisibility(true);
        } else {
            attentionMessage.setDotVisibility(false);
        }
      /*  BadgeViewPro bv1 = new BadgeViewPro(Message_center_Activity.this);
        BadgeViewPro bv2 = new BadgeViewPro(Message_center_Activity.this);
        BadgeViewPro bv3 = new BadgeViewPro(Message_center_Activity.this);
        BadgeViewPro bv4 = new BadgeViewPro(Message_center_Activity.this);
        bv1.setStrText(type1).setMargin(10, 3, 10, 0).setStrSize(10).setTargetView(messageSystem);
        bv2.setStrText(type2).setMargin(10, 3, 10, 0).setStrSize(10).setTargetView(likeMessage);
        bv3.setStrText(type3).setMargin(10, 3, 10, 0).setStrSize(10).setTargetView(discussMessage);
        bv4.setStrText(type4).setMargin(10, 3, 10, 0).setStrSize(10).setTargetView(attentionMessage);*/

    }


}
