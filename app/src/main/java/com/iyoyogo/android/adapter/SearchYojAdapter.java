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

public class SearchYojAdapter extends RecyclerView.Adapter<SearchYojAdapter.ViewHolder>{
    private Context context;
    private List<KeywordBean.DataBean.YojListBean> mUser;
    private List<KeywordBean.DataBean> mData;

    public SearchYojAdapter(SearchResultActivity searchResultActivity, List<KeywordBean.DataBean.YojListBean> myox) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_recycle_yoji,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KeywordBean.DataBean.YojListBean userListBean = mUser.get(position);
        if (userListBean.getUser_info()!=null){
            Glide.with(context).load(userListBean.getUser_info().getUser_logo()).into(holder.user_logo);
        }
        if (userListBean.getUser_info() !=null) {
            holder.user_nickname.setText(userListBean.getUser_info().getUser_nickname());
        }
        if (userListBean.getCount_view() != 0){
            holder.count_view.setText(userListBean.getCount_view()+"");
        }
        Glide.with(context).load(userListBean.getFile_path()).into(holder.file_path);
        Glide.with(context).load(userListBean.getUser_info().getUser_logo()).into(holder.user_logo_);
        holder.content.setText(userListBean.getUser_info().getUser_nickname());
        holder.title.setText(userListBean.getTitle());
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView position_name,user_nickname,user_nickname__,user_nickname___,content,content_,content__,count_view,
                title;
        ImageView user_logo,user_logo_,file_path;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            user_logo = itemView.findViewById(R.id.user_logo);
            user_logo_ = itemView.findViewById(R.id.user_logo_);
            position_name = itemView.findViewById(R.id.position_name);
            user_nickname__ = itemView.findViewById(R.id.user_nickname__);
            user_nickname___ = itemView.findViewById(R.id.user_nickname__);
            user_nickname = itemView.findViewById(R.id.user_nickname);
            content = itemView.findViewById(R.id.content);
            content__ = itemView.findViewById(R.id.content__);
            content_ = itemView.findViewById(R.id.content_);
            user_logo_ = itemView.findViewById(R.id.user_logo_);
            count_view = itemView.findViewById(R.id.count_view);
            file_path = itemView.findViewById(R.id.file_path);
        }
    }


}
