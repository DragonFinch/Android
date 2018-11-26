package com.iyoyogo.android.bean.login.login;

import com.iyoyogo.android.bean.BaseBean;

public class LoginBean extends BaseBean {

    /**
     * data : {"status":2,"user_id":"","user_token":"","have_interest":0}
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
         * status : 2
         * user_id :
         * user_token :
         * have_interest : 0
         */

        private int status;
        private String user_id;
        private String user_token;
        private int have_interest;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_token() {
            return user_token;
        }

        public void setUser_token(String user_token) {
            this.user_token = user_token;
        }

        public int getHave_interest() {
            return have_interest;
        }

        public void setHave_interest(int have_interest) {
            this.have_interest = have_interest;
        }
    }
}
