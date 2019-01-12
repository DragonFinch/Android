package com.iyoyogo.android.ui.home.yoxiu;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.ImageAdapter;
import com.iyoyogo.android.adapter.MediaAdapter;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.FileSizeUtil;
import com.iyoyogo.android.utils.imagepicker.activities.ImagesPreviewActivity;
import com.iyoyogo.android.utils.imagepicker.bean.ImageBean;
import com.iyoyogo.android.utils.imagepicker.component.OnItemChooseCallback;
import com.iyoyogo.android.utils.imagepicker.component.OnRecyclerViewItemClickListener;
import com.iyoyogo.android.utils.imagepicker.util.ImageFinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class
SourceChooseActivity extends AppCompatActivity {


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
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.bar)
    RelativeLayout bar;
    @BindView(R.id.gv_photos)
    RecyclerView gvPhotos;
    private List<HashMap<String, String>> listImage;
    private String path;
    private String autoFileOrFilesSize;

    private String latitude;
    private String longitude;
    private int num = 0;
    private String positionName;
    private String desc;
    private int type;
    private Intent intent;
    private List<ImageBean> mList;
    private ImageAdapter imagesListAdapter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();

        setContentView(R.layout.activity_source_choose);
        ButterKnife.bind(this);
        checkAllPermission();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();


