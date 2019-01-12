package com.iyoyogo.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iyoyogo.android.ui.common.MainActivity;
import com.iyoyogo.android.widget.wheel.DateChooseWheelViewDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {


    @BindView(R.id.show_content_tv)
    TextView showContentTv;
    @BindView(R.id.start_date_btn)
    Button startDateBtn;
    @BindView(R.id.end_date_btn)
    Button endDateBtn;
    @BindView(R.id.date_valid_btn)
    Button dateValidBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);


//        setTime();
// hour.getCurrentItem(), mins.getCurrentItem()

    }

    @OnClick({R.id.start_date_btn, R.id.end_date_btn, R.id.date_valid_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start_date_btn:
                DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(TestActivity.this, new DateChooseWheelViewDialog.DateChooseInterface() {
                    @Override
                    public void getDateTime(String time, boolean longTimeChecked) {
                        showContentTv.setText(time);
                    }
                });
                startDateChooseDialog.setDateDialogTitle("开始时间");
                startDateChooseDialog.showDateChooseDialog();
                break;
            case R.id.end_date_btn:
                DateChooseWheelViewDialog endDateChooseDialog = new DateChooseWheelViewDialog(TestActivity.this,
                        new DateChooseWheelViewDialog.DateChooseInterface() {
                            @Override
                            public void getDateTime(String time, boolean longTimeChecked) {
                                showContentTv.setText(time);
                            }
                        });
                endDateChooseDialog.setTimePickerGone(true);
                endDateChooseDialog.setDateDialogTitle("结束时间");
                endDateChooseDialog.showDateChooseDialog();
                break;
            case R.id.date_valid_btn:
                DateChooseWheelViewDialog dateValidChooseDialog = new DateChooseWheelViewDialog(TestActivity.this,
                        new DateChooseWheelViewDialog.DateChooseInterface() {
                            @Override
                            public void getDateTime(String time, boolean longTimeChecked) {
                                if (longTimeChecked) {
                                    showContentTv.setText("长期  ");
                                } else {
                                    showContentTv.setText(time);
                                }
                            }
                        });
                dateValidChooseDialog.setTimePickerGone(true);
                dateValidChooseDialog.showLongTerm(true);
                dateValidChooseDialog.setDateDialogTitle("身份证到期时间");
                dateValidChooseDialog.showDateChooseDialog();
                break;
        }
    }
}
