package com.iyoyogo.android.bean.collection;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class CollectionFolderBean  extends BaseBean {


    /**
     * data : {"list":[{"id":2,"user_id":2,"name":"b","open":2},{"id":1,"user_id":2,"name":"a","open":1}]}
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
             * id : 2
             * user_id : 2
             * name : b
             * open : 2
             */

            private int folder_id;
            private int user_id;
            private String name;
            private int open;

            public int getId() {
                return folder_id;
            }

            public void setId(int id) {
                this.folder_id = id;
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
