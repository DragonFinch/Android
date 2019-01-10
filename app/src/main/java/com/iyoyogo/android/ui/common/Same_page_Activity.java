package com.iyoyogo.android.ui.common;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.SamePageBean;
import com.iyoyogo.android.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * 拍同款
 */

public class Same_page_Activity extends AppCompatActivity {

    @BindView(R.id.img_return)
    ImageView imgReturn;
    @BindView(R.id.recycler_channel)
    RecyclerView recyclerChannel;
    @BindView(R.id.camera_icon)
    ImageView cameraIcon;
    private int heigth;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_page_);
        ButterKnife.bind(this);
        StatusBarUtils.setWindowStatusBarColor(this, Color.WHITE);
        init();
        initDate();
    }

    private void initDate() {
        ArrayList<SamePageBean> list = new ArrayList<>();
        list.add(new SamePageBean(2, R.mipmap.sea, "房地产v", "2", 1));
        list.add(new SamePageBean(1, R.mipmap.ic_wechat_select, "地方VC", "3", 2));
        list.add(new SamePageBean(1, R.mipmap.chuangjianhuati, "放大", "1", 3));
        list.add(new SamePageBean(1, R.mipmap.add, "突然", "9", 3));
        list.add(new SamePageBean(1, R.mipmap.ic_camera, "放入", "6", 3));
        list.add(new SamePageBean(2, R.mipmap.dog, "发到", "5", 3));
        list.add(new SamePageBean(2, R.mipmap.yanzhengm, "若风", "8", 3));
        list.add(new SamePageBean(1, R.mipmap.back_black, "人v", "4", 3));
        list.add(new SamePageBean(2, R.mipmap.ic_all_btn, "与", "7", 3));
        list.add(new SamePageBean(1, R.mipmap.ranking1, "惹我风格", "10", 3));
        list.add(new SamePageBean(2, R.mipmap.fabu, "一句话", "12", 3));
        list.add(new SamePageBean(2, R.mipmap.da, "UI家", "11", 3));
        SamePageAdapter adapter = new SamePageAdapter(list);
        recyclerChannel.setAdapter(adapter);
        recyclerChannel.setLayoutManager(new GridLayoutManager(this, 1));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                for (int i = 0; i < list.size(); i++) {
//                    if (list.get(i).getType() == 1) {
//                        Intent intent = new Intent(Same_page_Activity.this, Vertical_picture_Activity.class);
//                        startActivity(intent);
//                    } else if (list.get(i).getType() == 2) {
//                    }
//
//                }
                Intent intent = new Intent(Same_page_Activity.this, Album_details_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {

        //获取屏幕宽高
        DisplayMetrics dm = getResources().getDisplayMetrics();
        width = dm.widthPixels;
        heigth = dm.heightPixels;
    }

    @OnClick({R.id.img_return, R.id.camera_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                finish();
                break;
            case R.id.camera_icon:
                break;
        }
    }


    class SamePageAdapter extends BaseMultiItemQuickAdapter<SamePageBean, BaseViewHolder> {

        /**
         * Same as QuickAdapter#QuickAdapter(Context,int) but with
         * some initialization data.
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        public SamePageAdapter(List<SamePageBean> data) {
            super(data);
            addItemType(SamePageBean.Type1, R.layout.item_same_page1);
            addItemType(SamePageBean.Type2, R.layout.item_same_page2);
            addItemType(SamePageBean.Type3, R.layout.item_same_page3);
        }

        @Override
        protected void convert(BaseViewHolder helper, SamePageBean item) {
            int type = item.getType();
            ImageView video_play = helper.getView(R.id.video_play);
            ImageView video_play_21 = helper.getView(R.id.video_play_21);
            ImageView video_play_22 = helper.getView(R.id.video_play_22);
            ImageView video_play_31 = helper.getView(R.id.video_play_31);
            ImageView video_play_32 = helper.getView(R.id.video_play_32);
            ImageView video_play_33 = helper.getView(R.id.video_play_33);
            switch (item.getItemType()) {
                case SamePageBean.Type1:
                    if (type == 1) {
                        video_play.setVisibility(View.GONE);
                    } else {
                        video_play.setVisibility(View.VISIBLE);
                    }
                    Glide.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.first_img));
                    helper.setText(R.id.first_title, item.getTitle());
                    Glide.with(mContext).load(R.mipmap.add_friend).into((ImageView) helper.getView(R.id.first_icon));
                    break;
                case SamePageBean.Type2:
                    if (type == 1) {
                        video_play_21.setVisibility(View.GONE);
                        video_play_22.setVisibility(View.GONE);
                    } else {
                        video_play_21.setVisibility(View.VISIBLE);
                        video_play_22.setVisibility(View.VISIBLE);
                    }
                    Glide.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.second_img));
                    Glide.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.fourth2_img));
                    break;
                case SamePageBean.Type3:
                    if (type == 1) {
                        video_play_31.setVisibility(View.GONE);
                        video_play_32.setVisibility(View.GONE);
                        video_play_33.setVisibility(View.GONE);
                    } else {
                        video_play_31.setVisibility(View.VISIBLE);
                        video_play_32.setVisibility(View.VISIBLE);
                        video_play_33.setVisibility(View.VISIBLE);
                    }
                    Glide.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.third_img));
                    Glide.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.four_img));
                    Glide.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.fifth_img));
                    break;
            }
        }
    }
}
