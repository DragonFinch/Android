package com.iyoyogo.android.ui.common;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmptyActivity extends BaseActivity {
    ListView show_list;
    ArrayList names = null;
    ArrayList descs = null;
    ArrayList fileNames = null;
    ArrayList video_names = null;
    ArrayList video_descs = null;
    ArrayList video_fileNames = null;
    private Button look;
    private Button add;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_empty;
    }

    protected void initView() {
        super.initView();
        statusbar();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        look = (Button) findViewById(R.id.look);
        add = (Button) findViewById(R.id.add);
        show_list = (ListView) findViewById(R.id.show_list);
        names = new ArrayList();
        descs = new ArrayList();
        fileNames = new ArrayList();
        video_names = new ArrayList();
        video_descs = new ArrayList();
        video_fileNames = new ArrayList();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
//            cursor.getString(cursor.getColumnIndex(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            //获取图片的名称
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            //获取图片的生成日期
            byte[] data = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            //获取图片的详细信息
            String desc = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
            names.add(name);
            descs.add(desc);
            fileNames.add(new String(data, 0, data.length - 1));


        }
        Cursor cursors = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursors.moveToNext()) {
//            cursor.getString(cursor.getColumnIndex(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            //获取图片的名称
            String name = cursors.getString(cursors.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
            //获取图片的生成日期
            byte[] data = cursors.getBlob(cursors.getColumnIndex(MediaStore.Video.Media.DATA));
            //获取图片的详细信息
            String desc = cursors.getString(cursors.getColumnIndex(MediaStore.Video.Media.DESCRIPTION));
//            cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
            video_names.add(name);
            video_descs.add(desc);
            video_fileNames.add(new String(data, 0, data.length - 1));


        }
        for (int i = 0; i < fileNames.size(); i++) {
            Log.d("EmptyActivity", "fileNames.get(i):" + fileNames.get(i));
        }
        for (int i = 0; i < video_fileNames.size(); i++) {
            Log.d("EmptyActivity", "video_fileNames.get(i):" + video_fileNames.get(i));
        }
        List<String> mediaAll = new ArrayList<>();
        mediaAll.addAll(fileNames);
        mediaAll.addAll(video_fileNames);

        List<Map<String, Object>> listItems = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", names.get(i));
            map.put("desc", descs.get(i));
            listItems.add(map);
        }
        //设置adapter
        SimpleAdapter adapter = new SimpleAdapter(EmptyActivity.this, listItems,
                R.layout.line, new String[]{"name", "data"}, new int[]{R.id.name, R.id.desc});

        show_list.setAdapter(adapter);
        show_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                View viewDiag = getLayoutInflater().inflate(R.layout.view, null);

                ImageView image = (ImageView) viewDiag.findViewById(R.id.image);
                image.setImageBitmap(BitmapFactory.decodeFile((String) fileNames.get(i)));

                new AlertDialog.Builder(EmptyActivity.this).setView(viewDiag)
                        .setPositiveButton("确定", null).show();
            }
        });
    }


    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
}
