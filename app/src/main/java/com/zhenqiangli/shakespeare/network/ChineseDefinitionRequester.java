package com.zhenqiangli.shakespeare.network;

import android.os.AsyncTask;
import android.util.Log;

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
        List<String> getDefinition();
    }

    public interface GetDefinitionCallback {
        void run(GetDefinitionResult result);
    }

    private static String cleanWord(String word) {
        return word.replaceAll("[^a-zA-Z']", "");
    }

    public static void getDefinition(String word, GetDefinitionCallback callback) {
        Log.d(TAG, "getDefinition: " + word + " " + cleanWord(word));
        word = cleanWord(word);
         new AsyncTask<String, Void, GetDefinitionResult>() {
            @Override
            protected GetDefinitionResult doInBackground(String... words) {
                String word = words[0];
                List<String> result = new LinkedList<>();
                try {
                    Document document = Jsoup.connect(LINK_BASE + word).get();
                    Elements es = document.getElementsByClass("prop");
                    for (Element e : es) {
                        Element next = e.nextElementSibling();
                        result.add(next.text());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new GetDefinitionResult() {
                    @Override
                    public List<String> getDefinition() {
                        return result;
                    }
                };
            }

             @Override
             protected void onPostExecute(GetDefinitionResult result) {
                 callback.run(result);
             }
         }.execute(word);
    }
}
