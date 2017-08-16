package com.zhenqiangli.shakespeare.data.dictionary;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhenqiangli on 8/7/17.
 */

public class WordInfo {

  private String keyword;
  private String pronEn;
  private String pronAm;
  private String soundEn;
  private String soundAm;
  private List<String> definitions = new LinkedList<>();

  public WordInfo() {
  }

  public static WordInfo from(Definition definition) {
    return null;
  }

  public String getPronEn() {
    return pronEn;
  }

  public void setPronEn(String pronEn) {
    this.pronEn = pronEn;
  }

  public String getPronAm() {
    return pronAm;
  }

  public void setPronAm(String pronAm) {
    this.pronAm = pronAm;
  }

  public String getSoundEn() {
    return soundEn;
  }

  public void setSoundEn(String soundEn) {
    this.soundEn = soundEn;
  }

  public String getSoundAm() {
    return soundAm;
  }

  public void setSoundAm(String soundAm) {
    this.soundAm = soundAm;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public List<String> getDefinitions() {
    return definitions;
  }

  public void addDefinition(String definition) {
    this.definitions.add(definition);
  }
}
