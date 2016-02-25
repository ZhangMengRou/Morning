package com.example.administrator.morning.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CardMesDatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    private boolean NULL = true;                //标记表是否初始化
    public static final String CREATE_CARDMARKMES = "create table CardMarkMes ("
            + "id integer primary key,"
            + "num text,"
            + "objectid text,"
            + "content text,"
            + "user text,"
            + "createdAt text)";

    public CardMesDatabaseHelper(Context context, String databasename, CursorFactory factory, int version) {
        super(context, databasename, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_CARDMARKMES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists CardMarkMes");
        onCreate(db);
    }

}