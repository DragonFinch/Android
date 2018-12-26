package com.iyoyogo.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.mine.message.MessageBean;
import com.iyoyogo.android.widget.CircleImageView;

import java.util.List;

public class MessageDetailAdapter extends RecyclerView.Adapter<MessageDetailAdapter.ViewHolder> implements View.OnClickListener{
    Context context;
    List<MessageBean.DataBean.ListBean> mList;

    public MessageDetailAdapter(Context context, List<MessageBean.DataBean.ListBean> list) {
        this.context = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public MessageDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_like_recycleview_layout, null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageDetailAdapter.ViewHolder holder, int position) {
        holder.tv_reply.setVisibility(View.GONE);
        int is_read = mList.get(position).getIs_read();
        if (is_read == 1) {
            holder.dot_read.setVisibility(View.GONE);
        } else {
            holder.dot_read.setVisibility(View.VISIBLE);
        }
        holder.tv_action.setText(mList.get(position).getTitle());
        holder.user_nick_name.setText(mList.get(position).getUser_nickname());
        holder.tv_content.setText(mList.get(position).getContent());
        Glide.with(context).load(mList.get(position).getUser_logo()).into(holder.user_icon);
        Glide.with(context).load(mList.get(position).getFile_path()).into(holder.item_like_iv_id);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
            if (onClickListener!=null){
                onClickListener.setOnClickListener(v, (Integer) v.getTag());
            }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView user_icon;
        ImageView item_like_iv_id;
        View dot_read;
        TextView user_nick_name, tv_action, tv_content, tv_time, tv_reply;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dot_read = itemView.findViewById(R.id.dot_read);
            user_icon = itemView.findViewById(R.id.user_icon);
            item_like_iv_id = itemView.findViewById(R.id.item_like_iv_id);
            user_nick_name = itemView.findViewById(R.id.user_nick_name);
            tv_action = itemView.findViewById(R.id.tv_action);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_reply = itemView.findViewById(R.id.tv_reply);
        }
    }
   public interface OnClickListener{
        void setOnClickListener(View v,int position);
    }
    OnClickListener onClickListener;
    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener=onClickListener;
    }
}
