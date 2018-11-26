package com.iyoyogo.android.ui.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.ItemBean;
import com.iyoyogo.android.R;

import java.util.List;

public class SameParaElseAdapter extends BaseAdapter {
    private List<ItemBean> elseList;
    private Context context;

    public SameParaElseAdapter(Context context, List<ItemBean> elseList) {
        this.context = context;
        this.elseList = elseList;
    }


    @Override
    public int getCount() {
        return elseList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
       return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_same_para, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Glide.with(context).load(elseList.get(position).getUri()).into(viewHolder.img);

        return convertView;
    }


    public static class ViewHolder {
        public View rootView;
        public ImageView img;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.img = (ImageView) rootView.findViewById(R.id.img);
        }

    }
}
