package com.example.administrator.morning.allmes;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/2/7.
 */
public class CardMark extends BmobObject{
    private String qq_number;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String con) {
        this.content = con;
    }

    public String getQq_number() {
        return qq_number;
    }

    public void setQq_number(String qq_numbere) {
        this.qq_number = qq_numbere;
    }


}
