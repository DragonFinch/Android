package com.iyoyogo.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.iyoyogo.android.R;
import com.iyoyogo.android.utils.emoji.ChatEmoji;

import java.util.List;

/**
 * @author zhuhui
 * @date 2019/1/19
 * @description
 */
public class EmojiPageAdapter extends PagerAdapter {

    private List<List<ChatEmoji>> mData;

    private Context mContext;

    private OnEmojiClick mOnEmojiClick;

    public EmojiPageAdapter(Context context, List<List<ChatEmoji>> list, OnEmojiClick onEmojiClick) {
        mData = list;
        mContext = context;
        mOnEmojiClick = onEmojiClick;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        RecyclerView recyclerView = new RecyclerView(mContext);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 7));
        EmojiAdapter adapter = new EmojiAdapter(R.layout.item_emoji, mData.get(position));
        adapter.setOnItemClickListener((adapter1, view, i) -> mOnEmojiClick.onEmojiClick(view, position, i));
        recyclerView.setAdapter(adapter);
        container.addView(recyclerView);
        return recyclerView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public interface OnEmojiClick {
        void onEmojiClick(View v, int pageIndex, int position);
    }
}
