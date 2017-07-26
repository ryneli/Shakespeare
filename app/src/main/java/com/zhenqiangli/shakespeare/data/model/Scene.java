package com.zhenqiangli.shakespeare.data.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class Scene {
  private Map<Integer, Paragraph> paragraphs = new HashMap<>();

  public Map<Integer, Paragraph> getParagraphs() {
    return paragraphs;
  }

  public void put(String character, String paragraph, int paragraphNum) {
    if (!paragraphs.containsKey(paragraphNum)) {
      paragraphs.put(paragraphNum, new Paragraph());
    }
    paragraphs.get(paragraphNum).put(character, paragraph);
  }
}
