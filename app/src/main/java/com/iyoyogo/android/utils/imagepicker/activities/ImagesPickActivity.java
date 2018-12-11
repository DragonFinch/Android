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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.utils.imagepicker.adapter.ImagesListAdapter;
import com.iyoyogo.android.utils.imagepicker.bean.ImageBean;
import com.iyoyogo.android.utils.imagepicker.component.OnItemChooseCallback;
import com.iyoyogo.android.utils.imagepicker.component.OnRecyclerViewItemClickListener;
import com.iyoyogo.android.utils.imagepicker.util.ImageFinder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImagesPickActivity extends AppCompatActivity implements View.OnClickListener{
    public final static String RESULT_IMAGES_LIST = "imagesPath";
    private final static int REQUEST_CAMERA = 200;
    private final static String EXTRA_COUNT = "max";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    private Toolbar mTbPickActivity;
    private TextView mTvCount;
    private ImageButton mImageBtnPickerBack;
    private Button mBtnSure;
    private RecyclerView mRecyclerViewPickActivity;
    private List<ImageBean> list;
    private Uri cameraImageUri;
    private int maxCount;
    private ArrayList<Integer> chosenList = new ArrayList<>();

    /**
     * 提供启动活动的方法
     * @param activity 起点活动
     * @param max 最大图片数
     * @param requestCode 请求值
     */
    public static void startPicker(Activity activity, int max, int requestCode){
        Intent intent = new Intent(activity,ImagesPickActivity.class);
        intent.putExtra(EXTRA_COUNT,max);
        activity.startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick);
        Intent intent = getIntent();
        maxCount = intent.getIntExtra(EXTRA_COUNT,9);
        initView();
        requestStoragePermission();
    }

    /**
     * 初始化控件资源
     */
    private void initView() {
        mTbPickActivity = (Toolbar) findViewById(R.id.tb_pick_activity);
        mTvCount = (TextView) findViewById(R.id.tv_count);
        mImageBtnPickerBack = (ImageButton) findViewById(R.id.imageBtn_picker_back);
        mBtnSure = (Button) findViewById(R.id.btn_sure);
        mRecyclerViewPickActivity = (RecyclerView) findViewById(R.id.recyclerView_pick_activity);

        mTvCount.setText(0+"/"+maxCount);
        mImageBtnPickerBack.setOnClickListener(this);
        mBtnSure.setOnClickListener(this);
    }

    /**
     * 初始化图片列表
     */
    private void initRecyclerView(){
        list = ImageFinder.getImages(this,ImageFinder.TYPE_GIF);
        MyChooseCallback callback = new MyChooseCallback();
        MyOnItemClickListener listener = new MyOnItemClickListener();
        ImagesListAdapter adapter = new ImagesListAdapter(this,list);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        adapter.setMaxNum(maxCount);
        adapter.setChooseCallback(callback);
        adapter.setOnRecyclerViewItemClickListener(listener);
        mRecyclerViewPickActivity.setLayoutManager(layoutManager);
        mRecyclerViewPickActivity.setAdapter(adapter);
    }

    /**
     * 照相机
     */
    private void takePhoto(){
        //文件io流存储图片
        File outputImage = new File(getExternalCacheDir(),"outputImage.jpg");
        try {
            if (outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile(); //创建新的对象
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24){
            cameraImageUri = FileProvider.getUriForFile(this,"com.eric.photopicker.camera",outputImage);
        } else {
            cameraImageUri = Uri.fromFile(outputImage);
        }
        //启动相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,cameraImageUri);
        startActivityForResult(intent,REQUEST_CAMERA);
    }

    /**
     * 请求读写权限
     */
    private void requestStoragePermission(){
        int permission = ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
        } else {
            initRecyclerView();
        }
    }

    /**
     * 启动活动返回值处
     * @param requestCode 请求码
     * @param resultCode 结果码
     * @param data 数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK){
                    Toast.makeText(this,"图片获取成功",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 权限请求结果处理
     * @param requestCode 请求码
     * @param permissions 权限数组
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initRecyclerView();
                } else {
                    Toast.makeText(this,"您未开启读取储存权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 系统view点击回调
     * @param v view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageBtn_picker_back:
                finish();
                break;
            case R.id.btn_sure:
                onPickedDone();
                break;
            default:
                break;
        }
    }

    /**
     * 返回一组图片path
     */
    private void onPickedDone(){
        ArrayList<String> images = new ArrayList<>();
        for (int i : chosenList){
            images.add(list.get(i).getImagePath());
        }
        onResult(images);
    }

    /**
     * 返回结果
     * @param images
     */
    private void onResult(ArrayList<String> images){
        Intent intent = new Intent();
        intent.putStringArrayListExtra(RESULT_IMAGES_LIST,images);
        setResult(RESULT_OK,intent);
        finish();
    }

    /**
     * Item点击事件的监听类
     */
    private class MyOnItemClickListener implements OnRecyclerViewItemClickListener {

        @Override
        public void onItemClick(int position) {
            ImagesPreviewActivity.startPreView(ImagesPickActivity.this,list.get(position).getImagePath());
        }
    }

    /**
     * Item选则事件的监听类
     */
    private class MyChooseCallback implements OnItemChooseCallback {

        @Override
        public void chooseState(int position, boolean isChosen) {
            if (isChosen){
                chosenList.add(position);
            } else {
                int index = 0;
                for (int i : chosenList){
                    if (i == position){
                        chosenList.remove(index);
                    }
                    index ++;
                }
            }
        }

        @Override
        public void countNow(int countNow) {
            mTvCount.setText(countNow +"/"+ maxCount);
        }

        @Override
        public void countWarning(int count) {
            Toast.makeText(ImagesPickActivity.this,"最多选择"+count+"张图片",Toast.LENGTH_SHORT).show();
        }
    }
}
