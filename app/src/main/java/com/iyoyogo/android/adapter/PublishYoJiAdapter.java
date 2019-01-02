package com.iyoyogo.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.yoji.publish.MessageBean;
import com.iyoyogo.android.view.DrawableTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 发布yo记的适配器
 */
public class PublishYoJiAdapter extends RecyclerView.Adapter<PublishYoJiAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<MessageBean> mList;
    private ArrayList<String> path_list;

    public PublishYoJiAdapter(Context context, List<MessageBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_publish_yoji_content, parent, false);
        view.setOnClickListener(this);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
//选择开始时间
    private void startTime(ViewHolder holder, int position) {
        TimePickerView pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {

                holder.tv_time_start.setText(getTime(date));
                if (null != onLocationClickListener) {
                    onLocationClickListener.onStartTimeClick(position, getTime(date));
                }
                int month = date.getMonth();
                int day = date.getDay();

            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .gravity(Gravity.CENTER)
//                .setContentSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                //.setTitleText("Title")//标题文字
//                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
//                .isCyclic(true)//是否循环滚动
//                //.setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                //.setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
////                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
////                .setRangDate(startDate,endDate)//起始终止年月日设定
//                //.setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                //.isDialog(true)//是否显示为对话框样式
                .build();

        pvTime.show();

    }
    //选择结束时间
    private void endTime(ViewHolder holder, int position) {
        TimePickerView pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (null != onLocationClickListener) {
                    onLocationClickListener.onEndTimeClick(position, getTime(date));
                }
                holder.tv_time_end.setText(getTime(date));
                int month = date.getMonth();
                int day = date.getDay();

            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .gravity(Gravity.CENTER)
//                .setContentSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                //.setTitleText("Title")//标题文字
//                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
//                .isCyclic(true)//是否循环滚动
//                //.setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                //.setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
////                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
////                .setRangDate(startDate,endDate)//起始终止年月日设定
//                //.setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                //.isDialog(true)//是否显示为对话框样式
                .build();

        pvTime.show();

    }
//获取时间
    private String getTime(Date date) {//可根据需要自行截取数据显示
        //"YYYY-MM-DD HH:MM:SS"        "yyyy-MM-dd"
        SimpleDateFormat format;
        format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.tv_time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime(holder, position);
            }
        });
        holder.tv_time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime(holder, position);


            }
        });
        holder.view_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onLocationClickListener) {
                    onLocationClickListener.onAddAddressClick(position, holder);
                }
            }
        });
        holder.add_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onLocationClickListener) {
                    onLocationClickListener.onImageAddClickListener(position, holder);
                }
            }
        });


        holder.view_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onLocationClickListener) {
                    onLocationClickListener.onTagClick(position, holder);
                }
            }
        });
        holder.tv_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (null != onLocationClickListener) {
                    onLocationClickListener.onImageAddClick(position, holder);
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.recycler_inner.setLayoutManager(linearLayoutManager);
        YoJiInnerAdapter yoJiInnerAdapter = new YoJiInnerAdapter(context, mList.get(position).getLogos());
        holder.recycler_inner.setAdapter(yoJiInnerAdapter);
        yoJiInnerAdapter.setOnImageClickListener(new YoJiInnerAdapter.OnImageClickListener() {
            @Override
            public void setOnClick(View v, int position) {
                if (null != onLocationClickListener) {
                    onLocationClickListener.onImageEditClickListener(position, holder);
                }
            }
        });
    }

    public void addData(int position, MessageBean messageBean) {
//      在list中添加数据，并通知条目加入一条

        mList.add(position, messageBean);
        //添加动画
        notifyItemInserted(position);
    }

    //  删除数据
    public void removeData(int position) {
        mList.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        DrawableTextView tv_time_start, tv_time_end, location_tv;
        RelativeLayout view_location;
        LinearLayout view_sign;
        TextView tv_insert, tv_delete;
        RecyclerView recycler_inner;
        ImageButton add_image_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            add_image_button = itemView.findViewById(R.id.add_image_button);
            location_tv = itemView.findViewById(R.id.location_tv);
            recycler_inner = itemView.findViewById(R.id.recycler_inner);
            tv_insert = itemView.findViewById(R.id.tv_insert);
            tv_delete = itemView.findViewById(R.id.tv_delete);
            tv_time_start = itemView.findViewById(R.id.tv_time_start);
            tv_time_end = itemView.findViewById(R.id.tv_time_end);
            view_location = itemView.findViewById(R.id.view_location);
            view_sign = itemView.findViewById(R.id.view_sign);
        }
    }

    public interface OnLocationClickListener {
        void onLocationClick(int position);

        void onStartTimeClick(int postion, String startTime);

        void onEndTimeClick(int postion, String endTime);

        void onTagClick(int position, ViewHolder holder);

        void onTagReomveClick(int position, int index);

        void onImageReomveClick(int position, int index);

        void onImageAddClick(int position, ViewHolder holder);

        void onImageAddClickListener(int position, ViewHolder holder);

        void onImageEditClickListener(int position, ViewHolder holder);

        void onAddAddressClick(int position, ViewHolder holder);

        void onTitleEdit(EditText title, EditText content, EditText cost);

        void onCoverClick(int position);

    }

    // add click callback
    OnLocationClickListener onLocationClickListener;

    public void setOnPlayClickListener(OnLocationClickListener onItemPlayClick) {
        this.onLocationClickListener = onItemPlayClick;
    }
}
