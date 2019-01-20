package com.iyoyogo.android.widget.wheel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 使用说明：1.showLongTerm()是否显示长期选项
 * 2.setTimePickerGone隐藏时间选择
 * 3.接口DateChooseInterface
 * <p>
 * 用于时间日期的选择
 * Created by liuhongxia on 2016/4/16.
 */
public class DateChooseWheelViewDialog extends Dialog implements View.OnClickListener {
    //控件
    private WheelView mYearWheelView;
    private WheelView mDateWheelView;
    private WheelView mHourWheelView;
    private WheelView mMinuteWheelView;
    private CalendarTextAdapter mDateAdapter;
    private CalendarTextAdapter mMonthAdapter;
    private CalendarTextAdapter mHourAdapter;
    private CalendarTextAdapter mMinuteAdapter;
    private CalendarTextAdapter mYearAdapter;
    private TextView mTitleTextView;
    private TextView mSureButton;
    private Dialog mDialog;
    private Button mCloseDialog;
    private LinearLayout mLongTermLayout;
    private TextView mLongTermTextView;

    //变量
    private ArrayList<String> arry_date = new ArrayList<String>();
    private ArrayList<String> arry_month = new ArrayList<String>();
    private ArrayList<String> arry_hour = new ArrayList<String>();
    private ArrayList<String> arry_minute = new ArrayList<String>();
    private ArrayList<String> arry_year = new ArrayList<String>();

    private int nowDateId = 0;
    private int nowHourId = 0;
    private int nowMinuteId = 0;
    private int nowYearId = 0;
    private int nowMonthId = 0;
    private String mYearStr;
    private String mMonth;
    private String mDateStr;
    private String mHourStr;
    private String mMinuteStr;
    private boolean mBlnBeLongTerm = false;//是否需要长期
    private boolean mBlnTimePickerGone = false;//时间选择是否显示


    //常量
    private final int MAX_TEXT_SIZE = 18;
    private final int MIN_TEXT_SIZE = 16;

    private Context mContext;
    private DateChooseInterface dateChooseInterface;
    private WheelView mMonthWheelView;

    public DateChooseWheelViewDialog(Context context, DateChooseInterface dateChooseInterface) {
        super(context);
        this.mContext = context;
        this.dateChooseInterface = dateChooseInterface;
        mDialog = new Dialog(context, R.style.dialog);
        initView();
        initData();
    }


    private void initData() {
        initYear();
        initMonth();
        initDate();
//        initHour();
//        initMinute();
        initListener();
    }

    /**
     * 初始化滚动监听事件
     */
    private void initListener() {
        //年份*****************************
        mYearWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mYearAdapter);
                mYearStr = arry_year.get(wheel.getCurrentItem()) + "";
            }
        });

        mYearWheelView.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mYearAdapter);
            }
        });
        mMonthWheelView.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mMonthAdapter);
            }
        });
        mMonthWheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mMonthAdapter);
                mMonth = arry_month.get(wheel.getCurrentItem()) + "";
            }
        });
        //日期********************
        mDateWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) mDateAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mDateAdapter);
