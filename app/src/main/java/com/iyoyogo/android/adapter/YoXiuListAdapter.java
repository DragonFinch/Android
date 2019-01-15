package com.iyoyogo.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.yoxiu.YouXiuListBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.ui.home.yoji.UserHomepageActivity;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuDetailActivity;
import com.iyoyogo.android.ui.home.yoxiu.AllCommentActivity;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuListActivity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.GlideRoundTransform;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;

import java.util.List;

import io.reactivex.functions.Consumer;

/*8
yo秀列表的适配器
 */
public class YoXiuListAdapter extends RecyclerView.Adapter<YoXiuListAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<YouXiuListBean.DataBean.ListBean> mList;
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示
    private Activity activity;
    private int yo_id;
    private String user_id;
    private String user_token;
    private PopupWindow popup;
    private int count_collect;
    TextView tv_message;
    ImageView img_tip;
    TextView tv_message_two;
    TextView tv_message_three;
    private View view;

    public YoXiuListAdapter(Context context, List<YouXiuListBean.DataBean.ListBean> mList) {
        this.context = context;
        this.mList = mList;
        activity = (Activity) context;
        initPopup();
    }

    private void initDislike() {
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
                        .dislike(user_id, user_token, yo_id, 1)
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
                        .dislike(user_id, user_token, yo_id, 2)
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
        popupWindow.showAtLocation(activity.findViewById(R.id.activity_yoxiu_list), Gravity.CENTER, 0, 0);
    }

    private void initDelete(ViewHolder holder, String yo_user_id, int yo_id) {
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_like, null);
        PopupWindow popupWindow = new PopupWindow(view, DensityUtil.dp2px(context, 125), DensityUtil.dp2px(context, 50), true);
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
                initDislike();

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
        popupWindow.showAsDropDown(holder.img_more, DensityUtil.dp2px(context, -95), 0);
    }

    // 暴露接口，改变fadeTips的方法
    public boolean isFadeTips() {
        return fadeTips;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_yoxiu_list, null);
        view.setOnClickListener(this);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.tv_yoxiu_desc.setText(mList.get(position).getPosition_name());
        viewHolder.img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDelete(viewHolder, String.valueOf(mList.get(position).getUser_id()), mList.get(position).getId());
            }
        });
        viewHolder.num_browse.setText(mList.get(position).getCount_view());
        viewHolder.num_like.setText(mList.get(position).getCount_praise() + "");
        viewHolder.user_name.setText(mList.get(position).getUser_nickname());
        viewHolder.comment_all.setText("全部评论(" + mList.get(position).getCount_comment() + ")");
        int file_type = mList.get(position).getFile_type();
        RequestOptions requestOptions = new RequestOptions().placeholder(R.mipmap.default_touxiang).error(R.mipmap.default_touxiang);
        if (file_type == 2) {
            Glide.with(context).load(mList.get(position).getUser_logo()).apply(requestOptions).into(viewHolder.user_icon);
            RequestOptions myOptions = new RequestOptions()
                    .centerCrop()
                    .transform(new GlideRoundTransform(context, 8));
            Glide.with(context).load(mList.get(position).getFile_path())
                    .apply(myOptions).into(viewHolder.img_yoxiu);
            viewHolder.img_video.setVisibility(View.VISIBLE);
        } else {
            Glide.with(context).load(mList.get(position).getUser_logo()).apply(requestOptions).into(viewHolder.user_icon);
            RequestOptions myOptions = new RequestOptions()
                    .centerCrop()
                    .transform(new GlideRoundTransform(context, 8));
            Glide.with(context).load(mList.get(position).getFile_path())
                    .apply(myOptions).into(viewHolder.img_yoxiu);
        }
        viewHolder.tv_yoxiu_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YoXiuListActivity.class);
                intent.putExtra("position", mList.get(position).getPosition_name());
                context.startActivity(intent);
            }
        });
        viewHolder.img_level.setVisibility(View.VISIBLE);
        viewHolder.medal.setVisibility(View.VISIBLE);

        int partner_type = mList.get(position).getPartner_type();
        if (partner_type == 0) {
            mList.get(position).setPartner_type(0);
            viewHolder.medal.setVisibility(View.INVISIBLE);
        } else if (partner_type == 1) {
            mList.get(position).setPartner_type(1);
            viewHolder.medal.setImageResource(R.mipmap.daren);
        } else if (partner_type == 2) {
            mList.get(position).setPartner_type(2);
            viewHolder.medal.setImageResource(R.mipmap.hongren);
        } else if (partner_type == 3) {
            mList.get(position).setPartner_type(3);
            viewHolder.medal.setImageResource(R.mipmap.kol);
        } else {
            viewHolder.medal.setVisibility(mList.get(position).getPartner_type() == 0 ? View.INVISIBLE : View.VISIBLE);
        }
        int user_level = mList.get(position).getUser_level();
        if (user_level == 0) {
            viewHolder.img_level.setVisibility(View.INVISIBLE);
        } else if (user_level == 1) {
            mList.get(position).setUser_level(1);
            viewHolder.img_level.setImageResource(R.mipmap.lv1);

        } else if (user_level == 2) {
            mList.get(position).setUser_level(2);
            viewHolder.img_level.setImageResource(R.mipmap.lv2);
        } else if (user_level == 3) {
            mList.get(position).setUser_level(3);
            viewHolder.img_level.setImageResource(R.mipmap.lv3);
        } else if (user_level == 4) {
            mList.get(position).setUser_level(4);
            viewHolder.img_level.setImageResource(R.mipmap.lv4);
        } else if (user_level == 5) {
            mList.get(position).setUser_level(5);
            viewHolder.img_level.setImageResource(R.mipmap.lv5);
        } else {
            viewHolder.img_level.setVisibility(View.INVISIBLE);
        }
        viewHolder.user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserHomepageActivity.class);
                intent.putExtra("yo_user_id", String.valueOf(mList.get(position).getUser_id()));
                context.startActivity(intent);
            }
        });
        viewHolder.recycler_comment.setLayoutManager(new LinearLayoutManager(context));
        List<YouXiuListBean.DataBean.ListBean.CommentListBean> comment_list = mList.get(position).getComment_list();
        YoXiuListItemAdapter adapter = new YoXiuListItemAdapter(context, comment_list);
        viewHolder.recycler_comment.setAdapter(adapter);
        viewHolder.itemView.setTag(position);

        viewHolder.recycler_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mList.get(position).getId();
                Intent intent = new Intent(context, YoXiuDetailActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });
        viewHolder.comment_all.setOnClickListener(new View.OnClickListener() {//全部
            @Override
            public void onClick(View v) {
                int id = mList.get(position).getId();
                Intent intent = new Intent(context, AllCommentActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });
        if (mList.get(position).getIs_my_like() == 0) {
            viewHolder.img_like.setImageResource(R.mipmap.datu_xihuan);
        } else {
            viewHolder.img_like.setImageResource(R.mipmap.yixihuan_xiangqing);
        }
        viewHolder.img_like.setImageResource(mList.get(position).getIs_my_like() == 0 ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);
        yo_id = mList.get(position).getId();

        viewHolder.img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = SpUtils.getString(context, "user_id", null);
                String user_token = SpUtils.getString(context, "user_token", null);

               /* Drawable like = getResources().getDrawable(
                        R.mipmap.xihuan_xiangqing);
                Drawable liked = getResources().getDrawable(
                        R.mipmap.yixihuan_xiangqing);*/
                String count_praise = mList.get(position).getCount_praise();
                int count_praises = Integer.parseInt(count_praise);
                viewHolder.img_like.setImageResource(mList.get(position).getIs_my_like() > 0 ? R.mipmap.yixihuan_xiangqing : R.mipmap.datu_xihuan);

                Log.d("Test", "dataBeans.get(0).getIs_my_like():" + mList.get(position).getIs_my_like());
                if (mList.get(position).getIs_my_like() > 0) {
                    //由喜欢变为不喜欢，亮变暗
                    viewHolder.img_like.setImageResource(R.mipmap.datu_xihuan);
                    count_praises -= 1;
                    //设置点赞的数量
                    viewHolder.num_like.setText(count_praises + "");
                    mList.get(position).setIs_my_like(0);
                    mList.get(position).setCount_praise(String.valueOf(count_praises));

                } else {
                    //由不喜欢变为喜欢，暗变亮
                    viewHolder.img_like.setImageResource(R.mipmap.yixihuan_xiangqing);
                    count_praises += 1;
                    //设置点赞的数量
                    viewHolder.num_like.setText(count_praises + "");
                    mList.get(position).setIs_my_like(1);
                    mList.get(position).setCount_praise(String.valueOf(count_praises));

                }
                DataManager.getFromRemote().praise(user_id, user_token, mList.get(position).getId(), 0)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                            }
                        });
            }
        });
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
        popup.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public void loadMore(int comment_id) {
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

    @Override
    public int getItemCount() {
        return mList.size();
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

    public void setOnItemClickListener(OnClickListener onItemClickListener) {
        this.onClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_yoxiu_desc, num_like, user_name, comment_all, num_browse;
        ImageView img_yoxiu, img_like, img_more, img_video, medal, img_level;
        CircleImageView user_icon;
        RecyclerView recycler_comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medal = itemView.findViewById(R.id.medal);
            img_level = itemView.findViewById(R.id.img_level);
            tv_yoxiu_desc = itemView.findViewById(R.id.tv_yoxiu_desc);
            num_like = itemView.findViewById(R.id.num_like);
            img_more = itemView.findViewById(R.id.img_more);
            user_name = itemView.findViewById(R.id.tv_user_name);
            comment_all = itemView.findViewById(R.id.comment_all);
            user_icon = itemView.findViewById(R.id.user_icon);
            img_like = itemView.findViewById(R.id.img_like);
            recycler_comment = itemView.findViewById(R.id.recycler_comment);
            img_yoxiu = itemView.findViewById(R.id.img_yoxiu);
            img_video = itemView.findViewById(R.id.img_video);
            num_browse = itemView.findViewById(R.id.num_browse);
        }
    }
}
