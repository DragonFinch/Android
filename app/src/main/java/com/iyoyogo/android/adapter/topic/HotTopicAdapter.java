package com.iyoyogo.android.adapter.topic;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;
import com.iyoyogo.android.utils.KeyWordUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 热门话题适配器
 */
public class HotTopicAdapter extends RecyclerView.Adapter<HotTopicAdapter.Holder> implements View.OnClickListener {
    private Context context;
    private List<HotTopicBean.DataBean.ListBean> mList;
    private String text;
    List<Integer> integerList = new ArrayList<>();

    public void setText(String text) {
        this.text = text;
    }

    public HotTopicAdapter(Context context, List<HotTopicBean.DataBean.ListBean> mList) {
        this.context = context;
        this.mList = mList;
        init();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_topic_more, null);
        Holder holder = new Holder(view);
        view.setOnClickListener(this);
        return holder;
    }

    private void init() {
        for (int i = 2; i < mList.size() + 3; i++) {
            integerList.add(i);

        }
    }

    private SpannableString matcherSearchText(int color, String text, String keyword) {
        SpannableString spannableString = new SpannableString(text);
        //条件 keyword
        Pattern pattern = Pattern.compile(keyword);
        //匹配
        Matcher matcher = pattern.matcher(spannableString);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            //ForegroundColorSpan 需要new 不然也只能是部分变色
            spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        //返回变色处理的结果
        return spannableString;
    }

    public void setSearchContent(String searchContent) {
        this.text = searchContent;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        KeyWordUtil keyWordUtil = new KeyWordUtil();
        if (position == 0) {
            if (text != null) {

                SpannableString number = keyWordUtil.matcherSearchTitle(Color.parseColor("#ff9314"), mList.get(position).getTopic() + "", text);

                holder.tv_message_one.setText(number);
            } else {
                holder.tv_message_one.setText(mList.get(position).getTopic());

            }
            holder.relative_normal.setVisibility(View.GONE);
            holder.relative_one.setVisibility(View.VISIBLE);
            holder.img_one.setImageResource(R.mipmap.one);
        } else if (position == 1) {
            if (text != null) {
                SpannableString number = keyWordUtil.matcherSearchTitle(Color.parseColor("#ff9314"), mList.get(position).getTopic() + "", text);

                holder.tv_message_one.setText(number);
            } else {
                holder.tv_message_one.setText(mList.get(position).getTopic());

            }
            holder.relative_normal.setVisibility(View.GONE);
            holder.relative_one.setVisibility(View.VISIBLE);
            holder.img_one.setImageResource(R.mipmap.two);
            holder.tv_message_one.setText(mList.get(position).getTopic());
        } else if (position == 2) {
            if (text != null) {
                SpannableString number = keyWordUtil.matcherSearchTitle(Color.parseColor("#ff9314"), mList.get(position).getTopic() + "", text);

                holder.tv_message_one.setText(number);
            } else {
                holder.tv_message_one.setText(mList.get(position).getTopic());

            }
            holder.relative_normal.setVisibility(View.GONE);
            holder.relative_one.setVisibility(View.VISIBLE);
            holder.img_one.setImageResource(R.mipmap.three);

        } else {


            for (Integer integer : integerList) {

                Log.e("HotTopicAdapter", "integer:" + integer);
            }
            Log.d("HotTopicAdapter", "integerList.size():" + integerList.size());
            Log.d("HotTopicAdapter", integerList.get(position) + "");
            if (text != null) {
                SpannableString number = keyWordUtil.matcherSearchTitle(Color.parseColor("#ff9314"), mList.get(position).getTopic() + "", text);

                holder.tv_normal_message.setText(number);
            } else {
                holder.tv_normal_message.setText(mList.get(position).getTopic());

            }
            holder.tv_normal.setText(integerList.get(position - 1) + "");
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnClickListener {
        void setOnClickListener(View view, int position);
    }

    private OnClickListener onClickListener;

    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.setOnClickListener(v, (Integer) v.getTag());
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private ImageView img_hot;
        private TextView tv_message_more;
        private RelativeLayout relative_more;
        private ImageView img_one;
        private TextView tv_message_one;
        private RelativeLayout relative_one;
        private TextView tv_normal;
        private TextView tv_normal_message;
        private RelativeLayout relative_normal;

        public Holder(@NonNull View itemView) {
            super(itemView);
            img_hot = itemView.findViewById(R.id.img_hot);
            tv_message_more = itemView.findViewById(R.id.tv_message_more);
            relative_more = itemView.findViewById(R.id.relative_more);
            img_one = itemView.findViewById(R.id.img_one);
            tv_message_one = itemView.findViewById(R.id.tv_message_one);
            relative_one = itemView.findViewById(R.id.relative_one);
            tv_normal = itemView.findViewById(R.id.tv_normal);
            tv_normal_message = itemView.findViewById(R.id.tv_normal_message);
            relative_normal = itemView.findViewById(R.id.relative_normal);
        }
    }
}
