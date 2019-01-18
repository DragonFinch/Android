package com.iyoyogo.android.ui.mine.homepage;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.app.Constants;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.mine.center.UserCenterBean;
import com.iyoyogo.android.contract.PersonalCenterContract;
import com.iyoyogo.android.presenter.PersonalCenterPresenter;
import com.iyoyogo.android.ui.mine.BigPictureActivity;
import com.iyoyogo.android.ui.mine.collection.CollectionActivity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.FastBlurUtil;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人主页
 */

public class Personal_homepage_Activity extends BaseActivity<PersonalCenterContract.Presenter> implements PersonalCenterContract.View {


    @BindView(R.id.img_bg)
    ImageView imgBg;
    @BindView(R.id.img_sign)
    ImageView imgSign;
    @BindView(R.id.img_view)
    ImageView imgView;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.personal_bg_rl_id)
    RelativeLayout personalBgRlId;
    @BindView(R.id.tv_attention_count)
    TextView tvAttentionCount;
    @BindView(R.id.tv_collection_count)
    TextView tvCollectionCount;
    @BindView(R.id.tv_fans_count)
    TextView tvFansCount;
    @BindView(R.id.img_user_icon)
    CircleImageView imgUserIcon;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_star)
    TextView tvStar;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.my_collection)
    LinearLayout myCollection;
    @BindView(R.id.get_hisFans)
    LinearLayout getHisFans;
    @BindView(R.id.rb_yoji)
    RadioButton rbYoji;
    @BindView(R.id.rb_yoxiu)
    RadioButton rbYoxiu;
    @BindView(R.id.group)
    RadioGroup group;
    @BindView(R.id.frame_container)
    FrameLayout frameContainer;
    @BindView(R.id.img_vip_sign)
    ImageView imgVipSign;
    @BindView(R.id.img_level)
    ImageView imgLevel;
    @BindView(R.id.collect)
    LinearLayout collect;

    private String user_id;
    private String user_token;
    private int age;
    private String yo_user_id;
    private String user_logo;
    private String user_nickName;
    public static boolean flag = false;
    private YoJiFragment yoJiFragment;
    String user_logo_big;
    YoXiuFragment yoXiuFragment;

    protected void initView() {
        super.initView();
        statusbar();
        yoXiuFragment = new YoXiuFragment();
        yoJiFragment = new YoJiFragment();
    }

    public int getAge(Date birthDay) throws Exception {
        //获取当前系统时间
        Calendar cal = Calendar.getInstance();
        //如果出生日期大于当前时间，则抛出异常
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        //取出系统当前时间的年、月、日部分
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        //将日期设置为出生日期
        cal.setTime(birthDay);
        //取出出生日期的年、月、日部分
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        //当前年份与出生年份相减，初步计算年龄
        int age = yearNow - yearBirth;
        //当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
        if (monthNow <= monthBirth) {
            //如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        System.out.println("age:" + age);
        return age;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        user_id = SpUtils.getString(Personal_homepage_Activity.this, "user_id", null);
        user_token = SpUtils.getString(Personal_homepage_Activity.this, "user_token", null);
        Intent intent = getIntent();
        yo_user_id = intent.getStringExtra("yo_user_id");
//        mPresenter.getPersonalCenter(user_id, user_token, yo_user_id);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_homepage;
    }

    @Override
    protected PersonalCenterContract.Presenter createPresenter() {
        return new PersonalCenterPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.getPersonalCenter(user_id, user_token, yo_user_id);
        rbYoji.setChecked(true);
      /*  switchContent(yoJiFragment,yoXiuFragment);
        Bundle bundle = new Bundle();
        bundle.putString("yo_user_id", yo_user_id);
        yoJiFragment.setArguments(bundle);
        yoXiuFragment.setArguments(bundle);*/
    }

    @OnClick({R.id.img_back, R.id.img_share, R.id.my_collection, R.id.get_hisFans, R.id.collect, R.id.img_view,R.id.img_user_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_share:
                share();
                break;
            case R.id.my_collection:
                Intent intent = new Intent(this, MyFollowActivity.class);
                intent.putExtra("yo_user_id", yo_user_id);
                startActivity(intent);
                break;
            case R.id.get_hisFans:
                Intent intent1 = new Intent(this, HisFansActivity.class);
                intent1.putExtra("yo_user_id", yo_user_id);
                startActivity(intent1);
                break;
            case R.id.collect:
                Intent intent2 = new Intent(this, CollectionActivity.class);
                intent2.putExtra("collect", 2);
                startActivity(intent2);
                break;
            case R.id.img_view:
                if (YoJiFragment.mList.size() != 0) {
                    if (imgView.getDrawable().getCurrent().getConstantState().equals(ContextCompat.getDrawable(this, R.mipmap.view22).getConstantState())) {
                        imgView.setImageResource(R.mipmap.view11);
                        yoJiFragment.refreshData2();
//                        YoJiFragment.yoJiCenterAdapter.notifyDataSetChanged();
//                        recyclerYoji.setLayoutManager(new LinearLayoutManager(this));
//                        recyclerYoji.setAdapter(YoJiFragment.yoJiCenterAdapter);
                    } else {
                        imgView.setImageResource(R.mipmap.view22);
                        yoJiFragment.refreshData();
//                        YoJiFragment.yoJiContentAdapter2.notifyDataSetChanged();
//                        recyclerYoji.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//                        recyclerYoji.setAdapter(YoJiFragment.yoJiContentAdapter2);
                    }
//                    if (flag == false) {
//                        flag = true;
//                        imgView.setImageResource(R.mipmap.view2);
//                        recyclerYoji = getSupportFragmentManager().findFragmentById(R.id.frame_container).getView().findViewById(R.id.recycler_yoji);
//                        recyclerYoji.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//                        recyclerYoji.setAdapter(YoJiFragment.yoJiContentAdapter2);
//                        YoJiFragment.yoJiContentAdapter2.notifyDataSetChanged();
//                    } else {
//                        flag = false;
//                        imgView.setImageResource(R.mipmap.view1);
//                        recyclerYoji.setLayoutManager(new LinearLayoutManager(this));
//                        recyclerYoji.setAdapter(YoJiFragment.yoJiCenterAdapter);
//                        YoJiFragment.yoJiCenterAdapter.notifyDataSetChanged();
//                    }
                }
                break;
            case R.id.img_user_icon:
                Intent intent3 = new Intent(this, BigPictureActivity.class);
                intent3.putExtra("url",user_logo_big);
                startActivity(intent3);
                break;
        }
    }

    public void share() {
        View view = getLayoutInflater().inflate(R.layout.popup_share_user, null);
        PopupWindow popup_share = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(getApplicationContext(), 310), true);
        popup_share.setBackgroundDrawable(new ColorDrawable());
        popup_share.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup_share.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        LinearLayout qq_layout = view.findViewById(R.id.qq_layout);
        LinearLayout comment_layout = view.findViewById(R.id.comment_layout);
        LinearLayout wechat_layout = view.findViewById(R.id.wechat_layout);
        LinearLayout sina_layout = view.findViewById(R.id.sina_layout);
        LinearLayout clone_layout = view.findViewById(R.id.clone_layout);
        TextView tv_cancel = view.findViewById(R.id.cancel);
        ImageView img_close = view.findViewById(R.id.close_img);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popup_share.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
            }
        });
        comment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
                shareWeb(SHARE_MEDIA.WEIXIN_CIRCLE);
            }
        });
        wechat_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
                shareWeb(SHARE_MEDIA.WEIXIN);
            }
        });
        sina_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
                shareWeb(SHARE_MEDIA.SINA);
            }
        });
        qq_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWeb(SHARE_MEDIA.QQ);
                popup_share.dismiss();
            }
        });
        clone_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(Constants.BASE_URL+ "home/share/center_yoj/share_user_id/" + user_id + "/his_id/" + yo_user_id);
                Toast.makeText(Personal_homepage_Activity.this, "链接已复制到剪贴板", Toast.LENGTH_SHORT).show();
                popup_share.dismiss();
            }
        });
        backgroundAlpha(0.6f);

        //添加pop窗口关闭事件
        popup_share.setOnDismissListener(new poponDismissListener());
        popup_share.showAtLocation(findViewById(R.id.activity_personal_homepage), Gravity.BOTTOM, 0, 0);

    }

    private void shareWeb(SHARE_MEDIA share_media) {
        /*80002/yo_id/4143*/
        String url = Constants.BASE_URL+ "home/share/center_yoj/share_user_id/" + user_id + "/his_id/" + yo_user_id;
        UMWeb web = new UMWeb(url);
        web.setTitle(user_nickName);//标题
        UMImage thumb = new UMImage(getApplicationContext(), R.mipmap.logo);
        web.setThumb(thumb);  //缩略图

        web.setDescription("个人主页");//描述

        new ShareAction(Personal_homepage_Activity.this)
                .withMedia(web)
                .setPlatform(share_media)
                .share();
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }

    //隐藏事件PopupWindow
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1.0f);
        }
    }

    @Override
    public void getPersonalCenterSuccess(UserCenterBean.DataBean data) {
        List<Fragment> fragments = new ArrayList<>();

        Bundle bundle = new Bundle();
        bundle.putString("yo_user_id", yo_user_id);
        yoJiFragment.setArguments(bundle);
        yoXiuFragment.setArguments(bundle);
//        fragments.add(yoJiFragment);
//        fragments.add(yoXiuFragment);
        switchContent(yoXiuFragment,yoJiFragment);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 具体的fragment切换逻辑可以根据应用调整，例如使用show()/hide()
                switch (checkedId){
                    case R.id.rb_yoji:
                        imgView.setVisibility(View.VISIBLE);
                        switchContent(yoXiuFragment,yoJiFragment);
                        break;
                    case R.id.rb_yoxiu:
                        imgView.setVisibility(View.GONE);
                        switchContent(yoJiFragment,yoXiuFragment);
                        break;
                }
            }
        });
        user_logo = data.getUser_logo();
        user_nickName = data.getUser_nickname();
        rbYoji.setText(getResources().getString(R.string.yoji) + "  " + data.getCount_yoj());
        rbYoxiu.setText(getResources().getString(R.string.yoxiu) + "  " + data.getCount_yox());
        List<String> titles = new ArrayList<>();
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.default_touxiang)
                .error(R.mipmap.default_touxiang);
        Glide.with(this).load(data.getUser_logo()).apply(requestOptions).into(imgUserIcon);
        String user_sex = data.getUser_sex();
        String user_birthday = data.getUser_birthday();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = df.parse(user_birthday);
            age = getAge(date);
            tvAge.setText(age + "岁");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {


        }

        if (user_sex.equals("男")) {
            Drawable nan_xuanzhong = getResources().getDrawable(
                    R.mipmap.nv_wxz);

            tvAge.setCompoundDrawablesWithIntrinsicBounds(nan_xuanzhong,
                    null, null, null);
        } else {
            Drawable nv_xz = getResources().getDrawable(
                    R.mipmap.nv_xz);

            tvAge.setCompoundDrawablesWithIntrinsicBounds(nv_xz,
                    null, null, null);
        }
        tvNickName.setText(data.getUser_nickname());
        tvAttentionCount.setText(data.getCount_attention() + "");
        tvCollectionCount.setText(data.getCount_collect() + "");
        tvFansCount.setText(data.getCount_fans() + "");
        tvCity.setText(data.getUser_city());

      /*  titles.add(getResources().getString(R.string.yoji) + "  " + data.getCount_yoj());
        titles.add(getResources().getString(R.string.yoxiu) + "  " + data.getCount_yox());
        MineFragmentAdapter mineFragmentAdapter = new MineFragmentAdapter(getSupportFragmentManager(), fragments, titles);
        personalVpId.setAdapter(mineFragmentAdapter);
        tab.setupWithViewPager(personalVpId);*/

        //获取用户等级
        int user_level = data.getUser_level();
        if (user_level == 1) {
            imgLevel.setVisibility(View.VISIBLE);
            imgLevel.setImageResource(R.mipmap.lv1);
            imgVipSign.setImageResource(R.mipmap.level_one);
            imgSign.setImageResource(R.mipmap.sign_one);
        } else if (user_level == 2) {
            imgLevel.setVisibility(View.VISIBLE);
            imgLevel.setImageResource(R.mipmap.lv2);
            imgVipSign.setImageResource(R.mipmap.level_two);
            imgSign.setImageResource(R.mipmap.sign_two);
        } else if (user_level == 3) {
            imgLevel.setVisibility(View.VISIBLE);
            imgLevel.setImageResource(R.mipmap.lv3);
            imgVipSign.setImageResource(R.mipmap.level_three);
            imgSign.setImageResource(R.mipmap.sign_three);
        } else if (user_level == 4) {
            imgLevel.setVisibility(View.VISIBLE);
            imgLevel.setImageResource(R.mipmap.lv4);
            imgVipSign.setImageResource(R.mipmap.level_four);
            imgSign.setImageResource(R.mipmap.sign_four);
        } else if (user_level == 5) {
            imgLevel.setVisibility(View.VISIBLE);
            imgLevel.setImageResource(R.mipmap.lv5);
            imgVipSign.setImageResource(R.mipmap.level_five);
            imgSign.setImageResource(R.mipmap.sign_five);
        } else {
            imgLevel.setVisibility(View.GONE);
            imgVipSign.setImageResource(R.mipmap.level_zero);
            imgSign.setImageResource(R.mipmap.sign_zero);
        }

