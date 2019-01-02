package com.iyoyogo.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.mine.message.MessageBean;
import com.iyoyogo.android.ui.mine.homepage.Personal_homepage_Activity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.widget.CircleImageView;

import java.util.List;

/**
 * 消息详情
 */
public class MessageDetailAdapter extends RecyclerView.Adapter<MessageDetailAdapter.ViewHolder> implements View.OnClickListener {
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
        boolean is_reply = mList.get(position).isIs_reply();
        holder.layout.setVisibility(View.VISIBLE);
        if (is_reply) {
            holder.tv_reply.setVisibility(View.VISIBLE);

        } else {
            holder.tv_reply.setVisibility(View.GONE);

        }
        int is_read = mList.get(position).getIs_read();
        if (is_read == 1) {
            holder.dot_read.setVisibility(View.GONE);
        } else {
            holder.dot_read.setVisibility(View.VISIBLE);
        }
        if (mList.get(position).getTitle().equals("")) {
            holder.tv_action.setVisibility(View.GONE);
        } else {
            holder.tv_action.setText(mList.get(position).getTitle());

        }
        if (mList.get(position).getUser_nickname().equals("")) {
            holder.user_nick_name.setVisibility(View.GONE);
        } else {
            holder.user_nick_name.setText(mList.get(position).getUser_nickname());

        }
        if (mList.get(position).getContent().equals("")) {
            holder.tv_content.setVisibility(View.GONE);
        } else {
            holder.tv_content.setText(mList.get(position).getContent());

        }
        if (mList.get(position).getUser_logo().equals("")) {
            holder.user_icon.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(mList.get(position).getUser_logo()).into(holder.user_icon);

        }
        if (mList.get(position).getFile_path().equals("")) {
            holder.item_like_iv_id.setVisibility(View.GONE);
        } else {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.centerCrop();
            requestOptions.override(DensityUtil.dp2px(context,50),DensityUtil.dp2px(context,50));
            Glide.with(context).load(mList.get(position).getFile_path()).apply(requestOptions).into(holder.item_like_iv_id);

        }
        if (mList.get(position).getCreate_time().equals("")) {
            holder.tv_time.setVisibility(View.GONE);
        } else {
            holder.tv_time.setText(mList.get(position).getCreate_time());
        }
        if (mList.get(position).getUser_logo().equals("")&&mList.get(position).getUser_nickname().equals("")&&mList.get(position).getTitle().equals("")&&mList.get(position).isIs_reply()){
            holder.layout.setVisibility(View.GONE);
        }
        holder.user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yo_user_id = mList.get(position).getUser_id();
                Intent intent = new Intent(context, Personal_homepage_Activity.class);
                intent.putExtra("yo_user_id",yo_user_id);
                context.startActivity(intent);
            }
        });
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.setOnClickListener(v, (Integer) v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView user_icon;
        ImageView item_like_iv_id;
        View dot_read;
        LinearLayout layout;
        TextView user_nick_name, tv_action, tv_content, tv_time, tv_reply;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
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

    public interface OnClickListener {
        void setOnClickListener(View v, int position);
    }

    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
