package com.iyoyogo.android.utils.imagepicker.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.ui.home.yoji.PublishYoJiActivity;
import com.iyoyogo.android.utils.imagepicker.adapter.ImagesListAdapter;
import com.iyoyogo.android.utils.imagepicker.bean.ImageBean;
import com.iyoyogo.android.utils.imagepicker.component.OnItemChooseCallback;
import com.iyoyogo.android.utils.imagepicker.component.OnRecyclerViewItemClickListener;
import com.iyoyogo.android.utils.imagepicker.util.ImageFinder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ImagesPickActivity extends BaseActivity  {
    public final static String RESULT_IMAGES_LIST = "imagesPath";
    private final static int REQUEST_CAMERA = 200;
    private final static String EXTRA_COUNT = "max";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.imageBtn_picker_back)
    ImageButton imageBtnPickerBack;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.tb_pick_activity)
    Toolbar tbPickActivity;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.recyclerView_pick_activity)
    RecyclerView recyclerViewPickActivity;
    @BindView(R.id.img_yuantu)
    ImageView imgYuantu;
    @BindView(R.id.tv_yuantu)
    TextView tvYuantu;
    @BindView(R.id.tv_kb)
    TextView tvKb;
    @BindView(R.id.rela_before_img)
    RelativeLayout relaBeforeImg;
    @BindView(R.id.btn_continue)
    TextView btnContinue;
    @BindView(R.id.ly_top_bar)
    RelativeLayout lyTopBar;

    private Toolbar mTbPickActivity;
    private TextView mTvCount;
    private ImageButton mImageBtnPickerBack;
    private Button mBtnSure;
    private RecyclerView mRecyclerViewPickActivity;
    private List<ImageBean> list;
    private Uri cameraImageUri;
    private int maxCount = 9;
    private ArrayList<Integer> chosenList = new ArrayList<>();
    private ImagesListAdapter adapter;
    private MyOnItemClickListener listener;
    private MyChooseCallback callback;

    /**
     * 提供启动活动的方法
     *
     * @param activity    起点活动
     * @param max         最大图片数
     * @param requestCode 请求值
     */
    public static void startPicker(Activity activity, int max, int requestCode) {
        Intent intent = new Intent(activity, ImagesPickActivity.class);
        intent.putExtra(EXTRA_COUNT, max);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        initView();
        requestStoragePermission();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pick;
    }

    @Override
    protected void initView() {
        super.initView();
        mTbPickActivity = (Toolbar) findViewById(R.id.tb_pick_activity);
        mTvCount = (TextView) findViewById(R.id.tv_count);
        mImageBtnPickerBack = (ImageButton) findViewById(R.id.imageBtn_picker_back);
        mBtnSure = (Button) findViewById(R.id.btn_sure);
        mRecyclerViewPickActivity = (RecyclerView) findViewById(R.id.recyclerView_pick_activity);

        mTvCount.setText(0 + "/" + maxCount);

    }

    /**
     * 初始化控件资源
     */


    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    /**
     * 初始化图片列表
     */
    private void initRecyclerView() {
        list = ImageFinder.getImages(this, ImageFinder.TYPE_GIF);

        callback = new MyChooseCallback();
        listener = new MyOnItemClickListener();
        adapter = new ImagesListAdapter(this, list);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        adapter.setMaxNum(maxCount);
        adapter.setChooseCallback(callback);
        adapter.setOnRecyclerViewItemClickListener(listener);
        mRecyclerViewPickActivity.setLayoutManager(layoutManager);
        mRecyclerViewPickActivity.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        listener=null;
        callback=null;
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ArrayList<String> strings = adapter.selectPhoto();
        if (strings!=null){
        strings.clear();

        }
    }

    /**
     * 照相机
     */
    private void takePhoto() {
        //文件io流存储图片
        File outputImage = new File(getExternalCacheDir(), "outputImage.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile(); //创建新的对象
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24) {
            cameraImageUri = FileProvider.getUriForFile(this, "com.eric.photopicker.camera", outputImage);
        } else {
            cameraImageUri = Uri.fromFile(outputImage);
        }
        //启动相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    /**
     * 请求读写权限
     */
    private void requestStoragePermission() {
        int permission = ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        } else {
            initRecyclerView();
        }
    }

    /**
     * 启动活动返回值处
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "图片获取成功", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 权限请求结果处理
     *
     * @param requestCode  请求码
     * @param permissions  权限数组
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initRecyclerView();
                } else {
                    Toast.makeText(this, "您未开启读取储存权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 系统view点击回调
     *
     * @param v view
     */


    /**
     * 返回一组图片path
     */
    private void onPickedDone() {
        ArrayList<String> strings = adapter.selectPhoto();
        ArrayList<String> images = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            images.add(strings.get(i));
            Log.d("ImagesPickActivity", strings.get(i));
        }
        onResult(images);
    }

    /**
     * 返回结果
     *
     * @param images
     */
    private void onResult(ArrayList<String> images) {
        Intent intent = new Intent();
        int type = intent.getIntExtra("type", 0);
        if (type==1){
            setResult(12, intent);
            finish();
        }else {
            intent.putStringArrayListExtra(RESULT_IMAGES_LIST, images);
            finish();
        }

    }

    @OnClick({R.id.iv_back, R.id.btn_continue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_continue:
                ArrayList<String> strings = adapter.selectPhoto();
                Intent intent1 = getIntent();
                int type =intent1.getIntExtra("type", 0);
                if (type==1){
                    intent1.putStringArrayListExtra("path_list",strings);
                    setResult(4,intent1);
                    finish();
                }else {
                    Intent intent = new Intent(ImagesPickActivity.this, PublishYoJiActivity.class);
                    intent.putStringArrayListExtra("path_list", strings);
                    startActivity(intent);
                    finish();
                }

                break;
        }
    }

    /**
     * Item点击事件的监听类
     */
    private class MyOnItemClickListener implements OnRecyclerViewItemClickListener {

        @Override
        public void onItemClick(int position) {
            ImagesPreviewActivity.startPreView(ImagesPickActivity.this, list.get(position).getImagePath());
        }
    }

    /**
     * Item选则事件的监听类
     */
    private class MyChooseCallback implements OnItemChooseCallback {

        @Override
        public void chooseState(int position, boolean isChosen) {

        }

        @Override
        public void countNow(int countNow) {
            btnContinue.setText("继续" + "（" + countNow + "）");
        }

        @Override
        public void countWarning(int count) {
            Toast.makeText(ImagesPickActivity.this, "最多选择" + count + "张图片", Toast.LENGTH_SHORT).show();
        }
    }
}
