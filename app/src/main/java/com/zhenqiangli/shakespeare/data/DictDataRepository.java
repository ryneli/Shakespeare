package com.zhenqiangli.shakespeare.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.zhenqiangli.shakespeare.data.dictionary.WordInfo;
import com.zhenqiangli.shakespeare.data.model.DictDatabaseSchema.Words;
import com.zhenqiangli.shakespeare.data.model.DictDatabaseSchema.Words.Cols;
import com.zhenqiangli.shakespeare.network.ChineseDefinitionRequester;

/**
 * Created by zhenqiangli on 8/7/17.
 */

public class DictDataRepository {
  private static DictDataRepository INSTANCE;
  private Context context;
  private SQLiteDatabase database;
  private DictDataRepository(Context context) {
    this.context = context;
    database = new DictDatabaseHelper(context).getWritableDatabase();
  }

  public static synchronized DictDataRepository get(Context context) {
    if (INSTANCE == null) {
      INSTANCE = new DictDataRepository(context);
    }
    return INSTANCE;
  }

  public void getDefinition(String word, GetDefinitionCallback callback) {
    ChineseDefinitionRequester.getDefinition(word,
        result -> {
          insert(result.getWordInfo());
          callback.run(result);
        });
  }

  private void insert(WordInfo wordInfo) {
    ContentValues cv = new ContentValues();
    cv.put(Cols.KEYWORD, wordInfo.getKeyword());
    cv.put(Cols.PRON_EN, wordInfo.getPronEn());
    cv.put(Cols.PRON_AM, wordInfo.getPronAm());
    cv.put(Cols.SOUND_EN, wordInfo.getSoundEn());
    cv.put(Cols.SOUND_AM, wordInfo.getSoundAm());
    String defs = "";
    for (String def : wordInfo.getDefinitions()) {
      defs += def + "\n";
    }
    cv.put(Cols.DEFINITION, defs);
    database.insert(Words.NAME, null, cv);
  }

  public interface GetDefinitionResult {
    WordInfo getWordInfo();
  }

  public interface GetDefinitionCallback {
    void run(GetDefinitionResult result);
  }
}
