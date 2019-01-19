package com.iyoyogo.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.ui.home.yoji.ReplyDiscussActivity;
import com.iyoyogo.android.ui.home.yoji.UserHomepageActivity;
import com.iyoyogo.android.ui.mine.homepage.Personal_homepage_Activity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.util.MyConversionUtil;
import com.iyoyogo.android.widget.CircleImageView;

import java.util.List;

import io.reactivex.functions.Consumer;

public class YoJiDetailCommentAdapter extends RecyclerView.Adapter<YoJiDetailCommentAdapter.Holder> {
    private Context context;
    private TextView tv_message;
    private TextView tv_message_two;
    private TextView tv_message_three;
    private ImageView img_tip;
    private PopupWindow popup;
    private final Activity activity;
    private List<CommentBean.DataBean.ListBean> mList;
    private String user_id;
    private String user_token;
    private final int MAX_LINE_COUNT = 7;//最大显示行数

    private final int STATE_UNKNOW = -1;//未知状态

    private final int STATE_NOT_OVERFLOW = 1;//文本行数小于最大可显示行数

    private final int STATE_COLLAPSED = 2;//折叠状态

    private final int STATE_EXPANDED = 3;//展开状态

    private SparseArray<Integer> mTextStateList;//保存文本状态集合
    public YoJiDetailCommentAdapter(Context context, List<CommentBean.DataBean.ListBean> mList) {
        this.context = context;
        this.mList = mList;
        activity = (Activity) context;
        initPopup();
        mTextStateList=new SparseArray<>();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
        Holder holder = new Holder(view);
        user_id = SpUtils.getString(context, "user_id", null);
        return holder;
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

        user_token = SpUtils.getString(context, "user_token", null);
        tv_advert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_more.dismiss();
                report();

                DataManager.getFromRemote().report(user_id, user_token, 0, comment_id, tv_advert.getText().toString())
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
                DataManager.getFromRemote().report(user_id, user_token, 0, comment_id, tv_harm.getText().toString())
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
                DataManager.getFromRemote().report(user_id, user_token, 0, comment_id, tv_violate.getText().toString())
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
                DataManager.getFromRemote().report(user_id, user_token, 0, comment_id, tv_else.getText().toString())
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
        popup_more.showAtLocation(activity.findViewById(R.id.activity_yoji_detail), Gravity.BOTTOM, 0, 0);
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

    public interface DeleteOnClickListener {
        void delete();
    }

    DeleteOnClickListener deleteOnClickListener;

    public void setDeleteOnClickListener(DeleteOnClickListener deleteOnClickListener) {
        this.deleteOnClickListener = deleteOnClickListener;
    }

    public void comment() {
        backgroundAlpha(0.6f);
        tv_message.setText("Hi~");
        tv_message.setTextColor(Color.parseColor("#FA800A"));
        tv_message_two.setTextColor(Color.parseColor("#FA800A"));
        tv_message_three.setTextColor(Color.parseColor("#FA800A"));
        img_tip.setImageResource(R.mipmap.stamo_heart);
        tv_message_two.setText("谢谢评论~");
        tv_message_three.setText("给你小心心");
        popup.showAtLocation(activity.findViewById(R.id.activity_yoji_detail), Gravity.CENTER, 0, 0);
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
        popup.showAtLocation(activity.findViewById(R.id.activity_yoji_detail), Gravity.CENTER, 0, 0);
    }

    public void initPopup() {
        View view = LayoutInflater.from(context).inflate(R.layout.like_layout, null);
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
    private void initDelete(Holder holder, String yo_user_id, int comment_id,  int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_delete_or_report, null);
        PopupWindow popupWindow = new PopupWindow(view, DensityUtil.dp2px(context, 125), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        String user_id = SpUtils.getString(context, "user_id", null);
        String user_token = SpUtils.getString(context, "user_token", null);
        TextView tv_delete = view.findViewById(R.id.tv_delete);
        TextView tv_report = view.findViewById(R.id.tv_report);
        Log.d("YoJiDetailCommentAdapte", yo_user_id);
        Log.d("YoJiDetailCommentAdapte", user_id);
        if (yo_user_id.equals(user_id)) {
            tv_delete.setVisibility(View.VISIBLE);
            tv_report.setVisibility(View.GONE);
        } else {
            tv_delete.setVisibility(View.GONE);
            tv_report.setVisibility(View.VISIBLE);
        }
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getFromRemote().deleteComment(user_id, user_token, comment_id)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                if (deleteOnClickListener != null) {
                                    deleteOnClickListener.delete();
                                    mList.remove(position);
                                    notifyDataSetChanged();
                                }
                            }
                        });
                popupWindow.dismiss();
            }
        });
        tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                loadMore(comment_id);
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        backgroundAlpha(0.6f);
        popupWindow.setOnDismissListener(new poponDismissListener());
        popupWindow.showAsDropDown(holder.img_function, DensityUtil.dp2px(context,-95),  DensityUtil.dp2px(context,5));
    }
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        CommentBean.DataBean.ListBean listBean = mList.get(position);
        int state = mTextStateList.get(position, STATE_UNKNOW);
        if (state == STATE_UNKNOW) {
            holder.tv_content.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    //这个回掉会调用多次，获取玩行数后记得注销监听
                    holder.tv_content.getViewTreeObserver().removeOnPreDrawListener(this);
                    //如果内容显示的行数大于限定显示行数
                    if (holder.tv_content.getLineCount() > MAX_LINE_COUNT) {
                        holder.tv_content.setMaxLines(MAX_LINE_COUNT);//设置最大显示行数
                        holder.homeShowOrHide.setVisibility(View.VISIBLE);//让其显示全文的文本框状态为显示
                        holder.homeShowOrHide.setText("全文");//设置其文字为全文
                        mTextStateList.put(position, STATE_COLLAPSED);
                    } else {
                        holder.homeShowOrHide.setVisibility(View.INVISIBLE);//显示全文隐藏
                        mTextStateList.put(position, STATE_NOT_OVERFLOW);//让其不能超过限定的行数
                    }
                    return true;
                }
            });
        } else {
            //            如果之前已经初始化过了，则使用保存的状态，无需在获取一次
            switch (state) {
                case STATE_NOT_OVERFLOW:
                    holder.homeShowOrHide.setVisibility(View.INVISIBLE);
                    break;
                case STATE_COLLAPSED:
                    holder.tv_content.setMaxLines(MAX_LINE_COUNT);
                    holder.homeShowOrHide.setVisibility(View.VISIBLE);
                    holder.homeShowOrHide.setText("全文");
                    break;
                case STATE_EXPANDED:
                    holder.tv_content.setMaxLines(Integer.MAX_VALUE);
                    holder.homeShowOrHide.setVisibility(View.VISIBLE);
                    holder.homeShowOrHide.setText("收起");
                    break;
            }
        }


        holder.homeShowOrHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = mTextStateList.get(position, STATE_UNKNOW);
                if (state == STATE_COLLAPSED) {
                    holder.tv_content.setMaxLines(Integer.MAX_VALUE);
                    holder.homeShowOrHide.setText("收起");
                    mTextStateList.put(position, STATE_EXPANDED);
                } else if (state == STATE_EXPANDED) {
                    holder.tv_content.setMaxLines(MAX_LINE_COUNT);
                    holder.homeShowOrHide.setText("全文");
                    mTextStateList.put(position, STATE_COLLAPSED);
                }
            }
        });
        MyConversionUtil.getInstace().getFileText(context);
        SpannableString spannableString =   MyConversionUtil.getInstace().getExpressionString(context, mList.get(position).getContent());
        holder.tv_content.setText(spannableString);
        holder.tv_comment_like_num.setText(listBean.getCount_praise() + "");
        holder.tv_huifu_num.setText(listBean.getCount_comment() + "");
        holder.tv_time.setText(listBean.getCreate_time());
        holder.user_name.setText(listBean.getUser_nickname());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.mipmap.default_touxiang)
                .placeholder(R.mipmap.default_touxiang);
        if (listBean.getUser_logo().equals("")) {
            holder.img_user_icon.setImageResource(R.mipmap.default_touxiang);
        } else {
            Glide.with(context).load(listBean.getUser_logo())
                    .apply(requestOptions)
                    .into(holder.img_user_icon);

        }

        RoundedCorners roundedCorners = new RoundedCorners(6);
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        holder.img_huifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReplyDiscussActivity.class);
                intent.putExtra("data", mList.get(position));
                context.startActivity(intent);
            }
        });
        if (mList.get(position).getIs_my_praise() == 0) {
            holder.img_comment_like.setImageResource(R.mipmap.zan_select);
        } else {
            holder.img_comment_like.setImageResource(R.mipmap.zan_selected);
        }
        holder.img_comment_like.setImageResource(mList.get(position).getIs_my_praise() == 0 ? R.mipmap.zan_select : R.mipmap.zan_selected);

        holder.img_comment_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = SpUtils.getString(context, "user_id", null);
                String user_token = SpUtils.getString(context, "user_token", null);

                int count_praise = mList.get(position).getCount_praise();
                mList.get(position).setIs_my_praise(mList.get(position).getIs_my_praise() == 1 ? 0 : 1);
                if (mList.get(position).getIs_my_praise() == 1) {
                    count_praise += 1;
                } else if (count_praise > 0) {
                    count_praise -= 1;
                }
                mList.get(position).setCount_praise(count_praise);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataManager.getFromRemote().praise(user_id, user_token, 0, mList.get(position).getId())
                                .subscribe(new Consumer<BaseBean>() {
                                    @Override
                                    public void accept(BaseBean baseBean) throws Exception {
                                        notifyDataSetChanged();
                                    }
                                });
                    }
                }).start();

            }
        });

        holder.img_function.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDelete(holder, String.valueOf(mList.get(position).getUser_id()), mList.get(position).getId(),  position);
            }
        });

        holder.img_user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int yo_user_id = mList.get(position).getUser_id();
                String yo_user_ids = yo_user_id+"";
                if (yo_user_ids.equals(user_id)){
                    Intent intent = new Intent(context, Personal_homepage_Activity.class);
                    intent.putExtra("yo_user_id", String.valueOf(yo_user_id));
                    context.startActivity(intent);
                }else {
                    Intent intent = new Intent(context, UserHomepageActivity.class);
                    intent.putExtra("yo_user_id", String.valueOf(yo_user_id));
                    context.startActivity(intent);
                }
            }
        });

        holder.medal.setVisibility(View.VISIBLE);
        int user_level = mList.get(position).getUser_level();
        holder.medal.setVisibility(View.VISIBLE);
        int partner_type = mList.get(position).getPartner_type();
        if (partner_type == 0) {
            mList.get(position).setPartner_type(0);
            holder.medal.setVisibility(View.INVISIBLE);
        } else if (partner_type == 1) {
            mList.get(position).setPartner_type(1);
            holder.medal.setImageResource(R.mipmap.daren);
        } else if (partner_type == 2) {
            mList.get(position).setPartner_type(2);
            holder.medal.setImageResource(R.mipmap.hongren);
        } else if (partner_type == 3) {
            mList.get(position).setPartner_type(3);
            holder.medal.setImageResource(R.mipmap.kol);
        } else {
            holder.medal.setVisibility(mList.get(position).getPartner_type() == 0 ? View.INVISIBLE : View.VISIBLE);
        }
        if (user_level == 0) {
            holder.img_level.setVisibility(View.GONE);
        } else if (user_level == 1) {
            mList.get(position).setUser_level(1);
            holder.img_level.setImageResource(R.mipmap.lv1);
            holder.img_level.setVisibility(View.VISIBLE);
        } else if (user_level == 2) {
            mList.get(position).setUser_level(2);
            holder.img_level.setImageResource(R.mipmap.lv2);
            holder.img_level.setVisibility(View.VISIBLE);
        } else if (user_level == 3) {
            mList.get(position).setUser_level(3);
            holder.img_level.setImageResource(R.mipmap.lv3);
            holder.img_level.setVisibility(View.VISIBLE);
        } else if (user_level == 4) {
            mList.get(position).setUser_level(4);
            holder.img_level.setImageResource(R.mipmap.lv4);
            holder.img_level.setVisibility(View.VISIBLE);
        } else if (user_level == 5) {
            mList.get(position).setUser_level(5);
            holder.img_level.setImageResource(R.mipmap.lv5);
            holder.img_level.setVisibility(View.VISIBLE);
        } else {
            holder.img_level.setVisibility(View.INVISIBLE);
        }

    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CircleImageView img_user_icon;
        TextView tv_content, tv_time, user_name, tv_comment_like_num, tv_huifu_num,homeShowOrHide;
        ImageView img_comment_like, img_huifu, img_function,medal,img_level;

        public Holder(@NonNull View itemView) {
            super(itemView);
            homeShowOrHide = itemView.findViewById(R.id.tv_show_hide);
            tv_comment_like_num = itemView.findViewById(R.id.tv_comment_like_num);
            tv_huifu_num = itemView.findViewById(R.id.tv_huifu_num);
            img_user_icon = itemView.findViewById(R.id.img_user_icon);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_time = itemView.findViewById(R.id.tv_time);
            user_name = itemView.findViewById(R.id.user_name);
            img_comment_like = itemView.findViewById(R.id.img_comment_like);
            img_huifu = itemView.findViewById(R.id.img_huifu);
            img_function = itemView.findViewById(R.id.img_function);
            medal = itemView.findViewById(R.id.medal);
            img_level =itemView.findViewById(R.id.img_level);
        }
    }
}