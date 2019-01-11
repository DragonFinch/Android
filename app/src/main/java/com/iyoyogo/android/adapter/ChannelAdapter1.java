package com.iyoyogo.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.login.interest.InterestBean;
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
public class ChannelAdapter1 extends BaseRecyclerAdapter {

    private int count = 0;
    private int maxNum = 1;
    private Context context;
    private List<InterestBean.DataBean.ListBean> list;
    private ArrayList<String> str = new ArrayList<>();
    private ArrayList<Integer> ids = new ArrayList<>();
    private int id;
    private String interest;

    public ChannelAdapter1(Context context, List<InterestBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classify, null);
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
        str = new ArrayList<>();
        for (InterestBean.DataBean.ListBean listBean : list) {
            if (listBean.isChoose()) {
                str.add(listBean.getInterest());
            }
        }
        return str;
    }

    public ArrayList<Integer> selectChannelIds() {
        ids = new ArrayList<>();
        for (InterestBean.DataBean.ListBean listBean : list) {
            if (listBean.isChoose()) {
                ids.add(listBean.getId());
            }
        }
        return ids;
    }

    private class MyViewHolder extends BaseViewHolder {
        ImageView mImg;
        TextView tag_name;
        ImageButton mSelect;

        private MyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void intOnItemChooseCallback(final OnItemChooseCallback chooseCallback, final int position) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    id = list.get(position).getId();
                    interest = list.get(position).getInterest();
                    count = selectChannelIds().size();
                    if (count < maxNum) {
                        if (!list.get(position).isChoose()) {
                            mImg.setColorFilter(Color.parseColor("#77000000"));
                            mSelect.setImageResource(R.mipmap.xz);
                            list.get(position).setChoose(true);
                            chooseCallback.chooseState(position, true);
                        } else {
                            mImg.setColorFilter(null);
                            mSelect.setImageResource(R.color.transparent);
                            list.get(position).setChoose(false);
                            chooseCallback.chooseState(position, false);
                        }

                    } else if (count >= maxNum) { //count >= maxNum
                        if (!list.get(position).isChoose()) {
                            ids.remove(interest);
                            chooseCallback.countWarning(count);
                        } else {
                            mImg.setColorFilter(null);
                            mSelect.setImageResource(R.color.transparent);
                            list.get(position).setChoose(false);
                            chooseCallback.chooseState(position, false);
                        }
                    }
                    chooseCallback.countNow(count);
                }
            });
        }

        @Override
        public void initOnItemClickListener(OnItemChooseCallback chooseCallback, final OnRecyclerViewItemClickListener listener, final int position) {
            int id = list.get(position).getId();
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
            mImg = itemView.findViewById(R.id.img);
            tag_name = itemView.findViewById(R.id.tag_name);
            mSelect = itemView.findViewById(R.id.choice_icon);
        }

        @Override
        public void onBind(int position) {
            if (list.get(position).isChoose()) {
                mImg.setColorFilter(Color.parseColor("#77000000"));
                mSelect.setImageResource(R.mipmap.xz);
            } else {
                mImg.setColorFilter(null);
                mSelect.setImageResource(R.color.transparent);
            }
            tag_name.setText(list.get(position).getInterest());
            Glide.with(context)
                    .load(list.get(position).getLogo())
                    .into(mImg);
        }
    }

}
