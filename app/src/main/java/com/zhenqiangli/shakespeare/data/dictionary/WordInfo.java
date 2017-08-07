package com.zhenqiangli.shakespeare.data.dictionary;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhenqiangli on 8/7/17.
 */

public class WordInfo {
  private String keyword;
  private String pronunciation;
  private List<String> definitions;

  public WordInfo(String keyword, String pronunciation) {
    this.keyword = keyword;
    this.pronunciation = pronunciation;
    this.definitions = new LinkedList<>();
  }

  public String getKeyword() {
    return keyword;
  }

  public String getPronunciation() {
    return pronunciation;
  }

  public List<String> getDefinitions() {
    return definitions;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public void setPronunciation(String pronunciation) {
    this.pronunciation = pronunciation;
  }

  public void addDefinition(String definition) {
    this.definitions.add(definition);
  }

  public void setDefinitions(List<String> definitions) {
    this.definitions = definitions;
  }

  public static WordInfo from(Definition definition) {
    return null;
  }
}
