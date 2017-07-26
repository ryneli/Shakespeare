package com.zhenqiangli.shakespeare.data.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class Paragraph {
  private String charactorName;
  private Map<Integer, Line> lines = new HashMap<>();

  public String getCharactorName() {
    return charactorName;
  }

  public void setCharactorName(String charactorName) {
    this.charactorName = charactorName;
  }

  public Map<Integer, Line> getLines() {
    return lines;
  }

  public void put(String character, String paragraph) {
    charactorName = character;
    // TODO: parse paragraph into lines
    int i = 0;
    for (String line : paragraph.split(System.getProperty("line.separator"))) {
      lines.put(i, new Line(line));
      i++;
    }

  }
}
