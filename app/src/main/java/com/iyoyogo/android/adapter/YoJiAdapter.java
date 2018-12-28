package com.iyoyogo.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.home.HomeBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.ui.home.yoji.UserHomepageActivity;
import com.iyoyogo.android.ui.mine.homepage.Personal_homepage_Activity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.GlideRoundTransform;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;
import com.iyoyogo.android.widget.PileLayout;

import java.util.List;

import io.reactivex.functions.Consumer;

public class YoJiAdapter extends RecyclerView.Adapter<YoJiAdapter.Holder> implements View.OnClickListener {
    private List<HomeBean.DataBean.YojListBean> mList;
    private Context context;
    private String user_token;
    private String user_id;
    private final Activity activity;
    private int yo_id;

    public YoJiAdapter(Context context, List<HomeBean.DataBean.YojListBean> mList) {
        this.mList = mList;
        this.context = context;
        activity = (Activity) context;
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
       holder.user_icon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int yo_user_id = mList.get(position).getUser_info().getUser_id();
               Intent intent = new Intent(context, UserHomepageActivity.class);
//               Intent intent = new Intent(context, Personal_homepage_Activity.class);
               intent.putExtra("yo_user_id", String.valueOf(yo_user_id));
               context.startActivity(intent);
           }
       });
        holder.user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int yo_user_id = mList.get(position).getUser_info().getUser_id();
                Intent intent = new Intent(context, Personal_homepage_Activity.class);
                intent.putExtra("yo_user_id", String.valueOf(yo_user_id));
                context.startActivity(intent);
            }
        });
        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.default_ic)
                .error(R.mipmap.default_ic)
                .override(DensityUtil.dp2px(context, ViewGroup.LayoutParams.MATCH_PARENT), DensityUtil.dp2px(context, 200))
                .transform(new GlideRoundTransform(context, 8));
        Log.d("YoJiAdapter", mList.get(position).getFile_path());
        Glide.with(context).load(mList.get(position).getFile_path()).apply(myOptions).into(holder.zuji_image);
        holder.location.setText(mList.get(position).getP_start());
        holder.num_look.setText(mList.get(position).getCount_view() + "");
        Log.d("YoJiAdapter", "mList.get(position).getUsers_praise():" + mList.get(position).getUsers_praise());
        LayoutInflater inflater = LayoutInflater.from(context);
        if (mList.get(position).getUsers_praise().size() == 0) {
            holder.pile_layout.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < mList.get(position).getUsers_praise().size(); i++) {
                com.iyoyogo.android.view.CircleImageView imageView = (com.iyoyogo.android.view.CircleImageView) inflater.inflate(R.layout.item_head_image, holder.pile_layout, false);
                Glide.with(context).load(mList.get(position).getUsers_praise().get(i).getUser_logo()).into(imageView);
                holder.pile_layout.addView(imageView);
                int finalI = i;
                int yo_user_id = mList.get(position).getUsers_praise().get(i).getUser_id();

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Personal_homepage_Activity.class);
                        intent.putExtra("yo_user_id", String.valueOf(yo_user_id));
                        context.startActivity(intent);
                        Toast.makeText(context, "mList.get(position).getUsers_praise().get(i).getUser_id():" + mList.get(position).getUsers_praise().get(finalI).getUser_id(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        if (mList.get(position).getUser_info().getUser_logo().equals("")){
            Glide.with(context).load(R.mipmap.default_touxiang).into(holder.user_icon);
        }else {
            Glide.with(context).load(mList.get(position).getUser_info().getUser_logo()).into(holder.user_icon);
        }
        holder.user_name.setText(mList.get(position).getUser_info().getUser_nickname());
        holder.title.setText(mList.get(position).getTitle());
        holder.tv_cost.setText(" ￥" + mList.get(position).getCost() + "/人");
        holder.tv_day.setText(mList.get(position).getCount_dates() + "天");
        holder.tv_num_comment.setText("全部评论(" + mList.get(position).getCount_comment() + ")");
        holder.tv_num_like.setText("等" + mList.get(position).getCount_praise() + "人喜欢过");
        List<HomeBean.DataBean.YojListBean.CommentListBean> comment_list = mList.get(position).getComment_list();
        YoJiListItemAdapter adapter = new YoJiListItemAdapter(context, comment_list);
        Log.d("YoJiAdapter", "comment_list:" + comment_list.size());
        holder.recycler_comment.setLayoutManager(new LinearLayoutManager(context));
        holder.recycler_comment.setAdapter(adapter);
        holder.dt_like.setImageResource(mList.get(position).getIs_my_praise() > 0 ? R.mipmap.yixihuan_xiangqing : R.mipmap.datu_xihuan);

        holder.dt_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count_praise = mList.get(position).getCount_praise();

                Log.d("Test", "dataBeans.get(0).getIs_my_like():" + mList.get(position).getIs_my_praise());
                if (mList.get(position).getIs_my_praise() > 0) {
                    //由喜欢变为不喜欢，亮变暗
                    holder.dt_like.setImageResource(R.mipmap.datu_xihuan);
                    count_praise -= 1;
                    //设置点赞的数量
                    holder.tv_num_like.setText("等" + mList.get(position).getCount_praise() + "人喜欢过");
                    mList.get(position).setIs_my_praise(0);
                    mList.get(position).setCount_praise(count_praise);
                } else {
                    //由不喜欢变为喜欢，暗变亮
                    holder.dt_like.setImageResource(R.mipmap.yixihuan_xiangqing);
                    count_praise += 1;
                    //设置点赞的数量
                    holder.tv_num_like.setText("等" + mList.get(position).getCount_praise() + "人喜欢过");
                    mList.get(position).setIs_my_praise(1);
                    mList.get(position).setCount_praise(count_praise);
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
        yo_id = mList.get(position).getYo_id();
        holder.view_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopup(holder);
            }
        });
        holder.itemView.setTag(position);

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
            }
        });
        popupWindow.showAsDropDown(holder.view_like, DensityUtil.dp2px(context, 30), DensityUtil.dp2px(context, 10));
    }

    private void initDislike() {
        View view = LayoutInflater.from(context).inflate(R.layout.item_popwindow_not_like, null);
        PopupWindow popupWindow = new PopupWindow(view, DensityUtil.dp2px(context, 300), DensityUtil.dp2px(context, 230), true);
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
                                if (onRetryClickListener != null) {
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
                                if (onRetryClickListener != null) {
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

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnClickListener {
        void onClick(View v, int position);
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

    public interface OnRetryClickListener {
        void onretry();
    }

    private OnRetryClickListener onRetryClickListener;

    public void setOnRetryClickListener(OnRetryClickListener onRetryClickListener) {
        this.onRetryClickListener = onRetryClickListener;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView zuji_image, typeImageView, dt_like;
        CircleImageView user_icon;
        TextView num_look, user_name, title, tv_cost, location, tv_day, tv_num_like, tv_num_comment;
        RelativeLayout view_like;
        PileLayout pile_layout;
        RecyclerView recycler_comment;

        public Holder(@NonNull View itemView) {
            super(itemView);
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
        }
    }
}
