package com.iyoyogo.android.bean.yoxiu;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

/**
 * @author zhuhui
 * @date 2019/1/4
 * @description
 */
public class PublishYoXiuBean extends BaseBean {

    /**
     * code : 200
     * msg : ok
     * data : {"user_id":2,"yo_id":4006,"file_path":"file_path","file_type":0,"file_desc":"file_desc","open":1,"valid":1,"topics":[{"topic_id":2,"topic":"话题2"},{"topic_id":3,"topic":"话题3"},{"topic_id":4,"topic":"话题4"}],"channels":[{"channel_id":4,"channel":"摆个pose"},{"channel_id":5,"channel":"主题公园"},{"channel_id":6,"channel":"地标打卡"}],"position_name":"名字1","position_areas":"名字2,a","position_address":"名字3","lng":"lng","lat":"lat","filter_id":"filter_id"}
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
         * yo_id : 4006
         * file_path : file_path
         * file_type : 0
         * file_desc : file_desc
         * open : 1
         * valid : 1
         * topics : [{"topic_id":2,"topic":"话题2"},{"topic_id":3,"topic":"话题3"},{"topic_id":4,"topic":"话题4"}]
         * channels : [{"channel_id":4,"channel":"摆个pose"},{"channel_id":5,"channel":"主题公园"},{"channel_id":6,"channel":"地标打卡"}]
         * position_name : 名字1
         * position_areas : 名字2,a
         * position_address : 名字3
         * lng : lng
         * lat : lat
         * filter_id : filter_id
         */

        private int user_id;
        private int                yo_id;
        private String             file_path;
        private int                file_type;
        private String             file_desc;
        private int                open;
        private int                valid;
        private String             position_name;
        private String             position_areas;
        private String             position_address;
        private String             position_city;
        private String             lng;
        private String             lat;
        private String             filter_id;
        private List<TopicsBean>   topics;
        private List<ChannelsBean> channels;

        public  String getPosition_city() {
            return position_city;
        }

        public  void setPosition_city(String position_city) {
            this.position_city = position_city;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getYo_id() {
            return yo_id;
        }

        public void setYo_id(int yo_id) {
            this.yo_id = yo_id;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }

        public int getFile_type() {
            return file_type;
        }

        public void setFile_type(int file_type) {
            this.file_type = file_type;
        }

        public String getFile_desc() {
            return file_desc;
        }

        public void setFile_desc(String file_desc) {
            this.file_desc = file_desc;
        }

        public int getOpen() {
            return open;
        }

        public void setOpen(int open) {
            this.open = open;
        }

        public int getValid() {
            return valid;
        }

        public void setValid(int valid) {
            this.valid = valid;
        }

        public String getPosition_name() {
            return position_name;
        }

        public void setPosition_name(String position_name) {
            this.position_name = position_name;
        }

        public String getPosition_areas() {
            return position_areas;
        }

        public void setPosition_areas(String position_areas) {
            this.position_areas = position_areas;
        }

        public String getPosition_address() {
            return position_address;
        }

        public void setPosition_address(String position_address) {
            this.position_address = position_address;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getFilter_id() {
            return filter_id;
        }

        public void setFilter_id(String filter_id) {
            this.filter_id = filter_id;
        }

        public List<TopicsBean> getTopics() {
            return topics;
        }

        public void setTopics(List<TopicsBean> topics) {
            this.topics = topics;
        }

        public List<ChannelsBean> getChannels() {
            return channels;
        }

        public void setChannels(List<ChannelsBean> channels) {
            this.channels = channels;
        }

        public static class TopicsBean {
            /**
             * topic_id : 2
             * topic : 话题2
             */

            private int topic_id;
            private String topic;

            public int getTopic_id() {
                return topic_id;
            }

            public void setTopic_id(int topic_id) {
                this.topic_id = topic_id;
            }

            public String getTopic() {
                return topic;
            }

            public void setTopic(String topic) {
                this.topic = topic;
            }
        }

        public static class ChannelsBean {
            /**
             * channel_id : 4
             * channel : 摆个pose
             */

            private int channel_id;
            private String channel;

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
