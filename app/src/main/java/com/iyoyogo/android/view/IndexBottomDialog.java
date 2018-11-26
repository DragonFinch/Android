package com.iyoyogo.android.view;


import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.iyoyogo.android.R;
import com.iyoyogo.android.ui.common.MainActivity;

/**
 * 创建时间：2018/5/30
 * 描述：
 */
public class IndexBottomDialog extends BottomDialogBase implements View.OnClickListener {

    private Context context;
    private LinearLayout ll_close;
    private DrawableTextView yoji;
    private DrawableTextView yoxiu;

    public IndexBottomDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate() {
        setContentView(R.layout.dialog_index_bottom);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        ll_close = findViewById(R.id.ll_close);
        ll_close.setOnClickListener(this);
        yoji = findViewById(R.id.yoji);
        yoji.setOnClickListener(this);
        yoxiu = findViewById(R.id.yoxiu);
        yoxiu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yoji:
                //TODO
//                ActivityUtils.goActSendZujiActivity(context);
                dismiss();
                break;
            case R.id.yoxiu:
//                ActivityUtils.goReleaseYoXiuActivity(context, null);
                dismiss();
                break;
            case R.id.ll_close:
                if (!((MainActivity) context).isFinishing() && isShowing()) {
                    dismiss();
                }
                break;
        }
    }
}
