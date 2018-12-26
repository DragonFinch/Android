package com.iyoyogo.android.bean.collection;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

/***
 * 获取通讯录中 关注情况
 */
public class AddressBookBean extends BaseBean {

    /**
     * data : {"list":[{"user_id":80004,"user_phone":"18233382571","user_nickname":"月帅?x-oss-process=image/resize,w_50","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006963.jpg","status":0},{"user_id":2,"user_phone":"18233382572","user_nickname":"222?x-oss-process=image/resize,w_50","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006964.jpg","status":0},{"user_id":0,"user_phone":"","user_nickname":"小9","user_logo":"","status":-1}]}
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
             * user_id : 80004
             * user_phone : 18233382571
             * user_nickname : 月帅?x-oss-process=image/resize,w_50
             * user_logo : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006963.jpg
             * status : 0
             */

            private int user_id;
            private String user_phone;
            private String user_nickname;
            private String user_logo;
            private int status;

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUser_phone() {
                return user_phone;
            }

            public void setUser_phone(String user_phone) {
                this.user_phone = user_phone;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
