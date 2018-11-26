package com.iyoyogo.android.ui.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.iyoyogo.android.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        byte[] bis = getIntent().getByteArrayExtra("bitmap");
        if(bis != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
            if(bitmap != null){
                ((ImageView) findViewById(R.id.clipResultIV)).setImageBitmap(bitmap);
            }
        }
    }
}
