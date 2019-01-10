package com.iyoyogo.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
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
import com.iyoyogo.android.adapter.YoJiListInnerAdapter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.home.HomeBean;
import com.iyoyogo.android.bean.yoji.list.YoJiListBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.ui.home.yoji.UserHomepageActivity;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.ui.home.yoxiu.AllCommentActivity;
import com.iyoyogo.android.ui.mine.homepage.Personal_homepage_Activity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.GlideRoundTransform;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;
import com.iyoyogo.android.widget.PileLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class YoJiListHorizontalAdapter extends RecyclerView.Adapter<YoJiListHorizontalAdapter.Holder> implements View.OnClickListener {
    private List<YoJiListBean.DataBean.ListBean> mList;
    private Context context;
    private int yo_id;
    private Activity activity;
    private String user_token;
    private String user_id;
    private TextView tv_message;
    private TextView tv_message_two;
    private TextView tv_message_three;
    private ImageView img_tip;
    private PopupWindow popup;
    private View view;

    public YoJiListHorizontalAdapter(Context context, List<YoJiListBean.DataBean.ListBean> data) {
        this.mList = data;
        this.context = context;
        activity = (Activity) context;
        initPopup();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.yoji_item_recycler, viewGroup, false);
        Holder holder = new Holder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.typeImageView.setVisibility(View.GONE);
        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.default_ic)
                .error(R.mipmap.default_ic)
                .override(DensityUtil.dp2px(context, ViewGroup.LayoutParams.MATCH_PARENT), DensityUtil.dp2px(context, 200))
                .transform(new GlideRoundTransform(context, 8));
        Glide.with(context).load(mList.get(position).getFile_path()).apply(myOptions).into(holder.zuji_image);
        holder.location.setText(mList.get(position).getP_start());
        holder.location_end.setText(mList.get(position).getP_end());
        holder.num_look.setText(mList.get(position).getCount_view() + "");
        LayoutInflater inflater = LayoutInflater.from(context);
        if (mList.get(position).getUsers_praise().size() == 0) {
            holder.pile_layout.setVisibility(View.GONE);
            holder.tv_num_like.setVisibility(View.GONE);
        } else {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.mipmap.default_touxiang).error(R.mipmap.default_touxiang);
            Log.d("YoJiListHorizontalAdapt", "mList.get(position).getUsers_praise().size():" + mList.get(position).getUsers_praise().size());
            if (mList.get(position).getUsers_praise().size() <= 10) {
                List<String> user_icons = new ArrayList<>();
                user_icons.clear();
                int size = mList.get(position).getUsers_praise().size();
                holder.pile_layout.removeAllViews();
                for (int i = 0; i < size; i++) {
                    user_icons.add(mList.get(position).getUsers_praise().get(i).getUser_logo());
                    Log.e("YoJiListHorizontalAdapt", mList.get(position).getUsers_praise().get(i).getUser_logo());
                    com.iyoyogo.android.view.CircleImageView imageView = (com.iyoyogo.android.view.CircleImageView) inflater.inflate(R.layout.item_head_image, holder.pile_layout, false);
                    Glide.with(context).load(user_icons.get(i)).apply(requestOptions).into(imageView);
                    holder.pile_layout.addView(imageView);
                    int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(context, "mList.get(position).getUsers_praise().get(i).getUser_id():" + mList.get(position).getUsers_praise().get(finalI).getUser_id(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, UserHomepageActivity.class);
                            intent.putExtra("yo_user_id", String.valueOf(mList.get(finalI).getUser_info().getUser_id()));
                            context.startActivity(intent);
                        }
                    });
                    Log.d("YoJiListHorizontalAdapt", "user_icons.size():" + user_icons.size());
                }
            } else {
                List<YoJiListBean.DataBean.ListBean.UsersPraiseBean> user_icons = new ArrayList<>();
                user_icons.clear();
                holder.pile_layout.removeAllViews();
                for (int i = 0; i < 10; i++) {
                    user_icons.add(mList.get(position).getUsers_praise().get(i));
                    com.iyoyogo.android.view.CircleImageView imageView = (com.iyoyogo.android.view.CircleImageView) inflater.inflate(R.layout.item_head_image, holder.pile_layout, false);
                    Glide.with(context).load(user_icons.get(i).getUser_logo()).apply(requestOptions).into(imageView);
                    holder.pile_layout.addView(imageView);
                    int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(context, "mList.get(position).getUsers_praise().get(i).getUser_id():" + mList.get(position).getUsers_praise().get(finalI).getUser_id(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, UserHomepageActivity.class);
                            intent.putExtra("yo_user_id", String.valueOf(mList.get(position).getUsers_praise().get(finalI).getUser_id()));
                            context.startActivity(intent);
                        }
                    });
                }
                Log.d("YoJiListHorizontalAdapt", "user_icons.size():" + user_icons.size());
            }

        }

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.mipmap.default_touxiang).placeholder(R.mipmap.default_touxiang);
        Glide.with(context).load(mList.get(position).getUser_info().getUser_logo())
                .apply(requestOptions)
                .into(holder.user_icon);
        holder.user_name.setText(mList.get(position).getUser_info().getUser_nickname());
        holder.title.setText(mList.get(position).getTitle());
        holder.tv_cost.setText(" ￥" + mList.get(position).getCost() + "/人");
        holder.tv_day.setText(mList.get(position).getCount_dates() + "天");
        holder.tv_num_comment.setText("全部评论(" + mList.get(position).getCount_comment() + ")");
        holder.tv_num_like.setText("等" + mList.get(position).getCount_praise() + "人喜欢过");
        holder.tv_num_like.setVisibility(View.VISIBLE);
        List<YoJiListBean.DataBean.ListBean.CommentListBean> comment_list = mList.get(position).getComment_list();
        YoJiListInnerAdapter adapter = new YoJiListInnerAdapter(context, comment_list);
        Log.d("YoJiAdapter", "comment_list:" + comment_list.size());
        holder.recycler_comment.setLayoutManager(new LinearLayoutManager(context));
        holder.recycler_comment.setAdapter(adapter);
        holder.dt_like.setImageResource(mList.get(position).getIs_my_praise() > 0 ? R.mipmap.yixihuan_xiangqing : R.mipmap.datu_xihuan);
        holder.dt_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String count_praise = mList.get(position).getCount_praise();
                int count_praises = Integer.parseInt(count_praise);
                Log.d("Test", "dataBeans.get(0).getIs_my_like():" + mList.get(position).getIs_my_praise());
                if (mList.get(position).getIs_my_praise() > 0) {
                    //由喜欢变为不喜欢，亮变暗
                    holder.dt_like.setImageResource(R.mipmap.yixihuan_xiangqing);
                    count_praises -= 1;
                    //设置点赞的数量
                    holder.tv_num_like.setText("等" + count_praises + "人喜欢过");
                    mList.get(position).setIs_my_praise(0);
                    mList.get(position).setCount_praise(String.valueOf(count_praises));
                } else {
                    //由不喜欢变为喜欢，暗变亮
                    holder.dt_like.setImageResource(R.mipmap.datu_xihuan);
                    count_praises += 1;
                    //设置点赞的数量
                    holder.tv_num_like.setText("等" + count_praises + "人喜欢过");
                    mList.get(position).setIs_my_praise(1);
                    mList.get(position).setCount_praise(String.valueOf(count_praises));
                }
                String user_id = SpUtils.getString(context, "user_id", null);
                String user_token = SpUtils.getString(context, "user_token", null);
                DataManager.getFromRemote().praise(user_id, user_token, mList.get(position).getYo_id(), 0)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                            }
                        });
            }
        });
        holder.medal.setVisibility(View.VISIBLE);
        holder.img_level.setVisibility(View.VISIBLE);
        int partner_type = mList.get(position).getQuality_type();
        if (partner_type == 0) {
            mList.get(position).setQuality_type(0);
            holder.medal.setVisibility(View.INVISIBLE);
        } else if (partner_type == 1) {
            mList.get(position).setQuality_type(1);
            holder.medal.setImageResource(R.mipmap.daren);
        } else if (partner_type == 2) {
            mList.get(position).setQuality_type(2);
            holder.medal.setImageResource(R.mipmap.hongren);
        } else if (partner_type == 3) {
            mList.get(position).setQuality_type(3);
            holder.medal.setImageResource(R.mipmap.kol);
        } else {
            holder.medal.setVisibility(mList.get(position).getQuality_type() == 0 ? View.INVISIBLE : View.VISIBLE);
        }
        int user_level = mList.get(position).getUser_info().getUser_level();
        if (user_level == 0) {
            holder.img_level.setVisibility(View.INVISIBLE);
        } else if (user_level == 1) {
            mList.get(position).getUser_info().setUser_level(1);
            holder.img_level.setImageResource(R.mipmap.lv1);

        } else if (user_level == 2) {
            mList.get(position).getUser_info().setUser_level(2);
            holder.img_level.setImageResource(R.mipmap.lv2);
        } else if (user_level == 3) {
            mList.get(position).getUser_info().setUser_level(3);
            holder.img_level.setImageResource(R.mipmap.lv3);
        } else if (user_level == 4) {
            mList.get(position).getUser_info().setUser_level(4);
            holder.img_level.setImageResource(R.mipmap.lv4);
        } else if (user_level == 5) {
            mList.get(position).getUser_info().setUser_level(5);
            holder.img_level.setImageResource(R.mipmap.lv5);
        } else {
            holder.img_level.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setTag(position);

        //点赞
        if (mList.get(position).getIs_my_praise() == 0) {
            holder.dt_like.setImageResource(R.mipmap.datu_xihuan);
        } else {
            holder.dt_like.setImageResource(R.mipmap.yixihuan_xiangqing);
        }
        holder.dt_like.setImageResource(mList.get(position).getIs_my_praise() == 0 ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);
        yo_id = mList.get(position).getYo_id();
        holder.dt_like.setOnClickListener(new View.OnClickListener() {
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
                } else if (count_praises > 0) {
                    count_praises -= 1;
                    mList.get(position).setCount_praise(String.valueOf(count_praises));

                }
                holder.tv_num_like.setText("等" + mList.get(position).getCount_praise() + "人喜欢过");
                holder.dt_like.setImageResource(mList.get(position).getIs_my_praise() == 0 ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataManager.getFromRemote().praise(user_id, user_token, 0, mList.get(position).getYo_id())
                                .subscribe(new Consumer<BaseBean>() {
                                    @Override
                                    public void accept(BaseBean baseBean) throws Exception {
                                    }
                                });
                    }
                }).start();
            }
        });

        holder.user_icon.setOnClickListener(new View.OnClickListener() {//头像
            @Override
            public void onClick(View v) {
                int id = mList.get(position).getUser_info().getUser_id();
                Intent intent = new Intent(context, UserHomepageActivity.class);
                intent.putExtra("yo_user_id", String.valueOf(id));
                context.startActivity(intent);
            }
        });
        holder.index_look_icon.setOnClickListener(new View.OnClickListener() {//眼睛
            @Override
            public void onClick(View v) {
                int id = mList.get(position).getYo_id();
                Intent intent = new Intent(context, YoJiDetailActivity.class);
                intent.putExtra("yo_id", id);
                context.startActivity(intent);
            }
        });
        holder.zuji_image.setOnClickListener(new View.OnClickListener() {//背景图
            @Override
            public void onClick(View v) {
                int id = mList.get(position).getYo_id();
                Intent intent = new Intent(context, YoJiDetailActivity.class);
                intent.putExtra("yo_id", id);
                context.startActivity(intent);
            }
        });
        holder.more_img.setOnClickListener(new View.OnClickListener() {//...
            @Override
            public void onClick(View v) {
                initDelete(holder, String.valueOf(mList.get(position).getUser_info().getUser_id()), mList.get(position).getYo_id());
            }
        });
        holder.tv_num_comment.setOnClickListener(new View.OnClickListener() {//全部评论
            @Override
            public void onClick(View v) {
                int id = mList.get(position).getYo_id();
                Intent intent = new Intent(context, AllCommentActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });
        holder.view_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDelete(holder, String.valueOf(mList.get(position).getUser_info().getUser_id()), mList.get(position).getYo_id());
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
        ImageView zuji_image, typeImageView, dt_like, index_look_icon, more_img, medal, img_level;
        CircleImageView user_icon;
        TextView num_look, location_end, user_name, title, tv_cost, location, tv_day, tv_num_like, tv_num_comment;
        RelativeLayout view_like;
        PileLayout pile_layout;
        RecyclerView recycler_comment;

        public Holder(@NonNull View itemView) {
            super(itemView);
            img_level = itemView.findViewById(R.id.img_level);
            medal = itemView.findViewById(R.id.medal);
            zuji_image = itemView.findViewById(R.id.zuji_image);
            tv_num_comment = itemView.findViewById(R.id.tv_num_comment);
            recycler_comment = itemView.findViewById(R.id.recycler_comment);
            tv_num_like = itemView.findViewById(R.id.tv_num_like);
            pile_layout = itemView.findViewById(R.id.pile_layout);
            tv_day = itemView.findViewById(R.id.tv_day);
            location = itemView.findViewById(R.id.location);
            title = itemView.findViewById(R.id.title);
            tv_cost = itemView.findViewById(R.id.tv_cost);
            view_like = itemView.findViewById(R.id.view_like);
            num_look = itemView.findViewById(R.id.num_look);
            user_name = itemView.findViewById(R.id.user_name);
            typeImageView = itemView.findViewById(R.id.img_type);
            user_icon = itemView.findViewById(R.id.user_icon);
            dt_like = itemView.findViewById(R.id.dt_like);
            index_look_icon = itemView.findViewById(R.id.index_look_icon);
            more_img = itemView.findViewById(R.id.more_img);
            location_end = itemView.findViewById(R.id.location_end);
        }
    }

    private void initDelete(Holder holder, String yo_user_id, int yo_id) {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_delete_or_report, null);
        PopupWindow popupWindow = new PopupWindow(view, DensityUtil.dp2px(context, 125), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        String user_id = SpUtils.getString(context, "user_id", null);
        String user_token = SpUtils.getString(context, "user_token", null);
        TextView tv_delete = view.findViewById(R.id.tv_delete);
        TextView tv_report = view.findViewById(R.id.tv_report);
        Log.d("YoXiuDetailAdapter", yo_user_id);
        if (yo_user_id.equals(user_id)) {
//            tv_delete.setVisibility(View.VISIBLE);
            tv_report.setVisibility(View.GONE);
        } else {
//            tv_delete.setVisibility(View.GONE);
            tv_report.setVisibility(View.VISIBLE);
        }
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getFromRemote().deleteYo(user_id, user_token, yo_id)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {

                            }
                        });
                popupWindow.dismiss();
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
        popupWindow.showAsDropDown(holder.view_like, DensityUtil.dp2px(context,-95),  DensityUtil.dp2px(context,5));
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
                report();
                popup_more.dismiss();

                DataManager.getFromRemote().report(user_id, user_token, yo_id, 0, tv_advert.getText().toString())
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
                DataManager.getFromRemote().report(user_id, user_token, yo_id, 0, tv_harm.getText().toString())
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
                DataManager.getFromRemote().report(user_id, user_token, yo_id, 0, tv_violate.getText().toString())
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

                DataManager.getFromRemote().report(user_id, user_token, yo_id, 0, tv_else.getText().toString())
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
