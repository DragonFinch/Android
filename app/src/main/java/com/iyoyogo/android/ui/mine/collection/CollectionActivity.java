package com.iyoyogo.android.ui.mine.collection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.iyoyogo.android.R;

import java.util.ArrayList;


public class CollectionActivity extends AppCompatActivity {
    //我的收藏夹
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        ArrayList<Object> list = new ArrayList<>();
        int size = list.size();
        if (size == 0) {
            LinearLayout layout = findViewById(R.id.collection_ll_id);
            layout.setVisibility(View.GONE);
        }
    }
}
