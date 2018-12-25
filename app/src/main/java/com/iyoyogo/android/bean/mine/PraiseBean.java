package com.iyoyogo.android.bean.mine;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class PraiseBean extends BaseBean {

    /**
     * data : {"list":[{"praise_id":77445,"user_id":2,"yo_id":2,"yo_type":1,"file_type":2,"file_path":"http://a-image-demo.oss-cn-qingdao.aliyuncs.com/demo.mp4?x-oss-process=video/snapshot,t_10000,w_1000,f_jpg,m_fast"},{"praise_id":27096,"user_id":2,"yo_id":1277,"yo_type":1,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000003999.jpg?x-oss-process=image/resize,w_200"}]}
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
             * praise_id : 77445
             * user_id : 2
             * yo_id : 2
             * yo_type : 1
             * file_type : 2
             * file_path : http://a-image-demo.oss-cn-qingdao.aliyuncs.com/demo.mp4?x-oss-process=video/snapshot,t_10000,w_1000,f_jpg,m_fast
             */

            private int praise_id;
            private int user_id;
            private int yo_id;
            private int yo_type;
            private int file_type;
            private String file_path;

            public int getPraise_id() {
                return praise_id;
            }

            public void setPraise_id(int praise_id) {
                this.praise_id = praise_id;
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

            public int getYo_type() {
                return yo_type;
            }

            public void setYo_type(int yo_type) {
                this.yo_type = yo_type;
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
        }
    }
}
