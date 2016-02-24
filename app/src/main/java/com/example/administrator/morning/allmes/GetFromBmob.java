package com.example.administrator.morning.allmes;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.example.administrator.morning.aboutuser.mUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
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
    public List<CardMark> today_object;
    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;

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

    public boolean getcardmes( final View v) {
        BmobQuery<CardMark> query = new BmobQuery<CardMark>();
        List<BmobQuery<CardMark>> and = new ArrayList<BmobQuery<CardMark>>();
        //大于00：00：00
        BmobQuery<CardMark> q1 = new BmobQuery<CardMark>();
        String start = "2016-02-24 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        q1.addWhereGreaterThanOrEqualTo("createdAt", new BmobDate(date));
        and.add(q1);
        //小于23：59：59
        BmobQuery<CardMark> q2 = new BmobQuery<CardMark>();
        String end = "2016-02-24 23:59:59";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        try {
            date1 = sdf1.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        q2.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date1));
        and.add(q2);

//添加复合与查询
        query.and(and);
        query.findObjects(v.getContext(), new FindListener<CardMark>() {
            @Override
            public void onSuccess(List<CardMark> object) {
                // TODO Auto-generated method stub


                editor = sharedPreference.edit();
                editor.putInt("today_msg_size", object.size());
                editor.commit();
                editor.clear();
                today_object=object;
                setdate(v);
                Log.d("qqqqqq", "onSuccess: "+object.get(0).getCreatedAt());

            }
            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                Log.d("qqqqqq", "onno: "+msg);
            }
        });
        return  true;
    }


    public List<CardMark> setdate( final View v) {

        return today_object;

    }
}
