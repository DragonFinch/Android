package com.iyoyogo.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.search.KeywordBean;

import java.util.List;

public class SearchYoXiuAdapter extends RecyclerView.Adapter<SearchYoXiuAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    List<KeywordBean.DataBean.YoxListBean.CommentListBean> mList;
    private OnClickListener onClickListener;
    public SearchYoXiuAdapter(Context context, List<KeywordBean.DataBean.YoxListBean.CommentListBean> comment_list) {
        this.context = context;
        this.mList = comment_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_comment_layout, null);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (mList.size() != 0) {
            viewHolder.user_name.setVisibility(View.VISIBLE);
            Log.e("ccccc", "onBindViewHolder: "+mList.get(position).getUser_nickname() );
            Log.e("ccccc", "onBindViewHolder: "+mList.get(position).getContent() );
            viewHolder.tv_content.setText(mList.get(position).getContent() + "");
            viewHolder.user_name.setText(mList.get(position).getUser_nickname() + "  ");

            viewHolder.itemView.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        if (mList.size() <=0){
            return 0;
        }else{
           return mList.size();
        }
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
