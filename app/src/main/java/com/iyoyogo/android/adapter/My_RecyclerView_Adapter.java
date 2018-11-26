package com.iyoyogo.android.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class My_RecyclerView_Adapter extends RecyclerView.Adapter<My_RecyclerView_Adapter.ViewHolder> implements View.OnClickListener {

    private Map<Integer, Boolean> map = new HashMap<>();
    private Context context;
    private List<String> data;
     OnItemClickListener mItemClickListener;

    public My_RecyclerView_Adapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Glide.with(context).load(data.get(position)).into(holder.mImageView);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    map.put(position, true);
                } else {
                    map.remove(position);
                }
            }
        });
        if (map != null && map.containsKey(position)) {
            holder.mCheckBox.setChecked(true);
            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   if (isChecked){

                   }
                }
            });
        } else {
            holder.mCheckBox.setChecked(false);
        }

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private CheckBox mCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_id);
            mCheckBox = itemView.findViewById(R.id.cb_id);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mItemClickListener=onItemClickListener;
    }
}
