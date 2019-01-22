package com.iyoyogo.android.bean.search;

import com.iyoyogo.android.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

public class KeywordUserBean extends BaseBean {


    /**
     * data : {"list":[{"type":1,"user_id":60299,"user_nickname":"以南的地方天如海","yo_id":5007,"title":"用7天认识上海,一个人的地铁+暴走之旅","file_desc":"津湾广场是欣赏海河昼夜景致的最佳观赏地点","label_id":1,"label":"地理位置","key_type":"label","position_id":163,"position_name":"海南母瑞山革命根据地纪念园","channel_id":6,"channel":"地标打卡"},{"type":2,"yo_id":5007,"title":"用7天认识上海,一个人的地铁+暴走之旅"},{"type":3,"yo_id":52,"file_desc":"津湾广场是欣赏海河昼夜景致的最佳观赏地点"},{"type":4,"label_id":1,"label":"地理位置","key_type":"label"},{"type":5,"position_id":163,"position_name":"海南母瑞山革命根据地纪念园","key_type":"position"},{"type":6,"channel_id":6,"channel":"地标打卡","key_type":"channel"},{"type":1,"user_id":60738,"user_nickname":"人生地不熟"},{"type":2,"yo_id":5054,"title":"一起去地道的古都西安"},{"type":3,"yo_id":55,"file_desc":"私藏旅行地解放北路"},{"type":4,"label_id":5,"label":"旅游胜地111121121","key_type":"label"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * type : 1
             * user_id : 60299
             * user_nickname : 以南的地方天如海
             * yo_id : 5007
             * title : 用7天认识上海,一个人的地铁+暴走之旅
             * file_desc : 津湾广场是欣赏海河昼夜景致的最佳观赏地点
             * label_id : 1
             * label : 地理位置
             * key_type : label
             * position_id : 163
             * position_name : 海南母瑞山革命根据地纪念园
             * channel_id : 6
             * channel : 地标打卡
             */

            private int type;
            private int user_id;
            private String user_nickname;
            private int yo_id;
            private String title;
            private String file_desc;
            private int label_id;
            private String label;
            private String key_type;
            private String position_id;
            private String position_name;
            private int channel_id;
            private String channel;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUser_nickname() {
                return user_nickname;
            }

            public void setUser_nickname(String user_nickname) {
                this.user_nickname = user_nickname;
            }

            public int getYo_id() {
                return yo_id;
            }

            public void setYo_id(int yo_id) {
                this.yo_id = yo_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getFile_desc() {
                return file_desc;
            }

            public void setFile_desc(String file_desc) {
                this.file_desc = file_desc;
            }

            public int getLabel_id() {
                return label_id;
            }

            public void setLabel_id(int label_id) {
                this.label_id = label_id;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getKey_type() {
                return key_type;
            }

            public void setKey_type(String key_type) {
                this.key_type = key_type;
            }

            public String getPosition_id() {
                return position_id;
            }

            public void setPosition_id(String position_id) {
                this.position_id = position_id;
            }

            public String getPosition_name() {
                return position_name;
            }

            public void setPosition_name(String position_name) {
                this.position_name = position_name;
            }

            public int getChannel_id() {
                return channel_id;
            }

            public void setChannel_id(int channel_id) {
                this.channel_id = channel_id;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }
        }
    }
}
