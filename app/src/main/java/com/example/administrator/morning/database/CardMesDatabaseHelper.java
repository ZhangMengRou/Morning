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
            + "major text,"
            + "academy text,"
            + "httpurl text,"
            + "description1 text,"
            + "description2 text,"
            + "y_n integer)";

    public CardMesDatabaseHelper(Context context, String databasename, CursorFactory factory, int version) {
        super(context, databasename, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (NULL) {
            db.execSQL(CREATE_CARDMARKMES);
            NULL = false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists CardMarkMes");
        onCreate(db);
    }

}