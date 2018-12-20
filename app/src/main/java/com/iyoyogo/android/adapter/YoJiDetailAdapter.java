package com.iyoyogo.android.adapter;

import android.content.Context;
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
import com.iyoyogo.android.bean.yoji.detail.YoJiDetailBean;
import com.iyoyogo.android.utils.RoundTransform;

import java.util.List;

public class YoJiDetailAdapter extends RecyclerView.Adapter<YoJiDetailAdapter.Holder> implements View.OnClickListener {
    List<YoJiDetailBean.DataBean.ListBean> mList;
    private Context context;

    public YoJiDetailAdapter(Context context, List<YoJiDetailBean.DataBean.ListBean> data) {
        this.mList = data;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_yoji_detail, viewGroup, false);
        Holder holder = new Holder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new RoundTransform(context, 8));
//        Glide.with(context).load(mList.get(i).getThumbnail_pic_s()).apply(requestOptions).into(holder.zuji_image);
        holder.tv_position_name.setText(mList.get(position).getPosition_name());
        List<String> logos = mList.get(position).getLogos();
        int size = logos.size();
        if (size == 1) {
            holder.picture_count_one.setVisibility(View.VISIBLE);
            Glide.with(context).load(logos.get(0)).into(holder.img_count_one_one);
        } else if (size == 2) {
            holder.picture_count_two.setVisibility(View.VISIBLE);
            Glide.with(context).load(logos.get(0)).into(holder.img_count_two_one);
            Glide.with(context).load(logos.get(1)).into(holder.img_count_two_two);
        } else if (size == 3) {
            holder.picture_count_three.setVisibility(View.VISIBLE);
            Glide.with(context).load(logos.get(0)).into(holder.img_count_three_one);
            Glide.with(context).load(logos.get(1)).into(holder.img_count_three_two);
            Glide.with(context).load(logos.get(2)).into(holder.img_count_three_three);
        } else if (size == 4) {
            holder.picture_count_four.setVisibility(View.VISIBLE);
            Glide.with(context).load(logos.get(0)).into(holder.img_count_four_one);
            Glide.with(context).load(logos.get(1)).into(holder.img_count_four_two);
            Glide.with(context).load(logos.get(2)).into(holder.img_count_four_three);
            Glide.with(context).load(logos.get(2)).into(holder.img_count_four_four);
        } else if (size == 5) {
            holder.picture_count_five.setVisibility(View.VISIBLE);
            Glide.with(context).load(logos.get(0)).into(holder.img_count_five_one);
            Glide.with(context).load(logos.get(1)).into(holder.img_count_five_two);
            Glide.with(context).load(logos.get(2)).into(holder.img_count_five_three);
            Glide.with(context).load(logos.get(2)).into(holder.img_count_five_four);
            Glide.with(context).load(logos.get(2)).into(holder.img_count_five_five);
        } else if (size > 5) {
            holder.picture_count_five.setVisibility(View.VISIBLE);
            Glide.with(context).load(logos.get(0)).into(holder.img_count_five_one);
            Glide.with(context).load(logos.get(1)).into(holder.img_count_five_two);
            Glide.with(context).load(logos.get(2)).into(holder.img_count_five_three);
            Glide.with(context).load(logos.get(2)).into(holder.img_count_five_four);
            Glide.with(context).load(logos.get(2)).into(holder.img_count_five_five);
        }
        if (OnPlayListener != null) {
            OnPlayListener.getData(holder, position);
        }

        holder.itemView.setTag(position);
        if (position == mList.size() - 1) {
            holder.plane.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnClickListener {
        void onClick(View v, int position);
    }

    private OnClickListener onClickListener;

    public void setOnItemClickListener(OnClickListener onItemClickListener) {
        this.onClickListener = onItemClickListener;
    }


    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.onClick(v, (Integer) v.getTag());
        }
    }

    public interface OnPlayListener {
        void getData(Holder holder, int position);
    }

    private OnPlayListener OnPlayListener;

    public void setOnItemDataListener(OnPlayListener onPlayListener) {
        this.OnPlayListener = onPlayListener;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView zuji_image, plane,
                img_count_one_one, img_count_two_one, img_count_three_one, img_count_four_one, img_count_five_one,
                img_count_two_two, img_count_three_two, img_count_four_two, img_count_five_two,
                img_count_three_three, img_count_four_three, img_count_five_three,
                img_count_four_four, img_count_five_four, img_count_five_five;
        TextView tv_position_name;
        RelativeLayout picture_count_one, picture_count_two, picture_count_three, picture_count_four, picture_count_five;

        public Holder(@NonNull View itemView) {
            super(itemView);
            img_count_one_one = itemView.findViewById(R.id.img_count_one_one);
            img_count_two_one = itemView.findViewById(R.id.img_count_two_one);
            img_count_three_one = itemView.findViewById(R.id.img_count_three_one);
            img_count_four_one = itemView.findViewById(R.id.img_count_four_one);
            img_count_five_one = itemView.findViewById(R.id.img_count_five_one);
            img_count_two_two = itemView.findViewById(R.id.img_count_two_two);
            img_count_three_two = itemView.findViewById(R.id.img_count_three_two);
            img_count_four_two = itemView.findViewById(R.id.img_count_four_two);
            img_count_five_two = itemView.findViewById(R.id.img_count_five_two);
            img_count_three_three = itemView.findViewById(R.id.img_count_three_three);
            img_count_four_three = itemView.findViewById(R.id.img_count_four_three);
            img_count_five_three = itemView.findViewById(R.id.img_count_five_three);
            img_count_four_four = itemView.findViewById(R.id.img_count_four_four);
            img_count_five_four = itemView.findViewById(R.id.img_count_five_four);
            img_count_five_five = itemView.findViewById(R.id.img_count_five_five);
            picture_count_one = itemView.findViewById(R.id.picture_count_one);
            picture_count_two = itemView.findViewById(R.id.picture_count_two);
            picture_count_three = itemView.findViewById(R.id.picture_count_three);
            picture_count_four = itemView.findViewById(R.id.picture_count_four);
            picture_count_five = itemView.findViewById(R.id.picture_count_five);
            tv_position_name = itemView.findViewById(R.id.tv_position_name);
            zuji_image = itemView.findViewById(R.id.zuji_image);
            plane = itemView.findViewById(R.id.plane);
        }
    }

}
