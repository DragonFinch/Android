package com.iyoyogo.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.search.KeywordBean;
import com.iyoyogo.android.ui.home.search.SearchResultActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.ViewHolder>{
    private Context context;
    private List<KeywordBean.DataBean.UserListBean> mUser;
    private List<KeywordBean.DataBean> mData;
    public SearchUserAdapter(SearchResultActivity searchResultActivity, List<KeywordBean.DataBean.UserListBean> user, List<KeywordBean.DataBean> mData) {
        this.context = searchResultActivity;
        this.mUser = user;
        this.mData = mData;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_page_attention,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KeywordBean.DataBean.UserListBean userListBean = mUser.get(position);
        holder.user_nickname.setText(userListBean.getUser_nickname());
        Glide.with(context).load(userListBean.getUser_logo()).into(holder.user_logo);
        holder.tv_guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setOnClickListener != null) {
                    setOnClickListener.setOnClickListener(holder.tv_guanzhu,position);
                }
            }
        });
            holder.count_yoj.setText(userListBean.getCount_yoj()+"");
            holder.count_yox.setText(userListBean.getCount_yox()+"");
                if (mUser.get(position).getAttention_status().equals("0")) {
                    holder.tv_guanzhu.setText("+关注");
                    Log.e("qq12", "keyWordMessage: 11111" + mUser.get(position).getAttention_status());
                }
        if (mUser.get(position).getAttention_status().equals("1")) {
            Log.e("qq", "keyWordMessage:22222 " + mUser.get(position).getAttention_status());
            holder.tv_guanzhu.setText("互相关注");
        }
        if (mUser.get(position).getAttention_status().equals("2")) {
            Log.e("qq", "keyWordMessage: " + mUser.get(position).getAttention_status());
            holder.tv_guanzhu.setText("已关注");
        }
    /*            holder.tv_guanzhu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                                Toast.makeText(context,"请在用户列表选择关注",Toast.LENGTH_SHORT).show();
                    }
                });*/

    }

    /* @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder  = null;
            if (viewType == 1){
                View view = LayoutInflater.from(context).inflate(R.layout.home_page_attention,null);
                viewHolder = new ViewHolderOne(view);
            }else if (viewType == 2){
                View view = LayoutInflater.from(context).inflate(R.layout.item_search_recycle_yoxiu,null);
                viewHolder = new ViewHolderTwo(view);
            }else  if (viewType == 3){
             *//*   View view = LayoutInflater.from(context).inflate(R.layout.layout_three,null);
            viewHolder = new ViewHolderThree(view);*//*
        }else{

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderOne){
            ViewHolderOne holderOne = (ViewHolderOne) holder;
            KeywordBean.DataBean.UserListBean userListBean = mUser.get(position);

            holderOne.user_nickname.setText(userListBean.getUser_nickname());
            Glide.with(context).load(userListBean.getUser_logo()).into(holderOne.user_logo);
            holderOne.count_yoj.setText(userListBean.getCount_yo().getYoj());
            holderOne.count_yox.setText(userListBean.getCount_yo().getYox());
        }
    }*/
    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user_nickname,count_yox,count_yoj,tv_guanzhu;
        ImageView user_logo;

        public ViewHolder(View itemView) {
            super(itemView);
            user_nickname = itemView.findViewById(R.id.user_nickname);
            count_yox = itemView.findViewById(R.id.count_yox);
            count_yoj = itemView.findViewById(R.id.count_yoj);
            tv_guanzhu = itemView.findViewById(R.id.tv_guanzhu);
            user_logo = itemView.findViewById(R.id.user_logo);
            EventBus.getDefault().post(tv_guanzhu);
        }
    }

    private SetOnClickListener setOnClickListener;

    public void setSetOnClickListener(SetOnClickListener setOnClickListener) {
        this.setOnClickListener = setOnClickListener;
    }

    public interface SetOnClickListener{
        void setOnClickListener(TextView tv_guanzhu, int po);

    }

}
