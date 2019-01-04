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
import com.iyoyogo.android.bean.mine.center.YoXiuContentBean;
import com.iyoyogo.android.bean.yoxiu.YouXiuListBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.ui.home.yoxiu.AllCommentActivity;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuDetailActivity;
import com.iyoyogo.android.utils.GlideRoundTransform;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * yo秀用户主页的适配器
 */
public class YoXiuContentAdapter extends RecyclerView.Adapter<YoXiuContentAdapter.ViewHolder> {
    List<YoXiuContentBean.DataBean.ListBean> mList;
    private Context context;
    private int yo_id;

    public YoXiuContentAdapter(Context context, List<YoXiuContentBean.DataBean.ListBean> list) {
        this.context=context;
        this.mList=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.yoxiu_content_recycler, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.tv_yoxiu_desc.setText(mList.get(position).getPosition_name());
        viewHolder.num_like.setText(mList.get(position).getCount_praise() + "");
        viewHolder.user_name.setText(mList.get(position).getUser_nickname());
        viewHolder.comment_all.setText("全部评论(" + mList.get(position).getCount_view() + ")");
        int file_type = mList.get(position).getFile_type();
        if (file_type == 2) {
            Glide.with(context).load(mList.get(position).getUser_logo()).into(viewHolder.user_icon);
            RequestOptions myOptions = new RequestOptions()
                    .centerCrop()
                    .transform(new GlideRoundTransform(context, 8));
            Glide.with(context).load(mList.get(position).getFile_path())
                    .apply(myOptions).into(viewHolder.img_yoxiu);
            viewHolder.img_video.setVisibility(View.VISIBLE);
        } else {
            Glide.with(context).load(mList.get(position).getUser_logo()).into(viewHolder.user_icon);
            RequestOptions myOptions = new RequestOptions()
                    .centerCrop()
                    .transform(new GlideRoundTransform(context, 8));
            Glide.with(context).load(mList.get(position).getFile_path())
                    .apply(myOptions).into(viewHolder.img_yoxiu);
        }

        viewHolder.recycler_comment.setLayoutManager(new LinearLayoutManager(context));
        List<YoXiuContentBean.DataBean.ListBean.CommentListBean> comment_list = mList.get(position).getComment_list();
        YoXiuContentItemAdapter adapter = new YoXiuContentItemAdapter(context, comment_list);
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
        viewHolder.comment_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mList.get(position).getId();
                Intent intent = new Intent(context, AllCommentActivity.class);
                intent.putExtra("yo_id",id);
                context.startActivity(intent);
            }
        });
        if (mList.get(position).getIs_my_like() == 0) {
            viewHolder.img_like.setImageResource(R.mipmap.datu_xihuan);
        } else {
            viewHolder.img_like.setImageResource(R.mipmap.yixihuan_xiangqing);
        }
        viewHolder.img_like.setImageResource(mList.get(position).getIs_my_like() == 0 ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);
        yo_id = mList.get(position).getId();

        viewHolder.img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = SpUtils.getString(context, "user_id", null);
                String user_token = SpUtils.getString(context, "user_token", null);

                String count_praise = mList.get(position).getCount_praise();
                int count_praises = Integer.parseInt(count_praise);
                mList.get(position).setIs_my_like(mList.get(position).getIs_my_like() == 1 ? 0 : 1);
                if (mList.get(position).getIs_my_like() == 1) {
                    count_praises += 1;
                    mList.get(position).setCount_praise(count_praise);
                } else {
                    count_praises -= 1;
                    mList.get(position).setCount_praise(count_praise);

                }
                viewHolder.num_like.setText(count_praises + "");
                viewHolder.img_like.setImageResource(mList.get(position).getIs_my_like() == 0 ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataManager.getFromRemote().praise(user_id, user_token, 0, mList.get(position).getId())
                                .subscribe(new Consumer<BaseBean>() {
                                    @Override
                                    public void accept(BaseBean baseBean) throws Exception {
                                    }
                                });
                    }
                }).start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_yoxiu_desc, num_like, user_name, comment_all;
        ImageView img_yoxiu, img_like, img_video,user_level_img;
        CircleImageView user_icon;
        RecyclerView recycler_comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_yoxiu_desc = itemView.findViewById(R.id.tv_yoxiu_desc);
            user_level_img = itemView.findViewById(R.id.user_level_img);
            num_like = itemView.findViewById(R.id.num_like);
            user_name = itemView.findViewById(R.id.tv_user_name);
            comment_all = itemView.findViewById(R.id.comment_all);
            user_icon = itemView.findViewById(R.id.user_icon);
            img_like = itemView.findViewById(R.id.img_like);
            recycler_comment = itemView.findViewById(R.id.recycler_comment);
            img_yoxiu = itemView.findViewById(R.id.img_yoxiu);
            img_video = itemView.findViewById(R.id.img_video);
        }
    }
}
