package com.iyoyogo.android.ui.home.yoji;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.view.CircleImageView;
import com.iyoyogo.android.widget.PileLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YoJiListActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.pile_layout)
    PileLayout pileLayout;
    private List<String> imageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yo_ji_list);
        ButterKnife.bind(this);

        imageData = new ArrayList<>();
        imageData.add("http://t2.hddhhn.com/uploads/tu/201702/132/st5.png");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201701/323/st1.png");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201701/91/st3.png");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201612/429/st1.png");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201610/141/st6.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201708/760/18a21a92591.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        initPraises();
    }
    public void initPraises() {
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < imageData.size(); i++) {
            CircleImageView imageView = (CircleImageView) inflater.inflate(R.layout.item_head_image, pileLayout, false);
            Glide.with(this).load(imageData.get(i)).into(imageView);
            imageView.setId(i);
            // 为item绑定数据
            imageView.setTag(imageData.get(i));
            // 为item设置点击事件
            imageView.setOnClickListener(this);
            pileLayout.addView(imageView);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        // 获取item绑定的数据
        String content = (String) v.getTag();
        for (int i = 0; i < imageData.size(); i++) {
            if (i == id) {
                Toast.makeText(this, content, Toast.LENGTH_LONG).show();
            }
        }
    }
}
