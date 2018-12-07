package com.iyoyogo.android.ui.mine.collection;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.iyoyogo.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DefaultCollectionActivity extends AppCompatActivity {

    @BindView(R.id.default_back_iv_id)
    ImageView defaultBackIvId;
    @BindView(R.id.default_spot_iv_id)
    ImageView defaultSpotIvId;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_collection);
        ButterKnife.bind(this);


    }


    @OnClick({R.id.default_back_iv_id, R.id.default_spot_iv_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.default_back_iv_id:
                finish();
                break;
            case R.id.default_spot_iv_id:
                initSearchPopupWindow();
                break;
        }
    }

    //初始化搜索popup (可以启动初始化)
    private void initSearchPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View contentview = inflater.inflate(R.layout.popup_spot_default_collection, null);//自己的弹框布局


        contentview.setFocusable(true); // 这个很重要
        contentview.setFocusableInTouchMode(true);
        popupWindow = new PopupWindow(contentview, RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        contentview.setOnKeyListener(new View.OnKeyListener() {//监听系统返回键
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();

                    return true;
                }
                return false;
            }
        });
    }
}
