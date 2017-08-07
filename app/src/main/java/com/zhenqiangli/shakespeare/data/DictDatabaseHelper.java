package com.zhenqiangli.shakespeare.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.zhenqiangli.shakespeare.data.model.DictDatabaseSchema.Words;
import com.zhenqiangli.shakespeare.data.model.DictDatabaseSchema.Words.Cols;

/**
 * Created by zhenqiangli on 8/7/17.
 */

public class DictDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dictionary.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE =
        "create table " + Words.NAME + " (" +
            Cols.KEYWORD + " TEXT primary key, " +
            Cols.PRON_EN + " TEXT, " +
            Cols.PRON_AM + " TEXT, " +
            Cols.SOUND_EN + " TEXT, " +
            Cols.SOUND_AM + " TEXT, " +
            Cols.DEFINITION + " TEXT)";
    public DictDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
