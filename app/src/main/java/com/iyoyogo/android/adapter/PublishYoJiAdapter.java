package com.iyoyogo.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.iyoyogo.android.R;
import com.iyoyogo.android.view.DrawableTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublishYoJiAdapter extends RecyclerView.Adapter<PublishYoJiAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<String> mList;
    private ArrayList<String> path_list;

    public PublishYoJiAdapter(Context context, List<String> mList, ArrayList<String> path_list) {
        this.context = context;
        this.mList = mList;
        this.path_list=path_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_publish_yoji_content, parent, false);
        view.setOnClickListener(this);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    private void startTime( ViewHolder holder) {
        TimePickerView pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {

                holder.tv_time_start.setText(getTime(date));
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
    private void endTime( ViewHolder holder) {
        TimePickerView pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {

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
                startTime(holder);
            }
        });
        holder.tv_time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime(holder);
            }
        });
        holder.view_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (null!=onLocationClickListener){
                  onLocationClickListener.onAddAddressClick(position,holder);
              }
            }
        });
        holder.view_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=onLocationClickListener){
                    onLocationClickListener.onTagClick(position);
                }
            }
        });
        holder.tv_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.add("");
                notifyDataSetChanged();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.recycler_inner.setLayoutManager(linearLayoutManager);
        YoJiInnerAdapter yoJiInnerAdapter = new YoJiInnerAdapter(context, path_list);
        holder.recycler_inner.setAdapter(yoJiInnerAdapter);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        DrawableTextView tv_time_start, tv_time_end,location_tv;
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
        public void onAddAddressClick(int position,ViewHolder holder);
        public void onTagClick(int position);
       /* public void onImageAddClick(int position);
        public void onImageReomveClick(int position, int index);
        public void onLocationClick(int position);


        public void onTagReomveClick(int position, int index);




        public void onTitleEdit(EditText title, EditText content, EditText cost);

        public void onCoverClick(int position);*/

    }

    // add click callback
    OnLocationClickListener onLocationClickListener;

    public void setOnPlayClickListener(OnLocationClickListener onItemPlayClick) {
        this.onLocationClickListener = onItemPlayClick;
    }
}
