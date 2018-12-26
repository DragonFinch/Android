package com.iyoyogo.android.bean.collection;

/**
 * 封装一个联系人信息的类
 * name 名字
 * phone 手机号
 */
public class AddressBookPhoneInfoBean {
    private String name;
    private String phone;

    public AddressBookPhoneInfoBean(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
