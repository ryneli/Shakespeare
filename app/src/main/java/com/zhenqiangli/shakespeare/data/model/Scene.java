package com.zhenqiangli.shakespeare.data.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Scene Model
 */

public class Scene {
  private int actIndex;
  private int sceneIndex;
  public Scene(int actIndex, int sceneIndex) {
    this.actIndex = actIndex;
    this.sceneIndex = sceneIndex;
  }
  private Map<Integer, Paragraph> paragraphs = new HashMap<>();

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
}
