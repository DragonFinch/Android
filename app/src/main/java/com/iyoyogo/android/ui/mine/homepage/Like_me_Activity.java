package com.iyoyogo.android.ui.mine.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.iyoyogo.android.R;

import java.util.ArrayList;

public class Like_me_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_me);

        init();

        ArrayList<Object> arrayList = new ArrayList<>();
        if (arrayList.size() == 0) {
            RecyclerView recyclerView = findViewById(R.id.like_me_rv_id);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void init() {

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        TextView title_id = findViewById(R.id.like_me_title_tv_id);
        title_id.setText(title);

        final ImageView back = findViewById(R.id.message_center_back_im_id);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
