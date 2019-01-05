package com.iyoyogo.android.adapter.search;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.map.MapBean;
import com.iyoyogo.android.utils.map.KeyWordUtil1;

import java.util.List;

public class SearchListViewAdapter extends BaseAdapter {


    private Context context;
    private List<MapBean.DataBean.ListBean> mUserList;
    private String searchContent;//用户搜索的内容

    public SearchListViewAdapter(Context context,
                                 List<MapBean.DataBean.ListBean> mUserList, String searchContent) {
        super();
        this.context = context;
        this.mUserList = mUserList;
        this.searchContent = searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mUserList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUserList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*@Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //获得对象
        MapBean.DataBean.ListBean listBean = mUserList.get(position);
        //加载布局
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(context).inflate(R.layout.search_listview_item,null);
            viewHolder=new ViewHolder();
            //获取控件
            viewHolder.textViewNumber=(TextView) view.findViewById(R.id.search_listView_item_tv_userNumber);
            viewHolder.textViewNick=(TextView) view.findViewById(R.id.fubiaoti);
            viewHolder.biaoti=(TextView) view.findViewById(R.id.biaoti);

            //将ViewHolder存储在View中
            view.setTag(viewHolder);
        }else{
            view=convertView;
            //重新获取ViewHolder
            viewHolder=(ViewHolder) view.getTag();
        }
        //设置控件的值
        //搜索高亮显示
        if(listBean.getChina_name()!=null&&listBean.getChina_name().length()>0){
            SpannableString number= KeyWordUtil1.matcherSearchTitle(Color.parseColor("#ff9314"), listBean.getChina_name()+"", searchContent);
            viewHolder.textViewNumber.setText(number);
            viewHolder.biaoti.setText("("+mUserList.get(position).getEnglish_name()+")");
            viewHolder.textViewNick.setText(mUserList.get(position).getCode());
        }
        if(listBean.getChina_name()!=null&&listBean.getChina_name().length()>0){
            SpannableString nick= KeyWordUtil1.matcherSearchTitle(Color.parseColor("#ff9314"), listBean.getChina_name(), searchContent);
            viewHolder.textViewNick.setText(nick);
            viewHolder.biaoti.setText("("+mUserList.get(position).getEnglish_name()+")");
            viewHolder.textViewNick.setText(mUserList.get(position).getCode());
        }

        return view;
    }
    class  ViewHolder{
        TextView textViewNumber;
        TextView textViewNick;
        TextView biaoti;
    }


}
