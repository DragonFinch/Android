package com.iyoyogo.android.ui.mine.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.iyoyogo.android.R;
import com.iyoyogo.android.utils.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Message_center_Activity extends AppCompatActivity {

    @BindView(R.id.message_center_back_im_id)
    ImageView messageCenterBackImId;
    @BindView(R.id.message_center_system_message_im_id)
    ImageView messageCenterSystemMessageImId;
    @BindView(R.id.message_center_system_message_rl_id)
    RelativeLayout messageCenterSystemMessageRlId;
    @BindView(R.id.message_center_like_me_im_id)
    ImageView messageCenterLikeMeImId;
    @BindView(R.id.message_center_like_me_rl_id)
    RelativeLayout messageCenterLikeMeRlId;
    @BindView(R.id.message_center_commentary_message_iv_id)
    ImageView messageCenterCommentaryMessageIvId;
    @BindView(R.id.message_center_commentary_message_rl_id)
    RelativeLayout messageCenterCommentaryMessageRlId;
    @BindView(R.id.message_center_focus_on_news_im_id)
    ImageView messageCenterFocusOnNewsImId;
    @BindView(R.id.message_center_focus_on_news_rl_id)
    RelativeLayout messageCenterFocusOnNewsRlId;

    private String string;
    private Intent title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);
        StatusBarUtils.setWindowStatusBarColor(Message_center_Activity.this, R.color.white);
        title = new Intent(this, Like_me_Activity.class);

        ButterKnife.bind(this);

    }

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
                title.putExtra("title", string);
                startActivity(title);
                break;
            case R.id.message_center_like_me_rl_id:

                string = "喜欢我的";
                title.putExtra("title", string);
                startActivity(title);
                break;
            case R.id.message_center_commentary_message_rl_id:

                string = "评论消息";
                title.putExtra("title", string);
                startActivity(title);
                break;
            case R.id.message_center_focus_on_news_rl_id:

                string = "关注消息";
                title.putExtra("title", string);
                startActivity(title);
                break;
        }
    }
}
