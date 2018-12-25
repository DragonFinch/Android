package com.iyoyogo.android.bean.collection;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class CollectionFolderBean  extends BaseBean {

    /**
     * data : {"list":[{"folder_id":55,"user_id":80002,"name":"我","open":1},{"folder_id":15,"user_id":80002,"name":"我","open":1},{"folder_id":14,"user_id":80002,"name":"默认收藏","open":1},{"folder_id":13,"user_id":80002,"name":"我喜欢","open":1},{"folder_id":12,"user_id":80002,"name":"啦啦","open":1},{"folder_id":11,"user_id":80002,"name":"啦啦啦","open":2},{"folder_id":10,"user_id":80002,"name":"啦啦啦","open":1},{"folder_id":9,"user_id":80002,"name":"金雪晗","open":1},{"folder_id":8,"user_id":80002,"name":"龙雀","open":1}]}
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
             * folder_id : 55
             * user_id : 80002
             * name : 我
             * open : 1
             */

            private int folder_id;
            private int user_id;
            private String name;
            private int open;

            public int getFolder_id() {
                return folder_id;
            }

            public void setFolder_id(int folder_id) {
                this.folder_id = folder_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getOpen() {
                return open;
            }

            public void setOpen(int open) {
                this.open = open;
            }
        }
    }
}
