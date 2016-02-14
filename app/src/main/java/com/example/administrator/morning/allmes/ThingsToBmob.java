package com.example.administrator.morning.allmes;

import android.view.View;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/2/14.
 */
public class ThingsToBmob {

    private String path ="/sdcard/Morning/pic/head.png";
    private String QQ_b;
    private String name_b;
    private View v_b;
    private String things_b;

    public boolean sendmsg(String things, String QQ, View v)
    {
        things_b=things;
        QQ_b=QQ;
        v_b=v;
        send_things(v_b);
        return true;
    }
    public void send_things(View v)
    {
        final CardMark things = new CardMark();
        things.setContent(things_b);
        things.setQq_number(QQ_b);
        things.save(v_b.getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                System.out.print("添加数据成功，返回objectId为："+things.getObjectId());
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                System.out.print("创建数据失败：" + msg);
            }
        });
    }
}
