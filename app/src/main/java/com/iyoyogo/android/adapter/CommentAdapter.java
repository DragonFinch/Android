package com.iyoyogo.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.ui.home.recommend.YoXiuDetailActivity;
import com.iyoyogo.android.utils.DensityUtil;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.Holder> {
    private Context context;
    private TextView tv_message;
    private TextView tv_message_two;
    private TextView tv_message_three;
    private ImageView img_tip;
    private PopupWindow popup;

    public CommentAdapter(Context context) {
        this.context = context;

        initPopup();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
        Holder holder = new Holder(view);
        return holder;
    }
    public void loadMore(Holder holder) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_more, null);
        PopupWindow popup_more = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popup_more.setBackgroundDrawable(new ColorDrawable());
        popup_more.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup_more.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口
        //广告信息
        TextView tv_advert = view.findViewById(R.id.tv_advert);
        tv_advert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report(holder);
                popup_more.dismiss();
            }
        });
        //有害信息
        TextView tv_harm = view.findViewById(R.id.tv_harm);
        tv_harm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report(holder);
                popup_more.dismiss();
            }
        });
        //违法违规
        TextView tv_violate = view.findViewById(R.id.tv_violate);
        tv_violate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report(holder);
                popup_more.dismiss();
            }
        });
        //其他
        TextView tv_else = view.findViewById(R.id.tv_else);
        tv_else.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report(holder);
                popup_more.dismiss();
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

        //添加pop窗口关闭事件
        popup_more.setOnDismissListener(new poponDismissListener());
        popup_more.showAtLocation(holder.itemView.findViewById(R.id.activity_yoxiu_detail), Gravity.BOTTOM, 0, 0);
    }

    public void initPopup() {
        View view = LayoutInflater.from(context).inflate(R.layout.like_layout, null);
        popup = new PopupWindow(view, DensityUtil.dp2px(context, 300), DensityUtil.dp2px(context, 145), true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable());
        tv_message = view.findViewById(R.id.tv_message);
        tv_message_two = view.findViewById(R.id.tv_message_two);

        tv_message_three = view.findViewById(R.id.tv_message);
        img_tip = view.findViewById(R.id.tip_img);


        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口

        backgroundAlpha(0.6f);

        //添加pop窗口关闭事件
        popup.setOnDismissListener(new poponDismissListener());
    }
    public void backgroundAlpha(float bgAlpha) {
        Activity activity=(Activity)context;
        WindowManager.LayoutParams lp =activity.getWindow().getAttributes();
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
    public void like(Holder holder) {
        tv_message.setText("Hi~");
        img_tip.setImageResource(R.mipmap.stamo_heart);
        tv_message_two.setText("谢谢喜欢~");
        tv_message_three.setText("给你小心心");
        popup.showAtLocation(holder.itemView.findViewById(R.id.activity_yoxiu_detail), Gravity.CENTER, 0, 0);
    }

    public void comment(Holder holder) {
        tv_message.setText("Hi~");
        img_tip.setImageResource(R.mipmap.stamo_heart);
        tv_message_two.setText("谢谢评论~");
        tv_message_three.setText("给你小心心");
        popup.showAtLocation(holder.itemView.findViewById(R.id.activity_yoxiu_detail), Gravity.CENTER, 0, 0);
    }

    public void report(Holder holder) {
        tv_message.setText("举报成功");
        tv_message.setTextColor(Color.parseColor("#333333"));
        tv_message_two.setTextColor(Color.parseColor("#888888"));
        tv_message_three.setTextColor(Color.parseColor("#888888"));
        img_tip.setImageResource(R.mipmap.stamp_report);
        tv_message_two.setText("打击恶势力小分队");
        tv_message_three.setText("已前去为您扫清障碍~");
        popup.showAtLocation(holder.itemView.findViewById(R.id.activity_yoxiu_detail), Gravity.CENTER, 0, 0);
    }
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {

        RoundedCorners roundedCorners= new RoundedCorners(6);
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);

    holder.img_comment_like.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            like(holder);
        }
    });
    holder.img_huifu.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            comment(holder);
        }
    });
    holder.img_function.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loadMore(holder);
        }
    });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView img_comment_like,img_huifu,img_function;
        public Holder(@NonNull View itemView) {
            super(itemView);
            img_comment_like=itemView.findViewById(R.id.img_comment_like);
            img_huifu=itemView.findViewById(R.id.img_huifu);
            img_function=itemView.findViewById(R.id.img_function);
        }
    }
}
