package com.iyoyogo.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iyoyogo.android.R;

import java.util.List;

public class YoJiListAdapter extends RecyclerView.Adapter<YoJiListAdapter.ViewHolder> {
    private Context context;
    private List<String> mList;

    public YoJiListAdapter(Context context, List<String> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.youxiu_item_recycler, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
       /* ViewGroup.LayoutParams params=viewHolder.itemView.getLayoutParams();
        params.height=new Random().nextInt(10)*50+50;
        viewHolder.itemView.setLayoutParams(params);*/
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_type;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_type=itemView.findViewById(R.id.img_type);
        }
    }
}
