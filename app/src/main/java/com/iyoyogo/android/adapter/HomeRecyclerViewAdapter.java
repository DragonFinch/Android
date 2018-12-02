package com.iyoyogo.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.home.HomeViewPagerBean;
import com.iyoyogo.android.ui.home.recommend.YoXiuDetailActivity;
import com.iyoyogo.android.view.CardTransformer;
import com.iyoyogo.android.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEWPAGER = 0;
    public static final int YOUXIU = 1;
    public static final int YOUJI = 2;
    private Context context;
    private List<HomeViewPagerBean.DataBean> mList;
    private boolean isLoop;
    private static final int TIME = 2000;
    private MyViewPager viewpager;
    private int mCount;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int item = viewpager.getCurrentItem() + 1;
//            viewPager.setCurrentItem(item);
            mHandler.postDelayed(runnableForViewPager, TIME);
        }
    };
    Runnable runnableForViewPager = new Runnable() {
        @Override
        public void run() {
            try {
                Log.i("mzh", "task is run.");
                int currentItem = viewpager.getCurrentItem();
                if (currentItem == mCount - 1) {
                    currentItem = 0;
                } else {
                    currentItem++;
                }
                mHandler.postDelayed(this, TIME);
                viewpager.setCurrentItem(currentItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private ImageView img_yoji;
    private ImageView yoji_all;
    private RecyclerView recycler_yoji;

    public void startLoop() {
        if (viewpager != null && !isLoop) {
            mHandler.postDelayed(runnableForViewPager, TIME);
            isLoop = true;
        }
    }

    public void stopLoop() {
        if (viewpager != null) {
            mHandler.removeCallbacksAndMessages(null);
            isLoop = false;
        }
    }

    public HomeRecyclerViewAdapter(Context context, List<HomeViewPagerBean.DataBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view;
        if (viewType == VIEWPAGER) {
            view = LayoutInflater.from(context).inflate(R.layout.vp, viewGroup, false);

            return new Holder_ViewPager(view);
        } else if (viewType == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.youxiu_item, viewGroup, false);

            return new Holder_YouXiu(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.youji_item, viewGroup, false);
            return new Holder_YouJi(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Log.d("HomeRecyclerViewAdapter", "mList.size():" + mList.size());
        Log.d("HomeRecyclerViewAdapter", "mList.get(position).getYox_list().size():" + mList.get(0).getYox_list().size());
        if (viewHolder instanceof Holder_ViewPager) {
            setViewPagerHolder((Holder_ViewPager) viewHolder, position);

        } else if (viewHolder instanceof Holder_YouXiu) {
            List<HomeViewPagerBean.DataBean.YoxListBean> yox_list = mList.get(0).getYox_list();
            setYouXiuHolder((Holder_YouXiu) viewHolder, yox_list);
        } else if (viewHolder instanceof Holder_YouJi) {
            setYouJiHolder((Holder_YouJi) viewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    //轮播图
    private void setViewPagerHolder(Holder_ViewPager viewHolder, int position) {
        List<HomeViewPagerBean.DataBean.BannerListBean> banner_list = mList.get(position).getBanner_list();


        List<HomeViewPagerBean.DataBean.BannerListBean> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            List<HomeViewPagerBean.DataBean.BannerListBean> adList = mList.get(position).getBanner_list();

            list.addAll(adList);
        }
        mCount = list.size();
        Log.e("IndexRecommendRecyclerV", "list.size():" + list.size());

        viewpager = viewHolder.viewPager;
        viewHolder.viewPager.setPageTransformer(true, new CardTransformer());
        viewpager.setAdapter(new HomeViewPagerAdapter(context, list));
        viewHolder.viewPager.setClipChildren(false);

        viewHolder.viewPager.setFocusableInTouchMode(false);
        viewHolder.viewPager.requestFocus();
        mHandler.postDelayed(runnableForViewPager, TIME);
        viewHolder.viewPager.setOffscreenPageLimit(2);

        viewHolder.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                if (list.size() == position) {
                    viewpager.setCurrentItem(0);
                    mHandler.sendEmptyMessageDelayed(0, 2000);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    isLoop = true;
                    mHandler.removeCallbacksAndMessages(null);
                } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                    isLoop = false;
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler.postDelayed(runnableForViewPager, TIME);
                } else if (state == ViewPager.SCROLL_STATE_SETTLING) {

                }

            }
        });
        isLoop = true;

    }

    //友秀
    private void setYouXiuHolder(Holder_YouXiu youXiuHolder, List<HomeViewPagerBean.DataBean.YoxListBean> yox_list) {

        YoXiuAdapter yoXiuAdapter = new YoXiuAdapter(yox_list, context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        youXiuHolder.recycler_youxiu.setLayoutManager(linearLayoutManager);
        youXiuHolder.recycler_youxiu.setAdapter(yoXiuAdapter);
        yoXiuAdapter.setOnItemClickListener(new YoXiuAdapter.OnClickListener() {
            @Override
            public void onClick(View v, int position) {
                context.startActivity(new Intent(context, YoXiuDetailActivity.class));
            }
        });
    }

    private void setYouJiHolder(Holder_YouJi youJiHolder, int position) {
      /*  Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://v.juhe.cn/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.test("toutiao/index?type=top&key=8838c5c10b0613d95fbfc4dfc47eaa93")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TestBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TestBean testBean) {
                        List<TestBean.ResultBean.DataBean> data = testBean.getResult().getData();
                        Log.d("HomeRecyclerViewAdapter", "data.size():" + data.size());
                        YoJiAdapter yoJiAdapter = new YoJiAdapter(data, context);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        youJiHolder.recycler_youji.setLayoutManager(linearLayoutManager);
                        youJiHolder.recycler_youji.setAdapter(yoJiAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("HomeRecyclerViewAdapter", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
        youJiHolder.recycler_youji.setVisibility(View.GONE);
    }

    public void disVisible() {
        if (isLoop) {
            stopLoop();
        }
    }

    /**
     * 可见
     */
    public void inVisiable() {
        if (!isLoop) {
            startLoop();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEWPAGER;
        } else if (position == 1) {
            return YOUXIU;
        } else if (position == 2) {
            return YOUJI;
        }
        return super.getItemViewType(position);

    }

    public class Holder_ViewPager extends RecyclerView.ViewHolder {
        public MyViewPager viewPager;

        public Holder_ViewPager(@NonNull View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.viewPager);
        }
    }

    public class Holder_YouXiu extends RecyclerView.ViewHolder {
        ImageView img_youxiu, youxiu_all;
        RecyclerView recycler_youxiu;

        public Holder_YouXiu(@NonNull View itemView) {
            super(itemView);
            img_youxiu = itemView.findViewById(R.id.img_youxiu);
            youxiu_all = itemView.findViewById(R.id.youxiu_all);
            recycler_youxiu = itemView.findViewById(R.id.recycler_youxiu_item);
        }
    }

    public class Holder_YouJi extends RecyclerView.ViewHolder {
        ImageView img_youji, youji_all;
        RecyclerView recycler_youji;

        public Holder_YouJi(@NonNull View itemView) {
            super(itemView);
            img_youji = itemView.findViewById(R.id.img_yoji);
            youji_all = itemView.findViewById(R.id.yoji_all);
            recycler_youji = itemView.findViewById(R.id.recycler_yoji);
        }
    }
}
