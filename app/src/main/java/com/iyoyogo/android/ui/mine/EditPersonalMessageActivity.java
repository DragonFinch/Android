package com.iyoyogo.android.ui.mine;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.iyoyogo.android.R;
import com.iyoyogo.android.ui.common.EmptyActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditPersonalMessageActivity extends AppCompatActivity {
    @BindView(R.id.back_iv_id)
    ImageView backIvId;
    @BindView(R.id.preservation_tv_id)
    TextView preservationTvId;
    @BindView(R.id.head_im_id)
    ImageView headImId;
    @BindView(R.id.nick_name)
    TextView nickName;
    @BindView(R.id.girl)
    RadioButton girl;
    @BindView(R.id.boy)
    RadioButton boy;
    @BindView(R.id.group_sex)
    RadioGroup groupSex;
    @BindView(R.id.brith_tv_id)
    TextView brithTvId;
    @BindView(R.id.brith_rl_id)
    RelativeLayout brithRlId;
    @BindView(R.id.tv_start_seat)
    TextView tvStartSeat;
    @BindView(R.id.city_tv_id)
    TextView cityTvId;
    @BindView(R.id.city_rl_id)
    RelativeLayout cityRlId;
    @BindView(R.id.main_llayout_id)
    LinearLayout mainLlayoutId;
    private RelativeLayout relativeLayout;
    private TextView textView;
    private RelativeLayout cityLayout;
    private TextView citytextView;
    private TextView preser;
    private View pop_view;
    private PopupWindow popMenu;
    private ImageView headimage;

    // 声明PopupWindow
    private PopupWindow popupWindow;
    // 声明PopupWindow对应的视图
    private View popupView;

    // 声明平移动画
    private TranslateAnimation animation;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_message);
        ButterKnife.bind(this);

        init();
        initListener();
    }

    public String getStarSeat(int mouth, int day) {
        String starSeat = null;

        if ((mouth == 3 && day >= 21) || (mouth == 4 && day <= 19)) {
            starSeat = "白羊座";
        } else if ((mouth == 4 && day >= 20) || (mouth == 5 && day <= 20)) {
            starSeat = "金牛座";
        } else if ((mouth == 5 && day >= 21) || (mouth == 6 && day <= 21)) {
            starSeat = "双子座";
        } else if ((mouth == 6 && day >= 22) || (mouth == 7 && day <= 22)) {
            starSeat = "巨蟹座";
        } else if ((mouth == 7 && day >= 23) || (mouth == 8 && day <= 22)) {
            starSeat = "狮子座";
        } else if ((mouth == 8 && day >= 23) || (mouth == 9 && day <= 22)) {
            starSeat = "处女座";
        } else if ((mouth == 9 && day >= 23) || (mouth == 10 && day <= 23)) {
            starSeat = "天秤座";
        } else if ((mouth == 10 && day >= 24) || (mouth == 11 && day <= 22)) {
            starSeat = "天蝎座";
        } else if ((mouth == 11 && day >= 23) || (mouth == 12 && day <= 21)) {
            starSeat = "射手座";
        } else if ((mouth == 12 && day >= 22) || (mouth == 1 && day <= 19)) {
            starSeat = "摩羯座";
        } else if ((mouth == 1 && day >= 20) || (mouth == 2 && day <= 18)) {
            starSeat = "水瓶座";
        } else {
            starSeat = "双鱼座";
        }
        return starSeat;
    }

    private void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //设置头像
        headimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeIcon();

            }
        });

        //保存

        preser.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                initPopuptWindow();

            }
        });
        //跳转城市选择
        cityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditPersonalMessageActivity.this, EmptyActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String tmp = intent.getStringExtra("mycity");
        citytextView.setText(tmp);

        //时间选择
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTiem();
            }
        });
    }

    private void init() {
        preser = findViewById(R.id.preservation_tv_id);
        relativeLayout = findViewById(R.id.brith_rl_id);
        textView = findViewById(R.id.brith_tv_id);
        cityLayout = findViewById(R.id.city_rl_id);
        citytextView = findViewById(R.id.city_tv_id);
        headimage = findViewById(R.id.head_im_id);
        back = findViewById(R.id.back_iv_id);

        //返回

    }

    private void MyTiem() {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {

                textView.setText(getTime(date));
                int month = date.getMonth();
                int day = date.getDay();
                String starSeat = getStarSeat(month, day);
                tvStartSeat.setText(starSeat);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
//                .setContentSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                //.setTitleText("Title")//标题文字
//                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
//                .isCyclic(true)//是否循环滚动
//                //.setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                //.setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
////                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
////                .setRangDate(startDate,endDate)//起始终止年月日设定
//                //.setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                //.isDialog(true)//是否显示为对话框样式
                .build();

        pvTime.show();

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        //"YYYY-MM-DD HH:MM:SS"        "yyyy-MM-dd"
        SimpleDateFormat format;
        format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initPopuptWindow() {
        pop_view = View.inflate(this, R.layout.item_save_popup, null);
        pop_view.findViewById(R.id.popup_llayout_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMenu.dismiss();
            }
        });
        popMenu = new PopupWindow(pop_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popMenu.setFocusable(true);//设置pw中的控件能够获取焦点
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popMenu.setBackgroundDrawable(dw);//设置mPopupWindow背景颜色或图片，这里设置半透明
        popMenu.setOutsideTouchable(true); //设置可以通过点击mPopupWindow外部关闭mPopupWindow
//        popMenu.setAnimationStyle(R.style.PopupAnimationAmount);//设置mPopupWindow的进出动画
        popMenu.update();//刷新mPopupWindow
        popMenu.showAsDropDown(pop_view, Gravity.CENTER, 0, 0);//mPopupWindow显示的位置
        /**
         * PopupWindow消失监听方法
         */
        popMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                Toast.makeText(getContext(), "退出", Toast.LENGTH_SHORT).show();
                Toast.makeText(EditPersonalMessageActivity.this, "保存成功!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.photo:
                // TODO 弹出popupwind选择拍照或者从相册选择
                break;

            case R.id.camera:
                // TODO 弹出popupwind选择拍照或者从相册选择
                break;

            case R.id.cancel:
                // TODO 弹出popupwind选择拍照或者从相册选择
                break;
        }
    }

    /**
     * 弹出popupWindow更改头像
     */
    private void changeIcon() {
        if (popupWindow == null) {
            popupView = View.inflate(this, R.layout.itme_head_image, null);
            // 参数2,3：指明popupwindow的宽度和高度
            popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    lighton();
                }
            });

            // 设置背景图片， 必须设置，不然动画没作用
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setFocusable(true);

            // 设置点击popupwindow外屏幕其它地方消失
            popupWindow.setOutsideTouchable(true);

            // 平移动画相对于手机屏幕的底部开始，X轴不变，Y轴从1变0
            animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                    Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
            animation.setInterpolator(new AccelerateInterpolator());
            animation.setDuration(200);

            popupView.findViewById(R.id.photo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 打开系统拍照程
                    Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    popupWindow.dismiss();
                    lighton();
                }
            });
            popupView.findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 打开系统图库选择图片
                    Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    popupWindow.dismiss();
                    lighton();
                }
            });
            popupView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 取消
                    popupWindow.dismiss();
                    lighton();
                }
            });
        }

        // 在点击之后设置popupwindow的销毁
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
            lighton();
        }

        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        popupWindow.showAtLocation(EditPersonalMessageActivity.this.findViewById(R.id.main_llayout_id),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popupView.startAnimation(animation);
    }

    /**
     * 设置手机屏幕亮度变暗
     */
    private void lightoff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
    }

    /**
     * 设置手机屏幕亮度显示正常
     */
    private void lighton() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
    }
}
