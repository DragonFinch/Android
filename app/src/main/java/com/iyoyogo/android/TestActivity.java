package com.iyoyogo.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.iyoyogo.android.utils.util.badgeview.BadgeViewPro;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.img)
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        BadgeViewPro bv1 = new BadgeViewPro(TestActivity.this);
        bv1.setStrText("10").setMargin(10, 3, 10, 0).setStrSize(10).setTargetView(img);


    }

    @Override
    public void onClick(View v) {

    }
}
