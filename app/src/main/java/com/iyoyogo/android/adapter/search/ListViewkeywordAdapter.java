package com.iyoyogo.android.adapter.search;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.search.KeywordUserBean;
import com.iyoyogo.android.ui.home.search.SearchResultActivity;

import java.util.List;

public class ListViewkeywordAdapter extends BaseAdapter {
    private Context context;
    private List<KeywordUserBean.DataBean.ListBean> listBeans;
    private String s = "";
    public ListViewkeywordAdapter(SearchResultActivity searchResultActivity, List<KeywordUserBean.DataBean.ListBean> listBeans, String s) {
        this.context = searchResultActivity;
        this.listBeans = listBeans;
        this.s = s;
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

       String content= listBeans.get(position).getPosition_name();
        if (!TextUtils.isEmpty(s)&&!TextUtils.isEmpty(content)) {
            int index = content.indexOf(s);
            if (index>=0) {
                viewHolder.txtv.setText(content.substring(0, index));
                viewHolder.txtv.append(Html.fromHtml("<font color='#FA800A'>" + s + "</font>"));
                String str = content.substring(s.length() + index, content.length());
                viewHolder.txtv.append(str);
            }
        }

        return convertView;
    }

    class ViewHolder {
        TextView txtv;
        ImageView im_dizhi;
    }

  /*  private Context context;
    private ArrayList<KeywordUserBean.DataBean.ListBean> mOriginalValues;
    private List<KeywordUserBean.DataBean.ListBean> mObjects;

    private final Object mLock = new Object();

    private LayoutInflater mInflater;

    private String keyWrold = ""; // 关键字
    private String str;

    public ListViewkeywordAdapter(Context context, List<KeywordUserBean.DataBean.ListBean> list) {
        this.context = context;
        this.mObjects = list;
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        Log.e("hanbaocheng", "getCount: "+mObjects.size() );
        // TODO Auto-generated method stub
        if (mObjects == null || mObjects.size() == 0)
            return 0;
        return mObjects.size();

    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.search_layout_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtv = (TextView) convertView.findViewById(R.id.text_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mObjects.get(position).getType() ==1){
            str = mObjects.get(position).getUser_nickname().toString().toLowerCase();
        }
        Log.e("hanbaocheng", "getView: "+str.toString() );
        if (!keyWrold.equals("")) {
            String patten = "" + keyWrold;
            Pattern p = Pattern.compile(patten);
            Matcher m = p.matcher(str);
            SpannableString spannableString = new SpannableString(str);
            while (m.find()) {
                if (str.contains(m.group())) {
                    spannableString.setSpan(
                            new ForegroundColorSpan(0xffec8b44), m.start(),
                            m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            viewHolder.txtv.setText(spannableString);
        } else {
            SpannableString spannableString = new SpannableString(str);
            spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0,
                    str.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.txtv.setText(mObjects.get(position).getPosition_name().toString());
        }
        return convertView;
    }

    class ViewHolder {
        TextView txtv;
    }


    public Filter getFilter() {
        // TODO Auto-generated method stub
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mObjects = (List<KeywordUserBean.DataBean.ListBean>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            // TODO Auto-generated method stub
            FilterResults filterResults = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<KeywordUserBean.DataBean.ListBean>(mObjects);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<KeywordUserBean.DataBean.ListBean> list;
                synchronized (mLock) {
                    list = new ArrayList<KeywordUserBean.DataBean.ListBean>(mOriginalValues);
                }
                filterResults.values = list;
                filterResults.count = list.size();
                keyWrold = "";
            } else {
                String prefixString = prefix.toString().toLowerCase();
                ArrayList<KeywordUserBean.DataBean.ListBean> values;
                synchronized (mLock) {
                    values = new ArrayList<KeywordUserBean.DataBean.ListBean>(mOriginalValues);
                }

                final int count = values.size();
                final ArrayList<KeywordUserBean.DataBean.ListBean> newValues = new ArrayList<KeywordUserBean.DataBean.ListBean>();

                for (int i = 0; i < count; i++) {
                    final KeywordUserBean.DataBean.ListBean value = values.get(i);
                    Log.e("hanbaocheng", "performFiltering111111111111111 "+value );
                    final String valueText = value.toString().toLowerCase();
                    // First match against the whole, non-splitted value
                    if (valueText.contains(prefixString)) {
                        newValues.add(value);
                        keyWrold = prefixString;
                    }
                }

                filterResults.values = newValues;
                filterResults.count = newValues.size();
            }

            return filterResults;
        }

    };

*/

}
