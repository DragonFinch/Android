package com.iyoyogo.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.home.HomeBean;

import java.util.List;

/**
 * yo记首页评论
 */
public class YoJiListItemAdapter extends RecyclerView.Adapter<YoJiListItemAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    List<HomeBean.DataBean.YojListBean.CommentListBean> mList;
    private OnClickListener onClickListener;
    public YoJiListItemAdapter(Context context, List<HomeBean.DataBean.YojListBean.CommentListBean> comment_list) {
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
      /*  SpannableString spannableString = new SpannableString("设置文字的前景色为淡蓝色");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        spannableString.setSpan(colorSpan, 9, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        viewHolder.user_name.setText(spannableString);*/
        viewHolder.user_name.setText(mList.get(position).getUser_nickname());
        viewHolder.tv_content.setText(mList.get(position).getContent());
        viewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public interface OnClickListener{
        void setOnClickListener(View v, int position);
    }
    @Override
    public void onClick(View v) {
        if (onClickListener!=null){
            onClickListener.setOnClickListener(v, (Integer) v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user_name, tv_content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }
}
