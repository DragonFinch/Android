package com.iyoyogo.android.bean.collection;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class MineCollectionBean extends BaseBean {

    /**
     * data : {"tree":[{"folder_id":5,"user_id":2,"name":"bbb","open":2,"count_record":7,"record_list":[{"folder_id":5,"yo_id":1,"yo_type":1,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000013028.jpg","file_desc":"3787"}]},{"folder_id":4,"user_id":2,"name":"aaa","open":1,"count_record":8,"record_list":[{"folder_id":4,"yo_id":3,"yo_type":1,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000009011.jpg","file_desc":"﻿镇远古城美景"},{"folder_id":4,"yo_id":2,"yo_type":1,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000009006.jpg","file_desc":"﻿小镇悠闲生活"}]}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<TreeBean> tree;

        public List<TreeBean> getTree() {
            return tree;
        }

        public void setTree(List<TreeBean> tree) {
            this.tree = tree;
        }

        public static class TreeBean {
            /**
             * folder_id : 5
             * user_id : 2
             * name : bbb
             * open : 2
             * count_record : 7
             * record_list : [{"folder_id":5,"yo_id":1,"yo_type":1,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000013028.jpg","file_desc":"3787"}]
             */

            private int folder_id;
            private String user_id;
            private String name;
            private int open;
            private int count_record;
            private List<RecordListBean> record_list;
            public boolean isSelect;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean isSelect) {
                this.isSelect = isSelect;
            }
            public int getFolder_id() {
                return folder_id;
            }

            public void setFolder_id(int folder_id) {
                this.folder_id = folder_id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
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

            public int getCount_record() {
                return count_record;
            }

            public void setCount_record(int count_record) {
                this.count_record = count_record;
            }

            public List<RecordListBean> getRecord_list() {
                return record_list;
            }

            public void setRecord_list(List<RecordListBean> record_list) {
                this.record_list = record_list;
            }

            public static class RecordListBean {
                /**
                 * folder_id : 5
                 * yo_id : 1
                 * yo_type : 1
                 * file_type : 1
                 * file_path : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000013028.jpg
                 * file_desc : 3787
                 */

                private int folder_id;
                private int yo_id;
                private int yo_type;
                private int file_type;
                private String file_path;
                private String file_desc;

                public int getFolder_id() {
                    return folder_id;
                }

                public void setFolder_id(int folder_id) {
                    this.folder_id = folder_id;
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
}
