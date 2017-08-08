package com.zhenqiangli.shakespeare.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import com.zhenqiangli.shakespeare.data.dictionary.WordInfo;
import com.zhenqiangli.shakespeare.data.model.DictDatabaseSchema.Words;
import com.zhenqiangli.shakespeare.data.model.DictDatabaseSchema.Words.Cols;
import com.zhenqiangli.shakespeare.network.ChineseDefinitionRequester;
import com.zhenqiangli.shakespeare.util.FileDownloader;

/**
 * Created by zhenqiangli on 8/7/17.
 */

public class DictDataRepository {
  private static DictDataRepository INSTANCE;
  private Context context;
  private SQLiteDatabase database;

  private DictDataRepository(Context context) {
    this.context = context.getApplicationContext();
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
        result -> insert(result.getWordInfo(), callback));
  }

  private void insert(WordInfo wordInfo, GetDefinitionCallback callback) {
    new AsyncTask<Void, Void, Void>() {
      String enPath;
      String amPath;
      @Override
      protected Void doInBackground(Void... params) {
        enPath = FileDownloader.get(context).downloadFile(wordInfo.getSoundEn());
        amPath = FileDownloader.get(context).downloadFile(wordInfo.getSoundAm());
        return null;
      }

      @Override
      protected void onPostExecute(Void aVoid) {
        wordInfo.setSoundAm(amPath);
        wordInfo.setSoundEn(enPath);
        String defs = "";
        for (String def : wordInfo.getDefinitions()) {
          defs += def + "\n";
        }
        ContentValues cv = new ContentValues();
        cv.put(Cols.KEYWORD, wordInfo.getKeyword());
        cv.put(Cols.PRON_EN, wordInfo.getPronEn());
        cv.put(Cols.PRON_AM, wordInfo.getPronAm());
        cv.put(Cols.SOUND_EN, wordInfo.getSoundEn());
        cv.put(Cols.SOUND_AM, wordInfo.getSoundAm());
        cv.put(Cols.DEFINITION, defs);

        database.insert(Words.NAME, null, cv);
        callback.run(() -> wordInfo);
      }
    }.execute();
  }

  public interface GetDefinitionResult {
    WordInfo getWordInfo();
  }

  public interface GetDefinitionCallback {
    void run(GetDefinitionResult result);
  }
}
