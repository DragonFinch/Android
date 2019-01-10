package com.iyoyogo.android.ui.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.iyoyogo.android.R;
import com.iyoyogo.android.utils.StatusBarUtils;
import com.iyoyogo.android.widget.CropImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CropImageActivity extends AppCompatActivity {

    @BindView(R.id.CropImageView)
    CropImageView cImageView;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);
        ButterKnife.bind(this);
        StatusBarUtils.setWindowStatusBarColor(this, Color.WHITE);

        cImageView.setImageBitmap(getBitmap(R.mipmap.sea));//为了兼容小图片，必须在代码中加载图片
//        cImageView.rotateImage(30);//设定图片的旋转角度
        cImageView.setFixedAspectRatio(true);//设置允许按比例截图，如果不设置就是默认的任意大小截图
        cImageView.setAspectRatio(1, 1);//设置比例为一比一
        cImageView.setGuidelines(CropImageView.ON);//设置显示网格的时机，默认为on_touch


    }

    /**
     * @param resId
     * @return 如果图片太小，那么就拉伸
     */
    public Bitmap getBitmap(int resId) {
        WindowManager wm = (WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
        float scaleWidth = 1, scaleHeight = 1;
        if (bitmap.getWidth() < width) {
            scaleWidth = width / bitmap.getWidth();
            scaleHeight = scaleWidth;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight()
                , matrix, true);
        return bitmap;
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4,R.id.button5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                cImageView.setAspectRatio(1, 1);//设置比例为一比一
                break;
            case R.id.button2:
                cImageView.setAspectRatio(16, 9);//设置比例为一比一

                break;
            case R.id.button3:
                cImageView.setAspectRatio(9, 16);//设置比例为一比一

                break;
            case R.id.button4:
                cImageView.setAspectRatio(4, 3);//设置比例为一比一

                break;
            case R.id.button5:
                Bitmap bitmap4 = cImageView.getCroppedImage();//得到裁剪好的图片
                ImageView croppedImageView4 = (ImageView) findViewById(R.id.imageView);
                croppedImageView4.setImageBitmap(bitmap4);//设置到imageview中
                break;
        }
    }
}