//                mDateCalendarTextView.setText(" " + arry_date.get(wheel.getCurrentItem()));
                mDateStr = arry_date.get(wheel.getCurrentItem());
            }
        });

        mDateWheelView.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mDateAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mDateAdapter);
            }
        });

        //小时***********************************
      /*  mHourWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) mHourAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mHourAdapter);
                mHourStr = arry_hour.get(wheel.getCurrentItem()) + "";
            }
        });

        mHourWheelView.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mHourAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mHourAdapter);
            }
        });

        //分钟********************************************
        mMinuteWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) mMinuteAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mMinuteAdapter);
                mMinuteStr = arry_minute.get(wheel.getCurrentItem()) + "";
            }
        });

        mMinuteWheelView.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mMinuteAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mMinuteAdapter);
            }
        });*/
    }

    /**
     * 初始化分钟
     */
    private void initMinute() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowMinite = nowCalendar.get(Calendar.MINUTE);
        arry_minute.clear();
        for (int i = 0; i <= 59; i++) {
            arry_minute.add(i + "");
            if (nowMinite == i) {
                nowMinuteId = arry_minute.size() - 1;
            }
        }

        mMinuteAdapter = new CalendarTextAdapter(mContext, arry_minute, nowMinuteId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mMinuteWheelView.setVisibleItems(5);
        mMinuteWheelView.setViewAdapter(mMinuteAdapter);
        mMinuteWheelView.setCurrentItem(nowMinuteId);
        mMinuteStr = arry_minute.get(nowMinuteId) + "";
        setTextViewStyle(mMinuteStr, mMinuteAdapter);

    }

    /**
     * 初始化时间
     */
    private void initHour() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowHour = nowCalendar.get(Calendar.HOUR_OF_DAY);
        arry_hour.clear();
        for (int i = 0; i <= 23; i++) {
            arry_hour.add(i + "");
            if (nowHour == i) {
                nowHourId = arry_hour.size() - 1;
            }
        }

        mHourAdapter = new CalendarTextAdapter(mContext, arry_hour, nowHourId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mHourWheelView.setVisibleItems(5);
        mHourWheelView.setViewAdapter(mHourAdapter);
        mHourWheelView.setCurrentItem(nowHourId);
        mHourStr = arry_hour.get(nowHourId) + "";
        setTextViewStyle(mHourStr, mHourAdapter);
    }

    /**
     * 初始化年
     */
    private void initYear() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowYear = nowCalendar.get(Calendar.YEAR);
        arry_year.clear();
