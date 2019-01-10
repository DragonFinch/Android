package com.iyoyogo.android.ui.common;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iyoyogo.android.R;
import com.iyoyogo.android.utils.StatusBarUtils;

/***
 * 拍同款相机
 */
public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        StatusBarUtils.setWindowStatusBarColor(this, Color.WHITE);

    }
}
