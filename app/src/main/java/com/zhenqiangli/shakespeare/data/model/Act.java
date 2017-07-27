package com.zhenqiangli.shakespeare.data.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class Act {
  private Map<Integer, Scene> scenes = new HashMap<>();

  public Map<Integer, Scene> getScenes() {
    return scenes;
  }

  public void put(int scene, String character, String paragraph, int paragraphNum) {
    if (!scenes.containsKey(scene)) {
      scenes.put(scene, new Scene());
    }
    scenes.get(scene).put(character, paragraph, paragraphNum);
  }
}