//        for (int i = 0; i <= 99; i++) {
            for (int i = 0; i <= 30; i++) {
                int year = nowYear - 30 + i;
            arry_year.add(year + "年");
            if (nowYear == year) {
                nowYearId = arry_year.size() - 1;
            }
        }
        mYearAdapter = new CalendarTextAdapter(mContext, arry_year, nowYearId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mYearWheelView.setVisibleItems(5);
        mYearWheelView.setViewAdapter(mYearAdapter);
        mYearWheelView.setCurrentItem(nowYearId);
        mYearStr = arry_year.get(nowYearId);
    }


    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_date_choose, null);
        mDialog.setContentView(view);
        mYearWheelView = (WheelView) view.findViewById(R.id.year_wv);
        mMonthWheelView = (WheelView) view.findViewById(R.id.month_wv);
        mDateWheelView = (WheelView) view.findViewById(R.id.date_wv);
        ImageView close_img = (ImageView) view.findViewById(R.id.close_img);
        ImageView tip_img = (ImageView) view.findViewById(R.id.tip_img);
        tip_img.setImageResource(R.mipmap.stamp_tips1);
        close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
        mTitleTextView = (TextView) view.findViewById(R.id.title_tv);
        TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
        TextView tv_message_two = (TextView) view.findViewById(R.id.tv_message_two);
        TextView tv_message_three = (TextView) view.findViewById(R.id.tv_message_three);
        mSureButton = (TextView) view.findViewById(R.id.sure_btn);
        mCloseDialog = (Button) view.findViewById(R.id.date_choose_close_btn);
        mLongTermLayout = (LinearLayout) view.findViewById(R.id.long_term_layout);
        mLongTermTextView = (TextView) view.findViewById(R.id.long_term_tv);
        tv_message.setText("Hi～");
        tv_message_two.setText("这里是时间选择器哦～");
        tv_message_three.setText("__yoyoGo");
        tv_message.setTextColor(Color.parseColor("#333333"));
        tv_message_two.setTextColor(Color.parseColor("#333333"));
        tv_message_three.setTextColor(Color.parseColor("#333333"));
        mSureButton.setOnClickListener(this);
        mCloseDialog.setOnClickListener(this);
        mLongTermTextView.setOnClickListener(this);
    }

    /**
     * 初始化日期
     */
    private int getDay(int year, int month) {
        int day = 30;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }

    private void initDate() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowYear = nowCalendar.get(Calendar.YEAR);
        arry_date.clear();
        setDate(nowYear);
        Log.d("DateChooseWheelViewDial", "arry_date.size():" + arry_date.size());
        mDateAdapter = new CalendarTextAdapter(mContext, arry_date, nowDateId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mDateWheelView.setVisibleItems(5);
        mDateWheelView.setViewAdapter(mDateAdapter);
        mDateWheelView.setCurrentItem(nowDateId);

        mDateStr = arry_date.get(nowDateId);
        setTextViewStyle(mDateStr, mDateAdapter);
    }

    private void initMonth() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowMonth = nowCalendar.get(Calendar.MONTH);
        arry_date.clear();
        String[] month_arr = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        for (int i = 0; i < month_arr.length; i++) {
            arry_month.add(month_arr[i] + "月");
            int month = nowMonth - 1;
            if (nowMonth == month) {
                nowMonthId = arry_month.size() - 1;

            }
        }
        mMonthAdapter = new CalendarTextAdapter(mContext, arry_month, nowMonthId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mMonthWheelView.setVisibleItems(5);
        mMonthWheelView.setViewAdapter(mMonthAdapter);
        mMonthWheelView.setCurrentItem(nowMonthId);

        mMonth = arry_month.get(nowMonthId);
        setTextViewStyle(mMonth, mMonthAdapter);
    }


    public void setDateDialogTitle(String title) {
        mTitleTextView.setText(title);
    }

    public void setTimePickerGone(boolean isGone) {
        mBlnTimePickerGone = isGone;
        if (isGone) {
            LinearLayout.LayoutParams yearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            yearParams.rightMargin = 22;

            LinearLayout.LayoutParams dateParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mYearWheelView.setLayoutParams(yearParams);
            mDateWheelView.setLayoutParams(dateParams);

            mHourWheelView.setVisibility(View.GONE);
            mMinuteWheelView.setVisibility(View.GONE);
        } else {
            mHourWheelView.setVisibility(View.VISIBLE);
            mMinuteWheelView.setVisibility(View.VISIBLE);
        }

    }

    public void showLongTerm(boolean show) {
        if (show) {
            mLongTermLayout.setVisibility(View.VISIBLE);
        } else {
            mLongTermLayout.setVisibility(View.GONE);
        }

    }


    /**
     * 将改年的所有日期写入数组
     *
     * @param year
     */
    private void setDate(int year) {
        boolean isRun = isRunNian(year);
        Calendar nowCalendar = Calendar.getInstance();
        int nowMonth = nowCalendar.get(Calendar.MONTH);
        int nowDay = nowCalendar.get(Calendar.DAY_OF_MONTH);
        boolean flag = false;
        String[] date_arr = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        String[] date_arr1 = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
        String[] date_arr2 = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",};
        String[] date_arr3 = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"};
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (nowMonth) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                for (int i = 0; i < date_arr.length; i++) {
                    arry_date.add(date_arr[i] + "日");
                    if (i == nowDay) {

                        nowDateId = arry_date.size() - 1;
                    }

                }

                break;
            case 2:
                if (flag) {
                    for (int i = 0; i < date_arr2.length; i++) {
                        arry_date.add(date_arr2[i] + "日");
                        if (i == nowDay) {

                            nowDateId = arry_date.size() - 1;
                        }

                    }

                } else {
                    for (int i = 0; i < date_arr3.length; i++) {
                        arry_date.add(date_arr3[i] + "日");
                        if (i == nowDay) {

                            nowDateId = arry_date.size() - 1;
                        }

                    }

                }

                break;
            default:
                for (int i = 0; i < date_arr1.length; i++) {
                    arry_date.add(date_arr1[i] + "日");
                    if (i == nowDay) {

                        nowDateId = arry_date.size() - 1;
                    }

                }
                break;
        }
     /*
        for (int month = 1; month <= 12; month++){
            switch (month){
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    for (int day = 1; day <= 31; day++){
                        arry_date.add(month + "月" + day + "日");

                        if (month == nowMonth && day == nowDay){
                            nowDateId = arry_date.size() - 1;
                        }
                    }
                    break;
                case 2:
                    if (isRun){
                        for (int day = 1; day <= 29; day++){
                            arry_date.add(month + "月" + day + "日");
                            if (month == nowMonth && day == nowDay){
                                nowDateId = arry_date.size() - 1;
                            }
                        }
                    }else {
                        for (int day = 1; day <= 28; day++){
                            arry_date.add(month + "月" + day + "日");
                            if (month == nowMonth && day == nowDay){
                                nowDateId = arry_date.size() - 1;
                            }
                        }
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    for (int day = 1; day <= 30; day++){
                        arry_date.add(month + "月" + day + "日");
                        if (month == nowMonth && day == nowDay){
                            nowDateId = arry_date.size() - 1;
                        }
                    }
                    break;
                default:
                    break;
            }
        }*/
    }

    /**
     * 判断是否是闰年
     *
     * @param year
     * @return
     */
    private boolean isRunNian(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置文字的大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextViewStyle(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(MAX_TEXT_SIZE);
                textvew.setTextColor(mContext.getResources().getColor(R.color.text_10));
            } else {
                textvew.setTextSize(MIN_TEXT_SIZE);
                textvew.setTextColor(mContext.getResources().getColor(R.color.text_11));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure_btn://确定选择按钮监听
                String s = strTimeToDateFormat(mYearStr, mMonth, mDateStr);
                Log.d("DateChooseWheelViewDial", s);
                dateChooseInterface.getDateTime(s, mBlnBeLongTerm);
                dismissDialog();
                break;
            case R.id.date_choose_close_btn://关闭日期选择对话框
                dismissDialog();
                break;
            case R.id.long_term_tv://选择长期时间监听
                if (!mBlnBeLongTerm) {
                    mLongTermTextView.setBackgroundResource(R.drawable.gouxuanok);
                    mBlnBeLongTerm = true;
                } else {
                    mLongTermTextView.setBackgroundResource(R.drawable.gouxuanno);
                    mBlnBeLongTerm = false;
                }
            default:
                break;
        }
    }

    /**
     * 对话框消失
     */
    private void dismissDialog() {

        if (Looper.myLooper() != Looper.getMainLooper()) {

            return;
        }

        if (null == mDialog || !mDialog.isShowing() || null == mContext
                || ((Activity) mContext).isFinishing()) {

            return;
        }

        mDialog.dismiss();
        this.dismiss();
    }

    /**
     * 显示日期选择dialog
     */
    public void showDateChooseDialog() {

        if (Looper.myLooper() != Looper.getMainLooper()) {

            return;
        }

        if (null == mContext || ((Activity) mContext).isFinishing()) {

            // 界面已被销毁
            return;
        }

        if (null != mDialog) {

            mDialog.show();
            return;
        }

        if (null == mDialog) {

            return;
        }

        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
    }

    /**
     * xx年xx月xx日xx时xx分转成yyyy-MM-dd HH:mm
     *
     * @param yearStr
     * @param dateStr
     * @param
     * @param
     * @return
     */
    private String strTimeToDateFormat(String yearStr, String monthStr, String dateStr) {

        return yearStr.replace("年", "-") + monthStr.replace("月", "-") + dateStr.replace("日", "");
    }


    /**
     * 滚轮的adapter
     */
    private class CalendarTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;

        protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, R.id.tempValue, currentItem, maxsize, minsize);
            this.list = list;
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            String str = list.get(index) + "";
            return str;
        }
    }

    /**
     * 回调选中的时间（默认时间格式"yyyy-MM-dd HH:mm:ss"）
     */
    public interface DateChooseInterface {
        void getDateTime(String time, boolean longTimeChecked);
    }

}
