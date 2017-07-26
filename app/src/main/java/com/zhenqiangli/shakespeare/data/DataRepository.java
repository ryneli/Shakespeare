package com.zhenqiangli.shakespeare.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.zhenqiangli.shakespeare.data.model.DatabaseSchema.Chapters;
import com.zhenqiangli.shakespeare.data.model.DatabaseSchema.Characters;
import com.zhenqiangli.shakespeare.data.model.DatabaseSchema.Paragraphs;
import com.zhenqiangli.shakespeare.data.model.DatabaseSchema.Paragraphs.Cols;
import com.zhenqiangli.shakespeare.data.model.Drama;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class DataRepository {
  SQLiteDatabase database;
  private static final String TAG = "DataRepository";
  private static DataRepository INSTANCE;
  private DataRepository(Context context) {
    database = new DatabaseHelper(context).getWritableDatabase();
  }

  public static DataRepository get(Context context) {
    if (INSTANCE == null) {
      INSTANCE = new DataRepository(context);
    }
    return INSTANCE;
  }

  private static final String[] SELECT_COLUMNS = {
      Chapters.NAME + "." + Chapters.Cols.ACT,
      Chapters.NAME + "." + Chapters.Cols.SCENE,
      Paragraphs.NAME + "." + Paragraphs.Cols.PLAIN_TEXT,
      Characters.NAME + "." + Characters.Cols.NAME,
      Paragraphs.NAME + "." + Cols.PARAGRAPH_NUM,
  };

  private void putCursorRowToDrama(Cursor cursor, Drama drama) {
    int act;
    int scene;
    String paragraph;
    String character;
    int paragraphNum;
    act = cursor.getInt(0);
    scene = cursor.getInt(1);
    paragraph = cursor.getString(2).replace("\\n", System.getProperty("line.separator"));
    character = cursor.getString(3);
    paragraphNum = cursor.getInt(4);
    drama.put(act, scene, character, paragraph, paragraphNum);
  }

  private Drama dramaFrom(Cursor cursor) {
    Drama drama = new Drama();
    while (cursor.moveToNext()) {
      putCursorRowToDrama(cursor, drama);
    }
    return drama;
  }

  public Drama getDrama(int i) {
    /* select  */
    String sql = "select " + TextUtils.join(", ", SELECT_COLUMNS)
        + " from paragraphs "
        + " join chapters on chapters.id = paragraphs.chapter_id "
        + " join characters on characters.id = paragraphs.character_id "
        + " where chapters.work_id = ?;";
    String[] args = {String.valueOf(i)};
    Cursor cursor = database.rawQuery(
        sql,
        args
    );
    return dramaFrom(cursor);
  }
}
