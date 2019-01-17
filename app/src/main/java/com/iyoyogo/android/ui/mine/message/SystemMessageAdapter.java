package com.iyoyogo.android.ui.mine.message;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.mine.message.MessageBean;

import java.util.List;

//系统消息
public class SystemMessageAdapter extends BaseQuickAdapter<MessageBean.DataBean.ListBean, BaseViewHolder> {
    public SystemMessageAdapter(int layoutResId, @Nullable List<MessageBean.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean.DataBean.ListBean item) {
        helper.setText(R.id.system_message_content, item.getContent());
        helper.setText(R.id.system_message_time, item.getCreate_time());
        View view = helper.getView(R.id.dot_read);
        int is_read = item.getIs_read();
        if (is_read == 0) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}