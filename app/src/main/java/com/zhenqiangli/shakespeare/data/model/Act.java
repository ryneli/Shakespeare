package com.zhenqiangli.shakespeare.data.model;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.min;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class Act {
  private int sceneBase = Integer.MAX_VALUE;
  private Map<Integer, Scene> scenes = new HashMap<>();

  public Map<Integer, Scene> getScenes() {
    return scenes;
  }

  public Scene getScene(int scene) {
    scene += sceneBase;
    return scenes.get(scene);
  }

  public void put(int scene, String character, String paragraph, int paragraphNum) {
    sceneBase = min(sceneBase, scene);
    if (!scenes.containsKey(scene)) {
      scenes.put(scene, new Scene());
    }
    scenes.get(scene).put(character, paragraph, paragraphNum);
  }
}
