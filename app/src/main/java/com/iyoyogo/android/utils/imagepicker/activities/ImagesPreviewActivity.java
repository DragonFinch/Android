package com.iyoyogo.android.utils.imagepicker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;


public class ImagesPreviewActivity extends AppCompatActivity {
    private final static String EXTRA_PREVIEW = "imagePath";

    private ImageView mImagePreviewShow;
    private String imagePath;
    private ImageButton mImgBtnPreviewBack;
    public static void startPreView(Activity activity, String imagePath){
        Intent intent = new Intent(activity,ImagesPreviewActivity.class);
        intent.putExtra(EXTRA_PREVIEW,imagePath);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_preview);
        Intent intent = getIntent();
        imagePath = intent.getStringExtra(EXTRA_PREVIEW);
        mImagePreviewShow = (ImageView) findViewById(R.id.image_preview_show);
        mImgBtnPreviewBack = (ImageButton) findViewById(R.id.imgBtn_preview_back);
        mImgBtnPreviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Glide.with(this)
                .load(imagePath)
                .into(mImagePreviewShow);


    }

}
