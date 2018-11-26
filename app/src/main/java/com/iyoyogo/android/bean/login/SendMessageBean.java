package com.iyoyogo.android.bean.login;

import com.iyoyogo.android.bean.BaseBean;

public class SendMessageBean extends BaseBean {


    /**
     * data : {"phone":"15611695821","yzm":"1234"}
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
         * phone : 15611695821
         * yzm : 1234
         */

        private String phone;
        private String yzm;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getYzm() {
            return yzm;
        }

        public void setYzm(String yzm) {
            this.yzm = yzm;
        }
    }
}
