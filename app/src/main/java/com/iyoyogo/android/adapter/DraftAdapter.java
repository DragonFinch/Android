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
import com.iyoyogo.android.bean.mine.DraftBean;
import com.iyoyogo.android.utils.GlideRoundTransform;

import java.util.List;
//草稿适配器
public class DraftAdapter extends RecyclerView.Adapter<DraftAdapter.ViewHolder> {
    Context context;
    List<DraftBean.DataBean.ListBean> mList;

    public DraftAdapter(Context context, List<DraftBean.DataBean.ListBean> list) {
        this.context = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_draft, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
          RequestOptions myOptions = new RequestOptions()
                          .centerCrop()
                          .transform(new GlideRoundTransform(context,8));
        Glide.with(context).load(mList.get(position).getFile_path()).apply(myOptions).into(viewHolder.img_draft);
        int yo_type = mList.get(position).getYo_type();
        if (yo_type==1){
            viewHolder.img_type.setImageResource(R.mipmap.caogao_yoxiu);
        }else {
            viewHolder.img_type.setImageResource(R.mipmap.caogao_yoji);
        }
        viewHolder.tv_title.setText(mList.get(position).getTitle());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_draft, more, img_type;
        TextView tv_title, tv_commit_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_draft = itemView.findViewById(R.id.img_draft);
            more = itemView.findViewById(R.id.more);
            img_type = itemView.findViewById(R.id.img_type);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_commit_time = itemView.findViewById(R.id.tv_commit_time);
        }
    }
}
