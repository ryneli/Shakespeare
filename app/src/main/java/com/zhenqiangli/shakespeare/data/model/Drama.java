package com.zhenqiangli.shakespeare.data.model;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class Drama implements Work {
  private static final String TAG = "Drama";
  public static final int ACT_INDEX_BASE = 0;
  public static final int SCENE_INDEX_BASE = 1;
  private Map<Integer, Act> acts = new HashMap<>();

  public Map<Integer, Act> getActs() {
    return acts;
  }

  public Scene getScene(int act, int scene) {
    Log.d(TAG, "getScene: " + acts.keySet());
    Log.d(TAG, "getScene: " + acts.get(1).getScenes().keySet());
    return acts.get(act).getScenes().get(scene);
  }

  public void put(int act, int scene, String character, String paragraph, int paragraphNum) {
    if (!acts.containsKey(act)) {
      acts.put(act, new Act());
    }
    acts.get(act).put(scene, character, paragraph, paragraphNum);
  }
}
