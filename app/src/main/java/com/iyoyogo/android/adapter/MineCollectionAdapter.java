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
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.collection.MineCollectionBean;
import com.iyoyogo.android.utils.DensityUtil;

import java.util.List;

public class MineCollectionAdapter extends RecyclerView.Adapter<MineCollectionAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    List<MineCollectionBean.DataBean.TreeBean> mList;

    public MineCollectionAdapter(Context context, List<MineCollectionBean.DataBean.TreeBean> tree) {
        this.context = context;
        this.mList = tree;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.itme_collection, null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_collection_folder.setText(mList.get(position).getName() + "·" + mList.get(position).getCount_record());
        List<MineCollectionBean.DataBean.TreeBean.RecordListBean> record_list = mList.get(position).getRecord_list();
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override(DensityUtil.dp2px(context, 80), DensityUtil.dp2px(context, 80))
                .placeholder(R.mipmap.default_ic)
                .error(R.mipmap.default_ic)
                .centerCrop();


        if (record_list.size() >= 4) {


            Glide.with(context).load(record_list.get(0).getFile_path()).apply(requestOptions).into(holder.img_one);
            Glide.with(context).load(record_list.get(1).getFile_path()).apply(requestOptions).into(holder.img_two);
            Glide.with(context).load(record_list.get(2).getFile_path()).apply(requestOptions).into(holder.img_three);
            Glide.with(context).load(record_list.get(3).getFile_path()).apply(requestOptions).into(holder.img_four);
        } else if (record_list.size() == 3) {
            Glide.with(context).load(record_list.get(0).getFile_path()).apply(requestOptions).into(holder.img_one);
            Glide.with(context).load(record_list.get(1).getFile_path()).apply(requestOptions).into(holder.img_two);
            Glide.with(context).load(record_list.get(2).getFile_path()).apply(requestOptions).into(holder.img_three);
            holder.img_four.setVisibility(View.INVISIBLE);
        } else if (record_list.size() == 2) {
            Glide.with(context).load(record_list.get(0).getFile_path()).apply(requestOptions).into(holder.img_one);
            Glide.with(context).load(record_list.get(1).getFile_path()).apply(requestOptions).into(holder.img_two);
            holder.img_three.setVisibility(View.INVISIBLE);
            holder.img_four.setVisibility(View.INVISIBLE);
        } else if (record_list.size() == 1) {
            Glide.with(context).load(record_list.get(0).getFile_path()).apply(requestOptions).into(holder.img_one);
            holder.img_two.setVisibility(View.INVISIBLE);
            holder.img_three.setVisibility(View.INVISIBLE);
            holder.img_four.setVisibility(View.INVISIBLE);
        } else {
            holder.img_one.setVisibility(View.INVISIBLE);
            holder.img_two.setVisibility(View.INVISIBLE);
            holder.img_three.setVisibility(View.INVISIBLE);
            holder.img_four.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnClickListener {
        void setOnClickListener(View v, int position);
    }

    private OnClickListener onClickListener;

    public void setOnItemClickListener(OnClickListener onItemClickListener) {
        this.onClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.setOnClickListener(v, (Integer) v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_collection_folder;
        ImageView img_one, img_two, img_three, img_four;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_collection_folder = itemView.findViewById(R.id.tv_collection_folder);
            img_one = itemView.findViewById(R.id.img_one);
            img_two = itemView.findViewById(R.id.img_two);
            img_three = itemView.findViewById(R.id.img_three);
            img_four = itemView.findViewById(R.id.img_four);
        }
    }
}
