package com.example.administrator.morning.aboutuser;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/1/28.
 */
public class mUser extends BmobObject {

    private String qq_number;
    private String username;
    private BmobFile ic_image;

    public String getUser_name() {
        return username;
    }

    public void setUser_name(String name) {
        this.username = name;
    }

    public String getQq_number() {
        return qq_number;
    }

    public void setQq_number(String qq_numbere) {
        this.qq_number = qq_numbere;
    }

    public BmobFile getPic() {
        return ic_image;
    }

    public void setPic(BmobFile pic) {
        this.ic_image = pic;
    }
}
