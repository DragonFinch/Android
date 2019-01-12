package com.iyoyogo.android.bean;

import java.util.List;

/**
 * @author zhuhui
 * @date 2019/1/12
 * @description
 */
public class SameBean extends BaseBean {


    /**
     * data : {"list":[{"yo_id":5120,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000120.jpg?x-oss-process=image/resize,w_400","user_id":"60093","user_nickname":"少年英雄小哪吒","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000063.jpg?x-oss-process=image/resize,w_50"},{"yo_id":5376,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000010142.jpg?x-oss-process=image/resize,w_400"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * yo_id : 5120
             * file_type : 1
             * file_path : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000120.jpg?x-oss-process=image/resize,w_400
             * user_id : 60093
             * user_nickname : 少年英雄小哪吒
             * user_logo : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000063.jpg?x-oss-process=image/resize,w_50
             */

            private int yo_id;
            private int    file_type;
            private String file_path;
            private String user_id;
            private String user_nickname;
            private String user_logo;

            public int getYo_id() {
                return yo_id;
            }

            public void setYo_id(int yo_id) {
                this.yo_id = yo_id;
            }

            public int getFile_type() {
                return file_type;
            }

            public void setFile_type(int file_type) {
                this.file_type = file_type;
            }

            public String getFile_path() {
                return file_path;
            }

            public void setFile_path(String file_path) {
                this.file_path = file_path;
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
        }
    }
}
