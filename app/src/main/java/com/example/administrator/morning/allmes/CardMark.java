package com.example.administrator.morning.allmes;

import com.example.administrator.morning.aboutuser.mUser;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/2/7.
 */
public class CardMark extends BmobObject{
    private mUser user;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String con) {
        this.content = con;
    }

    public mUser getUser() {
        return user;
    }

    public void setUser(mUser user) {
        this.user = user;
    }


}
