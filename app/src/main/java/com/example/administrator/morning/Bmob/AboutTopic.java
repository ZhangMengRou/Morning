package com.example.administrator.morning.Bmob;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/2/26.
 */
public class AboutTopic {

    public String idea = null;
    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;
    private Handler handler_ = null;

    public AboutTopic(Handler handler) {
        this.handler_ = handler;
    }

    public String getTopic(final View v) {
        BmobQuery<MainIdeaForToday> query = new BmobQuery<MainIdeaForToday>();
        List<BmobQuery<MainIdeaForToday>> and = new ArrayList<BmobQuery<MainIdeaForToday>>();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String day = sDateFormat.format(new java.util.Date());
        Log.d("qqqqqq", "day   " + day);

        //大于00：00：00
        BmobQuery<MainIdeaForToday> q1 = new BmobQuery<MainIdeaForToday>();
        String start = day + " 00:00:00";
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
        BmobQuery<MainIdeaForToday> q2 = new BmobQuery<MainIdeaForToday>();
        String end = day + " 23:59:59";
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
        query.findObjects(v.getContext(), new FindListener<MainIdeaForToday>() {
            @Override
            public void onSuccess(List<MainIdeaForToday> object) {
                // TODO Auto-generated method stub


                String topic = object.get(0).getTopic();
                idea = topic;

                showtopic(topic);
                Log.d("qqqqqq", "yes: " + topic);

            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                Log.d("qqqqqq", "onno: " + msg);
            }
        });

        Log.d("qqqqqq", "idea: " + idea);
        return idea;
    }

    public void showtopic (String topic)
    {
        Bundle bundle= new Bundle();
        bundle.putString("topic",topic);

        Message msg  = new Message();
        msg.what = 2 ;
        msg.setData(bundle);
        handler_.sendMessage(msg);
    }
}
