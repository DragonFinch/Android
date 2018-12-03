package com.iyoyogo.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.yoxiu.YouXiuListBean;
import com.iyoyogo.android.utils.GlideRoundTransform;
import com.iyoyogo.android.widget.CircleImageView;

import java.util.List;

public class YoXiuListAdapter extends RecyclerView.Adapter<YoXiuListAdapter.ViewHolder> {
    private Context context;
    private List<YouXiuListBean.DataBean.ListBean> mList;

    public YoXiuListAdapter(Context context, List<YouXiuListBean.DataBean.ListBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_yoxiu_list, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.tv_yoxiu_desc.setText(mList.get(position).getPosition_name());
        viewHolder.num_like.setText(mList.get(position).getCount_praise() + "");
        viewHolder.user_name.setText(mList.get(position).getUser_nickname());
        viewHolder.comment_all.setText("全部评论(" + mList.get(position).getCount_view() + ")");

        Glide.with(context).load(mList.get(position).getUser_logo()).into(viewHolder.user_icon);
        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(context,8));
        Glide.with(context).load(mList.get(position).getFile_path())
                .apply(myOptions).into(viewHolder.img_yoxiu);
        viewHolder.recycler_comment.setLayoutManager(new LinearLayoutManager(context));
        List<YouXiuListBean.DataBean.ListBean.CommentListBean> comment_list = mList.get(position).getComment_list();
        YoXiuListItemAdapter adapter = new YoXiuListItemAdapter(context, comment_list);
        viewHolder.recycler_comment.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_yoxiu_desc, num_like, user_name, comment_all;
        ImageView img_yoxiu, img_like, img_more;
        CircleImageView user_icon;
        RecyclerView recycler_comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_yoxiu_desc = itemView.findViewById(R.id.tv_yoxiu_desc);
            num_like = itemView.findViewById(R.id.num_like);
            img_more = itemView.findViewById(R.id.img_more);
            user_name = itemView.findViewById(R.id.tv_user_name);
            comment_all = itemView.findViewById(R.id.comment_all);
            user_icon = itemView.findViewById(R.id.user_icon);
            img_like = itemView.findViewById(R.id.img_like);
            recycler_comment = itemView.findViewById(R.id.recycler_comment);
            img_yoxiu = itemView.findViewById(R.id.img_yoxiu);
        }
    }
}
