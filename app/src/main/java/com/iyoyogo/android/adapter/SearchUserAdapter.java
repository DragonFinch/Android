package com.iyoyogo.android.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.iyoyogo.android.ui.home.yoji.UserHomepageActivity;

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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KeywordBean.DataBean.UserListBean userListBean = mUser.get(position);
        holder.user_nickname.setText(userListBean.getUser_nickname());
        Glide.with(context).load(userListBean.getUser_logo()).into(holder.user_logo);
        holder.user_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context,UserHomepageActivity.class);
                intent.putExtra("yo_user_id",String.valueOf(mUser.get(position).getUser_id()));
                context.startActivity(intent);
            }
        });
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
                    holder.tv_guanzhu.setBackgroundResource(R.drawable.orange_fillet);
                    holder.tv_guanzhu.setTextColor(Color.parseColor("#ffffff"));
                    Log.e("qq12", "keyWordMessage: 11111" + mUser.get(position).getAttention_status());
                }
        if (mUser.get(position).getAttention_status().equals("1")) {
            Log.e("qq", "keyWordMessage:22222 " + mUser.get(position).getAttention_status());
            holder.tv_guanzhu.setText("互相关注");
            holder.tv_guanzhu.setTextColor(Color.parseColor("#888888"));
            holder.tv_guanzhu.setBackgroundResource(R.drawable.orange_fillet_yiguanzhu);
        }
        if (mUser.get(position).getAttention_status().equals("2")) {
            Log.e("qq", "keyWordMessage: " + mUser.get(position).getAttention_status());
            holder.tv_guanzhu.setText("已关注");
            holder.tv_guanzhu.setTextColor(Color.parseColor("#888888"));
            holder.tv_guanzhu.setBackgroundResource(R.drawable.orange_fillet_yiguanzhu);
        }

        holder.medal.setVisibility(View.VISIBLE);
        holder.img_level.setVisibility(View.VISIBLE);
        String partner_type = mUser.get(position).getPartner_type();
        if (partner_type .equals("0") ) {
            mUser.get(position).setPartner_type("0");
            holder.medal.setVisibility(View.INVISIBLE);
        } else if (partner_type .equals("1") ) {
            mUser.get(position).setPartner_type("1");
            holder.medal.setImageResource(R.mipmap.daren);
        } else if (partner_type .equals("2") ) {
            mUser.get(position).setPartner_type("2");
            holder.medal.setImageResource(R.mipmap.hongren);
        } else if (partner_type .equals("3") ) {
            mUser.get(position).setPartner_type("3");
            holder.medal.setImageResource(R.mipmap.kol);
        } else {
            holder.medal.setVisibility(mUser.get(position).getPartner_type() .equals("0")  ? View.INVISIBLE : View.VISIBLE);
        }

        int user_level = mUser.get(position).getUser_level();
        if (user_level == 0) {
            holder.img_level.setVisibility(View.GONE);
        } else if (user_level == 1) {
            mUser.get(position).setUser_level(1);
            holder.img_level.setImageResource(R.mipmap.lv1);

        } else if (user_level == 2) {
            mUser.get(position).setUser_level(2);
            holder.img_level.setImageResource(R.mipmap.lv2);
        } else if (user_level == 3) {
            mUser.get(position).setUser_level(3);
            holder.img_level.setImageResource(R.mipmap.lv3);
        } else if (user_level == 4) {
            mUser.get(position).setUser_level(4);
            holder.img_level.setImageResource(R.mipmap.lv4);
        } else if (user_level == 5) {
            mUser.get(position).setUser_level(5);
            holder.img_level.setImageResource(R.mipmap.lv5);
        } else {
            holder.img_level.setVisibility(View.INVISIBLE);
        }

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
        ImageView user_logo,medal,img_level;

        public ViewHolder(View itemView) {
            super(itemView);
            user_nickname = itemView.findViewById(R.id.user_nickname);
            count_yox = itemView.findViewById(R.id.count_yox);
            count_yoj = itemView.findViewById(R.id.count_yoj);
            tv_guanzhu = itemView.findViewById(R.id.tv_guanzhu);
            user_logo = itemView.findViewById(R.id.user_logo);
            medal = itemView.findViewById(R.id.medal);
            img_level =itemView.findViewById(R.id.user_level_img);
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
