package com.iyoyogo.android.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.iyoyogo.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.web_view)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        initDate();
    }

    private void initDate() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        if (url.equals("1")) {
            webView.loadUrl("http://app.iyoyogo.com/index.php/home/article/details?id=20");
        } else {
            webView.loadUrl("http://app.iyoyogo.com/index.php/home/article/details?id=21");
        }
    }
}
