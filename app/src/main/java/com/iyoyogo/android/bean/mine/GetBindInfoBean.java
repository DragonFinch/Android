package com.iyoyogo.android.bean.mine;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class GetBindInfoBean extends BaseBean {

    /**
     * data : {"user_id":"80025","user_phone":"15811473242","user_wx":[{"type":1,"openid":"oH2i_0Tf1KRaIME9lJVOYvdoWZ0E","nickname":"乖乖打怪"}],"user_qq":[{"type":2,"openid":"DD7423BA713A67C74636A509FA280101","nickname":"摇一摇"}],"user_wb":[{"type":1,"openid":"oH2i_0Tf1KRaIME9lJVOYvdoWZ0E","nickname":"乖乖打怪"}]}
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
         * user_id : 80025
         * user_phone : 15811473242
         * user_wx : [{"type":1,"openid":"oH2i_0Tf1KRaIME9lJVOYvdoWZ0E","nickname":"乖乖打怪"}]
         * user_qq : [{"type":2,"openid":"DD7423BA713A67C74636A509FA280101","nickname":"摇一摇"}]
         * user_wb : [{"type":1,"openid":"oH2i_0Tf1KRaIME9lJVOYvdoWZ0E","nickname":"乖乖打怪"}]
         */

        private String user_id;
        private String user_phone;
        private List<UserWxBean> user_wx;
        private List<UserQqBean> user_qq;
        private List<UserWbBean> user_wb;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public List<UserWxBean> getUser_wx() {
            return user_wx;
        }

        public void setUser_wx(List<UserWxBean> user_wx) {
            this.user_wx = user_wx;
        }

        public List<UserQqBean> getUser_qq() {
            return user_qq;
        }

        public void setUser_qq(List<UserQqBean> user_qq) {
            this.user_qq = user_qq;
        }

        public List<UserWbBean> getUser_wb() {
            return user_wb;
        }

        public void setUser_wb(List<UserWbBean> user_wb) {
            this.user_wb = user_wb;
        }

        public static class UserWxBean {
            /**
             * type : 1
             * openid : oH2i_0Tf1KRaIME9lJVOYvdoWZ0E
             * nickname : 乖乖打怪
             */

            private int type;
            private String openid;
            private String nickname;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }

        public static class UserQqBean {
            /**
             * type : 2
             * openid : DD7423BA713A67C74636A509FA280101
             * nickname : 摇一摇
             */

            private int type;
            private String openid;
            private String nickname;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }

        public static class UserWbBean {
            /**
             * type : 1
             * openid : oH2i_0Tf1KRaIME9lJVOYvdoWZ0E
             * nickname : 乖乖打怪
             */

            private int type;
            private String openid;
            private String nickname;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }
    }
}
