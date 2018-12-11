package com.iyoyogo.android.utils.select;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectPhotoActivity extends AppCompatActivity {
    private GridView mGridView;
    private List<String> mImgs;
    private RelativeLayout mbottomLayout;
    private TextView mTvDirName,mTvDirCount;
    private ImageView mIvBack;
    private Button mBtnSelect;
    private File mCurrentDir;
    private  int mMaxCount;
    private  ListImageDirPopupWindow mImageDirPopupWindow;
    private  List<FolderBean> mFolderBeans=new ArrayList<>();
    private List<HashMap<String, String>> listImage;
    private ProgressDialog mProgressDialog;
    private ImageAdapter adapter;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0x110){
                mProgressDialog.dismiss();
                dataToView();

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_main);
        initView();
        initDatas();
        initEvent();
        listImage = new ArrayList<HashMap<String, String>>();
        adapter=new ImageAdapter(SelectPhotoActivity.this,listImage);
        mGridView.setAdapter(adapter);
    }




    private void initEvent() {
        mbottomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  mImageDirPopupWindow.setAnimationStyle(R.style.dir_popupwindow_anim);
                mImageDirPopupWindow.showAsDropDown(mbottomLayout,0,0);*/
                lightOff();
            }
        });
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> photoPaths  = adapter.selectPhoto();
                if (photoPaths!=null){
                    photoPaths.clear();
                    finish();
                }else{
                    finish();
                }

            }
        });
        mBtnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> photoPaths  = adapter.selectPhoto();
                for (int i = 0; i < photoPaths.size(); i++) {
                    Log.d("SelectPhotoActivity", photoPaths.get(i));
                }
               /* Intent intent=new Intent(SelectPhotoActivity.this,MainActivity.class);
                intent.putExtra("photo",(Serializable) photoPaths);
                startActivity(intent);*/

            }
        });
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
    private void dataToView() {
        if (mCurrentDir==null){
            Toast.makeText(this,"未扫描到任何图片",Toast.LENGTH_LONG).show();
            return;
        }
//        mImgs= Arrays.asList(mCurrentDir.list());
        getImage();
        getVideo();
        adapter = new ImageAdapter(this,listImage);
        mGridView.setAdapter(adapter);
    }
    /**
     * 内容区域变亮
     */
    private void lightOn() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha=1.0f;
        getWindow().setAttributes(lp);
    }

    /**
     * 内容区域变暗
     */
    private void lightOff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha=0.3f;
        getWindow().setAttributes(lp);
    }
    /**
     * 利用Contentprovider扫描手机中的所有图片
     */
    private void initDatas() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(SelectPhotoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SelectPhotoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }else{
                aboutScanPhoto();
            }
        }else{
            aboutScanPhoto();
        }

    }

    private void aboutScanPhoto() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(this,"当前存储卡不可用！",Toast.LENGTH_LONG);
            return;
        }
        mProgressDialog= ProgressDialog.show(this,null,"正在加载。。。");
        new Thread(){
            @Override
            public void run() {
                Uri mImgUri= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver cr=SelectPhotoActivity.this.getContentResolver();
                Cursor mCursor = cr.query(mImgUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[] { "image/jpeg", "image/png" },
                        MediaStore.Images.Media.DATE_MODIFIED);
                Set<String> mDirPaths=new HashSet<String>();
                while (mCursor.moveToNext()){
                    String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    File parentFile=new File(path).getParentFile();
                    if (parentFile==null) {
                        continue;
                    }
                    String dirPath = parentFile.getAbsolutePath();
                    FolderBean folderBean=null;
                    if (mDirPaths.contains(dirPath)){
                        continue;
                    }else{
                        mDirPaths.add(dirPath);
                        folderBean=new FolderBean();
                        folderBean.setDir(dirPath);
                        folderBean.setFirstImamgPath(path);
                    }

                    if (parentFile.list()==null){
                        continue;
                    }
                    int picSize=parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            if (name.endsWith(".jpg")||name.endsWith("jpeg")||name.endsWith("png")){
                                return  true;
                            }
                            return false;
                        }
                    }).length;
                    folderBean.setCount(picSize);
                    mFolderBeans.add(folderBean);

                    if (picSize>mMaxCount){
                        mMaxCount=picSize;
                        mCurrentDir=parentFile;
                    }
                }
                mCursor.close();
                handler.sendEmptyMessage(0x110);

            }
        }.start();
    }

    private void initView() {
        mGridView= (GridView) findViewById(R.id.id_gridView);
        mbottomLayout= (RelativeLayout) findViewById(R.id.rl_bottom_layout);
        mTvDirName= (TextView) findViewById(R.id.tv_dir_name);
        mTvDirCount= (TextView) findViewById(R.id.tv_dir_count);
        mIvBack= (ImageView) findViewById(R.id.iv_back);
        mBtnSelect= (Button) findViewById(R.id.btn_sure);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    aboutScanPhoto();
                }else {
                    Toast.makeText(this, "请打开权限！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
