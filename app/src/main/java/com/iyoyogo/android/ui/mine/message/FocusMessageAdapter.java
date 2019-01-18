package com.iyoyogo.android.ui.mine.message;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.mine.message.MessageBean;
import com.iyoyogo.android.bean.mine.message.ReadMessage;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.ui.home.yoji.UserHomepageActivity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

//关注消息
public class FocusMessageAdapter extends BaseQuickAdapter<MessageBean.DataBean.ListBean, BaseViewHolder> {
    public FocusMessageAdapter(int layoutResId, @Nullable List<MessageBean.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean.DataBean.ListBean item) {
        String user_id = SpUtils.getString(mContext, "user_id", null);
        String user_token = SpUtils.getString(mContext, "user_token", null);
        View view = helper.getView(R.id.dot_read);
        int is_read = item.getIs_read();
        if (is_read == 0) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
        helper.setText(R.id.focus_message_user_nick_name, item.getUser_nickname());
        helper.setText(R.id.focus_message_tv_action, item.getTitle());
        helper.setText(R.id.focus_message_tv_time, item.getCreate_time());
        RequestOptions requestOptions1 = new RequestOptions();
        requestOptions1.placeholder(R.mipmap.default_touxiang);
        requestOptions1.error(R.mipmap.default_touxiang);
        Glide.with(mContext).load(item.getUser_logo()).apply(requestOptions1).into((CircleImageView) helper.getView(R.id.focus_message_user_icon));
        CircleImageView user_icon = helper.getView(R.id.focus_message_user_icon);
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getFromRemote().readMessage(user_id, user_token, item.getMessage_id() + "")
                        .subscribe(new Observer<ReadMessage>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ReadMessage readMessage) {
                                int code = readMessage.getCode();
                                if (code == 200) {
                                    view.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                String user_id1 = item.getUser_id();
                if (user_id.equals(user_id1)) {
                } else {
                    Intent intent = new Intent(mContext, UserHomepageActivity.class);
                    intent.putExtra("yo_user_id", item.getUser_id());
                    mContext.startActivity(intent);
                }
            }
        });
    }
}
