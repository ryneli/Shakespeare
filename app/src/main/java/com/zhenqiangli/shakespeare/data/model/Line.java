package com.zhenqiangli.shakespeare.data.model;

import android.util.Log;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class Line {
  private static final String TAG = "Line";
  private String content;

  public Line(String line) {
    content = line;
  }

  public String getContent() {
    return content;
  }
}
