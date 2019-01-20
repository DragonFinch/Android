package com.iyoyogo.android.ui.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.OSSUtils;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.TestActivity;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.mine.GetUserInfoBean;
import com.iyoyogo.android.contract.EditPersonalContract;
import com.iyoyogo.android.net.OssService;
import com.iyoyogo.android.presenter.EditPersonalPresenter;
import com.iyoyogo.android.ui.common.LikePrefencesActivity;
import com.iyoyogo.android.ui.home.HomeFragment;
import com.iyoyogo.android.ui.home.yoxiu.ChannelActivity;
import com.iyoyogo.android.utils.KeyBoardUtils;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.view.LoadingDialog;
import com.iyoyogo.android.widget.CircleImageView;
import com.iyoyogo.android.widget.flow.FlowLayout;
import com.iyoyogo.android.widget.flow.TagAdapter;
import com.iyoyogo.android.widget.flow.TagFlowLayout;
import com.iyoyogo.android.widget.wheel.DateChooseWheelViewDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 修改用户信息
 */
public class EditPersonalMessageActivity extends BaseActivity<EditPersonalContract.Presenter> implements EditPersonalContract.View, OssService.UploadImageListener {
    private static String path = "/sdcard/myHead/";//sd路径
    @BindView(R.id.back_iv_id)
    ImageView backIvId;
    @BindView(R.id.preservation_tv_id)
    TextView preservationTvId;
    @BindView(R.id.head_im_id)
    CircleImageView headImId;
    @BindView(R.id.nick_name)
    EditText nickName;
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
    @BindView(R.id.tv_interest)
    TextView tvInterest;
    @BindView(R.id.flow_interest)
    TagFlowLayout flow_interest;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.city_tv_id)
    TextView cityTvId;
    @BindView(R.id.city_rl_id)
    RelativeLayout cityRlId;
    @BindView(R.id.user_id)
    TextView userId;
    @BindView(R.id.main_llayout_id)
    LinearLayout mainLlayoutId;
    @BindView(R.id.interest_layout)
    RelativeLayout interestLayout;

    private RelativeLayout relativeLayout;
    private TextView textView;
    private RelativeLayout cityLayout;
    private TextView citytextView;
    private TextView preser;
    private View pop_view;
    private PopupWindow popMenu;
    private CircleImageView headimage;
    String url;
    private static final int ACTION_TYPE_PHOTO = 0;
    private final int CAMERA = 10;
    private final int ALBUM = 20;
    private final int CUPREQUEST = 50;
    private String picPath;
    private File mOutImage;
    private Uri mImageUri;
    private Uri uritempFile;

    // 声明PopupWindow
    private PopupWindow popupWindow;
    // 声明PopupWindow对应的视图
    private View popupView;
    private String sex = "女";
    // 声明平移动画
    private TranslateAnimation animation;
    private ImageView back;
    private String user_id;
    private String user_token;
    private File file;
    private String user_logo;

    private OssService mOssService;
    private ArrayList<Integer> channel_arrays;
    private ArrayList<String> channel_list;
    private String birthday;
    private String s;
    private String format;



    @Override
    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        init();
        initListener();
        nickName.setImeOptions(EditorInfo.IME_ACTION_DONE);
        groupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.boy) {
                    sex = "男";
                } else if (checkedId == R.id.girl) {
                    sex = "女";
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_personal_message;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
//        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        user_id = SpUtils.getString(EditPersonalMessageActivity.this, "user_id", null);
        user_token = SpUtils.getString(EditPersonalMessageActivity.this, "user_token", null);
        mPresenter.getUserInfo(EditPersonalMessageActivity.this,user_id, user_token);
        interestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(EditPersonalMessageActivity.this, LikePrefencesActivity.class)
                        .putIntegerArrayListExtra("data", channel_arrays).putExtra("type", 6), 1);

            }
        });
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String date = s.toString();
                String month = date.substring(5,7);
                String day = date.substring(8,10);
                tvStartSeat.setText(getStarSeat(Integer.parseInt(month),Integer.parseInt(day)));
            }
        });

    }

    @Override
    protected EditPersonalContract.Presenter createPresenter() {
        return new EditPersonalPresenter(EditPersonalMessageActivity.this,this);
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
                if (url == null) {
                    mPresenter.setUserInfo(EditPersonalMessageActivity.this,user_id, user_token, nickName.getText().toString(), user_logo, sex, textView.getText().toString().trim(), cityTvId.getText().toString());
                } else {
                    Log.d("EditPersonalMessageActi", textView.getText().toString().trim());
                    mPresenter.setUserInfo(EditPersonalMessageActivity.this,user_id, user_token, nickName.getText().toString(), url, sex, textView.getText().toString().trim(), cityTvId.getText().toString());
                }

            }
        });

        //跳转城市选择
        cityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditPersonalMessageActivity.this, CityActivity.class);
                intent.putExtra("name", citytextView.getText().toString());
                startActivityForResult(intent, 1);
            }
        });


        //时间选择
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(EditPersonalMessageActivity.this, new DateChooseWheelViewDialog.DateChooseInterface() {
                    @Override
                    public void getDateTime(String time, boolean longTimeChecked) {
                            textView.setText(time);

                    }
                });
                startDateChooseDialog.setDateDialogTitle("开始时间");
                startDateChooseDialog.showDateChooseDialog();
            }
        });
    }

    private void init() {
        preser = (TextView) findViewById(R.id.preservation_tv_id);
        relativeLayout = (RelativeLayout) findViewById(R.id.brith_rl_id);
        textView = (TextView) findViewById(R.id.brith_tv_id);
        cityLayout = (RelativeLayout) findViewById(R.id.city_rl_id);
        citytextView = (TextView) findViewById(R.id.city_tv_id);
        headimage = (CircleImageView) findViewById(R.id.head_im_id);
        back = (ImageView) findViewById(R.id.back_iv_id);
        //返回

        mOssService = new OssService(this);
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
        ColorDrawable dw = new ColorDrawable();
        popMenu.setBackgroundDrawable(dw);//设置mPopupWindow背景颜色或图片，这里设置半透明
        popMenu.setOutsideTouchable(true); //设置可以通过点击mPopupWindow外部关闭mPopupWindow
//        popMenu.setAnimationStyle(R.style.PopupAnimationAmount);//设置mPopupWindow的进出动画
        backgroundAlpha(0.6f);
        popMenu.showAsDropDown(pop_view, Gravity.CENTER, 0, 0);//mPopupWindow显示的位置
        /**
         * PopupWindow消失监听方法
         */
        popMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                Toast.makeText(getContext(), "退出", Toast.LENGTH_SHORT).show();
                finish();
                Toast.makeText(EditPersonalMessageActivity.this, "保存成功!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
            if (localMedia != null && localMedia.size() > 0) {
                LoadingDialog.get().create(this).show();
                String path = TextUtils.isEmpty(localMedia.get(0).getCompressPath()) ? localMedia.get(0).getPath() : localMedia.get(0).getCompressPath();
                Glide.with(this).load(path).apply(new RequestOptions().circleCrop()).into(headimage);
                mOssService.asyncPutImage(path, -1);
            }
            return;
        }
        if (requestCode == 1) {
            if (resultCode == 6) {
                String city_name = data.getStringExtra("city_name");
                cityTvId.setText(city_name);
                cityTvId.setTextColor(Color.parseColor("#333333"));
            }
        }
        if (requestCode == 1 && resultCode == 4) {
            channel_arrays = data.getIntegerArrayListExtra("channel_array");
            channel_list = data.getStringArrayListExtra("channel_list");
            if (channel_list != null && channel_list.size() > 0) {
                tvInterest.setVisibility(View.GONE);
                flow_interest.setVisibility(View.VISIBLE);
                TagAdapter<String> tagAdapter = new TagAdapter<String>(channel_list) {
                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        View contentView = getLayoutInflater().inflate(R.layout.item_interest_person, flow_interest, false);
                        TextView tv = contentView.findViewById(R.id.tv_interest);
                        tv.setText(channel_list.get(position));
                        return contentView;
                    }
                };
                flow_interest.setAdapter(tagAdapter);
            }
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
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setFocusable(true);

            // 设置点击popupwindow外屏幕其它地方消失
            popupWindow.setOutsideTouchable(true);
            backgroundAlpha(0.6f);
            // 平移动画相对于手机屏幕的底部开始，X轴不变，Y轴从1变0
            animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                    Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
            animation.setInterpolator(new AccelerateInterpolator());
            animation.setDuration(200);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1.0f);
                }
            });

            // 设置背景图片， 必须设置，不然动画没作用
            popupView.findViewById(R.id.photo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 打开系统拍照程
//                    Intent intent1 = new Intent(Intent.ACTION_PICK, null);
//                    intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                    startActivityForResult(intent1, 1);
                    PictureSelector.create(EditPersonalMessageActivity.this)
                            .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                            .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                            .previewImage(true)// 是否可预览图片 true or false
                            .isCamera(true)// 是否显示拍照按钮 true or false
                            .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                            .enableCrop(true)// 是否裁剪 true or false
                            .compress(true)// 是否压缩 true or false
                            .imageSpanCount(3)// 每行显示个数 int
                            .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                            .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                            .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                            .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                            .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                            .openClickSound(false)// 是否开启点击声音 true or false
                            .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                            .minimumCompressSize(500)// 小于100kb的图片不压缩
                            .synOrAsy(false)//同步true或异步false 压缩 默认同步
                            .rotateEnabled(false) // 裁剪是否可旋转图片 true or false
                            .scaleEnabled(false)// 裁剪是否可放大缩小图片 true or false
                            .isDragFrame(true)// 是否可拖动裁剪框(固定)
                            .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                    popupWindow.dismiss();
                }
            });
            popupView.findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 打开系统图库选择图片
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //头像文件名称 head.jpg
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                    startActivityForResult(intent2, 2);//采用ForResult打开
                    popupWindow.dismiss();
                }
            });
            popupView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 取消
                    popupWindow.dismiss();

                }
            });
        }

        // 在点击之后设置popupwindow的销毁
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        popupWindow.showAtLocation(EditPersonalMessageActivity.this.findViewById(R.id.main_llayout_id),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popupView.startAnimation(animation);
    }

    //背景透明
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }


    @Override
    public void getUserInfoSuccess(GetUserInfoBean.DataBean data) {//获取个人信息
        String user_nickname = data.getUser_nickname();
        if (user_nickname.equals("")) {
            nickName.setText("起个响亮的名字~");
        } else {
            nickName.setText(user_nickname);
        }
        user_logo = data.getUser_logo();
        if (user_logo.equals("")) {
            headimage.setImageResource(R.mipmap.default_touxiang);
        } else {
            Glide.with(this).load(user_logo).into(headimage);
        }
        String user_birthday = data.getUser_birthday();
        if (user_birthday.equals("")) {
            brithTvId.setText("你的破壳日?");
            brithTvId.setTextColor(Color.parseColor("#F1F1F1"));
        } else {
            brithTvId.setText(user_birthday);
        }
        String user_city = data.getUser_city();
        if (user_city.equals("")) {
            cityTvId.setText("你在哪?");
        } else {
            cityTvId.setText(user_city);
            cityTvId.setTextColor(Color.parseColor("#333333"));
        }
        if (data.getUser_phone().equals("")) {
            tvPhone.setText("");
        } else {
            tvPhone.setText(data.getUser_phone());

        }
        userId.setText(data.getUser_id() + "");
        String user_sex = data.getUser_sex();
        if (user_sex.equals("男")) {
            boy.setChecked(true);
        } else {
            girl.setChecked(true);
        }
        userId.setText(data.getUser_id() + "");
        if (data.getUser_sex().equals("男")) {
            boy.setChecked(true);
        } else {
            girl.setChecked(true);
        }
        List<GetUserInfoBean.DataBean.InterestListBean> interest_list = data.getInterest_list();
        if (interest_list.size() == 0) {
            flow_interest.setVisibility(View.GONE);
            tvInterest.setVisibility(View.VISIBLE);

        } else {
            flow_interest.setVisibility(View.VISIBLE);
            tvInterest.setVisibility(View.GONE);
            //绑定用户信息
            if(interest_list!=null){
                if(channel_arrays==null){
                    channel_arrays = new ArrayList<>();
                }else{
                    channel_arrays.clear();
                }
                for(GetUserInfoBean.DataBean.InterestListBean intro : interest_list){
                    channel_arrays.add(intro.getId());
                }
            }
            TagAdapter<GetUserInfoBean.DataBean.InterestListBean> tagAdapter = new TagAdapter<GetUserInfoBean.DataBean.InterestListBean>(interest_list) {
                @Override
                public View getView(FlowLayout parent, int position, GetUserInfoBean.DataBean.InterestListBean interestListBean) {
                    View contentView = getLayoutInflater().inflate(R.layout.item_interest_person, flow_interest, false);
                    TextView tv = contentView.findViewById(R.id.tv_interest);
                    tv.setText(interest_list.get(position).getInterest());
                    return contentView;
                }
            };
            flow_interest.setAdapter(tagAdapter);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void setUserInfoSuccess(BaseBean baseBean) {//设置个人信息
        initPopuptWindow();
    }


    @Override
    public void onUploadSuccess(String url, int position) {
        LoadingDialog.get().close();
        this.url = url;
    }

    @Override
    public void onUploadError(ServiceException e) {
        LoadingDialog.get().close();
        Toast.makeText(this, "图片上传失败", Toast.LENGTH_SHORT).show();
    }
}
