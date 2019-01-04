package com.iyoyogo.android.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

/**
 * @author zhuhui
 * @date 2018/9/8
 * @description EditText输入监听
 */
public class TextChangeListener implements TextWatcher {

    private View mView;

    private TextChange mTextChange;

    public TextChangeListener(View view, TextChange textChange) {
        this.mView = view;
        this.mTextChange = textChange;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mTextChange.onTextChange(mView, s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    public interface TextChange {
        void onTextChange(View view, String s);
    }
}
