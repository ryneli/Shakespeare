package com.zhenqiangli.shakespeare.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhenqiangli on 8/7/17.
 */

public class DictDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dictionary.sqlite";
    private static final int DATABASE_VERSION = 1;
    public DictDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
