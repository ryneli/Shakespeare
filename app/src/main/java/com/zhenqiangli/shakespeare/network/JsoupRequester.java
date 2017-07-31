package com.zhenqiangli.shakespeare.network;

import static com.android.volley.VolleyLog.TAG;

import android.os.AsyncTask;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by zhenqiangli on 7/31/17.
 */

public class JsoupRequester {
  public static void test() {
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        try {
          String url = "http://en.eywedu.net/LMOYZLY/420k0020zw_0039.htm";
          Document document = Jsoup.connect(url).get();
          Log.d(TAG, "test: " + document.title());
          Elements elements = document.select("font");
          for (Element element : elements) {
            if (!element.text().trim().equals("")) {
              Log.d(TAG, "test: \t" + element.text());
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        return null;
      }
    }.execute();
  }
}
