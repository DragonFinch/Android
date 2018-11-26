package com.iyoyogo.android.ui.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.ImageBean;
import com.iyoyogo.android.ItemBean;
import com.iyoyogo.android.R;

import java.util.List;

public class SameParaItemAdapter extends RecyclerView.Adapter<SameParaItemAdapter.Holder> {
    private List<ImageBean> mList;
    private Context context;

    public SameParaItemAdapter(Context context, List<ImageBean> imgList) {
        this.mList = imgList;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_two, viewGroup, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        if (position==0){
            Glide.with(context).load(mList.get(0).getUrl()).into(holder.imageView);
            holder.sign.setImageResource(R.drawable.no2);
        }else if (position==1){
            Glide.with(context).load(mList.get(1).getUrl()).into(holder.imageView);
            holder.sign.setImageResource(R.drawable.no3);
        }
        List<ItemBean> imgList = mList.get(0).getImgList();
//        holder.gridView.setLayoutManager(new GridLayoutManager(context,3));
        SameParaElseAdapter samePAraElseAdapter = new SameParaElseAdapter(context, imgList);
        holder.gridView.setAdapter(samePAraElseAdapter);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView,sign;
        GridView gridView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_same);
            gridView = itemView.findViewById(R.id.gv);
            sign=itemView.findViewById(R.id.sign);
        }
    }
}
