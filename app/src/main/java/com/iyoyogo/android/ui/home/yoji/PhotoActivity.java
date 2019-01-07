package com.iyoyogo.android.ui.home.yoji;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.luck.picture.lib.photoview.PhotoView;
import com.luck.picture.lib.photoview.PhotoViewAttacher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoActivity extends AppCompatActivity {

    @BindView(R.id.photo)
    PhotoView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        PhotoViewAttacher mAttacher = new PhotoViewAttacher(photo);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Glide.with(this).load(url).into(photo);
        mAttacher.update();
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
