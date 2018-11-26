package com.iyoyogo.android.model.en;

import com.iyoyogo.android.bean.BaseBean;

public class SendMessageRequest extends BaseBean {
    private String phone;
    private String yzm;
    private String datetime;
    private String sign;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setYzm(String yzm) {
        this.yzm = yzm;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