//        getAudio();
    }

    @Override
    protected void onResume() {
        super.onResume();
        positionName = intent.getStringExtra("positionName");
        desc = intent.getStringExtra("desc");
        type = intent.getIntExtra("type", 0);
        Log.d("SourceChooseActivity", "type:" + type);
        listImage = new ArrayList<HashMap<String, String>>();
        FileSizeUtil fileSizeUtil = new FileSizeUtil();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getImage();
                getVideo();
            }
        }).start();

        Log.d("SourceChooseActivitys", "listImage.size():" + listImage.size());
        List<ImageBean> list = ImageFinder.getImages(this, ImageFinder.TYPE_GIF);
        List<ImageBean> list1 = ImageFinder.getVideo(this);
        mList = new ArrayList<>();
        mList.addAll(list);
        mList.addAll(list1);

        MyChooseCallback callback = new MyChooseCallback();
        MyOnItemClickListener listener = new MyOnItemClickListener();

        imagesListAdapter = new ImageAdapter(SourceChooseActivity.this, mList);
        imagesListAdapter.setMaxNum(1);
        imagesListAdapter.setChooseCallback(callback);
        imagesListAdapter.setOnRecyclerViewItemClickListener(listener);
        gvPhotos.setLayoutManager(new GridLayoutManager(SourceChooseActivity.this, 3));
        gvPhotos.setAdapter(imagesListAdapter);
    }

    private void checkAllPermission() {
        if (Build.VERSION.SDK_INT >= 23) {


            String[] mPermissionList = new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_LOGS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.SET_DEBUG_APP,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.GET_ACCOUNTS,
                    Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                    if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                                if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                                } else {
                                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
                                }
                            } else {
                                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
                            }
                        } else {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
                        }
                    } else {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
                    }
                } else {
                    requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 123);
                }
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 123);
            }
        } else {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ArrayList<String> strings = imagesListAdapter.selectPhoto();
        if (strings!=null){
            strings.clear();

        }
    }

    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1.0f);
        }
    }

    /**
     * Item点击事件的监听类
     */
    private class MyOnItemClickListener implements OnRecyclerViewItemClickListener {

        @Override
        public void onItemClick(int position) {
            ImagesPreviewActivity.startPreView(SourceChooseActivity.this, mList.get(position).getImagePath());
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
            View view1 = getLayoutInflater().inflate(R.layout.common_yodialog, null);
            PopupWindow popup = new PopupWindow(view1, ViewGroup.LayoutParams.WRAP_CONTENT, DensityUtil.dp2px(SourceChooseActivity.this, 210), true);
            popup.setOutsideTouchable(true);
            popup.setBackgroundDrawable(new ColorDrawable());
            ImageView img_delete = view1.findViewById(R.id.close_img);
            TextView know_me = view1.findViewById(R.id.know_me);
            know_me.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup.dismiss();
                }
            });
            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup.dismiss();
                }
            });

            //点击空白处时，隐藏掉pop窗口

            backgroundAlpha(0.6f);

            //添加pop窗口关闭事件
            popup.setOnDismissListener(new poponDismissListener());
            popup.showAtLocation(findViewById(R.id.activity_choose), Gravity.CENTER, 0, 0);
        }
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }

    private boolean isSelect;

    /**
     * 获取ContentProvider
     *
     * @param projection
     * @param orderBy
     */
    public void getContentProvider(Uri uri, String[] projection, String orderBy) {
        // TODO Auto-generated method stub

        Cursor cursor = getContentResolver().query(uri, projection, null,
                null, orderBy);

        if (null == cursor) {
            return;
        }
        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < projection.length; i++) {
                map.put(projection[i], cursor.getString(i));
                Log.d("Test", projection[i] + ":" + cursor.getString(i));
            }

            listImage.add(map);
            HashMap<String, String> data = listImage.get(0);
            String data1 = data.get("_data");
            Log.d("SourceChooseActivity", data1);

        }
        Log.d("TAG", listImage.toString());
    }

    public void getImage() {

        String[] projection = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.LATITUDE,
                MediaStore.Images.Media.LONGITUDE,
                MediaStore.Images.Media.DATA};
        String orderBy = MediaStore.Images.Media.DISPLAY_NAME;
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        getContentProvider(uri, projection, orderBy);
    }

    /**
     * 获取视频列表
     */
    public void getVideo() {
        // TODO Auto-generated method stub
        String[] projection = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.LATITUDE,
                MediaStore.Video.Media.LONGITUDE,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATA};
        String orderBy = MediaStore.Video.Media.DISPLAY_NAME;
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        getContentProvider(uri, projection, orderBy);
    }

    /**
     * 获取音频列表
     */
    public void getAudio() {
        String[] projection = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.SIZE};
        String orderBy = MediaStore.Audio.Media.DISPLAY_NAME;
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        getContentProvider(uri, projection, orderBy);
    }


    @OnClick({R.id.rela_before_img, R.id.btn_continue, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.rela_before_img:
                if (!isSelect) {
                    imgYuantu.setImageResource(R.mipmap.log_select);
                    tvKb.setText(autoFileOrFilesSize);
                    imagesListAdapter.notifyDataSetChanged();
                    isSelect = true;
                } else {
                    imgYuantu.setImageResource(R.mipmap.log_unselect);
                    tvKb.setText("");
                    imagesListAdapter.notifyDataSetChanged();
                }

                break;
            case R.id.btn_continue:

                if (type == 1) {
                    Intent intent = new Intent(SourceChooseActivity.this, PublishYoXiuActivity.class);
                    ArrayList<String> strings = imagesListAdapter.selectPhoto();
                    if (strings != null) {
                        String path = strings.get(0);
                        intent.putExtra("path", path);
                    }


                    if (latitude != null && longitude != null) {
                        intent.putExtra("latitude", latitude);
                        intent.putExtra("longitude", longitude);
                        intent.putExtra("positionName", positionName);
                        intent.putExtra("desc", desc);
                        setResult(88, intent);
                        finish();
                    } else {
                        intent.putExtra("latitude", "0");
                        intent.putExtra("longitude", "0");
                        intent.putExtra("positionName", positionName);
                        intent.putExtra("desc", desc);
                        setResult(88, intent);
                        finish();
                    }
                } else if (type == 2) {
                    Intent intent = new Intent();
                    ArrayList<String> strings = imagesListAdapter.selectPhoto();
                    if (strings != null) {
                        String path = strings.get(0);
                        intent.putExtra("path", path);
                        setResult(2, intent);
                        finish();
                    }

                } else {


                    Intent intent = new Intent(SourceChooseActivity.this, PublishYoXiuActivity.class);

                    ArrayList<String> strings = imagesListAdapter.selectPhoto();
                    if (strings != null) {
                        String path = strings.get(0);
                        intent.putExtra("path", path);
                    }
                    if (latitude != null && longitude != null) {
                        intent.putExtra("latitude", latitude);
                        intent.putExtra("longitude", longitude);

                    } else {
                        intent.putExtra("latitude", "0");
                        intent.putExtra("longitude", "0");
                    }
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 66) {
//            mediaAdapter.notifyDataSetChanged();
            imagesListAdapter.notifyDataSetChanged();
        }

    }
}