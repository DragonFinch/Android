package com.iyoyogo.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.yoxiu.YouXiuListBean;

import java.util.List;

/**
 * yo秀列表评论的适配器
 */
public class YoXiuListItemAdapter extends RecyclerView.Adapter<YoXiuListItemAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    List<YouXiuListBean.DataBean.ListBean.CommentListBean> mList;
    private OnClickListener onClickListener;

    public YoXiuListItemAdapter(Context context, List<YouXiuListBean.DataBean.ListBean.CommentListBean> comment_list) {
        this.context = context;
        this.mList = comment_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_layout, null);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        SpannableString msp1 = new SpannableString(mList.get(position).getUser_nickname() + " : " + mList.get(position).getContent());
        msp1.setSpan(new ForegroundColorSpan(Color.parseColor("#FA800A")), 0, mList.get(position).getUser_nickname().length() + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色为洋红色
        viewHolder.tv_content.setText(msp1);
        viewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnClickListener {
        void setOnClickListener(View v, int position);
    }

    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.setOnClickListener(v, (Integer) v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }
}
