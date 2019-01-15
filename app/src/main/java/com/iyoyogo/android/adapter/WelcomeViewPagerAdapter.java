package com.iyoyogo.android.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.ui.common.LoginActivity;
import com.iyoyogo.android.ui.common.WelcomeActivity;
import com.iyoyogo.android.utils.SpUtils;

/**
 * 欢迎页
 */
public class WelcomeViewPagerAdapter extends PagerAdapter {
    //界面列表
    private int[] ids;
    private Context context;
    Activity activity;

    public WelcomeViewPagerAdapter(Context context, int[] ids) {
        this.ids = ids;
        this.context = context;
        activity = (Activity) context;
    }


    @Override
    public int getCount() {
        if (ids != null) {
            return ids.length;
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    //初始化每个条目要显示的内容
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.person_zuji_top_imge, null);
        ImageView image = view.findViewById(R.id.imageView);
        image.setImageResource(ids[position]);
        container.addView(view);
        if (position == ids.length - 1) {
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SpUtils.putBoolean(context, "isTrue", true);
                    context.startActivity(new Intent(context, LoginActivity.class));
                    activity.finish();
                }
            });
        }
        return view;
    }

    //销毁条目
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //移除条目
        container.removeView((View) object);
    }
}
