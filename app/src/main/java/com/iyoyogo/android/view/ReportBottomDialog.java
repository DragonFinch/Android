package com.iyoyogo.android.view;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.iyoyogo.android.R;

/**
 * 创建时间：2018/6/30
 * 描述：
 */
public class ReportBottomDialog extends BottomDialogBase implements View.OnClickListener {
    private ReportReason reportReason;

    public void setReasonCallback(ReportReason reportReason) {
        this.reportReason = reportReason;
    }

    public ReportBottomDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate() {
        setContentView(R.layout.dialog_report);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        TextView tv_report1 = findViewById(R.id.tv_report1);
        TextView tv_report2 = findViewById(R.id.tv_report2);
        TextView tv_report3 = findViewById(R.id.tv_report3);
        TextView tv_other = findViewById(R.id.tv_report_other);
        TextView tv_cancel = findViewById(R.id.tv_cancel);

        tv_report1.setOnClickListener(this);
        tv_report2.setOnClickListener(this);
        tv_report3.setOnClickListener(this);
        tv_other.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_report1:
                if (reportReason != null) {
                    reportReason.onReason(0, mContext.getString(R.string.str_report_ad));
                }
                dismiss();
                break;
            case R.id.tv_report2:
                if (reportReason != null) {
                    reportReason.onReason(1, mContext.getString(R.string.str_report_harmful));
                }
                dismiss();
                break;
            case R.id.tv_report3:
                if (reportReason != null) {
                    reportReason.onReason(2, mContext.getString(R.string.str_report_not_allowed));
                }
                dismiss();
                break;
            case R.id.tv_report_other:
                if (reportReason != null) {
                    reportReason.onReason(3, mContext.getString(R.string.str_other));
                }
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    public interface ReportReason {
        void onReason(int pos, String reason);
    }
}
