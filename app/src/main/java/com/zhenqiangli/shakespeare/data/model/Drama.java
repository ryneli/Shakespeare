package com.zhenqiangli.shakespeare.data.model;

import android.support.annotation.Nullable;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.min;

/**
 * Drama model
 */

public class Drama {
  private static final String TAG = "Drama";
  private Map<Integer, Scene> scenes = new HashMap<>();

  public Scene getScene(int sceneIndex) {
    Log.d(TAG, "getScene: " + sceneIndex);
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

  public void put(int actIndex, int sceneIndex, String character, String paragraph, int paragraphIndex) {
    int num = scenes.size();
    Scene scene = getScene(actIndex, sceneIndex);
    if (scene == null) {
      scene = new Scene(actIndex, sceneIndex);
      scenes.put(num, scene);
    }
    scene.put(character, paragraph, paragraphIndex);
  }

  public int getNumScenes() {
    return scenes.size();
  }
}
