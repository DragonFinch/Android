package com.iyoyogo.android.adapter;

import android.content.Context;
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
import com.iyoyogo.android.bean.yoji.list.YoJiListBean;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.GlideRoundTransform;
import com.iyoyogo.android.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class YoJiListAdapter extends RecyclerView.Adapter<YoJiListAdapter.ViewHolder> {
    private Context context;
    List<YoJiListBean.DataBean.ListBean> mList;
    private List<Integer> mHeight;

    public YoJiListAdapter(Context context, List<YoJiListBean.DataBean.ListBean> mList) {
        this.context = context;
        this.mList = mList;
        mHeight = new ArrayList<>();   //存放每一个item的高度
        for (int i = 0; i < mList.size(); i++) {
//            随机生成高度   400px~900px
            int height = (int) (Math.random() * (DensityUtil.dp2px(context, 20)) + DensityUtil.dp2px(context, 243));
            mHeight.add(height);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.youji_item_recycler, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ViewGroup.LayoutParams params = viewHolder.itemView.getLayoutParams();   //得到布局管理参数
        params.height = mHeight.get(position);
        viewHolder.itemView.setLayoutParams(params);
        RequestOptions requestOptions = new RequestOptions().centerCrop()
                .transform(new GlideRoundTransform(context, 8));
        requestOptions.placeholder(R.mipmap.default_ic);
        requestOptions.error(R.mipmap.default_ic);
        Glide.with(context).load(mList.get(position).getFile_path())
                .apply(requestOptions)
                .into(viewHolder.imageView);
        viewHolder.num_like.setText(mList.get(position).getCount_praise() + "");
        viewHolder.user_name.setText(mList.get(position).getUser_info().getUser_nickname());
        viewHolder.num_see.setText(mList.get(position).getCount_view()+"");
        Glide.with(context).load(mList.get(position).getUser_info().getUser_logo()).into(viewHolder.user_icon);
        viewHolder.tv_title.setText(mList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_type, imageView, iv_like;
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
        }
    }
}
