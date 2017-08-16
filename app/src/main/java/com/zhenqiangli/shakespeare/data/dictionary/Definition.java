package com.zhenqiangli.shakespeare.data.dictionary;

import java.util.ArrayList;

public class Definition {

  private ArrayList<Object> textProns;
  private String sourceDictionary;
  private ArrayList<Object> exampleUses;
  private ArrayList<Object> relatedWords;
  private ArrayList<Object> labels;
  private ArrayList<Object> citations;
  private String word;
  private String partOfSpeech;
  private String attributionText;
  private String sequence;
  private String text;
  private double score;

  public ArrayList<Object> getTextProns() {
    return this.textProns;
  }

  public void setTextProns(ArrayList<Object> textProns) {
    this.textProns = textProns;
  }

  public String getSourceDictionary() {
    return this.sourceDictionary;
  }

  public void setSourceDictionary(String sourceDictionary) {
    this.sourceDictionary = sourceDictionary;
  }

  public ArrayList<Object> getExampleUses() {
    return this.exampleUses;
  }

  public void setExampleUses(ArrayList<Object> exampleUses) {
    this.exampleUses = exampleUses;
  }

  public ArrayList<Object> getRelatedWords() {
    return this.relatedWords;
  }

  public void setRelatedWords(ArrayList<Object> relatedWords) {
    this.relatedWords = relatedWords;
  }

  public ArrayList<Object> getLabels() {
    return this.labels;
  }

  public void setLabels(ArrayList<Object> labels) {
    this.labels = labels;
  }

  public ArrayList<Object> getCitations() {
    return this.citations;
  }

  public void setCitations(ArrayList<Object> citations) {
    this.citations = citations;
  }

  public String getWord() {
    return this.word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public String getPartOfSpeech() {
    return this.partOfSpeech;
  }

  public void setPartOfSpeech(String partOfSpeech) {
    this.partOfSpeech = partOfSpeech;
  }

  public String getAttributionText() {
    return this.attributionText;
  }

  public void setAttributionText(String attributionText) {
    this.attributionText = attributionText;
  }

  public String getSequence() {
    return this.sequence;
  }

  public void setSequence(String sequence) {
    this.sequence = sequence;
  }

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public double getScore() {
    return this.score;
  }

  public void setScore(double score) {
    this.score = score;
  }
}
