
package com.zhenqiangli.shakespeare.data.dictionary;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Definition
{
    private ArrayList<Object> textProns;

    public ArrayList<Object> getTextProns() { return this.textProns; }

    public void setTextProns(ArrayList<Object> textProns) { this.textProns = textProns; }

    private String sourceDictionary;

    public String getSourceDictionary() { return this.sourceDictionary; }

    public void setSourceDictionary(String sourceDictionary) { this.sourceDictionary = sourceDictionary; }

    private ArrayList<Object> exampleUses;

    public ArrayList<Object> getExampleUses() { return this.exampleUses; }

    public void setExampleUses(ArrayList<Object> exampleUses) { this.exampleUses = exampleUses; }

    private ArrayList<Object> relatedWords;

    public ArrayList<Object> getRelatedWords() { return this.relatedWords; }

    public void setRelatedWords(ArrayList<Object> relatedWords) { this.relatedWords = relatedWords; }

    private ArrayList<Object> labels;

    public ArrayList<Object> getLabels() { return this.labels; }

    public void setLabels(ArrayList<Object> labels) { this.labels = labels; }

    private ArrayList<Object> citations;

    public ArrayList<Object> getCitations() { return this.citations; }

    public void setCitations(ArrayList<Object> citations) { this.citations = citations; }

    private String word;

    public String getWord() { return this.word; }

    public void setWord(String word) { this.word = word; }

    private String partOfSpeech;

    public String getPartOfSpeech() { return this.partOfSpeech; }

    public void setPartOfSpeech(String partOfSpeech) { this.partOfSpeech = partOfSpeech; }

    private String attributionText;

    public String getAttributionText() { return this.attributionText; }

    public void setAttributionText(String attributionText) { this.attributionText = attributionText; }

    private String sequence;

    public String getSequence() { return this.sequence; }

    public void setSequence(String sequence) { this.sequence = sequence; }

    private String text;

    public String getText() { return this.text; }

    public void setText(String text) { this.text = text; }

    private double score;

    public double getScore() { return this.score; }

    public void setScore(double score) { this.score = score; }
}
