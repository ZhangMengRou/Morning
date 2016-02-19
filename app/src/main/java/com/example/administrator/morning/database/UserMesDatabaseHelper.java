package com.example.administrator.morning.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class UserMesDatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    private boolean NULL = true;                //标记表是否初始化
    public static final String CREATE_USERMES = "create table UserMes ("
            + "id integer primary key,"
            + "objectId text,"
            + "username text,"
            + "ic_url text,"
            + "creat_t text,"
            + "update_t text,"
            + "qq text)";

    public UserMesDatabaseHelper(Context context, String databasename, CursorFactory factory, int version) {
        super(context, databasename, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (NULL) {
            db.execSQL(CREATE_USERMES);
            NULL = false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists UserMes");
        onCreate(db);
    }

}