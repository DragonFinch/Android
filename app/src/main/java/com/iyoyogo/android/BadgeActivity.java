package com.iyoyogo.android;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.iyoyogo.android.utils.BitmapUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BadgeActivity extends AppCompatActivity {


    @BindView(R.id.num_img)
    ImageView num_img;
Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge);
        activity=this;
        ButterKnife.bind(this);
        Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.mipmap.pinglun);
        num_img.setImageBitmap(BitmapUtil.generatorNumIcon2(activity, bmp1, true, "9"));

    }
}
