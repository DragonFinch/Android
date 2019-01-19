package com.iyoyogo.android.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.utils.emoji.ChatEmoji;

import java.util.List;

/**
 * @author zhuhui
 * @date 2019/1/19
 * @description
 */
public class EmojiAdapter extends BaseQuickAdapter<ChatEmoji, BaseViewHolder> {

    public EmojiAdapter(int layoutResId, @Nullable List<ChatEmoji> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatEmoji item) {
        helper.setImageResource(R.id.iv, item.getId());
    }
}
