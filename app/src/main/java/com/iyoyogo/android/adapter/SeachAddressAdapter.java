package com.iyoyogo.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.AddressBean;

import java.util.ArrayList;

public class SeachAddressAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<AddressBean> list;
    private LayoutInflater inflater;

    public SeachAddressAdapter(Context mContext, ArrayList<AddressBean> list) {
        this.mContext = mContext;
        this.list = list;
        this.inflater=LayoutInflater.from(mContext);
    }

    public void refreshData( ArrayList<AddressBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (view==null){
            holder=new ViewHolder();
            view=inflater.inflate(R.layout.item_adapter,null);
            holder.tv_name= (TextView) view.findViewById(R.id.tv_name);
            holder.tv_address= (TextView) view.findViewById(R.id.tv_address);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }

        AddressBean bean=list.get(i);
        holder.tv_name.setText(bean.getTitle());
        holder.tv_address.setText(bean.getAddress());

        return view;
    }

    class ViewHolder{
        TextView tv_name,tv_address;
    }
}