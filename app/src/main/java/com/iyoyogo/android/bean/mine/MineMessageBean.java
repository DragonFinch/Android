package com.iyoyogo.android.bean.mine;


import com.iyoyogo.android.bean.BaseBean;

public class MineMessageBean extends BaseBean {


    /**
     * data : {"count_noread":3,"user_id":"2","user_nickname":"","user_logo":"?x-oss-process=image/resize,w_50","user_level":0,"partner_type":"0","count_attention":6,"count_fans":1,"today_isclock":0,"clock_days":13,"clock_win":"65.76%"}
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
         * count_noread : 3
         * user_id : 2
         * user_nickname :
         * user_logo : ?x-oss-process=image/resize,w_50
         * user_level : 0
         * partner_type : 0
         * count_attention : 6
         * count_fans : 1
         * today_isclock : 0
         * clock_days : 13
         * clock_win : 65.76%
         */

        private int count_noread;
        private String user_id;
        private String user_nickname;
        private String user_logo;
        private int user_level;
        private String partner_type;
        private int count_attention;
        private int count_fans;
        private int today_isclock;
        private int clock_days;
        private String clock_win;

        public int getCount_noread() {
            return count_noread;
        }

        public void setCount_noread(int count_noread) {
            this.count_noread = count_noread;
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

        public int getCount_attention() {
            return count_attention;
        }

        public void setCount_attention(int count_attention) {
            this.count_attention = count_attention;
        }

        public int getCount_fans() {
            return count_fans;
        }

        public void setCount_fans(int count_fans) {
            this.count_fans = count_fans;
        }

        public int getToday_isclock() {
            return today_isclock;
        }

        public void setToday_isclock(int today_isclock) {
            this.today_isclock = today_isclock;
        }

        public int getClock_days() {
            return clock_days;
        }

        public void setClock_days(int clock_days) {
            this.clock_days = clock_days;
        }

        public String getClock_win() {
            return clock_win;
        }

        public void setClock_win(String clock_win) {
            this.clock_win = clock_win;
        }
    }
}
