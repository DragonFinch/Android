package com.iyoyogo.android.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.SameBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.util.UiUtils;

import io.reactivex.functions.Consumer;

/**
 * @author zhuhui
 * @date 2019/1/15
 * @description
 */
public class GoTakeDetailAdapter extends BaseQuickAdapter<SameBean.DataBean.ListBean, BaseViewHolder> {
    private SameBean.DataBean.ListBean listBean;
    public GoTakeDetailAdapter(SameBean.DataBean.ListBean listBean, int layoutResId) {
        super(layoutResId);
        this.listBean=listBean;
    }

    @Override
    protected void convert(BaseViewHolder helper, SameBean.DataBean.ListBean item) {
        if (item.getFile_type() == 2) {
            VideoView view = helper.getView(R.id.video_view);
            helper.setGone(R.id.iv, false).setGone(R.id.video_view, true).setGone(R.id.iv_video, !view.isPlaying());
        } else {
            helper.setGone(R.id.iv, true).setGone(R.id.video_view, false).setGone(R.id.iv_video, false);
            Glide.with(mContext).load(item.getFile_path().replace("?x-oss-process=image/resize,w_400", "")).apply(new RequestOptions().fitCenter()).into((ImageView) helper.getView(R.id.iv));
        }
        Glide.with(mContext).load(item.getUser_logo()).apply(new RequestOptions().circleCrop()).into((ImageView) helper.getView(R.id.iv_user_pic));
        helper.setText(R.id.tv_user_name, item.getUser_nickname()).setText(R.id.tv_address, item.getPosition_name())
                .setText(R.id.tv_info, item.getFile_desc())
                .setText(R.id.tv_time, item.getCreate_time())
                .setText(R.id.tv_read_num, item.getCount_view() + "")
                .setText(R.id.tv_comment_num, item.getCount_comment() + "")
                .setText(R.id.tv_collect_num, item.getCount_collect() + "")
                .setText(R.id.tv_like_num, item.getCount_praise() + "")
                .setGone(R.id.iv_go_take, item.getFile_type() != 2)
                .addOnClickListener(R.id.ll_read)
                .addOnClickListener(R.id.ll_comment)
                .addOnClickListener(R.id.ll_collect)
                .addOnClickListener(R.id.ll_like)
                .addOnClickListener(R.id.iv_go_take);

//        TextView tv  = helper.getView(R.id.tv_address);
//        View     dot = helper.getView(R.id.view_dot);
//        tv.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
//            int top = UiUtils.getStatusHeight(mContext) + UiUtils.dip2px(60);
//            if (tv.getY() < top) {
//                TranslateAnimation ani = new TranslateAnimation(tv.getX(), tv.getX(), tv.getY(), top);
//                ani.setDuration(10);
//                ani.setFillAfter(true);
//                tv.startAnimation(ani);
//
//                TranslateAnimation dotAni = new TranslateAnimation(dot.getX(), dot.getX()+UiUtils.dip2px(5), dot.getY(), top);
//                dotAni.setDuration(10);
//                dotAni.setFillAfter(true);
//                dot.startAnimation(dotAni);
//            }
//        });
        String user_id = SpUtils.getString(mContext, "user_id", null);
        String user_token = SpUtils.getString(mContext, "user_token", null);
        helper.getView(R.id.ll_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        helper.getView(R.id.ll_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getFromRemote().praise(user_id,user_token,item.getYo_id(),0).subscribe(new Consumer<BaseBean>() {
                    @Override
                    public void accept(BaseBean baseBean) throws Exception {

                    }
                });
            }
        });
        helper.getView(R.id.ll_collect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        helper.getView(R.id.ll_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        TextView                    tv    = helper.getView(R.id.tv_address);
//        View                        dot   = helper.getView(R.id.view_dot);
//        RelativeLayout.LayoutParams tvLp  = (RelativeLayout.LayoutParams) tv.getLayoutParams();
//        RelativeLayout.LayoutParams dotLp = (RelativeLayout.LayoutParams) dot.getLayoutParams();
//        tv.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
//            int top = UiUtils.getStatusHeight(mContext) + UiUtils.dip2px(60);
//            if (tv.getY() < top) {
//
//                tvLp.setMargins(UiUtils.dip2px(25), top, 0, 0);
//                dotLp.setMargins(UiUtils.dip2px(20), top, 0, 0);
//
//            } else {
//                tvLp.setMargins(UiUtils.dip2px(25), UiUtils.dip2px(27), 0, 0);
//                dotLp.setMargins(UiUtils.dip2px(20), UiUtils.dip2px(22), 0, 0);
//            }
//            tv.setLayoutParams(tvLp);
//            dot.setLayoutParams(dotLp);
//        });
    }
}
