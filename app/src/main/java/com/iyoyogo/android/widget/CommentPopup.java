package com.iyoyogo.android.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.EmojiPageAdapter;
import com.iyoyogo.android.utils.emoji.ChatEmoji;
import com.iyoyogo.android.utils.emoji.FaceConversionUtil;
import com.iyoyogo.android.utils.util.EmoticonsKeyboardUtils;
import com.iyoyogo.android.utils.util.KeyboardChangeListener;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author zhuhui
 * @date 2019/1/18
 * @description
 */
public class CommentPopup extends BasePopupWindow implements EmojiPageAdapter.OnEmojiClick, View.OnClickListener, TextView.OnEditorActionListener {

    private View               mView;
    private RelativeLayout     mRlEmoji;
    private RelativeLayout     mRlOption;
    private EditText           mEtContent;
    private ImageView          mIvEmoji;
    private ViewPager          mViewPager;
    private ViewPagerIndicator mViewPagerIndicator;

    private EmojiPageAdapter      mEmojiPageAdapter;
    private List<List<ChatEmoji>> emojiLists;

    private boolean isShowInputMethod = true;

    private OnSendComment mOnSendComment;


    public CommentPopup(Activity context) {
        super(context);
        setPopupGravity(Gravity.BOTTOM);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        emojiLists = FaceConversionUtil.getInstace().emojiLists;
        mView = findViewById(R.id.view);
        mRlEmoji = findViewById(R.id.rl_emoji);
        mRlOption = findViewById(R.id.rl_option);
        mIvEmoji = findViewById(R.id.iv_emoji);
        mEtContent = findViewById(R.id.et_content);
        mEtContent.setOnEditorActionListener(this);
        findViewById(R.id.tv_send).setOnClickListener(this);

        mIvEmoji.setOnClickListener(this);

        setAutoShowInputMethod(mEtContent, true);
        initViewPager();

        mIvEmoji.setImageResource(isShowInputMethod ? R.mipmap.input_biaoqing : R.mipmap.keyboard);


        mEtContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EmoticonsKeyboardUtils.openSoftKeyboard(mEtContent);
                isShowInputMethod = true;
                mRlEmoji.setVisibility(View.GONE);
                mRlOption.setVisibility(View.GONE);
                mView.setVisibility(View.VISIBLE);
                mIvEmoji.setImageResource(isShowInputMethod ? R.mipmap.input_biaoqing : R.mipmap.keyboard);
                return true;
            }
        });

        int defKeyboardHeight = EmoticonsKeyboardUtils.getDefKeyboardHeight(getContext());
        mView.getLayoutParams().height = defKeyboardHeight;
    }

    private void initViewPager() {
        mViewPager = findViewById(R.id.viewPager);
        mViewPagerIndicator = findViewById(R.id.view_pager_indicator);
        mEmojiPageAdapter = new EmojiPageAdapter(getContext(), emojiLists, this);
        mViewPager.setAdapter(mEmojiPageAdapter);
        mViewPagerIndicator.setViewPager(mViewPager);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_comment);
    }

    @Override
    public void onEmojiClick(View v, int pageIndex, int position) {
        ChatEmoji emoji = emojiLists.get(pageIndex).get(position);
        if (emoji.getId() == R.drawable.face_del_icon) {
            int    selection = mEtContent.getSelectionStart();
            String text      = mEtContent.getText().toString();
            if (selection > 0) {
                String text2 = text.substring(selection - 1);
                if ("]".equals(text2)) {
                    int start = text.lastIndexOf("[");
                    int end   = selection;
                    mEtContent.getText().delete(start, end);
                    return;
                }
                mEtContent.getText().delete(selection - 1, selection);
            }
        }
        if (!TextUtils.isEmpty(emoji.getCharacter())) {
            SpannableString spannableString = FaceConversionUtil.getInstace()
                    .addFace(getContext(), emoji.getId(), emoji.getCharacter());
            mEtContent.append(spannableString);
        }
    }

    public void setShowInputMethod(boolean showInputMethod) {
        isShowInputMethod = showInputMethod;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_emoji:
                isShowInputMethod = !isShowInputMethod;
                mIvEmoji.setImageResource(isShowInputMethod ? R.mipmap.input_biaoqing : R.mipmap.keyboard);
                if (isShowInputMethod) {
                    EmoticonsKeyboardUtils.openSoftKeyboard(mEtContent);
                    mRlEmoji.setVisibility(View.GONE);
                    mRlOption.setVisibility(View.GONE);
                    mView.setVisibility(View.VISIBLE);
                } else {
                    EmoticonsKeyboardUtils.closeSoftKeyboard(mEtContent);
                    mRlEmoji.setVisibility(View.VISIBLE);
                    mRlOption.setVisibility(View.VISIBLE);
                    mView.setVisibility(View.GONE);
                }
                break;

            case R.id.tv_send:
                if (TextUtils.isEmpty(mEtContent.getText())){
                    Toast.makeText(getContext(), "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                dismiss();
                if (mOnSendComment != null) {
                    mOnSendComment.onSendComment(mEtContent.getText().toString());
                }
                break;
        }
    }

    @Override
    public void showPopupWindow() {
        setAutoShowInputMethod(mEtContent, isShowInputMethod);
        super.showPopupWindow();
        if (isShowInputMethod) {
            mRlEmoji.setVisibility(View.GONE);
            mRlOption.setVisibility(View.GONE);
            mView.setVisibility(View.VISIBLE);
            setShowInputMethod(true);
            EmoticonsKeyboardUtils.openSoftKeyboard(mEtContent);
        } else {
            mRlEmoji.setVisibility(View.VISIBLE);
            mRlOption.setVisibility(View.VISIBLE);
            mView.setVisibility(View.GONE);
            setShowInputMethod(false);
            EmoticonsKeyboardUtils.closeSoftKeyboard(mEtContent);

        }
    }

    public void setKeyboardChange(boolean isShow, int keyboardHeight) {
        mView.getLayoutParams().height = keyboardHeight;
        if (!isShow) {
            mRlEmoji.setVisibility(View.VISIBLE);
            mRlOption.setVisibility(View.VISIBLE);
            mView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_SEND == actionId) {
            if (TextUtils.isEmpty(mEtContent.getText())){
                Toast.makeText(getContext(), "请输入内容", Toast.LENGTH_SHORT).show();
                return true;
            }
            dismiss();
            if (mOnSendComment != null) {
                mOnSendComment.onSendComment(mEtContent.getText().toString());
            }
            return true;
        }
        return false;
    }

    public void setOnSendComment(OnSendComment onSendComment) {
        mOnSendComment = onSendComment;
    }

    public interface OnSendComment {
        void onSendComment(String comment);
    }
}
