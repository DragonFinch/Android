package com.iyoyogo.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.yoxiu.channel.ChannelBean;

import java.util.List;

public class ChannelAdapter extends BaseAdapter {
    private Context context;
    private List<ChannelBean.DataBean.ListBean> mList;

    public ChannelAdapter(Context context, List<ChannelBean.DataBean.ListBean> list) {
        this.context = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
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
        ChannelAdapter.ViewHolder vh = null;
        if (vh == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_classify, null);
            vh = new ChannelAdapter.ViewHolder(convertView);
            convertView.setTag(vh);

        } else {
            vh = (ChannelAdapter.ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(mList.get(position).getLogo()).into(vh.img);
        vh.tag_name.setText(mList.get(position).getChannel());
        return convertView;
    }


    public static class ViewHolder {
        public View rootView;
        public ImageView img;
        public ImageView choice_icon;
        public TextView tag_name;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.img = (ImageView) rootView.findViewById(R.id.img);
            this.choice_icon = (ImageView) rootView.findViewById(R.id.choice_icon);
            this.tag_name = (TextView) rootView.findViewById(R.id.tag_name);
        }

    }
}

