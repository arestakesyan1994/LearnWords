package com.example.learnwords;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "contactDb";
    public static final String TABLE_CONTACTS = "words";

    public static final String KEY_ID = "_id";
    public static final String KEY_EN = "en";
    public static final String KEY_HY = "hy";
    public static final String KEY_EXAMPLE = "example";
    public static final String KEY_PRONOUNCE = "pronounce";
    public static final String KEY_EN_VERSION = "enVersion";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID
                + " integer primary key,"
                + KEY_EN + " text,"
                + KEY_HY + " text,"
                + KEY_EXAMPLE + " text,"
                + KEY_PRONOUNCE + " text,"
                + KEY_EN_VERSION + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);
        onCreate(db);
    }
}