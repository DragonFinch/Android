package com.iyoyogo.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

public class YoJiListAdapter extends RecyclerView.Adapter<YoJiListAdapter.ViewHolder> {
    private Context context;
    private List<String> mList;
    private List<Integer>mHeight;
    public YoJiListAdapter(Context context, List<String> mList) {
        this.context = context;
        this.mList = mList;
        mHeight = new ArrayList<>();   //存放每一个item的高度
        for (int i = 0; i < mList.size(); i++) {
//            随机生成高度   400px~900px
            int height = (int) (Math.random() * (DensityUtil.dp2px(context,20)) + DensityUtil.dp2px(context,243));
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
        Glide.with(context).load(R.mipmap.default_ic)
                .apply(requestOptions)
                .into(viewHolder.imageView);
       /* ViewGroup.LayoutParams params=viewHolder.itemView.getLayoutParams();
        params.height=new Random().nextInt(10)*50+50;
        viewHolder.itemView.setLayoutParams(params);*/
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_type,imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_type=itemView.findViewById(R.id.img_type);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}
