package com.example.administrator.morning.database;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBase_Operate {

    //初始化数据库
    public static void Init(Activity activity, String id, String name, String ic_url, String qq) {


        UserMesDatabaseHelper dbHelper = new UserMesDatabaseHelper(activity, "User", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("objectId", id);
        values.put("username", name);
        values.put("ic_url", ic_url);
        values.put("creat_t", "");
        values.put("update_t", "");
        values.put("qq", qq);
        db.insert("UserMes", null, values);
        values.clear();
        db.close();
    }

    public static String geturl(Activity a, String id) {

        UserMesDatabaseHelper dbHelper = new UserMesDatabaseHelper(a, "User", null, 1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from UserMes where objectId = ?", new String[]{id});
        if (cursor.moveToFirst()) {
            String url = cursor.getString(cursor.getColumnIndex("ic_url"));
            cursor.close();
            return url;
        }
        return null;
    }

}
