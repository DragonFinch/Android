package com.iyoyogo.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.map.MapBean;

import java.util.List;

/**
 * 爱生活，爱代码
 * 创建于：2018/11/12 09:55
 * 作 者：T
 * 微信：704003376
 */
public class MyAdapter1 extends BaseAdapter {
    private Context mContext;
    private List<MapBean.DataBean.ListBean> mList;

    public MyAdapter1(Context context, List<MapBean.DataBean.ListBean> strList) {
        this.mContext = context;
        this.mList = strList;

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = View.inflate(mContext, R.layout.list_item, null);
        ViewHolder viewHolder = ViewHolder.getViewHolder(convertView);

        String currentStrLetter = mList.get(position).getEnglish_name().charAt(0) + "";

        if (position > 0) {
            String lastStrLetter = mList.get(position - 1).getEnglish_name().charAt(0) + "";

            if (lastStrLetter.equals(currentStrLetter)) {
                viewHolder.tv_letter.setVisibility(View.GONE);
            } else {
                viewHolder.tv_letter.setText(currentStrLetter);
                viewHolder.tv_letter.setVisibility(View.VISIBLE);
            }
        } else {
            viewHolder.tv_letter.setText(currentStrLetter);
            viewHolder.tv_letter.setVisibility(View.VISIBLE);
        }
        viewHolder.tv_name.setText(mList.get(position).getChina_name());
        viewHolder.tv_name1.setText("("+mList.get(position).getEnglish_name()+")");
        return convertView;
    }


    public static class ViewHolder {
        private TextView tv_letter, tv_name,tv_name1;

        public ViewHolder(View convertView) {
            tv_letter = convertView.findViewById(R.id.tv_letter);
            tv_name = convertView.findViewById(R.id.tv_name);
            tv_name1 = convertView.findViewById(R.id.tv_name1);
        }

        public static ViewHolder getViewHolder(View convertView) {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            return viewHolder;
        }

    }
}
