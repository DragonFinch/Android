package com.iyoyogo.android.bean.yoji.detail;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class YoJiDetailBean  extends BaseBean {

    /**
     * data : {"user_id":2,"yo_id":4001,"logo":"logo","title":"title","desc":"desc","cost":"cost","open":1,"valid":1,"topics":[{"topic_id":2,"topic":"话题2"},{"topic_id":3,"topic":"话题3"},{"topic_id":4,"topic":"话题4"}],"channels":[{"channel_id":4,"channel":"摆个pose"},{"channel_id":5,"channel":"主题公园"},{"channel_id":6,"channel":"地标打卡"}],"list":[{"logos":["2","3"],"start_date":"2018-10-10","end_date":"2018-10-11","position_name":"名字1","position_areas":"名字2","position_address":"名字3","labels":[{"label_id":1,"type":1,"label":"地理位置"},{"label_id":2,"type":1,"label":"测试1111"}]},{"logos":["1","2"],"start_date":"2018-10-10","end_date":"2018-10-11","position_name":"1","position_areas":"2","position_address":"3","labels":[{"label_id":1,"type":1,"label":"地理位置"},{"label_id":2,"type":1,"label":"测试1111"}]}],"count_view":19,"create_time":"5小时前","is_my_praise":0,"is_my_attention":0,"count_yox":5,"count_yoj":3}
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
         * yo_id : 4001
         * logo : logo
         * title : title
         * desc : desc
         * cost : cost
         * open : 1
         * valid : 1
         * topics : [{"topic_id":2,"topic":"话题2"},{"topic_id":3,"topic":"话题3"},{"topic_id":4,"topic":"话题4"}]
         * channels : [{"channel_id":4,"channel":"摆个pose"},{"channel_id":5,"channel":"主题公园"},{"channel_id":6,"channel":"地标打卡"}]
         * list : [{"logos":["2","3"],"start_date":"2018-10-10","end_date":"2018-10-11","position_name":"名字1","position_areas":"名字2","position_address":"名字3","labels":[{"label_id":1,"type":1,"label":"地理位置"},{"label_id":2,"type":1,"label":"测试1111"}]},{"logos":["1","2"],"start_date":"2018-10-10","end_date":"2018-10-11","position_name":"1","position_areas":"2","position_address":"3","labels":[{"label_id":1,"type":1,"label":"地理位置"},{"label_id":2,"type":1,"label":"测试1111"}]}]
         * count_view : 19
         * create_time : 5小时前
         * is_my_praise : 0
         * is_my_attention : 0
         * count_yox : 5
         * count_yoj : 3
         */
/*
* "user_nickname": "nrgy",
        "user_logo": "https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200007236.jpg",
        "user_level": 1,
        "partner_type": 0,
* */
        private String user_nickname;
        private String user_logo;
        private int user_level;
        private int partner_type;

        private int user_id;
        private int yo_id;
        private String logo;
        private String title;
        private String desc;
        private String cost;
        private int open;
        private int valid;
        private int count_view;
        private String create_time;
        private int is_my_praise;
        private int is_my_attention;
        private int count_yox;
        private int count_yoj;
        private List<TopicsBean> topics;
        private List<ChannelsBean> channels;
        private List<ListBean> list;

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

        public int getPartner_type() {
            return partner_type;
        }

        public void setPartner_type(int partner_type) {
            this.partner_type = partner_type;
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

        public int getCount_view() {
            return count_view;
        }

        public void setCount_view(int count_view) {
            this.count_view = count_view;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getIs_my_praise() {
            return is_my_praise;
        }

        public void setIs_my_praise(int is_my_praise) {
            this.is_my_praise = is_my_praise;
        }

        public int getIs_my_attention() {
            return is_my_attention;
        }

        public void setIs_my_attention(int is_my_attention) {
            this.is_my_attention = is_my_attention;
        }

        public int getCount_yox() {
            return count_yox;
        }

        public void setCount_yox(int count_yox) {
            this.count_yox = count_yox;
        }

        public int getCount_yoj() {
            return count_yoj;
        }

        public void setCount_yoj(int count_yoj) {
            this.count_yoj = count_yoj;
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

            private String start_date;
            private String end_date;
            private String position_name;
            private String position_areas;
            private String position_address;
            private List<String> logos;
            private List<LabelsBean> labels;

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

            public static class LabelsBean {
                /**
                 * label_id : 1
                 * type : 1
                 * label : 地理位置
                 */

                private int label_id;
                private int type;
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
