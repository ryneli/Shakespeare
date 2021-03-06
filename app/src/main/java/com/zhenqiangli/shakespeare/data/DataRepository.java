package com.zhenqiangli.shakespeare.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import com.zhenqiangli.shakespeare.R;
import com.zhenqiangli.shakespeare.data.model.DatabaseSchema.Chapters;
import com.zhenqiangli.shakespeare.data.model.DatabaseSchema.Characters;
import com.zhenqiangli.shakespeare.data.model.DatabaseSchema.Paragraphs;
import com.zhenqiangli.shakespeare.data.model.DatabaseSchema.Paragraphs.Cols;
import com.zhenqiangli.shakespeare.data.model.DatabaseSchema.Works;
import com.zhenqiangli.shakespeare.data.model.Drama;
import com.zhenqiangli.shakespeare.data.model.DramaSummary;
import com.zhenqiangli.shakespeare.util.TimeUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class DataRepository {

  private static final String TAG = "DataRepository";
  private static final String[] SELECT_COLUMNS = {
      Chapters.NAME + "." + Chapters.Cols.ACT,
      Chapters.NAME + "." + Chapters.Cols.SCENE,
      Paragraphs.NAME + "." + Paragraphs.Cols.PLAIN_TEXT,
      Characters.NAME + "." + Characters.Cols.NAME,
      Paragraphs.NAME + "." + Cols.PARAGRAPH_NUM,
  };
  private static final String[] DRAMA_SUMMARY_COLUMNS = {
      Works.Cols.TITLE,
      Works.Cols.LONG_TITLE,
      Works.Cols.YEAR,
      Works.Cols.GENRE,
      Works.Cols.LAST_ACCESS,
      Works.Cols.ID,
  };
  private static DataRepository INSTANCE;
  SQLiteDatabase database;
  private Map<Integer, Drama> dramas = new HashMap<>();
  private Context context;

  private DataRepository(Context context) {
    this.context = context;
    database = new DatabaseHelper(context).getWritableDatabase();
  }

  public static DataRepository get(Context context) {
    if (INSTANCE == null) {
      INSTANCE = new DataRepository(context);
    }
    return INSTANCE;
  }

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
      cursor.close();
    }
    return dramas.get(i);
  }

  public List<DramaSummary> getDramaSummaryList(String genre) {
    String sql = "select " + TextUtils.join(", ", DRAMA_SUMMARY_COLUMNS)
        + " from " + Works.NAME;
    if (genre != null) {
      sql += " where genre=\'" + genre + "\' ";
    }
    sql += " order by " + Works.Cols.LAST_ACCESS + " desc";
    Cursor cursor = database.rawQuery(sql, null);
    List<DramaSummary> res = new LinkedList<>();
    while (cursor.moveToNext()) {
      res.add(new DramaSummary(
          cursor.getString(0),
          cursor.getString(1),
          cursor.getInt(2),
          cursor.getString(3),
          cursor.getInt(4),
          cursor.getInt(5)));
    }
    cursor.close();
    return res;
  }

  public void updateDramaAccessTime(int workId) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(Works.Cols.LAST_ACCESS, TimeUtil.now());
    database.update(
        Works.NAME,
        contentValues,
        Works.Cols.ID + " = " + workId,
        null
    );
  }

  public List<String> getGenreList() {
    String sql =
        "select " + Works.Cols.GENRE + " from " + Works.NAME + " group by " + Works.Cols.GENRE;
    Cursor cursor = database.rawQuery(sql, null);
    List<String> res = new LinkedList<>();
    while (cursor.moveToNext()) {
      res.add(cursor.getString(0));
    }
    cursor.close();
    return res;
  }

  public Drawable getBookCover(int workId) {
    String filepath = "covers/" + String.valueOf(workId) + ".jpg";
    Log.d(TAG, "getBookCover: " + filepath);
    Drawable res = context.getDrawable(R.drawable.close_book);

    try {
      InputStream in = context.getAssets().open(filepath);
      res = Drawable.createFromStream(in, null);
    } catch (IOException e) {
      Log.e(TAG, "getBookCover: " + filepath + " not exist!");
    }
    return res;
  }
}
