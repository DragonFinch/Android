package com.iyoyogo.android.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.yoxiu.YouXiuListBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.ui.home.recommend.YoXiuDetailActivity;
import com.iyoyogo.android.utils.GlideRoundTransform;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;

import java.util.List;

import io.reactivex.functions.Consumer;

public class YoXiuListAdapter extends RecyclerView.Adapter<YoXiuListAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<YouXiuListBean.DataBean.ListBean> mList;
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示
    public YoXiuListAdapter(Context context, List<YouXiuListBean.DataBean.ListBean> mList) {
        this.context = context;
        this.mList = mList;
    }
    // 暴露接口，改变fadeTips的方法
    public boolean isFadeTips() {
        return fadeTips;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_yoxiu_list, null);
        view.setOnClickListener(this);
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
                .transform(new GlideRoundTransform(context, 8));
        Glide.with(context).load(mList.get(position).getFile_path())
                .apply(myOptions).into(viewHolder.img_yoxiu);
        viewHolder.recycler_comment.setLayoutManager(new LinearLayoutManager(context));
        List<YouXiuListBean.DataBean.ListBean.CommentListBean> comment_list = mList.get(position).getComment_list();
        YoXiuListItemAdapter adapter = new YoXiuListItemAdapter(context, comment_list);
        viewHolder.recycler_comment.setAdapter(adapter);
        viewHolder.itemView.setTag(position);
        viewHolder.recycler_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = mList.get(position).getId();
                Intent intent = new Intent(context, YoXiuDetailActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });
        if (mList.get(position).getIs_my_like()==0){
            viewHolder.img_like.setImageResource(R.mipmap.datu_xihuan);
        }else {
            viewHolder.img_like.setImageResource(R.mipmap.yixihuan);
        }
        viewHolder.img_like.setImageResource(mList.get(position).getIs_my_like() == 0 ? R.mipmap.datu_xihuan : R.mipmap.yixihuan);


        viewHolder.img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = SpUtils.getString(context, "user_id", null);
                String user_token = SpUtils.getString(context, "user_token", null);
                DataManager.getFromRemote().praise(user_id, user_token, mList.get(position).getId(), 0)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                int count_praise = mList.get(position).getCount_praise();
                                mList.get(position).setIs_my_like(mList.get(position).getIs_my_like() == 1 ? 0 : 1);
                                if (mList.get(position).getIs_my_like() == 1) {
                                    count_praise += 1;
                                } else if (count_praise > 0) {
                                    count_praise -= 1;
                                }
                                mList.get(position).setCount_praise(count_praise);
                                notifyItemChanged(position);
                            }
                        });
            }
        });
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

    public interface OnClickListener {
        void setOnClickListener(View v, int position);
    }
    private OnClickListener onClickListener;

    public void setOnItemClickListener(OnClickListener onItemClickListener){
        this.onClickListener=onItemClickListener;
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
