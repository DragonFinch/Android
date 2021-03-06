package com.iyoyogo.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.yoji.list.YoJiListBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.GlideRoundTransform;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;


/**
 * yo记列表的适配器
 */
public class YoJiListAdapter extends RecyclerView.Adapter<YoJiListAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    List<YoJiListBean.DataBean.ListBean> mList;
    private List<Integer> mHeight;
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示
    private Activity activity;
    private String user_token;
    private String user_id;
    private TextView tv_message;
    private TextView tv_message_two;
    private TextView tv_message_three;
    private ImageView img_tip;
    private PopupWindow popup;
    private View view;
    private String type;
    /**
     * yo记列表的适配器
     */


        public YoJiListAdapter(Context context, List<YoJiListBean.DataBean.ListBean> mList, String type) {
            this.context = context;
            this.mList = mList;
            this.type=type;
            mHeight = new ArrayList<>();   //存放每一个item的高度
            for (int i = 0; i < mList.size(); i++) {
//            随机生成高度   400px~900px
                int height = (int) (Math.random() * (DensityUtil.dp2px(context, 20)) + DensityUtil.dp2px(context, 243));
                mHeight.add(height);
            }
            activity = (Activity) context;
            initPopup();
        }

        // 暴露接口，改变fadeTips的方法
        public boolean isFadeTips() {
            return fadeTips;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.youji_item_recycler, viewGroup, false);
            view.setOnClickListener(this);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position0) {
            final int position = position0;
//        ViewGroup.LayoutParams params = viewHolder.itemView.getLayoutParams();   //得到布局管理参数
//        params.height = mHeight.get(position);
//        viewHolder.itemView.setLayoutParams(params);
            if (type.equals("attention")){
                viewHolder.view_like.setVisibility(View.GONE);
            }
            RequestOptions requestOptions = new RequestOptions().centerCrop()
                    .transform(new GlideRoundTransform(context, 8));
            requestOptions.placeholder(R.mipmap.default_ic);
            requestOptions.error(R.mipmap.default_ic);
            RequestOptions requestOptions1 = new RequestOptions();
            requestOptions1.placeholder(R.mipmap.default_touxiang);
            requestOptions1.error(R.mipmap.default_touxiang);
            Glide.with(context).load(mList.get(position).getFile_path())
                    .apply(requestOptions)
                    .into(viewHolder.imageView);
            viewHolder.num_like.setText(mList.get(position).getCount_praise() + "");
            viewHolder.user_name.setText(mList.get(position).getUser_info().getUser_nickname());
            viewHolder.num_see.setText(mList.get(position).getCount_view() + "");
            viewHolder.num_see.setVisibility(View.VISIBLE);

            Glide.with(context).load(mList.get(position).getUser_info().getUser_logo()).apply(requestOptions1).into(viewHolder.user_icon);
            viewHolder.tv_title.setText(mList.get(position).getTitle());

            viewHolder.img_level.setVisibility(View.VISIBLE);
            viewHolder.medal.setVisibility(View.VISIBLE);
            int partner_type = mList.get(position).getUser_info().getPartner_type();
            if (partner_type == 0) {
                mList.get(position).getUser_info().setPartner_type(0);
                viewHolder.medal.setVisibility(View.INVISIBLE);
            } else if (partner_type == 1) {
                mList.get(position).getUser_info().setPartner_type(1);
                viewHolder.medal.setImageResource(R.mipmap.daren);
            } else if (partner_type == 2) {
                mList.get(position).getUser_info().setPartner_type(2);
                viewHolder.medal.setImageResource(R.mipmap.hongren);
            } else if (partner_type == 3) {
                mList.get(position).getUser_info().setPartner_type(3);
                viewHolder.medal.setImageResource(R.mipmap.kol);
            } else {
                viewHolder.medal.setVisibility(mList.get(position).getUser_info().getPartner_type() == 0 ? View.INVISIBLE : View.VISIBLE);
            }
            int user_level = mList.get(position).getUser_info().getUser_level();
            if (user_level == 0) {
                viewHolder.img_level.setVisibility(View.INVISIBLE);
            } else if (user_level == 1) {
                mList.get(position).getUser_info().setUser_level(1);
                viewHolder.img_level.setImageResource(R.mipmap.lv1);

            } else if (user_level == 2) {
                mList.get(position).getUser_info().setUser_level(2);
                viewHolder.img_level.setImageResource(R.mipmap.lv2);
            } else if (user_level == 3) {
                mList.get(position).getUser_info().setUser_level(3);
                viewHolder.img_level.setImageResource(R.mipmap.lv3);
            } else if (user_level == 4) {
                mList.get(position).getUser_info().setUser_level(4);
                viewHolder.img_level.setImageResource(R.mipmap.lv4);
            } else if (user_level == 5) {
                mList.get(position).getUser_info().setUser_level(5);
                viewHolder.img_level.setImageResource(R.mipmap.lv5);
            } else {
                viewHolder.img_level.setVisibility(View.INVISIBLE);
            }



            viewHolder.user_icon.setOnClickListener(new View.OnClickListener() {//头像
                @Override
                public void onClick(View v) {
                    int id = mList.get(position).getUser_info().getUser_id();
                    int user_id = mList.get(position).getUser_info().getUser_id();
                    int yo_id = mList.get(position).getYo_id();
                    Intent intent = new Intent(context, YoJiDetailActivity.class);
                    intent.putExtra("yo_id",yo_id);
                    context.startActivity(intent);
                }
            });
            viewHolder.index_look_icon.setVisibility(View.VISIBLE);
            viewHolder.index_look_icon.setOnClickListener(new View.OnClickListener() {//眼睛
                @Override
                public void onClick(View v) {
                    int id = mList.get(position).getYo_id();
                    Intent intent = new Intent(context, YoJiDetailActivity.class);
                    intent.putExtra("yo_id", id);
                    context.startActivity(intent);
                }
            });
            viewHolder.more_img.setVisibility(View.VISIBLE);
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {//背景图
                @Override
                public void onClick(View v) {
                    int id = mList.get(position).getYo_id();
                    Intent intent = new Intent(context, YoJiDetailActivity.class);
                    intent.putExtra("yo_id", id);
                    context.startActivity(intent);
                }
            });
            viewHolder.more_img.setOnClickListener(new View.OnClickListener() {//...
                @Override
                public void onClick(View v) {
                   /* int id = mList.get(position).getYo_id();
                    Intent intent = new Intent(context, YoJiDetailActivity.class);
                    intent.putExtra("yo_id", id);
                    context.startActivity(intent);*/

                }
            });
            viewHolder.view_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                    initDelete(viewHolder,String.valueOf(mList.get(position).getUser_info().getUser_id()),mList.get(position).getYo_id());
                }
            });

            //点赞
            if (mList.get(position).getIs_my_praise() == 0) {
                viewHolder.iv_like.setImageResource(R.mipmap.datu_xihuan);
            } else {
                viewHolder.iv_like.setImageResource(R.mipmap.yixihuan_xiangqing);
            }
            viewHolder.iv_like.setImageResource(mList.get(position).getIs_my_praise() == 0 ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);
            viewHolder.iv_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user_id = SpUtils.getString(context, "user_id", null);
                    String user_token = SpUtils.getString(context, "user_token", null);

                    String count_praise = mList.get(position).getCount_praise();
                    int count_praises = Integer.parseInt(count_praise);
                    mList.get(position).setIs_my_praise(mList.get(position).getIs_my_praise() == 1 ? 0 : 1);
                    if (mList.get(position).getIs_my_praise() == 1) {
                        count_praises += 1;
                        mList.get(position).setCount_praise(String.valueOf(count_praises));
                    } else  {
                        count_praises -= 1;
                        mList.get(position).setCount_praise(String.valueOf(count_praises));

                    }
                    viewHolder.num_like.setText(count_praises + "");
                    viewHolder.iv_like.setImageResource(mList.get(position).getIs_my_praise() == 0 ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DataManager.getFromRemote().praise(context,user_id, user_token, mList.get(position).getYo_id(), 0)
                                    .subscribe(new Consumer<BaseBean>() {
                                        @Override
                                        public void accept(BaseBean baseBean) throws Exception {
                                        }
                                    });
                        }
                    }).start();
                }
            });
            viewHolder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.setOnClickListener(v, (Integer) v.getTag());
            }
        }

        public interface OnClickListener {
            void setOnClickListener(View v, int position);
        }

        private OnClickListener onClickListener;

        public void setOnItemClickListener(YoJiListAdapter.OnClickListener onItemClickListener) {
            this.onClickListener = onItemClickListener;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView img_type, imageView, iv_like, iv_video, more_img, index_look_icon,medal,img_level;
            TextView num_like, user_name, tv_title, num_see;
            CircleImageView user_icon;
            RelativeLayout view_like;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view_like = itemView.findViewById(R.id.view_like);
            num_see = itemView.findViewById(R.id.num_see);
            tv_title = itemView.findViewById(R.id.tv_title);
            user_name = itemView.findViewById(R.id.user_name);
            user_icon = itemView.findViewById(R.id.user_icon);
            img_type = itemView.findViewById(R.id.img_type);
            iv_like = itemView.findViewById(R.id.iv_like);
            imageView = itemView.findViewById(R.id.imageView);
            num_like = itemView.findViewById(R.id.num_like);
            iv_video = itemView.findViewById(R.id.iv_video);
            more_img = itemView.findViewById(R.id.more_img);
            index_look_icon = itemView.findViewById(R.id.index_look_icon);
            medal = itemView.findViewById(R.id.medal);
            img_level = itemView.findViewById(R.id.img_level);
        }
    }
    private void initDislike(int id) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_popwindow_not_like, null);
        PopupWindow popupWindow = new PopupWindow(view, DensityUtil.dp2px(context, 300), DensityUtil.dp2px(context, 230), true);
        TextView dislike_this_kind = view.findViewById(R.id.dislike_this_kind);
        TextView dislike_this_item = view.findViewById(R.id.dislike_this_item);
        ImageView pop_im_id = view.findViewById(R.id.pop_im_id);
        pop_im_id.setImageResource(R.mipmap.stamp_shijian);
        backgroundAlpha(0.6f);
        dislike_this_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getFromRemote()
                        .dislike(context,user_id, user_token, id, 1)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                popupWindow.dismiss();
                                notifyDataSetChanged();
                            }
                        });
            }
        });
        dislike_this_kind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getFromRemote()
                        .dislike(context,user_id, user_token, id, 2)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                popupWindow.dismiss();
                                notifyDataSetChanged();
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
        popupWindow.showAtLocation(activity.findViewById(R.id.activity_yo_ji_list), Gravity.CENTER, 0, 0);
    }
    private void initDelete(ViewHolder holder, String yo_user_id, int yo_id) {
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_like, null);
        PopupWindow popupWindow = new PopupWindow(view, DensityUtil.dp2px(context, 125),  DensityUtil.dp2px(context, 50), true);
        String user_id = SpUtils.getString(context, "user_id", null);
        String user_token = SpUtils.getString(context, "user_token", null);
        TextView tv_dislike = view.findViewById(R.id.tv_dislike);
        View line = view.findViewById(R.id.line);
        line.setVisibility(View.GONE);
        TextView tv_report = view.findViewById(R.id.tv_report);
        tv_report.setVisibility(View.GONE);
        tv_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initDislike(yo_id);

            }
        });
        tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                loadMore(yo_id);
            }
        });


        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        backgroundAlpha(0.6f);
        popupWindow.setOnDismissListener(new poponDismissListener());
        popupWindow.showAsDropDown(holder.view_like, DensityUtil.dp2px(context,-95), 0);
    }

    public void initPopup() {
        view = LayoutInflater.from(context).inflate(R.layout.like_layout, null);
        popup = new PopupWindow(view, DensityUtil.dp2px(context, 300), DensityUtil.dp2px(context, 145), true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable());
        tv_message = view.findViewById(R.id.tv_message);
        tv_message_two = view.findViewById(R.id.tv_message_two);

        tv_message_three = view.findViewById(R.id.tv_message_three);
        img_tip = view.findViewById(R.id.tip_img);

        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口


        //添加pop窗口关闭事件
        popup.setOnDismissListener(new poponDismissListener());
    }
    public void report() {

        tv_message.setText("举报成功");
        tv_message.setTextColor(Color.parseColor("#333333"));
        tv_message_two.setTextColor(Color.parseColor("#888888"));
        tv_message_three.setTextColor(Color.parseColor("#888888"));
        img_tip.setImageResource(R.mipmap.stamp_report);
        tv_message_two.setText("打击恶势力小分队");
        tv_message_three.setText("已前去为您扫清障碍~");
        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口

        backgroundAlpha(0.6f);

        //添加pop窗口关闭事件
        popup.setOnDismissListener(new poponDismissListener());
        popup.showAtLocation(activity.findViewById(R.id.activity_yo_ji_list), Gravity.CENTER, 0, 0);
    }
    public void loadMore(int yo_id) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_more, null);
        PopupWindow popup_more = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popup_more.setBackgroundDrawable(new ColorDrawable());
        popup_more.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup_more.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口
        //广告信息
        TextView tv_advert = view.findViewById(R.id.tv_advert);
        user_id = SpUtils.getString(context, "user_id", null);
        user_token = SpUtils.getString(context, "user_token", null);
        tv_advert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_more.dismiss();
                report();

                DataManager.getFromRemote().report(context,user_id, user_token, yo_id, 0, tv_advert.getText().toString())
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                if (baseBean.getMsg().equals("success")) {

                                } else {
                                    Toast.makeText(context, baseBean.getMsg(), Toast.LENGTH_SHORT).show();

                                }
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
                report();
                DataManager.getFromRemote().report(context,user_id, user_token, yo_id, 0, tv_harm.getText().toString())
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                if (baseBean.getMsg().equals("success")) {

                                } else {
                                    Toast.makeText(context, baseBean.getMsg(), Toast.LENGTH_SHORT).show();

                                }
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
                report();
                DataManager.getFromRemote().report(context,user_id, user_token, yo_id, 0, tv_violate.getText().toString())
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                if (baseBean.getMsg().equals("success")) {

                                } else {
                                    Toast.makeText(context, baseBean.getMsg(), Toast.LENGTH_SHORT).show();

                                }
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
                report();

                DataManager.getFromRemote().report(context,user_id, user_token, yo_id, 0, tv_else.getText().toString())
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                if (baseBean.getMsg().equals("success")) {

                                } else {
                                    Toast.makeText(context, baseBean.getMsg(), Toast.LENGTH_SHORT).show();

                                }
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
        popup_more.showAtLocation(view, Gravity.BOTTOM, 0, 0);
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
}
