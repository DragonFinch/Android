package com.iyoyogo.android.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;

import java.util.List;

/**
 * 选择频道之后返回的数据的适配器
 */
public class ChannelMessageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ChannelMessageAdapter(@Nullable List<String> data) {
        super(R.layout.item_channel, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_channel, item);
    }
}
