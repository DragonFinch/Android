package com.iyoyogo.android.bean.collection;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class CollectionFolderContentBean extends BaseBean {

    /**
     * data : {"list":[{"record_id":3,"yo_id":3,"yo_type":1,"file_type":2,"file_path":"1","file_desc":"3"},{"record_id":2,"yo_id":2,"yo_type":1,"file_type":1,"file_path":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/z3ntQRCbSn.jpg","file_desc":""}]}
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
             * record_id : 3
             * yo_id : 3
             * yo_type : 1
             * file_type : 2
             * file_path : 1
             * file_desc : 3
             */

            private int record_id;
            private int yo_id;
            private int yo_type;
            private int file_type;
            private String file_path;
            private String file_desc;
            public boolean isSelect;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean isSelect) {
                this.isSelect = isSelect;
            }
            public int getRecord_id() {
                return record_id;
            }

            public void setRecord_id(int record_id) {
                this.record_id = record_id;
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

            public String getFile_desc() {
                return file_desc;
            }

            public void setFile_desc(String file_desc) {
                this.file_desc = file_desc;
            }
        }
    }
}
