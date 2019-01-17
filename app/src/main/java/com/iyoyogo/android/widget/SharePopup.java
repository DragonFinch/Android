package com.iyoyogo.android.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.iyoyogo.android.R;
import com.iyoyogo.android.app.Constants;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.utils.SpUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author zhuhui
 * @date 2019/1/17
 * @description
 */
public class SharePopup extends BasePopupWindow implements View.OnClickListener {

    private String title;
    private String desc;
    private String image;
    private String userId;
    private int id;

    public SharePopup(Context context) {
        super(context);
        userId = SpUtils.getString(getContext(), "user_id", null);
        setPopupGravity(Gravity.BOTTOM);
        setBackgroundColor(Color.parseColor("#8f000000"));
        findViewById(R.id.iv_close).setOnClickListener(this);
        findViewById(R.id.ll_wechat).setOnClickListener(this);
        findViewById(R.id.ll_we_circle).setOnClickListener(this);
        findViewById(R.id.ll_qq).setOnClickListener(this);
        findViewById(R.id.ll_weibo).setOnClickListener(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_new_share);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0, 260);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getTranslateVerticalAnimation(0, 1f, 260);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {
//            case R.id.iv_close:
//                dismiss();
//                break;
            case R.id.ll_wechat:
                shareWeb(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.ll_we_circle:
                shareWeb(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.id.ll_qq:
                shareWeb(SHARE_MEDIA.QQ);
                break;
            case R.id.ll_weibo:
                shareWeb(SHARE_MEDIA.SINA);
                break;
//            case R.id.tv_cancel:
//                dismiss();
//                break;
        }
    }

    private void shareWeb(SHARE_MEDIA share_media) {
        String url = Constants.BASE_URL + "home/share/details_yoj/share_user_id/" + userId + "/yo_id/" + id;
        UMWeb  web = new UMWeb(url);
        web.setTitle(title);//标题
        UMImage thumb = new UMImage(getContext(), image);
        web.setThumb(thumb);  //缩略图
        if (!TextUtils.isEmpty(desc)) {
            web.setDescription(desc);//描述
        }
        new ShareAction((Activity) getContext())
                .withMedia(web)
                .setPlatform(share_media)
                .share();
    }

    public SharePopup setTitle(String title) {
        this.title = title;
        return this;
    }

    public SharePopup setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public SharePopup setImage(String image) {
        this.image = image;
        return this;
    }

    public SharePopup setYoId(int id) {
        this.id = id;
        return this;
    }
}
