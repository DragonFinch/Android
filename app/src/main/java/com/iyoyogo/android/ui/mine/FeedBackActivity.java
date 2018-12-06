package com.iyoyogo.android.ui.mine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iyoyogo.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedBackActivity extends AppCompatActivity {

    @BindView(R.id.back_iv_id)
    ImageView backIvId;
    @BindView(R.id.tv_complete)
    TextView tvComplete;
    @BindView(R.id.edit_feed_back)
    EditText editFeedBack;
    @BindView(R.id.logo_iv_id)
    ImageView logoIvId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_iv_id, R.id.tv_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv_id:
                finish();
                break;
            case R.id.tv_complete:
                break;
        }
    }
}
