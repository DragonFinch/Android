package com.iyoyogo.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.home.HomeBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.GlideRoundTransform;
import com.iyoyogo.android.utils.SpUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

public class YoXiuAdapter extends RecyclerView.Adapter<YoXiuAdapter.Holder> implements View.OnClickListener {
    private List<HomeBean.DataBean.YoxListBean> mList;
    private Context context;
    private boolean isPraise;
    private String type;
    private Activity activity;
    private String user_id;
    private String user_token;
    private int yo_id;

    public YoXiuAdapter(List<HomeBean.DataBean.YoxListBean> mList, Context context, String type) {
        this.mList = mList;
        this.context = context;
        this.type = type;
        activity = (Activity) context;
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

        if (type.equals("attention")) {
            holder.view_like.setVisibility(View.INVISIBLE);

            Log.d("AA", "holder.img_level.getVisibility():" + holder.img_level.getVisibility());
        } else {

        }
        holder.typeImageView.setVisibility(View.VISIBLE);
        holder.img_level.setVisibility(View.VISIBLE);
        int user_level = mList.get(position).getUser_level();
        holder.medal.setVisibility(View.VISIBLE);
        int is_highquality = mList.get(position).getQuality_type();
        if (is_highquality == 1) {
            holder.typeImageView.setVisibility(View.VISIBLE);
            holder.typeImageView.setImageResource(R.mipmap.youzhi);
        } else if (is_highquality == 2) {
            holder.typeImageView.setVisibility(View.VISIBLE);
            holder.typeImageView.setImageResource(R.mipmap.jingxuan);
        } else {
            holder.typeImageView.setVisibility(View.INVISIBLE);
        }
        int partner_type = mList.get(position).getPartner_type();
        if (partner_type == 0) {
            mList.get(position).setPartner_type(0);
            holder.medal.setVisibility(View.INVISIBLE);
        } else if (partner_type == 1) {
            mList.get(position).setPartner_type(1);
            holder.medal.setImageResource(R.mipmap.da);
        } else if (partner_type == 2) {
            mList.get(position).setPartner_type(2);
            holder.medal.setImageResource(R.mipmap.hong);
        } else if (partner_type == 3) {
            mList.get(position).setPartner_type(3);
            holder.medal.setImageResource(R.mipmap.kol);
        } else {
            holder.medal.setVisibility(mList.get(position).getPartner_type() == 0 ? View.INVISIBLE : View.VISIBLE);
        }
        if (user_level == 0) {
            holder.img_level.setVisibility(View.INVISIBLE);
        } else if (user_level == 1) {
            mList.get(position).setUser_level(1);
            holder.img_level.setImageResource(R.mipmap.lv1);

        } else if (user_level == 2) {
            mList.get(position).setUser_level(2);
            holder.img_level.setImageResource(R.mipmap.lv2);
        } else if (user_level == 3) {
            mList.get(position).setUser_level(3);
            holder.img_level.setImageResource(R.mipmap.lv3);
        } else if (user_level == 4) {
            mList.get(position).setUser_level(4);
            holder.img_level.setImageResource(R.mipmap.lv4);
        } else if (user_level == 5) {
            mList.get(position).setUser_level(5);
            holder.img_level.setImageResource(R.mipmap.lv5);
        } else {
            holder.img_level.setVisibility(View.INVISIBLE);
        }

        holder.view_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yo_id = mList.get(position).getId();
                initPopup(holder);
            }
        });

        int is_video = mList.get(position).getIs_video();
        if (is_video==1){
            Glide.with(context).load(mList.get(position).getFile_path())
                    .apply(requestOptions)
                    .into(holder.imageView);
            holder.img_video.setVisibility(View.VISIBLE);
        }else {
            Glide.with(context).load(mList.get(position).getFile_path())
                    .apply(requestOptions)
                    .into(holder.imageView);
        }
        RequestOptions requestOption = new RequestOptions();
        requestOption.placeholder(R.mipmap.default_touxiang);
        requestOption.error(R.mipmap.default_touxiang);
       if (mList.get(position).getUser_logo().equals("")){
           Glide.with(context).load(R.mipmap.default_touxiang)
                   .apply(requestOption)
                   .into(holder.user_icon);
       }else {
           Glide.with(context).load(mList.get(position).getUser_logo()) .apply(requestOption).into(holder.user_icon);
       }
        holder.user_name.setText(mList.get(position).getUser_nickname());
        holder.num_like.setText(mList.get(position).getCount_praise() + "");
        holder.num_see.setText(mList.get(position).getCount_view() + "");
        if (mList.get(position).getIs_my_like() == 0) {
            holder.iv_like.setImageResource(R.mipmap.datu_xihuan);
        } else {
            holder.iv_like.setImageResource(R.mipmap.yixihuan_xiangqing);
        }
        holder.iv_like.setImageResource(mList.get(position).getIs_my_like() == 0 ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);


        holder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = SpUtils.getString(context, "user_id", null);
                String user_token = SpUtils.getString(context, "user_token", null);

                int count_praise = mList.get(position).getCount_praise();
                mList.get(position).setIs_my_like(mList.get(position).getIs_my_like() == 1 ? 0 : 1);
                if (mList.get(position).getIs_my_like() == 1) {
                    count_praise += 1;
                    mList.get(position).setCount_praise(count_praise);
                } else if (count_praise > 0) {
                    count_praise -= 1;
                    mList.get(position).setCount_praise(count_praise);

                }
                holder.num_like.setText(count_praise + "");
                holder.iv_like.setImageResource(mList.get(position).getIs_my_like() == 0 ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataManager.getFromRemote().praise(user_id, user_token, 0, mList.get(position).getId())
                                .subscribe(new Consumer<BaseBean>() {
                                    @Override
                                    public void accept(BaseBean baseBean) throws Exception {
                                    }
                                });
                    }
                }).start();

            }
        });


    }


    private void initPopup(Holder holder) {
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_like, null);
        PopupWindow popupWindow = new PopupWindow(view, DensityUtil.dp2px(context, 125), DensityUtil.dp2px(context, 50), true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        TextView tv_dislike = view.findViewById(R.id.tv_dislike);
        View line = view.findViewById(R.id.line);
        line.setVisibility(View.GONE);
        TextView tv_report = view.findViewById(R.id.tv_report);
        tv_report.setVisibility(View.GONE);
        user_id = SpUtils.getString(context, "user_id", null);
        user_token = SpUtils.getString(context, "user_token", null);
        tv_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initDislike();

            }
        });
        tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                loadMore();
            }
        });
        popupWindow.showAsDropDown(holder.view_like, DensityUtil.dp2px(context, 30), DensityUtil.dp2px(context, 10));
    }

    private void initDislike() {
        View view = LayoutInflater.from(context).inflate(R.layout.item_popwindow_not_like, null);
        PopupWindow popupWindow = new PopupWindow(view, DensityUtil.dp2px(context,300), DensityUtil.dp2px(context, 230), true);
        TextView dislike_this_kind = view.findViewById(R.id.dislike_this_kind);
        TextView dislike_this_item = view.findViewById(R.id.dislike_this_item);
        backgroundAlpha(0.6f);
        dislike_this_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getFromRemote()
                        .dislike(user_id, user_token, yo_id, 1)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                popupWindow.dismiss();
                                if (onRetryClickListener!=null){
                                    onRetryClickListener.onretry();
                                }
                            }
                        });
            }
        });
        dislike_this_kind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getFromRemote()
                        .dislike(user_id, user_token, yo_id, 2)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                popupWindow.dismiss();
                                if (onRetryClickListener!=null){
                                    onRetryClickListener.onretry();
                                }
                            }
                        });

            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        popupWindow.showAtLocation(activity.findViewById(R.id.activity_main), Gravity.CENTER, 0, 0);
    }


    public void loadMore() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_more, null);
        PopupWindow popup_more = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popup_more.setBackgroundDrawable(new ColorDrawable());
        popup_more.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup_more.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口
        //广告信息
        TextView tv_advert = view.findViewById(R.id.tv_advert);
        tv_advert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_more.dismiss();
                String s = tv_advert.getText().toString();
                DataManager.getFromRemote()
                        .report(user_id, user_token, yo_id, 0, s)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {

                            }
                        });
            }
        });
        //有害信息
        TextView tv_harm = view.findViewById(R.id.tv_harm);
        tv_harm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_more.dismiss();
                String s = tv_harm.getText().toString();
                DataManager.getFromRemote()
                        .report(user_id, user_token, yo_id, 0, s)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {

                            }
                        });
            }
        });
        //违法违规
        TextView tv_violate = view.findViewById(R.id.tv_violate);
        tv_violate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_more.dismiss();
                String s = tv_violate.getText().toString();
                DataManager.getFromRemote()
                        .report(user_id, user_token, yo_id, 0, s)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {

                            }
                        });
            }
        });
        //其他
        TextView tv_else = view.findViewById(R.id.tv_else);
        tv_else.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_more.dismiss();
                String s = tv_else.getText().toString();
                DataManager.getFromRemote()
                        .report(user_id, user_token, yo_id, 0, s)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {

                            }
                        });
            }
        });
        //取消
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_more.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        popup_more.setOnDismissListener(new poponDismissListener());
        //添加pop窗口关闭事件
        popup_more.showAtLocation(activity.findViewById(R.id.activity_main), Gravity.BOTTOM, 0, 0);
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

    public void backgroundAlpha(float bgAlpha) {

        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        activity.getWindow().setAttributes(lp); //act 是上下文context

    }

    //隐藏事件PopupWindow
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1.0f);
        }
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_addr_cover, user_name, num_like, num_see;
        RelativeLayout view_like;
        ImageView img_video, imageView, typeImageView, user_icon, iv_like, more_img, medal, img_level;

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
    public interface  OnRetryClickListener{
        void onretry();
    }
    private OnRetryClickListener onRetryClickListener;
    public void setOnRetryClickListener(OnRetryClickListener onRetryClickListener){
        this.onRetryClickListener=onRetryClickListener;
    }
}
