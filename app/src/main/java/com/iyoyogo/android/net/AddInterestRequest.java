package com.iyoyogo.android.net;

import com.iyoyogo.android.model.en.BaseRequest;

public class AddInterestRequest extends BaseRequest {
    private  Integer[] interest_ids;
    private int user_id;
    private String user_token;

    public void setIds(Integer[] interest_ids) {
        this.interest_ids = interest_ids;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }
}
