package com.iyoyogo.android.bean.yoji.publish;

import com.iyoyogo.android.bean.BaseBean;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PublishYoJiBean extends BaseBean {

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
         * yo_id : 4002
         * logo : logo
         * title : title
         * desc : desc
         * cost : cost
         * open : 1
         * valid : 2
         * topics : [{"topic_id":2,"topic":"话题2"},{"topic_id":3,"topic":"话题3"},{"topic_id":4,"topic":"话题4"}]
         * channels : [{"channel_id":4,"channel":"摆个pose"},{"channel_id":5,"channel":"主题公园"},{"channel_id":6,"channel":"地标打卡"}]
         * list : [{"logos":["2","3"],"start_date":"2018-10-10","end_date":"2018-10-11","position_name":"名字1","position_areas":"名字2","position_address":"名字3","labels":[{"label_id":1,"type":1,"label":"地理位置"},{"label_id":2,"type":1,"label":"测试1111"}]},{"logos":["1","2"],"start_date":"2018-10-10","end_date":"2018-10-11","position_name":"1","position_areas":"2","position_address":"3","labels":[{"label_id":1,"type":1,"label":"地理位置"},{"label_id":2,"type":1,"label":"测试1111"}]}]
         */

        private int                user_id;
        private int                yo_id;
        private String             logo;
        private String             title;
        private String             desc;
        private String             cost;
        private int                open;
        private int                valid;
        private List<TopicsBean>   topics;
        private List<ChannelsBean> channels;
        private List<ListBean>     list;

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

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class TopicsBean {
            /**
             * topic_id : 2
             * topic : 话题2
             */

            private int    topic_id;
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

            private int    channel_id;
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

        public static class ListBean {
            /**
             * logos : ["2","3"]
             * start_date : 2018-10-10
             * end_date : 2018-10-11
             * position_name : 名字1
             * position_areas : 名字2
             * position_address : 名字3
             * labels : [{"label_id":1,"type":1,"label":"地理位置"},{"label_id":2,"type":1,"label":"测试1111"}]
             */

            private String           start_date;
            private String           end_date;
            private String           position_name;
            private String           position_areas;
            private String           position_address;
            private String           lat;
            private String           lng;
            private List<String>     logos;
            private List<LabelsBean> labels;
            private List<LocalMedia> localMedia;

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public List<LocalMedia> getLocalMedia() {
                return localMedia;
            }

            public void setLocalMedia(List<LocalMedia> localMedia) {
                this.localMedia = localMedia;
            }

            public String getStart_date() {
                return start_date;
            }

            public void setStart_date(String start_date) {
                this.start_date = start_date;
            }

            public String getEnd_date() {
                return end_date;
            }

            public void setEnd_date(String end_date) {
                this.end_date = end_date;
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

            public List<String> getLogos() {
                if (logos == null) {
                    logos = new ArrayList<>();
                }
                return logos;
            }

            public void setLogos(List<String> logos) {
                this.logos = logos;
            }

            public List<LabelsBean> getLabels() {
                return labels;
            }

            public void setLabels(List<LabelsBean> labels) {
                this.labels = labels;
            }

            public static class LabelsBean implements Serializable {
                /**
                 * label_id : 1
                 * type : 1
                 * label : 地理位置
                 */

                private int    label_id;
                private int    type;
                private String label;

                public int getLabel_id() {
                    return label_id;
                }

                public void setLabel_id(int label_id) {
                    this.label_id = label_id;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getLabel() {
                    return label;
                }

                public void setLabel(String label) {
                    this.label = label;
                }
            }
        }
    }
}
