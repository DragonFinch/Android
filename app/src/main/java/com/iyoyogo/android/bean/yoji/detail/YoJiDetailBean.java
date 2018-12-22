package com.iyoyogo.android.bean.yoji.detail;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class YoJiDetailBean  extends BaseBean {

    /**
     * data : {"user_id":67765,"user_nickname":"nrgy","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200007236.jpg?x-oss-process=image/resize,w_50","user_level":1,"partner_type":0,"yo_id":4319,"logo":"http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/20/6xZcwwNkGi.jpg?x-oss-process=image/resize,w_1200","title":"1","desc":"哈哈哈","cost":"1","open":1,"valid":1,"topics":[],"channels":[],"list":[{"id":2054,"yo_id":4319,"logos":["http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/20/tSKfJN8CxJ.png?x-oss-process=image/resize,w_400"],"start_date":"2018-12-06","end_date":"2018-12-07","position_name":"T3","position_areas":"中国,北京,朝阳","position_address":"望京soho","lng":"116.454723","lat":"39.877593","labels":[{"id":2,"user_id":1,"type":1,"label":"测试1111","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/chKyisYBZD.jpg","sort":0,"del":0,"create_time":"2018-10-30 15:00:26","update_time":"2018-10-30 15:17:57"},{"id":9,"user_id":2,"type":1,"label":"旅游胜地1","logo":"http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/8/2ikQfYjpsW.jpg","sort":0,"del":0,"create_time":"2018-12-08 10:15:41","update_time":"2018-12-08 10:15:59"},{"id":22,"user_id":80003,"type":1,"label":"专属","logo":"","sort":0,"del":0,"create_time":"2018-12-17 21:57:47","update_time":"0000-00-00 00:00:00"}],"count_dates":2},{"id":2051,"yo_id":4319,"logos":["http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/20/e75wzTF5Ad.jpg?x-oss-process=image/resize,w_400","http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/20/6nGZKQysFG.jpg?x-oss-process=image/resize,w_400"],"start_date":"2018-12-27","end_date":"2018-12-28","position_name":"天安门","position_areas":"中国,北京,大兴","position_address":"金星西路","lng":"116.454723","lat":"39.877593","labels":[{"id":2,"user_id":1,"type":1,"label":"测试1111","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/chKyisYBZD.jpg","sort":0,"del":0,"create_time":"2018-10-30 15:00:26","update_time":"2018-10-30 15:17:57"},{"id":3,"user_id":1,"type":1,"label":"cehsi1","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/Q6QGs3FWGX.jpg","sort":0,"del":0,"create_time":"2018-10-30 15:17:49","update_time":"0000-00-00 00:00:00"},{"id":4,"user_id":1,"type":1,"label":"123aaa111","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/6C7Q22FFmi.jpg","sort":0,"del":0,"create_time":"2018-10-30 18:04:36","update_time":"2018-10-30 18:04:43"}],"count_dates":2},{"id":2052,"yo_id":4319,"logos":["http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/20/sJEh2kWstD.png?x-oss-process=image/resize,w_400","http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/20/TmQeYTMcBN.png?x-oss-process=image/resize,w_400"],"start_date":"2018-12-27","end_date":"2018-12-28","position_name":"天安门","position_areas":"中国,北京,大兴","position_address":"金星西路","lng":"116.454723","lat":"39.877593","labels":[{"id":2,"user_id":1,"type":1,"label":"测试1111","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/chKyisYBZD.jpg","sort":0,"del":0,"create_time":"2018-10-30 15:00:26","update_time":"2018-10-30 15:17:57"},{"id":3,"user_id":1,"type":1,"label":"cehsi1","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/Q6QGs3FWGX.jpg","sort":0,"del":0,"create_time":"2018-10-30 15:17:49","update_time":"0000-00-00 00:00:00"},{"id":4,"user_id":1,"type":1,"label":"123aaa111","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/6C7Q22FFmi.jpg","sort":0,"del":0,"create_time":"2018-10-30 18:04:36","update_time":"2018-10-30 18:04:43"}],"count_dates":2}],"p_start":"T3","p_end":"天安门","count_dates":4,"count_view":7,"count_praise":1,"count_comment":1,"count_collect":1,"create_time":"昨天17:04","is_my_praise":0,"is_my_attention":0,"is_my_collect":0,"count_yox":10,"count_yoj":7}
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
         * user_id : 67765
         * user_nickname : nrgy
         * user_logo : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200007236.jpg?x-oss-process=image/resize,w_50
         * user_level : 1
         * partner_type : 0
         * yo_id : 4319
         * logo : http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/20/6xZcwwNkGi.jpg?x-oss-process=image/resize,w_1200
         * title : 1
         * desc : 哈哈哈
         * cost : 1
         * open : 1
         * valid : 1
         * topics : []
         * channels : []
         * list : [{"id":2054,"yo_id":4319,"logos":["http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/20/tSKfJN8CxJ.png?x-oss-process=image/resize,w_400"],"start_date":"2018-12-06","end_date":"2018-12-07","position_name":"T3","position_areas":"中国,北京,朝阳","position_address":"望京soho","lng":"116.454723","lat":"39.877593","labels":[{"id":2,"user_id":1,"type":1,"label":"测试1111","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/chKyisYBZD.jpg","sort":0,"del":0,"create_time":"2018-10-30 15:00:26","update_time":"2018-10-30 15:17:57"},{"id":9,"user_id":2,"type":1,"label":"旅游胜地1","logo":"http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/8/2ikQfYjpsW.jpg","sort":0,"del":0,"create_time":"2018-12-08 10:15:41","update_time":"2018-12-08 10:15:59"},{"id":22,"user_id":80003,"type":1,"label":"专属","logo":"","sort":0,"del":0,"create_time":"2018-12-17 21:57:47","update_time":"0000-00-00 00:00:00"}],"count_dates":2},{"id":2051,"yo_id":4319,"logos":["http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/20/e75wzTF5Ad.jpg?x-oss-process=image/resize,w_400","http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/20/6nGZKQysFG.jpg?x-oss-process=image/resize,w_400"],"start_date":"2018-12-27","end_date":"2018-12-28","position_name":"天安门","position_areas":"中国,北京,大兴","position_address":"金星西路","lng":"116.454723","lat":"39.877593","labels":[{"id":2,"user_id":1,"type":1,"label":"测试1111","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/chKyisYBZD.jpg","sort":0,"del":0,"create_time":"2018-10-30 15:00:26","update_time":"2018-10-30 15:17:57"},{"id":3,"user_id":1,"type":1,"label":"cehsi1","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/Q6QGs3FWGX.jpg","sort":0,"del":0,"create_time":"2018-10-30 15:17:49","update_time":"0000-00-00 00:00:00"},{"id":4,"user_id":1,"type":1,"label":"123aaa111","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/6C7Q22FFmi.jpg","sort":0,"del":0,"create_time":"2018-10-30 18:04:36","update_time":"2018-10-30 18:04:43"}],"count_dates":2},{"id":2052,"yo_id":4319,"logos":["http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/20/sJEh2kWstD.png?x-oss-process=image/resize,w_400","http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/20/TmQeYTMcBN.png?x-oss-process=image/resize,w_400"],"start_date":"2018-12-27","end_date":"2018-12-28","position_name":"天安门","position_areas":"中国,北京,大兴","position_address":"金星西路","lng":"116.454723","lat":"39.877593","labels":[{"id":2,"user_id":1,"type":1,"label":"测试1111","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/chKyisYBZD.jpg","sort":0,"del":0,"create_time":"2018-10-30 15:00:26","update_time":"2018-10-30 15:17:57"},{"id":3,"user_id":1,"type":1,"label":"cehsi1","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/Q6QGs3FWGX.jpg","sort":0,"del":0,"create_time":"2018-10-30 15:17:49","update_time":"0000-00-00 00:00:00"},{"id":4,"user_id":1,"type":1,"label":"123aaa111","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/6C7Q22FFmi.jpg","sort":0,"del":0,"create_time":"2018-10-30 18:04:36","update_time":"2018-10-30 18:04:43"}],"count_dates":2}]
         * p_start : T3
         * p_end : 天安门
         * count_dates : 4
         * count_view : 7
         * count_praise : 1
         * count_comment : 1
         * count_collect : 1
         * create_time : 昨天17:04
         * is_my_praise : 0
         * is_my_attention : 0
         * is_my_collect : 0
         * count_yox : 10
         * count_yoj : 7
         */

        private int user_id;
        private String user_nickname;
        private String user_logo;
        private int user_level;
        private int partner_type;
        private int yo_id;
        private String logo;
        private String title;
        private String desc;
        private String cost;
        private int open;
        private int valid;
        private String p_start;
        private String p_end;
        private int count_dates;
        private int count_view;
        private int count_praise;
        private int count_comment;
        private int count_collect;
        private String create_time;
        private int is_my_praise;
        private int is_my_attention;
        private int is_my_collect;
        private int count_yox;
        private int count_yoj;
        private List<?> topics;
        private List<?> channels;
        private List<ListBean> list;

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

        public String getP_start() {
            return p_start;
        }

        public void setP_start(String p_start) {
            this.p_start = p_start;
        }

        public String getP_end() {
            return p_end;
        }

        public void setP_end(String p_end) {
            this.p_end = p_end;
        }

        public int getCount_dates() {
            return count_dates;
        }

        public void setCount_dates(int count_dates) {
            this.count_dates = count_dates;
        }

        public int getCount_view() {
            return count_view;
        }

        public void setCount_view(int count_view) {
            this.count_view = count_view;
        }

        public int getCount_praise() {
            return count_praise;
        }

        public void setCount_praise(int count_praise) {
            this.count_praise = count_praise;
        }

        public int getCount_comment() {
            return count_comment;
        }

        public void setCount_comment(int count_comment) {
            this.count_comment = count_comment;
        }

        public int getCount_collect() {
            return count_collect;
        }

        public void setCount_collect(int count_collect) {
            this.count_collect = count_collect;
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

        public int getIs_my_collect() {
            return is_my_collect;
        }

        public void setIs_my_collect(int is_my_collect) {
            this.is_my_collect = is_my_collect;
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

        public List<?> getTopics() {
            return topics;
        }

        public void setTopics(List<?> topics) {
            this.topics = topics;
        }

        public List<?> getChannels() {
            return channels;
        }

        public void setChannels(List<?> channels) {
            this.channels = channels;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 2054
             * yo_id : 4319
             * logos : ["http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/20/tSKfJN8CxJ.png?x-oss-process=image/resize,w_400"]
             * start_date : 2018-12-06
             * end_date : 2018-12-07
             * position_name : T3
             * position_areas : 中国,北京,朝阳
             * position_address : 望京soho
             * lng : 116.454723
             * lat : 39.877593
             * labels : [{"id":2,"user_id":1,"type":1,"label":"测试1111","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/chKyisYBZD.jpg","sort":0,"del":0,"create_time":"2018-10-30 15:00:26","update_time":"2018-10-30 15:17:57"},{"id":9,"user_id":2,"type":1,"label":"旅游胜地1","logo":"http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/8/2ikQfYjpsW.jpg","sort":0,"del":0,"create_time":"2018-12-08 10:15:41","update_time":"2018-12-08 10:15:59"},{"id":22,"user_id":80003,"type":1,"label":"专属","logo":"","sort":0,"del":0,"create_time":"2018-12-17 21:57:47","update_time":"0000-00-00 00:00:00"}]
             * count_dates : 2
             */

            private int id;
            private int yo_id;
            private String start_date;
            private String end_date;
            private String position_name;
            private String position_areas;
            private String position_address;
            private String lng;
            private String lat;
            private int count_dates;
            private List<String> logos;
            private List<LabelsBean> labels;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getYo_id() {
                return yo_id;
            }

            public void setYo_id(int yo_id) {
                this.yo_id = yo_id;
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

            public int getCount_dates() {
                return count_dates;
            }

            public void setCount_dates(int count_dates) {
                this.count_dates = count_dates;
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
                 * id : 2
                 * user_id : 1
                 * type : 1
                 * label : 测试1111
                 * logo : http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/chKyisYBZD.jpg
                 * sort : 0
                 * del : 0
                 * create_time : 2018-10-30 15:00:26
                 * update_time : 2018-10-30 15:17:57
                 */

                private int id;
                private int user_id;
                private int type;
                private String label;
                private String logo;
                private int sort;
                private int del;
                private String create_time;
                private String update_time;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
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

                public String getLogo() {
                    return logo;
                }

                public void setLogo(String logo) {
                    this.logo = logo;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                public int getDel() {
                    return del;
                }

                public void setDel(int del) {
                    this.del = del;
                }

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public String getUpdate_time() {
                    return update_time;
                }

                public void setUpdate_time(String update_time) {
                    this.update_time = update_time;
                }
            }
        }
    }
}
