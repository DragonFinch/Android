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
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.home.HomeViewPagerBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.utils.GlideRoundTransform;
import com.iyoyogo.android.utils.SpUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

public class YoXiuAdapter extends RecyclerView.Adapter<YoXiuAdapter.Holder> implements View.OnClickListener {
    private List<HomeViewPagerBean.DataBean.YoxListBean> mList;
    private Context context;
    private boolean isPraise;

    public YoXiuAdapter(List<HomeViewPagerBean.DataBean.YoxListBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.youxiu_item_recycler, viewGroup, false);
        Holder holder = new Holder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.tv_addr_cover.setText(mList.get(position).getPosition_name());


//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions requestOptions = new RequestOptions().centerCrop()
                .transform(new GlideRoundTransform(context, 8));
        requestOptions.placeholder(R.mipmap.default_ic);
        requestOptions.error(R.mipmap.default_ic);
        Glide.with(context).load(mList.get(position).getFile_path())
                .apply(requestOptions)
                .into(holder.imageView);

        if (mList.get(position).getFile_path().endsWith("jpg")) {
            holder.img_video.setVisibility(View.GONE);
        }
        if (position == 0) {
            holder.typeImageView.setImageResource(R.mipmap.jingxuan);
        } else if (position == 1) {
            holder.typeImageView.setImageResource(R.mipmap.youzhi);
        } else {
            holder.typeImageView.setVisibility(View.INVISIBLE);
        }
        Glide.with(context).load(mList.get(position).getUser_logo()).into(holder.user_icon);
        holder.user_name.setText(mList.get(position).getUser_nickname());
        holder.num_like.setText(mList.get(position).getCount_praise() + "");
        holder.num_see.setText(mList.get(position).getCount_view() + "");
        holder.itemView.setTag(position);
        holder.iv_like.setImageResource(mList.get(position).getIs_my_like() == 0 ? R.mipmap.ic_like : R.mipmap.ic_liked);


        holder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = SpUtils.getString(context, "user_id", null);
                String user_token = SpUtils.getString(context, "user_token", null);
                DataManager.getFromRemote().praise(user_id, user_token, mList.get(position).getId(), 0)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                int count_praise = mList.get(position).getCount_praise();
                                mList.get(position).setIs_my_like(mList.get(position).getIs_my_like() == 1 ? 0 : 1);
                                if (mList.get(position).getIs_my_like() == 1) {
                                    count_praise += 1;
                                } else if (count_praise > 0) {
                                    count_praise -= 1;
                                }
                                mList.get(position).setCount_praise(count_praise);
                                notifyDataSetChanged();
                            }
                        });
            }
        });
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


    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_addr_cover, user_name, num_like, num_see;
        ImageView img_video, imageView, typeImageView, user_icon, iv_like;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_addr_cover = itemView.findViewById(R.id.tv_addr_cover);
            iv_like = itemView.findViewById(R.id.iv_like);
            num_like = itemView.findViewById(R.id.num_like);
            num_see = itemView.findViewById(R.id.num_see);
            user_name = itemView.findViewById(R.id.user_name);
            img_video = itemView.findViewById(R.id.iv_video);
            imageView = itemView.findViewById(R.id.imageView);
            typeImageView = itemView.findViewById(R.id.img_type);
            user_icon = itemView.findViewById(R.id.user_icon);
        }
    }
}
