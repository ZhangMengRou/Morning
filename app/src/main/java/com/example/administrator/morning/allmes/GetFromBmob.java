package com.example.administrator.morning.allmes;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.example.administrator.morning.aboutuser.mUser;
import com.example.administrator.morning.database.CardMesDatabaseHelper;

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

    public boolean getcardmes(final View v) {

        BmobQuery<CardMark> query = new BmobQuery<CardMark>();
        List<BmobQuery<CardMark>> and = new ArrayList<BmobQuery<CardMark>>();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String day = sDateFormat.format(new java.util.Date());
        Log.d("qqqqqq", "day   " + day);

        //大于00：00：00
        BmobQuery<CardMark> q1 = new BmobQuery<CardMark>();
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
        BmobQuery<CardMark> q2 = new BmobQuery<CardMark>();
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
        query.findObjects(v.getContext(), new FindListener<CardMark>() {
            @Override
            public void onSuccess(List<CardMark> object) {
                // TODO Auto-generated method stub


                int size = object.size();

                CardMesDatabaseHelper dbHelper = new CardMesDatabaseHelper(v.getContext(), "CardMes.db", null, 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                int i = 0;
                for (i = checksize(v, size); i < size; i++) {

                    values.put("num", "" + i);
                    values.put("objectid", object.get(i).getObjectId());
                    values.put("content", object.get(i).getContent());
                    values.put("user", object.get(i).getUser().getObjectId());
                    db.insert("CardMarkMes", null, values);
                    values.clear();
                    //db.close();
                    Log.d("qqqqqq", "content   " + object.get(i).getContent());
                }
                //today_object = object;
                //setdate(v,size);
                Log.d("qqqqqq", "onSuccess: " + object.size() + "userid: " + object.get(0).getUser().getObjectId());

            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                Log.d("qqqqqq", "onno: " + msg);
            }
        });
        return true;
    }


    public int checksize(final View v, int size) {

        //对照数据容量，避免使用你不必要的储存空间~机智的我2333
        int check = 0;

        CardMesDatabaseHelper dbHelper = new CardMesDatabaseHelper(v.getContext(), "CardMes.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("CardMarkMes", null, null, null, null, null, null);
        if (cursor.moveToLast()) {
            int pos = cursor.getPosition();
            int i = Integer.parseInt(cursor.getString(cursor.getColumnIndex("num")));
            return i + 1;
        } else {
            Log.d("qqqq", "checksize: fail");
        }
        return check;
    }
}
