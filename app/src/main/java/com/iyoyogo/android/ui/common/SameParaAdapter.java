package com.iyoyogo.android.ui.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.Bean;
import com.iyoyogo.android.ImageBean;
import com.iyoyogo.android.R;

import java.util.ArrayList;
import java.util.List;

public class SameParaAdapter extends RecyclerView.Adapter<SameParaAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<Bean> mList;
    private Context context;


    public SameParaAdapter(Context context, ArrayList<Bean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String s = mList.get(0).getImgs();

            Glide.with(context).load(s).into(viewHolder.img);
        List<ImageBean> imageList = mList.get(0).getImageList();
        Log.d("SameParaAdapter", "imageList.size():" + imageList.size());
        SameParaItemAdapter sameParaItemAdapter = new SameParaItemAdapter(context, imageList);
        viewHolder.recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        viewHolder.recyclerView.setAdapter(sameParaItemAdapter);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.onClicks((Integer) v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_same);
            recyclerView = itemView.findViewById(R.id.recycler_same_para_item);
        }
    }

    interface OnClickListener {
        void onClicks(int position);
    }

    private OnClickListener onClickListener;

    public void setOnItemClickListener(OnClickListener onItemClickListener) {
        this.onClickListener = onItemClickListener;
    }
}
