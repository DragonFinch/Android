package com.iyoyogo.android.adapter;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.LocationBean;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 添加地点的适配器
 */
public class PoiSearchAdapter extends RecyclerView.Adapter<PoiSearchAdapter.ViewHolder> {
    private Context context;
    /**
     * adapter传递过来的数据集合
     */
    private List<LocationBean> list ;
    /**
     * 需要改变颜色的text
     */

    /**
     * 属性动画
     */
    private Animator animator;

    /**
     * 在MainActivity中设置text
     */
    private String text;
    public void setText(String text) {
        this.text = text;
    }
    public PoiSearchAdapter(Context context, List<LocationBean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_search, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /**如果没有进行搜索操作或者搜索之后点击了删除按钮 我们会在MainActivity中把text置空并传递过来*/
        if (text != null) {
            //设置span
            SpannableString string = matcherSearchText(Color.parseColor("#ffff8800"), list.get(position).getTitle(), text);
            holder.tv_name.setText(string);
        } else {
            holder.tv_name.setText(list.get(position).getTitle());

        }
        holder.tv_province.setText(list.get(position).getProvinceName());
        //属性动画
        /*animator = AnimatorInflater.loadAnimator(context, R.animator.anim_set);
        animator.setTarget(holder.mLlItem);
        animator.start();*/
        //点击监听
        holder.mLlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(view, position);
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Recyclerview的点击监听接口
     */
    public interface onItemClickListener {
        void onClick(View view, int pos);
    }

    private onItemClickListener onItemClickListener;

    public void setOnItemClickListener(PoiSearchAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    /**
     * 正则匹配 返回值是一个SpannableString 即经过变色处理的数据
     */
    private SpannableString matcherSearchText(int color, String text, String keyword) {
        SpannableString spannableString = new SpannableString(text);
        //条件 keyword
        Pattern pattern = Pattern.compile(keyword);
        //匹配
        Matcher matcher = pattern.matcher(spannableString);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            //ForegroundColorSpan 需要new 不然也只能是部分变色
            spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        //返回变色处理的结果
        return spannableString;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mLlItem;

        private TextView tv_name;
        private TextView tv_province;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mLlItem = (RelativeLayout) itemView.findViewById(R.id.ll_item);
            tv_province = (TextView) itemView.findViewById(R.id.tv_province);
            tv_name = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
