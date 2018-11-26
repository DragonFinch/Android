package com.iyoyogo.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.TestBean;

import com.iyoyogo.android.utils.RoundTransform;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.Holder> {
private List<TestBean.ResultBean.DataBean> mList;
private Context context;

    public TestAdapter(List<TestBean.ResultBean.DataBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.youxiu_item_recycler, viewGroup, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.tv_addr_cover.setText(mList.get(position).getTitle());


//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new RoundTransform(context,8));
        Glide.with(context).load(mList.get(position).getThumbnail_pic_s())
                .apply(requestOptions)
                .into(holder.imageView);

        if (mList.get(position).getThumbnail_pic_s().endsWith("jpg")){
            holder.img_video.setVisibility(View.GONE);
        }
        holder.tv_addr_cover.setText(mList.get(position).getAuthor_name());
        if (position==0){
            holder.typeImageView.setImageResource(R.mipmap.jingxuan);
        }else if (position==1){
            holder.typeImageView.setImageResource(R.mipmap.youzhi);
        }else {
            holder.typeImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_addr_cover,user_name;
        ImageView img_video,imageView,typeImageView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_addr_cover=itemView.findViewById(R.id.tv_addr_cover);
            user_name=itemView.findViewById(R.id.user_name);
            img_video=itemView.findViewById(R.id.iv_video);
            imageView=itemView.findViewById(R.id.imageView);
            typeImageView=itemView.findViewById(R.id.img_type);
        }
    }
}
