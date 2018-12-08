package com.iyoyogo.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.home.HomeViewPagerBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.GlideRoundTransform;
import com.iyoyogo.android.utils.SpUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

public class YoXiuAdapter extends RecyclerView.Adapter<YoXiuAdapter.Holder> implements View.OnClickListener {
    private List<HomeViewPagerBean.DataBean.YoxListBean> mList;
    private Context context;
    private boolean isPraise;
    private String type;
    private Activity activity;
    public YoXiuAdapter(List<HomeViewPagerBean.DataBean.YoxListBean> mList, Context context, String type) {
        this.mList = mList;
        this.context = context;
        this.type=type;
        activity= (Activity) context;
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
        holder.itemView.setTag(position);
        holder.tv_addr_cover.setText(mList.get(position).getPosition_name());

        RequestOptions requestOptions = new RequestOptions().centerCrop()
                .transform(new GlideRoundTransform(context, 8));
        requestOptions.placeholder(R.mipmap.default_ic);
        requestOptions.error(R.mipmap.default_ic);

        if (type.equals("attention")){
            holder.view_like.setVisibility(View.INVISIBLE);

            Log.d("AA", "holder.img_level.getVisibility():" + holder.img_level.getVisibility());
        }else {

        }
        holder.typeImageView.setVisibility(View.VISIBLE);
        holder.img_level.setVisibility(View.VISIBLE);
        int user_level = mList.get(position).getUser_level();
        holder.medal.setVisibility(View.VISIBLE);
        int is_highquality = mList.get(position).getQuality_type();
        if (is_highquality==1){
            holder.typeImageView.setVisibility(View.VISIBLE);
            holder.typeImageView.setImageResource(R.mipmap.youzhi);
        }else if (is_highquality==2){
            holder.typeImageView.setVisibility(View.VISIBLE);
            holder.typeImageView.setImageResource(R.mipmap.jingxuan);
        }else {
            holder.typeImageView.setVisibility(View.INVISIBLE);
        }
        int partner_type = mList.get(position).getPartner_type();
        if (partner_type==0){
            mList.get(position).setPartner_type(0);
            holder.medal.setVisibility(View.INVISIBLE);
        }else if (partner_type==1){
            mList.get(position).setPartner_type(1);
            holder.medal.setImageResource(R.mipmap.da);
        }else if (partner_type==2){
            mList.get(position).setPartner_type(2);
            holder.medal.setImageResource(R.mipmap.hong);
        }else if (partner_type==3){
            mList.get(position).setPartner_type(3);
            holder.medal.setImageResource(R.mipmap.kol);
        }else {
            holder.medal.setVisibility(mList.get(position).getPartner_type()==0? View.INVISIBLE:View.VISIBLE);
        }
        if (user_level==0){
            holder.img_level.setVisibility(View.INVISIBLE);
        }else if (user_level==1){
            mList.get(position).setUser_level(1);
            holder.img_level.setImageResource(R.mipmap.lv1);

        }else if (user_level==2){
            mList.get(position).setUser_level(2);
            holder.img_level.setImageResource(R.mipmap.lv2);
        }else if (user_level==3){
            mList.get(position).setUser_level(3);
            holder.img_level.setImageResource(R.mipmap.lv3);
        }else if (user_level==4){
            mList.get(position).setUser_level(4);
            holder.img_level.setImageResource(R.mipmap.lv4);
        }else if (user_level==5){
            mList.get(position).setUser_level(5);
            holder.img_level.setImageResource(R.mipmap.lv5);
        }else {
            holder.img_level.setVisibility(View.INVISIBLE);
        }

        holder.view_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopup(holder);
            }
        });


        int file_type = mList.get(position).getFile_type();
        if (file_type==2){
            Glide.with(context).load(mList.get(position).getFile_path_first())
                    .apply(requestOptions)
                    .into(holder.imageView);
            holder.img_video.setVisibility(View.VISIBLE);
        }else {
            Glide.with(context).load(mList.get(position).getFile_path())
                    .apply(requestOptions)
                    .into(holder.imageView);
        }

        Glide.with(context).load(mList.get(position).getUser_logo()).into(holder.user_icon);
        holder.user_name.setText(mList.get(position).getUser_nickname());
        holder.num_like.setText(mList.get(position).getCount_praise() + "");
        holder.num_see.setText(mList.get(position).getCount_view() + "");

        holder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_id = SpUtils.getString(context, "user_id", null);
                String user_token = SpUtils.getString(context, "user_token", null);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
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
//                                        holder.imageView.setEnabled(true);
                                        mList.get(position).setCount_praise(count_praise);
                                       notifyItemRangeChanged(0,mList.size());
                                    }
                                });
                    }
                }).start();

            }
        });
        if (mList.get(position).getIs_my_like()==0){
            holder.iv_like.setImageResource(R.mipmap.datu_xihuan);
        }else {
            holder.iv_like.setImageResource(R.mipmap.yixihuan_xiangqing);
        }
        holder.iv_like.setImageResource(mList.get(position).getIs_my_like() == 0 ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);


    }
    private void initPopup(Holder holder){
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_like, null);
        PopupWindow popupWindow = new PopupWindow(view, DensityUtil.dp2px(context, 130), DensityUtil.dp2px(context, 110), true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        TextView tv_dislike = view.findViewById(R.id.tv_dislike);
        TextView tv_report = view.findViewById(R.id.tv_report);
        tv_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(holder.view_like,DensityUtil.dp2px(context,30),DensityUtil.dp2px(context,10));
    }
/*
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if (payloads.isEmpty()){

        }else {





        }
    }*/



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
        RelativeLayout view_like;
        ImageView img_video, imageView, typeImageView, user_icon, iv_like,more_img,medal,img_level;

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
            view_like = itemView.findViewById(R.id.view_like);
            medal = itemView.findViewById(R.id.medal);
            img_level = itemView.findViewById(R.id.img_level);
        }
    }
}
