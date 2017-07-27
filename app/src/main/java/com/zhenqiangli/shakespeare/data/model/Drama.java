package com.zhenqiangli.shakespeare.data.model;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.min;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class Drama {
  private static final String TAG = "Drama";
  private int actBase = Integer.MAX_VALUE;
  private Map<Integer, Act> acts = new HashMap<>();

  public Map<Integer, Act> getActs() {
    return acts;
  }

  public Scene getScene(int act, int scene) {
    act += actBase;
    Scene res = acts.get(act).getScene(scene);
    if (res == null) {
      throw new RuntimeException("Scene is null!!!");
    }
    return res;
  }

  public void put(int act, int scene, String character, String paragraph, int paragraphNum) {
    actBase = min(actBase, act);
    if (!acts.containsKey(act)) {
      acts.put(act, new Act());
    }
    acts.get(act).put(scene, character, paragraph, paragraphNum);
  }
}
