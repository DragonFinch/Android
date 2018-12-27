package com.iyoyogo.android.ui.mine.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.MessageDetailAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.mine.message.MessageBean;
import com.iyoyogo.android.bean.mine.message.ReadMessage;
import com.iyoyogo.android.contract.MessageContract;
import com.iyoyogo.android.presenter.MessagePresenter;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.ui.home.yoxiu.SourceChooseActivity;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuDetailActivity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;
import com.iyoyogo.android.utils.imagepicker.activities.ImagesPickActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageDetailActivity extends BaseActivity<MessageContract.Presenter> implements MessageContract.View {


    @BindView(R.id.message_center_back_im_id)
    ImageView messageCenterBackImId;
    @BindView(R.id.like_me_title_tv_id)
    TextView likeMeTitleTvId;
    @BindView(R.id.bar)
    RelativeLayout bar;
    @BindView(R.id.recycler_message)
    RecyclerView recyclerMessage;
    @BindView(R.id.img_stamp)
    ImageView imgStamp;
    @BindView(R.id.tv_stamp_title)
    TextView tvStampTitle;
    @BindView(R.id.publish_yoji)
    TextView publishYoji;
    @BindView(R.id.publish_yoxiu)
    TextView publishYoxiu;
    @BindView(R.id.like_layout)
    LinearLayout likeLayout;
    private String user_id;
    private String user_token;
    private String title;

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        likeMeTitleTvId.setText(title);
        StatusBarUtils.setWindowStatusBarColor(MessageDetailActivity.this, R.color.white);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_message_detail;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
        user_token = SpUtils.getString(getApplicationContext(), "user_token", null);
        if (title.equals("喜欢我的")) {
            mPresenter.getMessage(user_id, user_token, 2, 1);
        } else if (title.equals("系统消息")) {
            mPresenter.getMessage(user_id, user_token, 1, 1);
        } else if (title.equals("评论消息")) {
            mPresenter.getMessage(user_id, user_token, 3, 1);
        } else if (title.equals("关注消息")) {
            mPresenter.getMessage(user_id, user_token, 4, 1);
        }


    }

    @Override
    protected MessageContract.Presenter createPresenter() {
        return new MessagePresenter(this);
    }


    @Override
    public void getMessageSuccess(List<MessageBean.DataBean.ListBean> list) {
        if (list.size() == 0) {
            likeLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerMessage.setLayoutManager(new LinearLayoutManager(MessageDetailActivity.this));
            MessageDetailAdapter messageDetailAdapter = new MessageDetailAdapter(MessageDetailActivity.this, list);
            recyclerMessage.setAdapter(messageDetailAdapter);
            messageDetailAdapter.setOnClickListener(new MessageDetailAdapter.OnClickListener() {
                @Override
                public void setOnClickListener(View v, int position) {
                    if (list.get(position).getYo_id().equals("")) {

                    } else {
                        if (list.get(position).getYo_type().equals("1")) {
                            Intent intent = new Intent(MessageDetailActivity.this, YoXiuDetailActivity.class);
                            intent.putExtra("id", Integer.parseInt(list.get(position).getYo_id()));
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MessageDetailActivity.this, YoJiDetailActivity.class);
                            intent.putExtra("yo_id", Integer.parseInt(list.get(position).getYo_id()));
                            startActivity(intent);
                        }
                    }

                    mPresenter.readMessage(user_id, user_token, String.valueOf(list.get(position).getMessage_id()));
                    messageDetailAdapter.notifyDataSetChanged();
                }
            });
        }

    }

    @Override
    public void readMessageSuccess(ReadMessage.DataBean data) {

        if (title.equals("喜欢我的")) {
            mPresenter.getMessage(user_id, user_token, 2, 1);
        } else if (title.equals("系统消息")) {
            mPresenter.getMessage(user_id, user_token, 1, 1);
        } else if (title.equals("评论消息")) {
            mPresenter.getMessage(user_id, user_token, 3, 1);
        } else if (title.equals("关注消息")) {
            mPresenter.getMessage(user_id, user_token, 4, 1);
        }
    }


    @OnClick({R.id.publish_yoji, R.id.publish_yoxiu, R.id.message_center_back_im_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.publish_yoji:
                startActivity(new Intent(MessageDetailActivity.this, ImagesPickActivity.class));
                break;
            case R.id.publish_yoxiu:
                startActivity(new Intent(MessageDetailActivity.this, SourceChooseActivity.class));
                break;
            case R.id.message_center_back_im_id:
                finish();
                break;
        }
    }
}
