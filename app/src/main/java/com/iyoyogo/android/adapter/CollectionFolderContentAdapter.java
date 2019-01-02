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

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏夹内容的适配器
 */
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

    List<String> idList = new ArrayList<>();

    /**
     * 刷新适配器
     * @param myLiveList
     * @param isAdd
     */
    public void notifyAdapter(List<CollectionFolderContentBean.DataBean.ListBean> myLiveList, boolean isAdd) {
        if (!isAdd) {
            this.mList = myLiveList;
        } else {
            this.mList.addAll(myLiveList);
        }
        notifyDataSetChanged();
    }

    public List<CollectionFolderContentBean.DataBean.ListBean> getMyLiveList() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        return mList;
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
        //获取文件路径
        Glide.with(context).load(mList.get(position).getFile_path()).into(holder.img);
        if (mEditMode == MYLIVE_MODE_CHECK) {
            holder.checkBox.setVisibility(View.GONE);

        } else {
            holder.checkBox.setVisibility(View.VISIBLE);
                //判断是否选中以及集合中是否包含，如果包含就删除，不包含，则添加
            if (mList.get(position).isSelect()&&!idList.contains(mList.get(position).getRecord_id())) {
                holder.checkBox.setImageResource(R.mipmap.zp_xz);
                idList.add(mList.get(position).getRecord_id()+"");
                mList.get(position).setSelect(true);


            } else {
                idList.remove(mList.get(position).getRecord_id()+"");
                holder.checkBox.setImageResource(R.mipmap.pic_wxz);
                mList.get(position).setSelect(false);

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

    public List<String> getIdList() {
        return idList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView checkBox;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            img = itemView.findViewById(R.id.img);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos, List<CollectionFolderContentBean.DataBean.ListBean> myLiveList);
    }

    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }
}
