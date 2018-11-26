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
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.yoxiu.TypeBean;

import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<TypeBean.DataBean> mList;
    public static final int ONE = 0;
    public static final int TWO = 1;


    public TypeAdapter(Context context, List<TypeBean.DataBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == ONE) {
            view = LayoutInflater.from(context).inflate(R.layout.item_recycler_left, viewGroup, false);
            view.setOnClickListener(this);
            return new ViewHolderOne(view);
        } else if (viewType == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.item_recycler_right, viewGroup, false);
            view.setOnClickListener(this);
            return new ViewHolderTwo(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolderOne) {
            Glide.with(context).load(mList.get(position).getList().get(position).getLogo()).into( ((ViewHolderOne) viewHolder).img_type);
            ((ViewHolderOne) viewHolder).tv_type.setText(mList.get(position).getList().get(position).getName());

        } else if (viewHolder instanceof ViewHolderTwo) {
            Glide.with(context).load(mList.get(position).getList().get(position).getLogo()).into( ((ViewHolderTwo) viewHolder).img_type);
            ((ViewHolderTwo) viewHolder).tv_type.setText(mList.get(position).getList().get(position).getName());
        }
        viewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position %2== 0) {
            return ONE;
        } else  {
            return TWO;
        }

    }
    public interface OnItemClickListener {
        void setOnItemClickListener( View v,int position);
    }
    private OnItemClickListener onItemClickListener;
    @Override
    public void onClick(View v) {
        if (onItemClickListener!=null){
            onItemClickListener.setOnItemClickListener(v, (Integer) v.getTag());
        }
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public class ViewHolderOne extends RecyclerView.ViewHolder {
        ImageView img_type;
        TextView tv_type;
        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            img_type=itemView.findViewById(R.id.img_type);
            tv_type=itemView.findViewById(R.id.tv_type);
        }
    }

    public class ViewHolderTwo extends RecyclerView.ViewHolder {
        ImageView img_type;
        TextView tv_type;
        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);
            img_type=itemView.findViewById(R.id.img_type_right);
            tv_type=itemView.findViewById(R.id.tv_type_right);
        }
    }
}
