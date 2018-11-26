package com.iyoyogo.android.view;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.iyoyogo.android.R;

/**
 * 创建时间：2018/6/30
 * 描述：
 */
public class LocationTypeDialog extends BottomDialogBase implements View.OnClickListener {
    private LoactionTypeResultCallback loactionTypeResultCallback;

    public void setLoactionTypeResultCallback(LoactionTypeResultCallback loactionTypeResultCallback) {
        this.loactionTypeResultCallback = loactionTypeResultCallback;
    }

    public LocationTypeDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate() {
        setContentView(R.layout.dialog_location_type);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        TextView tv_hotel = findViewById(R.id.tv_hotel);
        TextView tv_food = findViewById(R.id.tv_food);
        TextView tv_shopping = findViewById(R.id.tv_shopping);
        TextView tv_feature = findViewById(R.id.tv_feature);
        TextView tv_other = findViewById(R.id.tv_other);
        TextView tv_cancel = findViewById(R.id.tv_cancel);

        tv_hotel.setOnClickListener(this);
        tv_food.setOnClickListener(this);
        tv_shopping.setOnClickListener(this);
        tv_feature.setOnClickListener(this);
        tv_other.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_hotel:
                if (loactionTypeResultCallback != null) {
                    loactionTypeResultCallback.onResult(0, mContext.getString(R.string.str_hotel));
                }
                dismiss();
                break;
            case R.id.tv_food:
                if (loactionTypeResultCallback != null) {
                    loactionTypeResultCallback.onResult(1, mContext.getString(R.string.str_food));
                }
                dismiss();
                break;
            case R.id.tv_shopping:
                if (loactionTypeResultCallback != null) {
                    loactionTypeResultCallback.onResult(2, mContext.getString(R.string.str_shopping));
                }
                dismiss();
                break;
            case R.id.tv_feature:
                if (loactionTypeResultCallback != null) {
                    loactionTypeResultCallback.onResult(3, mContext.getString(R.string.str_feature));
                }
                dismiss();
                break;
            case R.id.tv_other:
                if (loactionTypeResultCallback != null) {
                    loactionTypeResultCallback.onResult(4, mContext.getString(R.string.str_other));
                }
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    public interface LoactionTypeResultCallback {
        void onResult(int locationType, String locationTypeName);
    }
}
