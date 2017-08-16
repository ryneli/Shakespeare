package com.zhenqiangli.shakespeare.data.model;

import java.util.Map;
import java.util.TreeMap;

/**
 * Scene Model
 */

public class Scene {

  private int actIndex;
  private int sceneIndex;
  // Need sorted map here
  private Map<Integer, Paragraph> paragraphs = new TreeMap<>();

  public Scene(int actIndex, int sceneIndex) {
    this.actIndex = actIndex;
    this.sceneIndex = sceneIndex;
  }

  public Map<Integer, Paragraph> getParagraphs() {
    return paragraphs;
  }

  public void put(String character, String paragraph, int paragraphIndex) {
    if (!paragraphs.containsKey(paragraphIndex)) {
      paragraphs.put(paragraphIndex, new Paragraph());
    }
    paragraphs.get(paragraphIndex).put(character, paragraph);
  }

  public boolean equals(int act, int scene) {
    return actIndex == act && sceneIndex == scene;
  }

  public int getActIndex() {
    return actIndex;
  }

  public int getSceneIndex() {
    return sceneIndex;
  }
}
