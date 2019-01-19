package com.iyoyogo.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.viewholder.OnItemClickLitener;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;

import java.util.List;

/**
 * 移动收藏夹内容的适配器
 */
public class DefaultCollectionAdapter extends RecyclerView.Adapter<DefaultCollectionAdapter.ViewHolder> {
    private Context context;
    private int selected = -1;

    List<CollectionFolderBean.DataBean.ListBean> mList;
    private OnItemClickLitener mOnItemClickLitener;
    private String title;
    public DefaultCollectionAdapter(Context context, List<CollectionFolderBean.DataBean.ListBean> list, String name) {
        this.mList = list;
        this.context = context;
        this.title=name;
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public void setSelection(int position) {
        this.selected = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_collection_move, null);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String name = mList.get(position).getName();
        Log.d("DefaultCollectionAdaptr", title);
        if (name.equals(title)){
            viewHolder.mTvName.setVisibility(View.GONE);
            viewHolder.mCheckBox.setVisibility(View.GONE);
        }
        viewHolder.mTvName.setText(name);
        if (selected == position) {
            viewHolder.mCheckBox.setChecked(true);
            viewHolder.itemView.setSelected(true);
        } else {
            viewHolder.mCheckBox.setChecked(false);
            viewHolder.itemView.setSelected(false);
        }
        if (mOnItemClickLitener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewHolder.mCheckBox.isChecked()){
                        viewHolder.mCheckBox.setChecked(false);
                        viewHolder.itemView.setSelected(false);
                    }else{
                        viewHolder.mCheckBox.setChecked(true);
                        viewHolder.itemView.setSelected(true);
                    }
                    mOnItemClickLitener.onItemClick(viewHolder.itemView, viewHolder.getAdapterPosition());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvName;
        CheckBox mCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tv_collection_fold);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.cb);
        }
    }
}
