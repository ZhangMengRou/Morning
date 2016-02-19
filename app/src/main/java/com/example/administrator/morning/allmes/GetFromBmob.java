package com.example.administrator.morning.allmes;

import android.graphics.Bitmap;
import android.view.View;

import com.example.administrator.morning.aboutuser.mUser;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/2/18.
 */
public class GetFromBmob {

    private String path = "/sdcard/Morning/pic/head.png";
    private String QQ_b;
    private mUser user_b = new mUser();
    private View v_b;
    private String things_b;
    public Boolean judge = true;
    private Bitmap bitmap;

    public boolean connect_mes(String QQ, final View v, String things) {
        things_b = things;
        QQ_b = QQ;
        v_b = v;
        BmobQuery<mUser> query = new BmobQuery<mUser>();

        query.addWhereEqualTo("qq_number", QQ);

        query.setLimit(1);

        query.findObjects(v.getContext(), new FindListener<mUser>() {
            @Override
            public void onSuccess(List<mUser> object) {

                judge = true;
                new ThingsToBmob().sendmsg(things_b, object.get(0), v);

            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                //toast("查询失败："+msg);
                judge = false;
            }
        });
        return judge;
    }


}
