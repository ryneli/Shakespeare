package com.zhenqiangli.shakespeare.network;

import android.os.AsyncTask;
import android.util.Log;
import com.zhenqiangli.shakespeare.data.DictDataRepository.GetDefinitionCallback;
import com.zhenqiangli.shakespeare.data.DictDataRepository.GetDefinitionResult;
import com.zhenqiangli.shakespeare.data.dictionary.WordInfo;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by zhenqiangli on 8/6/17.
 */

public class ChineseDefinitionRequester {

  private static final String TAG = "ChineseDefinition";
  private static final String LINK_BASE = "http://www.iciba.com/";

  private static String cleanWord(String word) {
    return word.replaceAll("[^a-zA-Z']", "");
  }

  public static void getDefinition(String word, GetDefinitionCallback callback) {
    Log.d(TAG, "getWordInfo: " + word + " " + cleanWord(word));
    word = cleanWord(word);
    new AsyncTask<String, Void, GetDefinitionResult>() {
      @Override
      protected GetDefinitionResult doInBackground(String... words) {
        WordInfo wordInfo = new WordInfo();
        String word = words[0];
        String keyword = "", pronunciation = "";
        List<String> definitions = new LinkedList<>();
        try {
          Document document = Jsoup.connect(LINK_BASE + word).get();
          Elements keywords = document.select("h1.keyword");
          for (Element e : keywords) {
            keyword += e.text();
          }
          wordInfo.setKeyword(keyword);

          Elements prons = document.select("div.base-top-voice > div.base-speak > span > span");
          if (prons.size() > 0) {
            wordInfo.setPronEn(prons.get(0).text());
          }
          if (prons.size() > 1) {
            wordInfo.setPronAm(prons.get(1).text());
          }

          Elements sounds = document.select("i.new-speak-step");
          if (sounds.size() > 0) {
            String url = sounds.get(0).attr("ms-on-mouseover");
            // sound('http://res.iciba.com/resource/amp3/0/0/de/6e/de6e614d32f65d12b439d1081c91da17.mp3')
            // http://res.iciba.com/resource/amp3/0/0/de/6e/de6e614d32f65d12b439d1081c91da17.mp3
            url = url.substring(7, url.length() - 2);
            Log.d(TAG, "doInBackground: sounds " + url);
            wordInfo.setSoundEn(url);
          }
          if (sounds.size() > 1) {
            String url = sounds.get(1).attr("ms-on-mouseover");
            url = url.substring(7, url.length() - 2);
            Log.d(TAG, "doInBackground: sounds " + url);
            wordInfo.setSoundAm(url);
          }

          Elements defs = document.getElementsByClass("prop");
          for (Element e : defs) {
            Element next = e.nextElementSibling();
            wordInfo.addDefinition(next.text());
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        return () -> wordInfo;
      }

      @Override
      protected void onPostExecute(GetDefinitionResult result) {
        callback.run(result);
      }
    }.execute(word);
  }
}
