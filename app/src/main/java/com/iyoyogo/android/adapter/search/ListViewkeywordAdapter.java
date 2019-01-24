package com.iyoyogo.android.adapter.search;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.search.KeywordUserBean;
import com.iyoyogo.android.ui.home.search.SearchResultActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListViewkeywordAdapter extends BaseAdapter {
    private Context context;
    private List<KeywordUserBean.DataBean.ListBean> listBeans;
    private String s = "";
    public ListViewkeywordAdapter(SearchResultActivity searchResultActivity, List<KeywordUserBean.DataBean.ListBean> listBeans, String s) {
        this.context = searchResultActivity;
        this.listBeans = listBeans;
        this.s = s;
    }

    public void clearData(){
        if (listBeans!=null){
            listBeans.clear();
        }
    }

    @Override
    public int getCount() {
        return listBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return listBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.search_layout_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.txtv = (TextView) convertView.findViewById(R.id.text_name);
            viewHolder.im_dizhi = (ImageView) convertView.findViewById(R.id.im_dizhi);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
            if (listBeans.get(position).getType() == 1){
                viewHolder.im_dizhi.setImageResource(R.drawable.yonghu);
            }
            if (listBeans.get(position).getType() == 2){
                viewHolder.im_dizhi.setImageResource(R.drawable.yoji_i);
            }
           if (listBeans.get(position).getType() == 3){
            viewHolder.im_dizhi.setImageResource(R.drawable.yoxiu_i);
           }
           if (listBeans.get(position).getType() == 4){
            viewHolder.im_dizhi.setImageResource(R.drawable.biaoqian);
        }
         if (listBeans.get(position).getType() == 5){
            viewHolder.im_dizhi.setImageResource(R.drawable.didian);
         }
         if (listBeans.get(position).getType() == 6){
            viewHolder.im_dizhi.setImageResource(R.drawable.pindao);
          }
      /*  Log.e("zczxczczx", "getView:11111111111111 +"+listBeans.get(position).getUser_nickname() );
        Log.e("zczxczczx", "getView:11111111111111 +"+listBeans.get(position).getTitle() );
        Log.e("zczxczczx", "getView:11111111111111 +"+listBeans.get(position).getFile_desc() );
        Log.e("zczxczczx", "getView:11111111111111 +"+listBeans.get(position).getPosition_name() );
          if (s != null && listBeans.get(position).getUser_nickname() != null){
              Log.e("zczxczczx", "getView: "+"s"+s+"List"+listBeans.get(position).getUser_nickname() );
              SpannableString string = matcherSearchText(Color.parseColor("#FA800A"), listBeans.get(position).getUser_nickname()+"", s);
              viewHolder.txtv.setText(string);
          }else{
              Log.e("zczxczczx", "getView:1 "+listBeans.get(position).getUser_nickname() );
            viewHolder.txtv.setText(listBeans.get(position).getUser_nickname());
          }
        if (s != null && listBeans.get(position).getTitle() != null){
            SpannableString string = matcherSearchText(Color.parseColor("#FA800A"), listBeans.get(position).getTitle()+"", s);
            viewHolder.txtv.setText(string);
        }else{
            viewHolder.txtv.setText(listBeans.get(position).getTitle());
        }
        if (s != null && listBeans.get(position).getFile_desc() != null){
            SpannableString string = matcherSearchText(Color.parseColor("#FA800A"), listBeans.get(position).getFile_desc()+"", s);
            viewHolder.txtv.setText(string);
        }else{
            viewHolder.txtv.setText(listBeans.get(position).getFile_desc());
        }
        if (s != null && listBeans.get(position).getLabel() != null){
            SpannableString string = matcherSearchText(Color.parseColor("#FA800A"), listBeans.get(position).getLabel()+"", s);
            viewHolder.txtv.setText(string);
        }else{
            viewHolder.txtv.setText(listBeans.get(position).getLabel());
        }
        if (s != null && listBeans.get(position).getPosition_name() != null){
            SpannableString string = matcherSearchText(Color.parseColor("#FA800A"), listBeans.get(position).getPosition_name()+"", s);
            viewHolder.txtv.setText(string);
        }else{
            viewHolder.txtv.setText(listBeans.get(position).getPosition_name());
        }
        if (s != null  && listBeans.get(position).getChannel() != null){
            SpannableString string = matcherSearchText(Color.parseColor("#FA800A"), listBeans.get(position).getChannel()+"", s);
            viewHolder.txtv.setText(string);
        }else{
            viewHolder.txtv.setText(listBeans.get(position).getChannel());
        }*/

        String content= listBeans.get(position).getUser_nickname();
        if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(content)){
            content = content.replace(s, "<font color='#FA800A'>" + s + "</font>");
            viewHolder.txtv.setText(Html.fromHtml(content));
        }
        String content1 = listBeans.get(position).getTitle();
        if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(content1)){
            content1 = content1.replace(s, "<font color='#FA800A'>" + s + "</font>");
            Log.e("asdzxc", "getView: "+content1 );
            Log.e("asdzxc", "getView: "+s );
            viewHolder.txtv.setText(Html.fromHtml(content1));
        }
        String content2 = listBeans.get(position).getFile_desc();
        if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(content2)){
            content2 = content2.replace(s, "<font color='#FA800A'>" + s + "</font>");
            viewHolder.txtv.setText(Html.fromHtml(content2));
        }
        String content3 = listBeans.get(position).getLabel();
        if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(content3)){
            content3 = content3.replace(s, "<font color='#FA800A'>" + s + "</font>");
            viewHolder.txtv.setText(Html.fromHtml(content3));
        }
        String content4 = listBeans.get(position).getPosition_name();
        if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(content4)){
            content4 = content4.replace(s, "<font color='#FA800A'>" + s + "</font>");
            viewHolder.txtv.setText(Html.fromHtml(content4));
        }
        String content5 = listBeans.get(position).getChannel();
        if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(content5)){
            content5 = content5.replace(s, "<font color='#FA800A'>" + s + "</font>");
            viewHolder.txtv.setText(Html.fromHtml(content5));
        }

       /*

        Log.e("wqezxc", "getView: "+s );
           *//* if (!TextUtils.isEmpty(s)&&!TextUtils.isEmpty(content)) {
                int index = content.indexOf(s);
                if (index>=0) {
                    viewHolder.txtv.setText(content.substring(0, index));
                    viewHolder.txtv.append(Html.fromHtml("<font color='#FA800A'>" + s + "</font>"));
                    String str = content.substring(s.length() + index, content.length());
                    viewHolder.txtv.append(str);
                }
            }*//*

      *//*  *//*

   *//*     if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(content1)) {
            int index = content1.indexOf(s);
            if (index >= 0) {
                viewHolder.txtv.setText(content1.substring(0, index));
                viewHolder.txtv.append(Html.fromHtml("<font color='#FA800A'>" + content1 + "</font>"));
                String str = content1.substring(s.length() + index, content1.length());
                viewHolder.txtv.append(str);
            }
        }*//*


      *//*  if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(content2)) {
            int index = content2.indexOf(s);
            if (index >= 0) {
                viewHolder.txtv.setText(content2.substring(0, index));
                viewHolder.txtv.append(Html.fromHtml("<font color='#FA800A'>" + content2 + "</font>"));
                String str = content2.substring(s.length() + index, content2.length());
                viewHolder.txtv.append(str);
            }
        }*//*

      *//*  if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(content3)) {
            int index = content3.indexOf(s);
            if (index >= 0) {
                viewHolder.txtv.setText(content3.substring(0, index));
                viewHolder.txtv.append(Html.fromHtml("<font color='#FA800A'>" + content3 + "</font>"));
                String str = content3.substring(s.length() + index, content3.length());
                viewHolder.txtv.append(str);
            }
        }*//*


    *//*    if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(content4)) {
            int index = content4.indexOf(s);
            if (index >= 0) {
                viewHolder.txtv.setText(content4.substring(0, index));
                viewHolder.txtv.append(Html.fromHtml("<font color='#FA800A'>" + content4 + "</font>"));
                String str = content4.substring(s.length() + index, content4.length());
                viewHolder.txtv.append(str);
            }
        }*//*
        */
       /* if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(content5)) {
            int index = content5.indexOf(s);
            if (index >= 0) {
                viewHolder.txtv.setText(content5.substring(0, index));
                viewHolder.txtv.append(Html.fromHtml("<font color='#FA800A'>" + content5 + "</font>"));
                String str = content5.substring(s.length() + index, content5.length());
                viewHolder.txtv.append(str);
            }
        }*/

        return convertView;
    }

    class ViewHolder {
        TextView txtv;
        ImageView im_dizhi;

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
}
