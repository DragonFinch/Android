package com.iyoyogo.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.collection.CollectionFolderContentBean;

import java.util.List;

public class CollectionFolderContentAdapter extends RecyclerView.Adapter<CollectionFolderContentAdapter.ViewHolder> {
    private Context context;
    List<CollectionFolderContentBean.DataBean.ListBean> mList;

    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;

    private int secret = 0;
    private String title = "";

    private OnItemClickListener mOnItemClickListener;
    public CollectionFolderContentAdapter(Context context, List<CollectionFolderContentBean.DataBean.ListBean> list) {
        this.context = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_folder_content, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CollectionFolderContentBean.DataBean.ListBean listBean = mList.get(position);
        Glide.with(context).load(mList.get(position).getFile_path()).into(holder.img);
        if (mEditMode == MYLIVE_MODE_CHECK) {
            holder.checkBox.setVisibility(View.GONE);
        } else {
            holder.checkBox.setVisibility(View.VISIBLE);

            if (listBean.isSelect()) {
                holder.checkBox.setImageResource(R.mipmap.zp_xz);
            } else {
                holder.checkBox.setImageResource(R.mipmap.pic_wxz);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(holder.getAdapterPosition(), mList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView checkBox;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkBox);
            img=itemView.findViewById(R.id.img);
        }
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClickListener(int pos,List<CollectionFolderContentBean.DataBean.ListBean> myLiveList);
    }
    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }
}
