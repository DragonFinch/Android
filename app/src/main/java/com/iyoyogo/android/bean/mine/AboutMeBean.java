package com.iyoyogo.android.bean.mine;

import com.iyoyogo.android.bean.BaseBean;

public class AboutMeBean extends BaseBean {

    /**
     * code : 200
     * data : {"us_weixin":"1","us_weibo":"1"}
     */


    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * us_weixin : 1
         * us_weibo : 1
         */

        private String us_weixin;
        private String us_weibo;

        public String getUs_weixin() {
            return us_weixin;
        }

        public void setUs_weixin(String us_weixin) {
            this.us_weixin = us_weixin;
        }

        public String getUs_weibo() {
            return us_weibo;
        }

        public void setUs_weibo(String us_weibo) {
            this.us_weibo = us_weibo;
        }
    }
}
