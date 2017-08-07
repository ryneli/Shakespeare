package com.zhenqiangli.shakespeare.network;

import android.os.AsyncTask;
import android.util.Log;

import com.zhenqiangli.shakespeare.data.dictionary.WordInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhenqiangli on 8/6/17.
 */

public class ChineseDefinitionRequester {
    private static final String TAG = "ChineseDefinition";
    private static final String LINK_BASE = "http://www.iciba.com/";

    public interface GetDefinitionResult {
        WordInfo getWordInfo();
    }

    public interface GetDefinitionCallback {
        void run(GetDefinitionResult result);
    }

    private static String cleanWord(String word) {
        return word.replaceAll("[^a-zA-Z']", "");
    }

    public static void getDefinition(String word, GetDefinitionCallback callback) {
        Log.d(TAG, "getWordInfo: " + word + " " + cleanWord(word));
        word = cleanWord(word);
         new AsyncTask<String, Void, GetDefinitionResult>() {
            @Override
            protected GetDefinitionResult doInBackground(String... words) {
                String word = words[0];
                String keyword = "", pronunciation = "";
                List<String> definitions = new LinkedList<>();
                try {
                    Document document = Jsoup.connect(LINK_BASE + word).get();
                    Elements keywords = document.select("h1.keyword");
                    for (Element e : keywords) {
                        keyword += e.text();
                    }

                    Elements prons = document.select("div.base-top-voice > div.base-speak");
                    for (Element e : prons) {
                        pronunciation += e.text();
                    }



                    Elements sounds = document.select("i.new-speak-step");
                    for (Element e : sounds) {
                        Log.d(TAG, "doInBackground: " + e.attr("ms-on-mouseover"));
                    }

                    Elements defs = document.getElementsByClass("prop");
                    for (Element e : defs) {
                        Element next = e.nextElementSibling();
                        definitions.add(next.text());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                WordInfo wordInfo = new WordInfo(keyword, pronunciation);
                wordInfo.setDefinitions(definitions);
                return () -> wordInfo;
            }

             @Override
             protected void onPostExecute(GetDefinitionResult result) {
                 callback.run(result);
             }
         }.execute(word);
    }
}
