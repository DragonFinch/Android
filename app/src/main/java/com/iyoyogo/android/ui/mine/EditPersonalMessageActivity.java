package com.iyoyogo.android.ui.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
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
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.mine.GetUserInfoBean;
import com.iyoyogo.android.contract.EditPersonalContract;
import com.iyoyogo.android.presenter.EditPersonalPresenter;
import com.iyoyogo.android.ui.common.LikePrefencesActivity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;
import com.iyoyogo.android.widget.flow.FlowLayout;
import com.iyoyogo.android.widget.flow.TagAdapter;
import com.iyoyogo.android.widget.flow.TagFlowLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 修改用户信息
 */
public class EditPersonalMessageActivity extends BaseActivity<EditPersonalContract.Presenter> implements EditPersonalContract.View {
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

    private void setLocalPhoto() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, ALBUM);
    }

    private void setMobilePhones() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        //获得项目缓存路径
        String sdPath = getExternalCacheDir().getPath();
        //根据时间随机生成图片名
        String photoName = new DateFormat().format("yyyyMMddhhmmss",
                Calendar.getInstance(Locale.CHINA)) + ".jpg";
        picPath = sdPath + "/" + photoName;
        mOutImage = new File(picPath);
        //如果是7.0以上 那么就把uir包装
        if (Build.VERSION.SDK_INT >= 24) {
            mImageUri = FileProvider.getUriForFile(EditPersonalMessageActivity.this, "com.test.FileProvider", mOutImage);
        } else {
            //否则就用老系统的默认模式
            mImageUri = Uri.fromFile(mOutImage);
        }
        //启动相机
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    protected void initView() {
        super.initView();
        init();
        initListener();
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
        user_id = SpUtils.getString(EditPersonalMessageActivity.this, "user_id", null);
        user_token = SpUtils.getString(EditPersonalMessageActivity.this, "user_token", null);
        mPresenter.getUserInfo(user_id, user_token);
        tvInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditPersonalMessageActivity.this, LikePrefencesActivity.class);
                intent.putExtra("type", 6);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected EditPersonalContract.Presenter createPresenter() {
        return new EditPersonalPresenter(this);
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
                mPresenter.setUserInfo(user_id, user_token, nickName.getText().toString(), url, sex, brithTvId.getText().toString(), cityTvId.getText().toString());


            }
        });
        //跳转城市选择
        cityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditPersonalMessageActivity.this, CitySelectorActivity.class);
                startActivityForResult(intent, 1);
            }
        });


        //时间选择
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTiem();
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
                .gravity(Gravity.CENTER)
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
        ColorDrawable dw = new ColorDrawable();
        popMenu.setBackgroundDrawable(dw);//设置mPopupWindow背景颜色或图片，这里设置半透明
        popMenu.setOutsideTouchable(true); //设置可以通过点击mPopupWindow外部关闭mPopupWindow
//        popMenu.setAnimationStyle(R.style.PopupAnimationAmount);//设置mPopupWindow的进出动画
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


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 6) {
                String city_name = data.getStringExtra("city_name");
                cityTvId.setText(city_name);
                cityTvId.setTextColor(R.color.color333333);
            }
        }
        if (requestCode == 1 && resultCode == 4) {
            ArrayList<String> interestList = data.getStringArrayListExtra("interestList");
            if (interestList != null) {
                tvInterest.setVisibility(View.GONE);
                flow_interest.setVisibility(View.VISIBLE);
                TagAdapter<String> tagAdapter = new TagAdapter<String>(interestList) {
                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        View contentView = getLayoutInflater().inflate(R.layout.item_interest_person, flow_interest, false);
                        TextView tv = contentView.findViewById(R.id.tv_interest);
                        tv.setText(interestList.get(position));
                        return contentView;
                    }
                };

                flow_interest.setAdapter(tagAdapter);
            }
        }
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
                    String absolutePath = temp.getAbsolutePath();
                    uploadYoXiuImage(absolutePath);
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    Bitmap head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);//保存在SD卡中
                        headimage.setImageBitmap(head);//用ImageView显示出来
                    }
                }
                break;
            default:
                break;

        }

    }

    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";//图片名字
        uploadYoXiuImage(fileName);
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String uploadYoXiuImage(String path) {

        final String endpoint = "oss-cn-beijing.aliyuncs.com";
        final String bucketName = "xzdtest";
        final String accessKeyId = "LTAInRzzjv0TZcA5";
        final String accessKeySecret = "jQZXJDYzAU7Ki0DfZvfIoU3PxazsLy";
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000);
        conf.setSocketTimeout(15 * 1000);
        conf.setMaxConcurrentRequest(8);
        conf.setMaxErrorRetry(2);
        OSSClient ossClient = new OSSClient(EditPersonalMessageActivity.this, endpoint, credentialProvider, conf);
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentType("image/jpeg");
        String name = "yoyogo/user_logo/image" + System.currentTimeMillis() + ".jpg";
        PutObjectRequest put = new PutObjectRequest(bucketName, name, path);
        put.setMetadata(objectMeta);

        try {
            PutObjectResult result = ossClient.putObject(put);
            if (result != null && result.getStatusCode() == 200) {
                url = "https://" + bucketName + "." + endpoint + "/" + name;
                Log.d("PublishYoXiuActivity", url);
            }
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return url;
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
                    Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                    intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent1, 1);

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

    @SuppressLint("ResourceAsColor")
    @Override
    public void getUserInfoSuccess(GetUserInfoBean.DataBean data) {
        String user_nickname = data.getUser_nickname();
        if (user_nickname.equals("")) {
            nickName.setText("起个响亮的名字~");
        } else {
            nickName.setText(user_nickname);
        }
        String user_logo = data.getUser_logo();
        if (user_logo.equals("")) {
            headimage.setImageResource(R.mipmap.default_touxiang);
        } else {
            Glide.with(this).load(user_logo).into(headimage);
        }
        String user_birthday = data.getUser_birthday();
        if (user_birthday.equals("")) {
            brithTvId.setText("你的破壳日?");
            brithTvId.setTextColor(R.color.colorF1F1F1);
        } else {
            brithTvId.setText(user_birthday);
        }
        String user_city = data.getUser_city();
        if (user_city.equals("")) {
            cityTvId.setText("你在哪?");
        } else {
            cityTvId.setText(user_city);
            cityTvId.setTextColor(R.color.colorF1F1F1);
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
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void setUserInfoSuccess(BaseBean baseBean) {
        initPopuptWindow();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
