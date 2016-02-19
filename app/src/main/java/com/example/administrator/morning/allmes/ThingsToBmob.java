package com.example.administrator.morning.allmes;

import android.view.View;

import com.example.administrator.morning.aboutuser.mUser;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/2/14.
 */
public class ThingsToBmob {


    private mUser v_user;
    private View v_b;
    private String things_b;

    public boolean sendmsg(String things, mUser user, View v) {
        things_b = things;
        v_user = user;
        v_b = v;
        send_things(v_b);
        return true;
    }

    public void send_things(View v) {
        final CardMark things = new CardMark();
        things.setContent(things_b);
        things.setUser(v_user);
        things.save(v_b.getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                System.out.print("添加数据成功，返回objectId为：" + things.getObjectId());
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                System.out.print("创建数据失败：" + msg);
            }
        });
    }
}
