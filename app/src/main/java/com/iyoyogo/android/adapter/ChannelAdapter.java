package com.iyoyogo.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.yoxiu.channel.ChannelBean;
import com.iyoyogo.android.utils.imagepicker.component.BaseRecyclerAdapter;
import com.iyoyogo.android.utils.imagepicker.component.BaseViewHolder;
import com.iyoyogo.android.utils.imagepicker.component.OnItemChooseCallback;
import com.iyoyogo.android.utils.imagepicker.component.OnRecyclerViewItemClickListener;
import com.iyoyogo.android.utils.imagepicker.component.OnRecyclerViewItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道适配器
 */
public class ChannelAdapter extends BaseRecyclerAdapter {

    private        int                                 count          = 0;
    private        int                                 maxNum         = 1;
    private        Context                             context;
    private        List<ChannelBean.DataBean.ListBean> list;

    public ChannelAdapter(Context context, List<ChannelBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picker_channel, null);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setMaxNum(int maxNum) {
        if (maxNum < 1) return;
        this.maxNum = maxNum;
    }

    public ArrayList<String> selectPhoto() {
        ArrayList<String> str=new ArrayList<>();
        for (ChannelBean.DataBean.ListBean listBean : list) {
            if (listBean.isChoose()){
                str.add(listBean.getChannel());
            }
        }
        return str;
    }

    public ArrayList<Integer> selectChannelIds() {
        ArrayList<Integer> ids=new ArrayList<>();
        for (ChannelBean.DataBean.ListBean listBean : list) {
            if (listBean.isChoose()){
                ids.add(listBean.getId());
            }
        }
        return ids;
    }

    private class MyViewHolder extends BaseViewHolder {
        private ImageView mImageSrc;
        private ImageView mImageChoose;
        private TextView  tv_name;

        private MyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void intOnItemChooseCallback(final OnItemChooseCallback chooseCallback, final int position) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    count=selectChannelIds().size();
                    if (count < maxNum) {
                        if (!list.get(position).isChoose()) {
                            mImageChoose.setImageResource(R.mipmap.zp_xz);
                            mImageSrc.setColorFilter(Color.parseColor("#77000000"));
                            list.get(position).setChoose(true);
                            chooseCallback.chooseState(position, true);
                        } else {
                            mImageChoose.setImageResource(R.color.transparent);
                            list.get(position).setChoose(false);
                            chooseCallback.chooseState(position, false);
                            mImageSrc.setColorFilter(Color.TRANSPARENT);
                        }

                    } else { //count >= maxNum
                        if (!list.get(position).isChoose()) {
                            chooseCallback.countWarning(count);
                        } else {
                            mImageChoose.setImageResource(R.color.transparent);
                            list.get(position).setChoose(false);
                            mImageSrc.setColorFilter(Color.TRANSPARENT);
                            chooseCallback.chooseState(position, false);
                        }
                    }
                    chooseCallback.countNow(count);
                }
            });
        }

        @Override
        public void initOnItemClickListener(OnItemChooseCallback chooseCallback, final OnRecyclerViewItemClickListener listener, final int position) {
            int    id       = list.get(position).getId();
            String filePath = String.valueOf(id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position);

                }
            });
        }

        @Override
        public void iniOnItemLongClickListener(OnRecyclerViewItemLongClickListener longClickListener, int position) {

        }


        @Override
        protected void findViewById(View itemView) {
            mImageSrc = itemView.findViewById(R.id.image_src);
            mImageChoose = itemView.findViewById(R.id.image_choose);
            tv_name = itemView.findViewById(R.id.tv_name);
        }

        @Override
        public void onBind(int position) {
            if (list.get(position).isChoose()) {
                mImageChoose.setImageResource(R.mipmap.zp_xz);
                mImageSrc.setColorFilter(Color.parseColor("#77000000"));
            } else {
                mImageChoose.setImageResource(R.color.transparent);
                mImageSrc.setColorFilter(Color.TRANSPARENT);
            }
            tv_name.setText(list.get(position).getChannel());
            Glide.with(context)
                    .load(list.get(position).getLogo())
                    .into(mImageSrc);
        }
    }

}
