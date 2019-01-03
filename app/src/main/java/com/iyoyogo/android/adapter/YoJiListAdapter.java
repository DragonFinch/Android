package com.iyoyogo.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.yoji.list.YoJiListBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.GlideRoundTransform;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;


/**
 * yo记列表的适配器
 */
public class YoJiListAdapter extends RecyclerView.Adapter<YoJiListAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    List<YoJiListBean.DataBean.ListBean> mList;
    private List<Integer> mHeight;
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示
    private Activity activity;
    private int yo_id;

    public YoJiListAdapter(Context context, List<YoJiListBean.DataBean.ListBean> mList) {
        this.context = context;
        this.mList = mList;
        mHeight = new ArrayList<>();   //存放每一个item的高度
        for (int i = 0; i < mList.size(); i++) {
//            随机生成高度   400px~900px
            int height = (int) (Math.random() * (DensityUtil.dp2px(context, 20)) + DensityUtil.dp2px(context, 243));
            mHeight.add(height);
        }
        activity = (Activity) context;
    }

    // 暴露接口，改变fadeTips的方法
    public boolean isFadeTips() {
        return fadeTips;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.youji_item_recycler, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
//        ViewGroup.LayoutParams params = viewHolder.itemView.getLayoutParams();   //得到布局管理参数
//        params.height = mHeight.get(position);
//        viewHolder.itemView.setLayoutParams(params);
        RequestOptions requestOptions = new RequestOptions().centerCrop()
                .transform(new GlideRoundTransform(context, 8));
        requestOptions.placeholder(R.mipmap.default_ic);
        requestOptions.error(R.mipmap.default_ic);
        Glide.with(context).load(mList.get(position).getFile_path())
                .apply(requestOptions)
                .into(viewHolder.imageView);
        viewHolder.num_like.setText(mList.get(position).getCount_praise() + "");
        viewHolder.user_name.setText(mList.get(position).getUser_info().getUser_nickname());
        viewHolder.num_see.setText(mList.get(position).getCount_view() + "");
        Glide.with(context).load(mList.get(position).getUser_info().getUser_logo()).into(viewHolder.user_icon);
        viewHolder.tv_title.setText(mList.get(position).getTitle());
        viewHolder.user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mList.get(position).getYo_id();
                Intent intent = new Intent(context, YoJiDetailActivity.class);
                intent.putExtra("yo_id", id);
                context.startActivity(intent);
            }
        });
        viewHolder.more_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mList.get(position).getYo_id();
                Intent intent = new Intent(context, YoJiDetailActivity.class);
                intent.putExtra("yo_id", id);
                context.startActivity(intent);
            }
        });
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mList.get(position).getYo_id();
                Intent intent = new Intent(context, YoJiDetailActivity.class);
                intent.putExtra("yo_id", id);
                context.startActivity(intent);
            }
        });
        viewHolder.index_look_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mList.get(position).getYo_id();
                Intent intent = new Intent(context, YoJiDetailActivity.class);
                intent.putExtra("yo_id", id);
                context.startActivity(intent);
            }
        });
        //点赞
        if (mList.get(position).getIs_my_praise() == 0) {
            viewHolder.iv_like.setImageResource(R.mipmap.datu_xihuan);
        } else {
            viewHolder.iv_like.setImageResource(R.mipmap.yixihuan_xiangqing);
        }
        viewHolder.iv_like.setImageResource(mList.get(position).getIs_my_praise() == 0 ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);
        yo_id = mList.get(position).getYo_id();
        viewHolder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = SpUtils.getString(context, "user_id", null);
                String user_token = SpUtils.getString(context, "user_token", null);

                int count_praise = mList.get(position).getCount_praise();
                mList.get(position).setIs_my_praise(mList.get(position).getIs_my_praise() == 1 ? 0 : 1);
                if (mList.get(position).getIs_my_praise() == 1) {
                    count_praise += 1;
                    mList.get(position).setCount_praise(count_praise);
                } else if (count_praise > 0) {
                    count_praise -= 1;
                    mList.get(position).setCount_praise(count_praise);

                }
                viewHolder.num_like.setText(count_praise + "");
                viewHolder.iv_like.setImageResource(mList.get(position).getIs_my_praise() == 0 ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataManager.getFromRemote().praise(user_id, user_token, 0, mList.get(position).getYo_id())
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

    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.setOnClickListener(v, (Integer) v.getTag());
        }
    }

    public interface OnClickListener {
        void setOnClickListener(View v, int position);
    }

    private OnClickListener onClickListener;

    public void setOnItemClickListener(YoJiListAdapter.OnClickListener onItemClickListener) {
        this.onClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_type, imageView, iv_like, iv_video, more_img, index_look_icon;
        TextView num_like, user_name, tv_title, num_see;
        CircleImageView user_icon;
        RelativeLayout view_like;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view_like = itemView.findViewById(R.id.view_like);
            num_see = itemView.findViewById(R.id.num_see);
            tv_title = itemView.findViewById(R.id.tv_title);
            user_name = itemView.findViewById(R.id.user_name);
            user_icon = itemView.findViewById(R.id.user_icon);
            img_type = itemView.findViewById(R.id.img_type);
            iv_like = itemView.findViewById(R.id.iv_like);
            imageView = itemView.findViewById(R.id.imageView);
            num_like = itemView.findViewById(R.id.num_like);
            iv_video = itemView.findViewById(R.id.iv_video);
            more_img = itemView.findViewById(R.id.more_img);
            index_look_icon = itemView.findViewById(R.id.index_look_icon);
        }
    }

}
