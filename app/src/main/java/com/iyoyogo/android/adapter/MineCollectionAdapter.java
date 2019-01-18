package com.iyoyogo.android.adapter;

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
import com.iyoyogo.android.bean.collection.MineCollectionBean;
import com.iyoyogo.android.ui.mine.collection.DefaultCollectionActivity;
import com.iyoyogo.android.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏
 */
public class MineCollectionAdapter extends RecyclerView.Adapter<MineCollectionAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    List<MineCollectionBean.DataBean.TreeBean> mList;
    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;
    List<String> idList = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public MineCollectionAdapter(Context context, List<MineCollectionBean.DataBean.TreeBean> tree) {
        this.context = context;
        this.mList = tree;
    }

    public void notifyAdapter(List<MineCollectionBean.DataBean.TreeBean> myLiveList, boolean isAdd) {
        if (!isAdd) {
            this.mList = myLiveList;
        } else {
            this.mList.addAll(myLiveList);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.itme_collection, null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position0) {
        final int index = position0;
        final MineCollectionBean.DataBean.TreeBean bean = mList.get(index);
        holder.tv_collection_folder.setText(bean.getName() + "·" + bean.getCount_record());
        List<MineCollectionBean.DataBean.TreeBean.RecordListBean> record_list = bean.getRecord_list();
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override(DensityUtil.dp2px(context, 80), DensityUtil.dp2px(context, 80))
                .placeholder(R.mipmap.default_ic)
                .error(R.mipmap.default_ic)
                .centerCrop();
        if (bean.getCount_record() >= 4) {
            Glide.with(context).load(record_list.get(0).getFile_path()).apply(requestOptions).into(holder.img_one);
            Glide.with(context).load(record_list.get(1).getFile_path()).apply(requestOptions).into(holder.img_two);
            Glide.with(context).load(record_list.get(2).getFile_path()).apply(requestOptions).into(holder.img_three);
            Glide.with(context).load(record_list.get(3).getFile_path()).apply(requestOptions).into(holder.img_four);
        } else if (bean.getCount_record() == 3) {
            Glide.with(context).load(record_list.get(0).getFile_path()).apply(requestOptions).into(holder.img_one);
            Glide.with(context).load(record_list.get(1).getFile_path()).apply(requestOptions).into(holder.img_two);
            Glide.with(context).load(record_list.get(2).getFile_path()).apply(requestOptions).into(holder.img_three);
            holder.img_four.setImageResource(R.mipmap.default_ic);
        } else if (bean.getCount_record() == 2) {
            Glide.with(context).load(record_list.get(0).getFile_path()).apply(requestOptions).into(holder.img_one);
            Glide.with(context).load(record_list.get(1).getFile_path()).apply(requestOptions).into(holder.img_two);
            holder.img_three.setImageResource(R.mipmap.default_ic);
            holder.img_four.setImageResource(R.mipmap.default_ic);
        } else if (bean.getCount_record() == 1) {
            Glide.with(context).load(record_list.get(0).getFile_path()).apply(requestOptions).into(holder.img_one);
            holder.img_two.setImageResource(R.mipmap.default_ic);
            holder.img_three.setImageResource(R.mipmap.default_ic);
            holder.img_four.setImageResource(R.mipmap.default_ic);
        }else{
            holder.img_one.setImageResource(R.mipmap.default_ic);
            holder.img_two.setImageResource(R.mipmap.default_ic);
            holder.img_three.setImageResource(R.mipmap.default_ic);
            holder.img_four.setImageResource(R.mipmap.default_ic);
        }
        holder.itemView.setTag(index);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               click(holder,index);
            }
        });
        if(mEditMode == 1){
            //删除模式
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.img_next.setVisibility(View.GONE);
            holder.img_next.setOnClickListener(null);
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.checkBox.getVisibility() == View.VISIBLE)
                        click(holder,index);
                }
            });
        }else{
            holder.checkBox.setVisibility(View.GONE);
            holder.img_next.setVisibility(View.VISIBLE);
            holder.checkBox.setOnClickListener(null);
            holder.img_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.img_next.getVisibility() == View.VISIBLE)
                        click(holder,index);
                }
            });
        }
        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DefaultCollectionActivity.class);
                intent.putExtra("type", 2);
                String name = mList.get(index).getName();
                int open = mList.get(index).getOpen();
                int folder_id = mList.get(index).getFolder_id();
                intent.putExtra("name", name + "·" + mList.get(index).getCount_record());
                intent.putExtra("title", name);
                intent.putExtra("folder_id", folder_id);
                intent.putExtra("open", open);
                intent.putExtra("user_id", mList.get(index).getUser_id());
                context.startActivity(intent);
            }
        });
    }

    public void click(ViewHolder holder,int position){
        mOnItemClickListener.onItemClickListener(holder.getAdapterPosition(), mList);
        if (mEditMode == MYLIVE_MODE_CHECK) {
            holder.checkBox.setVisibility(View.GONE);
            holder.img_next.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.img_next.setVisibility(View.GONE);
        }
        if (mList.get(position).isSelect() && !idList.contains(mList.get(position).getFolder_id())) {
            holder.checkBox.setImageResource(R.mipmap.zp_xz);
            idList.add(mList.get(position).getFolder_id() + "");
            mList.get(position).setSelect(true);
        } else {
            idList.remove(mList.get(position).getFolder_id() + "");
            holder.checkBox.setImageResource(R.mipmap.pic_wxz);
            mList.get(position).setSelect(false);
        }
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

    public List<String> getIdList() {
        return idList;
    }

    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.setOnClickListener(v, (Integer) v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_collection_folder;
        RelativeLayout next;
        ImageView img_one, img_two, img_three, img_four, checkBox, img_next;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            next = itemView.findViewById(R.id.next);
            img_next = itemView.findViewById(R.id.img_next);
            tv_collection_folder = itemView.findViewById(R.id.tv_collection_folder);
            img_one = itemView.findViewById(R.id.img_one);
            img_two = itemView.findViewById(R.id.img_two);
            img_three = itemView.findViewById(R.id.img_three);
            img_four = itemView.findViewById(R.id.img_four);
            checkBox = itemView.findViewById(R.id.cb);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos, List<MineCollectionBean.DataBean.TreeBean> myLiveList);
    }

    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }
}
