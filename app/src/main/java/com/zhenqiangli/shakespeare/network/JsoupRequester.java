package com.zhenqiangli.shakespeare.network;

import static com.android.volley.VolleyLog.TAG;

import android.os.AsyncTask;
import android.util.Log;
import java.util.Locale;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by zhenqiangli on 7/31/17.
 */

public class JsoupRequester {
  private static int EN_BASE = 2;
  private static int CN_BASE = 35;
  private static String FORMAT = "http://en.eywedu.net/LMOYZLY/420k0020zw_%04d.htm";

  private static void get(int i) {
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... params) {
        try {
          String url = String.format(Locale.US, FORMAT, i);
          Document document = Jsoup.connect(url).get();
          Elements elements = document.select("p[style=\"text-indent:2em;text-align:justify\"]");
          Log.d(TAG, String.format("Title %s, lines(%s)", document.title(), elements.size()));
          for (Element element : elements) {
            Log.d(TAG, "test: \t" + element.text());
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        return null;
      }
    }.execute();
  }
  public static void test() {
    for (int i = 0; i < 33; i++) {
      get(EN_BASE+i);
      get(CN_BASE+i);
    }
  }
}