//        final String pattern = "2";
//        if (Patterns.WEB_URL.matcher(data.getUser_logo()).matches()) {
//            final String url =
//                    data.getUser_logo();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    int scaleRatio = 0;
//                    if (TextUtils.isEmpty(pattern)) {
//                        scaleRatio = 0;
//                    } else if (scaleRatio < 0) {
//                        scaleRatio = 10;
//                    } else {
//                        scaleRatio = Integer.parseInt(pattern);
//                    }
//                    //                        下面的这个方法必须在子线程中执行
//                    final Bitmap blurBitmap2 = FastBlurUtil.GetUrlBitmap(url, scaleRatio);
//
//                    //                        刷新ui必须在主线程中执行
//                    runOnUiThread(new Runnable() {//这个是我自己封装的在主线程中刷新ui的方法。
//                        @Override
//                        public void run() {
//                            imgBg.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                            imgBg.setImageBitmap(blurBitmap2);
//                        }
//                    });
//                }
//            }).start();
//        } else {
//            int scaleRatio = 0;
//            if (TextUtils.isEmpty(pattern)) {
//                scaleRatio = 0;
//            } else if (scaleRatio < 0) {
//                scaleRatio = 10;
//            } else {
//                scaleRatio = Integer.parseInt(pattern);
//            }
//
//            //        获取需要被模糊的原图bitmap
//            Resources res = getResources();
//            Bitmap scaledBitmap = BitmapFactory.decodeResource(res, R.mipmap.default_touxiang);
//
//            //        scaledBitmap为目标图像，10是缩放的倍数（越大模糊效果越高）
//            Bitmap blurBitmap = FastBlurUtil.toBlur(scaledBitmap, scaleRatio);
//            imgBg.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imgBg.setImageBitmap(blurBitmap);
//        }
        user_logo_big = data.getUser_logo_big();

    }

    @Override
    public void addAttention1(AttentionBean attentionBean) {

    }


    @Override
    public void deleteAttention(AttentionBean attentionBean) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commitAllowingStateLoss();
    }
    /**
     * 是否第一次
     */
    private boolean mIsFirstIn = true;
    private void switchContent(Fragment from, Fragment to) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            // 先判断是否被add过
            if (mIsFirstIn) {
                mIsFirstIn = false;
                ft.add(R.id.frame_container, to).commit();
            } else {
                ft.hide(from).add(R.id.frame_container, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            }
        } else {
            ft.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
    }

}
