package com.iyoyogo.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.login.interest.InterestBean;
import com.iyoyogo.android.bean.yoxiu.channel.ChannelBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 选择兴趣
 */
public class InterestsAdapter extends BaseAdapter {

    private Context context;
    private List<InterestBean.DataBean.ListBean> mList;
    private LayoutInflater mInflater;
    private static List<Integer> mSelectImg = new LinkedList<>();
    private static ArrayList<String> titleList = new ArrayList<>();

    public InterestsAdapter(Context context, List<InterestBean.DataBean.ListBean> mDatas) {
        this.context = context;
        this.mList = mDatas;
        mInflater = LayoutInflater.from(context);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_classify, parent, false);
            vh = new ViewHolder();
            vh.mImg = convertView.findViewById(R.id.img);
            vh.tag_name = convertView.findViewById(R.id.tag_name);
            vh.mSelect = convertView.findViewById(R.id.choice_icon);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tag_name.setText(mList.get(position).getInterest());
     /*   vh.mImg.setImageResource(R.mipmap.default_error);
        vh.mSelect.setImageResource(R.mipmap.btn_unselected);*/
        vh.mImg.setColorFilter(null);
//        final String filePath=mDirPath+"/"+mImgPaths.get(position);
        //   new  ImageLoader(3, ImageLoader.Type.LIFO).loadImage(mDirPath + "/" + mImgPaths.get(position),vh.mImg);
//        ImageLoader.getInStance(3, ImageLoader.Type.LIFO).loadImage(mDirPath+"/"+mImgPaths.get(position),vh.mImg);
        String filePath = mList.get(position).getLogo();
        Integer id = mList.get(position).getId();
        String interest = mList.get(position).getInterest();
        Glide.with(context).load(filePath).into(vh.mImg);
        final ViewHolder finalVh = vh;
        vh.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //已经被选择

                if (mSelectImg.contains(id)) {
                    String s = String.valueOf(id);
                    mSelectImg.remove(s);
                    titleList.remove(interest);
                    finalVh.mImg.setColorFilter(null);
                    finalVh.mSelect.setImageResource(R.color.transparent);
                } else {
                    //未被选中
                    mSelectImg.add(id);
                    titleList.add(interest);
                    finalVh.mImg.setColorFilter(Color.parseColor("#77000000"));
                    finalVh.mSelect.setImageResource(R.mipmap.xz);
                    for (int i = 0; i < mSelectImg.size(); i++) {
                        Log.d("AAAA", "mSelectImg.get(i):" + mSelectImg.get(i));
                    }
                }

            }
        });
       /* if (mSelectImg.contains(id)){
            vh.mImg.setColorFilter(Color.parseColor("#77000000"));
            vh.mSelect.setImageResource(R.mipmap.xz);
        }*/
        return convertView;
    }


    public List<Integer> selectPhoto() {
        if (!mSelectImg.isEmpty()) {
            return mSelectImg;
        }
        return null;
    }

    public ArrayList<String> selectInterest() {
        if (!mSelectImg.isEmpty()) {
            return titleList;
        }
        return null;
    }

    private class ViewHolder {
        ImageView mImg;
        TextView tag_name;
        ImageButton mSelect;
    }
}
