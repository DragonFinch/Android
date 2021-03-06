package com.iyoyogo.android.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorMatrixColorFilter;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.home.HomeBean;
import com.iyoyogo.android.camera.utils.SpUtil;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuDetailActivity;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.ui.home.yoji.YoJiListActivity;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuListActivity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.util.MyConversionUtil;
import com.iyoyogo.android.view.CardTransformer;
import com.iyoyogo.android.view.MyViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 首页数据的适配器
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEWPAGER = 0;
    public static final int YOUXIU = 1;
    public static final int YOUJI = 2;
    public static final int FOOT = 3;
    private Context context;
    private List<HomeBean.DataBean> mList;
    //是否轮循
    private boolean isLoop;
    private static final int TIME = 3000;
    private MyViewPager viewpager;
    private FixedSpeedScroller mScroller;
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

    //使用Thread实现ViewPagerBanner图的效果
    Runnable runnableForViewPager = new Runnable() {
        @Override
        public void run() {
            try {
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

    private String type;
    private String user_token;
    private String user_id;
    private String mCitychengshi;

    //开始轮循
    public void startLoop() {
        if (viewpager != null && !isLoop) {
            mHandler.postDelayed(runnableForViewPager, TIME);
            isLoop = true;
        }
    }

    //停止轮循
    public void stopLoop() {
        if (viewpager != null) {
            mHandler.removeCallbacksAndMessages(null);
            isLoop = false;
        }
    }

    public HomeRecyclerViewAdapter(Context context, List<HomeBean.DataBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
 /*       MyConversionUtil.getInstace().getFileText(context);
        SpannableString spannableString =   MyConversionUtil.getInstace().getExpressionString(context, listBean.getContent());
        holder.tv_content.setText(spannableString);*/
        View view;
        if (viewType == VIEWPAGER) {
            view = LayoutInflater.from(context).inflate(R.layout.vp, viewGroup, false);

            return new Holder_ViewPager(view);
        } else if (viewType == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.youxiu_item, viewGroup, false);
            return new Holder_YouXiu(view);
        } else if (viewType == 2) {
            view = LayoutInflater.from(context).inflate(R.layout.youji_item, viewGroup, false);
            return new Holder_YouJi(view);
        } else if (viewType == 3) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_foot, viewGroup, false);
            return new Holder_Footer(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        type = mList.get(0).getType();
        Log.d("HomeRecyclerViewAdapter", "mList.size():" + mList.size());
        Log.d("HomeRecyclerViewAdapter", "mList.get(position).getYox_list().size():" + mList.get(0).getYox_list().size());
        if (viewHolder instanceof Holder_ViewPager) {
            setViewPagerHolder((Holder_ViewPager) viewHolder, position);
        } else if (viewHolder instanceof Holder_YouXiu) {
            List<HomeBean.DataBean.YoxListBean> yox_list = mList.get(0).getYox_list();
            setYouXiuHolder((Holder_YouXiu) viewHolder, yox_list);
        } else if (viewHolder instanceof Holder_YouJi) {
            List<HomeBean.DataBean.YojListBean> yoj_list = mList.get(0).getYoj_list();
            setYouJiHolder((Holder_YouJi) viewHolder, yoj_list);


        } else if (viewHolder instanceof Holder_Footer) {
            setFootHolder((Holder_Footer) viewHolder);
        }
    }


    @Override
    public int getItemCount() {
        return 4;
    }

    //底部
    private void setFootHolder(Holder_Footer viewHolder) {
        viewHolder.look_more_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YoJiListActivity.class);
                intent.putExtra("position", "");
                intent.putExtra("sign", "");
                intent.putExtra("type", mList.get(0).getType());
                context.startActivity(intent);
            }
        });
    }

    //轮播图
    private void setViewPagerHolder(Holder_ViewPager viewHolder, int position) {
        List<HomeBean.DataBean.BannerListBean> banner_list = mList.get(position).getBanner_list();


        List<HomeBean.DataBean.BannerListBean> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            List<HomeBean.DataBean.BannerListBean> adList = mList.get(position).getBanner_list();

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
        //给viewpager添加滚动动画类
        try {
            // 通过class文件获取mScroller属性
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mScroller = new FixedSpeedScroller(viewpager.getContext(), new AccelerateInterpolator());
            mField.set(viewpager, mScroller);
            mScroller.setmDuration(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //友秀
    private void setYouXiuHolder(Holder_YouXiu youXiuHolder, List<HomeBean.DataBean.YoxListBean> yox_list) {
        youXiuHolder.youxiu_all.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final float[] BT_SELECTED_DARK = new float[]{1, 0, 0, 0, -50, 0, 1,
                        0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0};
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ImageView iv = (ImageView) v;
                    iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED_DARK));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    ImageView iv = (ImageView) v;
                    iv.clearColorFilter();
                }
                return false;

            }
        });
        youXiuHolder.youxiu_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YoXiuListActivity.class);
                intent.putExtra("position", "");
                intent.putExtra("type", mList.get(0).getType());
                context.startActivity(intent);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        String type = mList.get(0).getType();
        YoXiuAdapter yoXiuAdapter = new YoXiuAdapter(yox_list, context, type);
        youXiuHolder.recycler_youxiu.setLayoutManager(linearLayoutManager);
        youXiuHolder.recycler_youxiu.setAdapter(yoXiuAdapter);
        yoXiuAdapter.setOnItemClickListener(new YoXiuAdapter.OnClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(context, YoXiuDetailActivity.class);
                intent.putExtra("id", yox_list.get(position).getId());
                user_id = SpUtils.getString(context, "user_id", null);
                user_token = SpUtils.getString(context, "user_token", null);
                Log.d("HomeRecyclerViewAdapter", "yox_list.get(position).getId():" + yox_list.get(position).getId());
                DataManager.getFromRemote().browse(context, user_id, user_token, String.valueOf(yox_list.get(position).getId()),mCitychengshi)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
//
                            }
                        });
                context.startActivity(intent);
            }
        });
        yoXiuAdapter.setOnRetryClickListener(new YoXiuAdapter.OnRetryClickListener() {
            @Override
            public void onretry() {
                if (onRetryClickListener != null) {
                    onRetryClickListener.onretry();
                }
            }
        });


    }

    private void setYouJiHolder(Holder_YouJi youJiHolder, List<HomeBean.DataBean.YojListBean> yoj_list) {
        youJiHolder.youji_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, YoJiListActivity.class);
                intent.putExtra("sign", "");
                intent.putExtra("position", "");
                intent.putExtra("type", mList.get(0).getType());
                context.startActivity(intent);
            }
        });


        youJiHolder.recycler_youji.setLayoutManager(new LinearLayoutManager(context));
        mCitychengshi = SpUtils.getString(context, "citychengshi", "");
        if (type.equals("attention")) {
            Log.e("attention", "yoj_list.size():" + yoj_list.size());
            YoJiAttentionAdapter yoJiAttentionAdapter = new YoJiAttentionAdapter(context, yoj_list, type);
            youJiHolder.recycler_youji.setLayoutManager(new LinearLayoutManager(context));
            youJiHolder.recycler_youji.setAdapter(yoJiAttentionAdapter);
            yoJiAttentionAdapter.setOnItemClickListener(new YoJiAttentionAdapter.OnClickListener() {
                @Override
                public void onClick(View v, int position) {
                    user_id = SpUtils.getString(context, "user_id", null);
                    user_token = SpUtils.getString(context, "user_token", null);
                    DataManager.getFromRemote().browse(context, user_id, user_token, String.valueOf(yoj_list.get(position).getYo_id()),mCitychengshi)
                            .subscribe(new Consumer<BaseBean>() {
                                @Override
                                public void accept(BaseBean baseBean) throws Exception {

                                }
                            });
                    int yo_id = yoj_list.get(position).getYo_id();
                    Intent intent = new Intent(context, YoJiDetailActivity.class);
                    intent.putExtra("yo_id", yo_id);
                    context.startActivity(intent);
                }
            });
            yoJiAttentionAdapter.onItemRetryOnClickListener(new YoJiAttentionAdapter.OnRetryConnection() {
                @Override
                public void on_retry() {
                    if (retryConnection != null) {
                        retryConnection.on_retry();
                        notifyDataSetChanged();
                    }
                }
            });
            yoJiAttentionAdapter.setOnRetryClickListener(new YoJiAttentionAdapter.OnRetryClickListener() {
                @Override
                public void onretry() {
                    if (onRetryClickListener != null) {
                        onRetryClickListener.onretry();
                    }
                }
            });

        } else {
            Log.d("Size", "yoj_list.size():" + yoj_list.size());
            YoJiAdapter yoJiAdapter = new YoJiAdapter(context, yoj_list);
            Log.d("Size", "yoj_list.size():" + yoj_list.size());
            youJiHolder.recycler_youji.setAdapter(yoJiAdapter);
            yoJiAdapter.setOnItemClickListener(new YoJiAdapter.OnClickListener() {
                @Override
                public void onClick(View v, int position) {
                    int yo_id = yoj_list.get(position).getYo_id();
                    user_id = SpUtils.getString(context, "user_id", null);
                    user_token = SpUtils.getString(context, "user_token", null);
                    Log.e("zxcvbn", "onClick: " + user_id + "..." + user_token);
                    DataManager.getFromRemote().browse(context, user_id, user_token, String.valueOf(yoj_list.get(position).getYo_id()),mCitychengshi)
                            .subscribe(new Consumer<BaseBean>() {
                                @Override
                                public void accept(BaseBean baseBean) throws Exception {

                                }
                            });
                    Intent intent = new Intent(context, YoJiDetailActivity.class);
                    intent.putExtra("yo_id", yo_id);
                    context.startActivity(intent);
                }
            });
            yoJiAdapter.setOnRetryClickListener(new YoJiAdapter.OnRetryClickListener() {
                @Override
                public void onretry() {
                    if (onRetryClickListener != null) {
                        onRetryClickListener.onretry();
                    }
                }
            });
        }

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
        } else if (position == 3) {
            return FOOT;
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

    public class Holder_Recommend extends RecyclerView.ViewHolder {

        public Holder_Recommend(@NonNull View itemView) {
            super(itemView);
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

    public class Holder_Footer extends RecyclerView.ViewHolder {
        TextView look_more;
        RelativeLayout look_more_relative;

        public Holder_Footer(@NonNull View itemView) {
            super(itemView);
            look_more = itemView.findViewById(R.id.look_more);
            look_more_relative = itemView.findViewById(R.id.look_more_relative);
        }
    }

    public interface OnRetryConnection {
        void on_retry();
    }

    OnRetryConnection retryConnection;

    public void onRetryClickListener(OnRetryConnection retryConnection) {
        this.retryConnection = retryConnection;
    }

    public interface OnRetryClickListener {
        void onretry();
    }

    OnRetryClickListener onRetryClickListener;

    public void onItemRetryOnClickListener(OnRetryClickListener onRetryClickListener) {
        this.onRetryClickListener = onRetryClickListener;
    }
}
