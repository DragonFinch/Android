package com.iyoyogo.android.bean.mine.setting;

import com.iyoyogo.android.bean.BaseBean;

public class MineSettingBean extends BaseBean {

    /**
     * data : {"user_id":3,"wifi_auto_play_video":1,"notice":1,"address_list":1}
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
         * user_id : 3
         * wifi_auto_play_video : 1
         * notice : 1
         * address_list : 1
         */

        private int user_id;
        private int wifi_auto_play_video;
        private int notice;
        private int address_list;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getWifi_auto_play_video() {
            return wifi_auto_play_video;
        }

        public void setWifi_auto_play_video(int wifi_auto_play_video) {
            this.wifi_auto_play_video = wifi_auto_play_video;
        }

        public int getNotice() {
            return notice;
        }

        public void setNotice(int notice) {
            this.notice = notice;
        }

        public int getAddress_list() {
            return address_list;
        }

        public void setAddress_list(int address_list) {
            this.address_list = address_list;
        }
    }
}
