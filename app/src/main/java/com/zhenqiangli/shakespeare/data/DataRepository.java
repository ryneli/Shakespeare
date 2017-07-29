package com.zhenqiangli.shakespeare.data;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.zhenqiangli.shakespeare.data.model.DatabaseSchema.Chapters;
import com.zhenqiangli.shakespeare.data.model.DatabaseSchema.Characters;
import com.zhenqiangli.shakespeare.data.model.DatabaseSchema.Paragraphs;
import com.zhenqiangli.shakespeare.data.model.DatabaseSchema.Paragraphs.Cols;
import com.zhenqiangli.shakespeare.data.model.DatabaseSchema.Works;
import com.zhenqiangli.shakespeare.data.model.Drama;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.min;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class DataRepository {
  SQLiteDatabase database;
  private int workBase = Integer.MAX_VALUE;
  private Map<Integer, Drama> dramas = new HashMap<>();
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

  private Drama dramaFrom(Cursor cursor, int work) {
    Drama drama = new Drama(work);
    while (cursor.moveToNext()) {
      putCursorRowToDrama(cursor, drama);
    }
    return drama;
  }

  public Drama getDrama(int i) {
      i += workBase;
    Log.d(TAG, "getDrama: " + i + " " + workBase);
    if (!dramas.containsKey(i)) {
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
      dramas.put(i, dramaFrom(cursor, i));
    }
    return dramas.get(i);
  }

  public List<String> getWorkNameList() {
    String sql = "select " + Works.Cols.TITLE + ", " + Works.Cols.ID
        + " from " + Works.NAME;
    Cursor cursor = database.rawQuery(
        sql,
        null
    );
    List<String> res = new LinkedList<>();
    while (cursor.moveToNext()) {
      res.add(cursor.getString(0));
      workBase = min(workBase, cursor.getInt(1));
    }
    return res;
  }
}
