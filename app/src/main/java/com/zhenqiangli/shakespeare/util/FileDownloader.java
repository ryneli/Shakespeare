package com.zhenqiangli.shakespeare.util;

import static com.android.volley.VolleyLog.TAG;

import android.content.Context;
import android.util.Log;
import com.zhenqiangli.shakespeare.ShakespeareApp;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executor;

/**
 * From https://stackoverflow.com/questions/7441140/how-to-play-audio-file-from-server-in-android
 */

public class FileDownloader {
  private static FileDownloader INSTANCE;
  Context context;
  String basepath;
  Executor backgroundExecutor;

  private FileDownloader(Context context) {
    this.context = context.getApplicationContext();
    this.basepath = context.getApplicationInfo().dataDir + "/download";
    createFolderIfNotExist(basepath);
    backgroundExecutor = ((ShakespeareApp) context.getApplicationContext()).getBackgroundExecutor();
  }

  private void createFolderIfNotExist(String path) {
    File dir = new File(path);

    if (!dir.exists()) {
      try {
        dir.mkdir();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public synchronized static FileDownloader get(Context context) {
    if (INSTANCE == null) {
      INSTANCE = new FileDownloader(context);
    }
    return INSTANCE;
  }

  public interface DownloadFileCallback {
    void onResult(String path);
  }

  public void downloadFile(String url, DownloadFileCallback callback) {
    backgroundExecutor.execute(() -> {
      String path = download(url);
      callback.onResult(path);
    });
  }

  private String download(String urlString) {
    int count;
    String filepath = basepath + urlString.substring(urlString.lastIndexOf('/'));
    try {
      URL url = new URL(urlString);
      URLConnection connection = url.openConnection();
      connection.connect();
      // download the file
      InputStream in = new BufferedInputStream(url.openStream());
      Log.d(TAG, "downloadFile: " + filepath);
      OutputStream out = new FileOutputStream(filepath);

      byte data[] = new byte[1024];
      while ((count = in.read(data)) != -1) {
        out.write(data, 0, count);
      }

      out.flush();
      out.close();
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return filepath;
  }
}
