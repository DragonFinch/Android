package com.iyoyogo.android.ui.common;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iyoyogo.android.R;
import com.iyoyogo.android.utils.StatusBarUtils;

/***
 * 拍同款—详情
 */
public class Album_details_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details_);
        StatusBarUtils.setWindowStatusBarColor(this, Color.BLACK);

    }
}
