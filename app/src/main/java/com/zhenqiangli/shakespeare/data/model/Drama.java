package com.zhenqiangli.shakespeare.data.model;

import android.support.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Drama model
 */

public class Drama {

  private static final String TAG = "Drama";
  private int workIndex;
  private Map<Integer, Scene> scenes = new HashMap<>();

  public Drama(int workIndex) {
    this.workIndex = workIndex;
  }

  public Scene getScene(int sceneIndex) {
    return scenes.get(sceneIndex);
  }

  @Nullable
  private Scene getScene(int actIndex, int sceneIndex) {
    for (Scene scene : scenes.values()) {
      if (scene.equals(actIndex, sceneIndex)) {
        return scene;
      }
    }
    return null;
  }

  public void put(int actIndex, int sceneIndex, String character, String paragraph,
      int paragraphIndex) {
    int num = scenes.size();
    Scene scene = getScene(actIndex, sceneIndex);
    if (scene == null) {
      scene = new Scene(actIndex, sceneIndex);
      scenes.put(num, scene);
    }
    scene.put(character, paragraph, paragraphIndex);
  }

  public int getWorkIndex() {
    return workIndex;
  }

  public int getNumScenes() {
    return scenes.size();
  }
}
