package com.iyoyogo.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.home.HomeViewPagerBean;
import com.iyoyogo.android.ui.common.WebActivity;
import com.iyoyogo.android.utils.RoundTransform;

import java.util.List;

public class HomeViewPagerAdapter extends PagerAdapter {
    private List<HomeViewPagerBean.DataBean.BannerListBean> images;
    private Context context;

    public HomeViewPagerAdapter(Context context, List<HomeViewPagerBean.DataBean.BannerListBean> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images == null ? 0 : images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.viewpager_item, null);
        ImageView imageView = view.findViewById(R.id.imageView);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new RoundTransform(context,8));
/*

        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int tempWidth = bitmap.getWidth();
        int tempHeight = bitmap.getHeight();
        imageView.setImageBitmap(bitmap);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
        params.height = (int) (tempHeight * ((double) dm.widthPixels / (double) tempWidth));

        layout.setLayoutParams(params);*/
        Glide.with(context)
                .load(images.get(position).getPath())
                .apply(requestOptions)

                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int user_id = Integer.parseInt(SPUtil.get(context, Constant.USER_ID, 0).toString());
                String target_url = images.get(position).getTarget_url();
                Intent intent = new Intent(context, WebActivity.class);
                if (target_url.length()>0){
                    intent.putExtra("url",target_url);
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context, "链接为空", Toast.LENGTH_SHORT).show();
                }

              /*  if ("V".equals(remark)) {
                    ActivityUtils.goVideoActivity(context, images.get(position).getJumpAddr());
                    return;
                }
                NetWorkManager.getRequest().getRecommandBannerAdCLick(images.get(position).getAdId(), user_id)
                        .compose(ResponseTransformer.handleResult())
                        .compose(SchedulerProvider.getInstance().applySchedulers())
                        .subscribe(result -> {
                            ActivityUtils.goWebView(context, images.get(position).getJumpAddr());
                        }, throwable ->
                                ToastUtil.showToast(context, ((ApiException) throwable).getDisplayMessage()));

                //ActivityUtils.goWebView(context,images.get(position).getJumpAddr());
*/
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}


