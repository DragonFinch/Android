package com.iyoyogo.android.bean.mine.center;

import com.iyoyogo.android.bean.BaseBean;

public class UserCenterBean extends BaseBean {


    /**
     * data : {"user_id":"2","user_nickname":"","user_logo":"?x-oss-process=image/resize,w_50","user_level":0,"partner_type":"0","user_sex":"","user_city":"","user_birthday":"1989-11-01","count_attention":"6","count_fans":"1","count_collect":"0","count_yox":"2","count_yoj":"0","is_my_attention":0}
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
         * user_nickname :
         * user_logo : ?x-oss-process=image/resize,w_50
         * user_level : 0
         * partner_type : 0
         * user_sex :
         * user_city :
         * user_birthday : 1989-11-01
         * count_attention : 6
         * count_fans : 1
         * count_collect : 0
         * count_yox : 2
         * count_yoj : 0
         * is_my_attention : 0
         */
        private String user_logo_big;
        private String user_id;
        private String user_nickname;
        private String user_logo;
        private int user_level;
        private String partner_type;
        private String user_sex;
        private String user_city;
        private String user_birthday;
        private String count_attention;
        private String count_fans;
        private String count_collect;
        private String count_yox;
        private String count_yoj;
        private int is_my_attention;

        public DataBean(String user_logo_big, String user_nickname) {
            this.user_logo_big = user_logo_big;
            this.user_nickname = user_nickname;
        }

        public String getUser_logo_big() {
            return user_logo_big;
        }

        public void setUser_logo_big(String user_logo_big) {
            this.user_logo_big = user_logo_big;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getUser_logo() {
            return user_logo;
        }

        public void setUser_logo(String user_logo) {
            this.user_logo = user_logo;
        }

        public int getUser_level() {
            return user_level;
        }

        public void setUser_level(int user_level) {
            this.user_level = user_level;
        }

        public String getPartner_type() {
            return partner_type;
        }

        public void setPartner_type(String partner_type) {
            this.partner_type = partner_type;
        }

        public String getUser_sex() {
            return user_sex;
        }

        public void setUser_sex(String user_sex) {
            this.user_sex = user_sex;
        }

        public String getUser_city() {
            return user_city;
        }

        public void setUser_city(String user_city) {
            this.user_city = user_city;
        }

        public String getUser_birthday() {
            return user_birthday;
        }

        public void setUser_birthday(String user_birthday) {
            this.user_birthday = user_birthday;
        }

        public String getCount_attention() {
            return count_attention;
        }

        public void setCount_attention(String count_attention) {
            this.count_attention = count_attention;
        }

        public String getCount_fans() {
            return count_fans;
        }

        public void setCount_fans(String count_fans) {
            this.count_fans = count_fans;
        }

        public String getCount_collect() {
            return count_collect;
        }

        public void setCount_collect(String count_collect) {
            this.count_collect = count_collect;
        }

        public String getCount_yox() {
            return count_yox;
        }

        public void setCount_yox(String count_yox) {
            this.count_yox = count_yox;
        }

        public String getCount_yoj() {
            return count_yoj;
        }

        public void setCount_yoj(String count_yoj) {
            this.count_yoj = count_yoj;
        }

        public int getIs_my_attention() {
            return is_my_attention;
        }

        public void setIs_my_attention(int is_my_attention) {
            this.is_my_attention = is_my_attention;
        }
    }
}
