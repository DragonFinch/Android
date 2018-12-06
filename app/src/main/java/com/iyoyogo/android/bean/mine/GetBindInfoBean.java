package com.iyoyogo.android.bean.mine;

import com.iyoyogo.android.bean.BaseBean;

public class GetBindInfoBean extends BaseBean {


    /**
     * data : {"user_id":2,"user_phone":"18233382571","user_wx":1,"user_qq":0,"user_wb":0}
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
         * user_id : 2
         * user_phone : 18233382571
         * user_wx : 1
         * user_qq : 0
         * user_wb : 0
         */

        private int user_id;
        private String user_phone;
        private int user_wx;
        private int user_qq;
        private int user_wb;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public int getUser_wx() {
            return user_wx;
        }

        public void setUser_wx(int user_wx) {
            this.user_wx = user_wx;
        }

        public int getUser_qq() {
            return user_qq;
        }

        public void setUser_qq(int user_qq) {
            this.user_qq = user_qq;
        }

        public int getUser_wb() {
            return user_wb;
        }

        public void setUser_wb(int user_wb) {
            this.user_wb = user_wb;
        }
    }
}
