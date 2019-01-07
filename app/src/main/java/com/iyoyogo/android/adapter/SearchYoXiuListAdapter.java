package com.iyoyogo.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.search.KeywordBean;
import com.iyoyogo.android.bean.search.KeywordUserBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.ui.home.yoxiu.AllCommentActivity;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuDetailActivity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.GlideRoundTransform;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;

import java.util.List;

import io.reactivex.functions.Consumer;

public class SearchYoXiuListAdapter extends RecyclerView.Adapter<SearchYoXiuListAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<KeywordBean.DataBean.YoxListBean> mList;
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示
    private Activity activity;
    private int yo_id;
    private String user_id;
    private String user_token;

    public SearchYoXiuListAdapter(Context context, List<KeywordBean.DataBean.YoxListBean> mList) {
        this.context = context;
        this.mList = mList;
        activity = (Activity) context;
    }

    // 暴露接口，改变fadeTips的方法
    public boolean isFadeTips() {
        return fadeTips;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_yoxiu_list, null);
        view.setOnClickListener(this);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.tv_yoxiu_desc.setText(mList.get(position).getPosition_name());
        viewHolder.tv_yoxiu_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
     /*   viewHolder.img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        viewHolder.num_browse.setText(mList.get(position).getCount_view()+"");
        viewHolder.user_name.setText(mList.get(position).getUser_info().getUser_nickname());
        viewHolder.comment_all.setText("全部评论(" + mList.get(position).getCount_view() + ")");
        int file_type = mList.get(position).getFile_type();
        if (file_type == 2) {
            Glide.with(context).load(mList.get(position).getUser_info().getUser_logo()).into(viewHolder.user_icon);
            RequestOptions myOptions = new RequestOptions()
                    .centerCrop()
                    .transform(new GlideRoundTransform(context, 8));
            Glide.with(context).load(mList.get(position).getFile_path())
                    .apply(myOptions).into(viewHolder.img_yoxiu);
            viewHolder.img_video.setVisibility(View.VISIBLE);
        } else {
            Glide.with(context).load(mList.get(position).getUser_info().getUser_logo()).into(viewHolder.user_icon);
            RequestOptions myOptions = new RequestOptions()
                    .centerCrop()
                    .transform(new GlideRoundTransform(context, 8));
            Glide.with(context).load(mList.get(position).getFile_path())
                    .apply(myOptions).into(viewHolder.img_yoxiu);
        }
        Glide.with(context).load(mList.get(position).getUser_info().getUser_logo()).into(viewHolder.user_icon);
        //条目页面
        viewHolder.recycler_comment.setLayoutManager(new LinearLayoutManager(context));
        List<KeywordBean.DataBean.YoxListBean.CommentListBean> yox_list = mList.get(position).getComment_list();

            SearchYoXiuAdapter adapter = new SearchYoXiuAdapter(context, yox_list);
            viewHolder.recycler_comment.setAdapter(adapter);
//        viewHolder.itemView.setTag(position);
        viewHolder.user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int yo_id = mList.get(position).getYo_id();
                Intent intent = new Intent(context, YoXiuDetailActivity.class);
                intent.putExtra("id", yo_id);
                context.startActivity(intent);
            }
        });
        viewHolder.comment_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AllCommentActivity.class);
                intent.putExtra("yo_id",mList.get(position).getYo_id());
                context.startActivity(intent);
            }
        });
    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int yo_id = mList.get(position).getYo_id();
            Intent intent = new Intent(context, YoXiuDetailActivity.class);
            intent.putExtra("id", yo_id);
            context.startActivity(intent);
        }
    });
        viewHolder.num_like.setText(mList.get(position).getCount_praise() + "");
     //   viewHolder.img_like.setImageResource(mList.get(position).isIs_my_praise() == true ?R.mipmap.yixihuan_xiangqing : R.mipmap.datu_xihuan);

/*        if (mList.get(position).isIs_my_praise() ){
            viewHolder.img_like.setImageResource(R.mipmap.datu_xihuan);
        } else {
            viewHolder.img_like.setImageResource(R.mipmap.yixihuan_xiangqing);
        }*/
        viewHolder.img_like.setImageResource(mList.get(position).isIs_my_praise() == true ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);
        yo_id = mList.get(position).getYo_id();

        viewHolder.img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = SpUtils.getString(context, "user_id", null);
                String user_token = SpUtils.getString(context, "user_token", null);
                int count_praise = mList.get(position).getCount_praise();
                mList.get(position).setYo_id(mList.get(position).getYo_id() == 1 ? 0 : 1);
                if (mList.get(position).getYo_id() == 1) {
                    count_praise += 1;
                    mList.get(position).setCount_praise(count_praise);
                    viewHolder.num_like.setText(count_praise + "");
                    viewHolder.img_like.setImageResource(mList.get(position).isIs_my_praise() == true ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);

                } else if (count_praise > 0) {
                    count_praise -= 1;
                    mList.get(position).setCount_praise(count_praise);
                    viewHolder.num_like.setText(count_praise + "");
                    viewHolder.img_like.setImageResource(mList.get(position).isIs_my_praise() == false ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);

                }

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
    }

    private void initPopup(ViewHolder viewHolder) {
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_like, null);
        PopupWindow popupWindow = new PopupWindow(view, DensityUtil.dp2px(context, 130), DensityUtil.dp2px(context, 110), true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        TextView tv_dislike = view.findViewById(R.id.tv_dislike);
        tv_dislike.setVisibility(View.GONE);
        TextView tv_report = view.findViewById(R.id.tv_report);
        user_id = SpUtils.getString(context, "user_id", null);
        user_token = SpUtils.getString(context, "user_token", null);
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
                loadMore();
            }
        });
        popupWindow.showAsDropDown(viewHolder.img_more, DensityUtil.dp2px(context, 30), DensityUtil.dp2px(context, 10));
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
        TextView tv_yoxiu_desc, num_like, user_name, comment_all,num_browse;
        ImageView img_yoxiu, img_like, img_more, img_video;
        CircleImageView user_icon;
        RecyclerView recycler_comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            num_browse = itemView.findViewById(R.id.num_browse);
            tv_yoxiu_desc = itemView.findViewById(R.id.tv_yoxiu_desc);
            num_like = itemView.findViewById(R.id.num_like);
//            img_more = itemView.findViewById(R.id.img_more);
            user_name = itemView.findViewById(R.id.tv_user_name);
            comment_all = itemView.findViewById(R.id.comment_all);
            user_icon = itemView.findViewById(R.id.user_icon);
            img_like = itemView.findViewById(R.id.img_like);
            recycler_comment = itemView.findViewById(R.id.recycler_comment);
            img_yoxiu = itemView.findViewById(R.id.img_yoxiu);
            img_video = itemView.findViewById(R.id.img_video);
        }
    }
}
