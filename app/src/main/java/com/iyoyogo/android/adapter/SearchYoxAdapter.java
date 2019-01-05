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
import com.iyoyogo.android.bean.search.KeywordBean;
import com.iyoyogo.android.ui.home.search.SearchResultActivity;

import java.util.List;

public class SearchYoxAdapter extends RecyclerView.Adapter<SearchYoxAdapter.ViewHolder>{
    private Context context;
    private List<KeywordBean.DataBean.YoxListBean> mUser;
    private List<KeywordBean.DataBean> mData;

    public SearchYoxAdapter(SearchResultActivity searchResultActivity, List<KeywordBean.DataBean.YoxListBean> myox) {
        this.context = searchResultActivity;
        this.mUser = myox;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_recycle_yoxiu,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KeywordBean.DataBean.YoxListBean userListBean = mUser.get(position);
        Glide.with(context).load(userListBean.getFile_path()).into(holder.user_logo);
        Glide.with(context).load(userListBean.getUser_info().getUser_logo()).into(holder.user_logo_);
        holder.position_name.setText(userListBean.getPosition_name());
        holder.count_view.setText(userListBean.getCount_view()+"");
/*        holder.content.setText(userListBean.getYox_list().get(0).getContent());
        holder.user_nickname.setText(userListBean.getYox_list().get(0).getUser_nickname());
        holder.content_.setText(userListBean.getYox_list().get(1).getContent());
        holder.user_nickname__.setText(userListBean.getYox_list().get(1).getContent());
        holder.content__.setText(userListBean.getYox_list().get(2).getContent());
        holder.user_nickname___.setText(userListBean.getYox_list().get(2).getContent());*/
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView position_name,user_nickname,user_nickname__,user_nickname___,content,content_,content__,count_view;
        ImageView user_logo,user_logo_;

        public ViewHolder(View itemView) {
            super(itemView);

            position_name = itemView.findViewById(R.id.position_name);
            user_nickname__ = itemView.findViewById(R.id.user_nickname__);
            user_nickname___ = itemView.findViewById(R.id.user_nickname__);
            user_nickname = itemView.findViewById(R.id.user_nickname);
            content = itemView.findViewById(R.id.content);
            content__ = itemView.findViewById(R.id.content__);
            content_ = itemView.findViewById(R.id.content_);
            user_logo = itemView.findViewById(R.id.user_logo);
            user_logo_ = itemView.findViewById(R.id.user_logo_);
            count_view = itemView.findViewById(R.id.count_view);
        }
    }


}
