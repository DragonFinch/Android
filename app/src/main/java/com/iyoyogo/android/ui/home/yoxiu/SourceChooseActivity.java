package com.iyoyogo.android.ui.home.yoxiu;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.MediaAdapter;
import com.iyoyogo.android.utils.FileSizeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SourceChooseActivity extends AppCompatActivity {


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
    GridView gvPhotos;
    private List<HashMap<String, String>> listImage;
    private String path;
    private String autoFileOrFilesSize;
    private MediaAdapter mediaAdapter;
    private String latitude;
    private String longitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_choose);
        ButterKnife.bind(this);
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
        mediaAdapter = new MediaAdapter(SourceChooseActivity.this, listImage);

        gvPhotos.setAdapter(mediaAdapter);
        gvPhotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mediaAdapter.setSelectedPosition(position);
                path = listImage.get(position).get("_data");
                btnContinue.setText("继续"+"(1)");
                 autoFileOrFilesSize = fileSizeUtil.getAutoFileOrFilesSize(path);
                latitude = listImage.get(position).get("latitude");
                longitude = listImage.get(position).get("longitude");
                Toast.makeText(SourceChooseActivity.this, path, Toast.LENGTH_SHORT).show();
                mediaAdapter.notifyDataSetChanged();
            }
        });

//        getAudio();
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
                MediaStore.Images.Media.LATITUDE,
                MediaStore.Images.Media.LONGITUDE,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
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

    @OnClick({R.id.rela_before_img, R.id.btn_continue, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.rela_before_img:
                imgYuantu.setImageResource(R.mipmap.log_select);
                tvKb.setText(autoFileOrFilesSize);
                mediaAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_continue:
                Intent intent = new Intent(SourceChooseActivity.this, PublishYoXiuActivity.class);
                intent.putExtra("path", path);
                if (latitude!=null&&longitude!=null){
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);

                }else {
                    intent.putExtra("latitude", "0");
                    intent.putExtra("longitude", "0");
                }
                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}