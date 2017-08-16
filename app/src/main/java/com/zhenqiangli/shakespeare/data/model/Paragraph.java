package com.zhenqiangli.shakespeare.data.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class Paragraph {
  private String charactorName;
  private List<String> lines = new ArrayList<>();

  public String getCharactorName() {
    return charactorName;
  }

  public void setCharactorName(String charactorName) {
    this.charactorName = charactorName;
  }

  public List<String> getLines() {
    return lines;
  }

  public void put(String character, String paragraph) {
    charactorName = character;
    // TODO: parse paragraph into lines
    lines = Arrays.asList(paragraph.split(System.getProperty("line.separator")));
  }
}
