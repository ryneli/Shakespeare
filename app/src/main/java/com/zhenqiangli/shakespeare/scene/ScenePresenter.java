package com.zhenqiangli.shakespeare.scene;

import com.zhenqiangli.shakespeare.data.DataRepository;
import com.zhenqiangli.shakespeare.scene.SceneContract.Presenter;
import com.zhenqiangli.shakespeare.scene.SceneContract.View;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class ScenePresenter implements Presenter {

  private View view;
  private DataRepository dataRepository;
  private int workIndex;

  public ScenePresenter(DataRepository dataRepository, View view, int workIndex) {
    this.view = view;
    this.dataRepository = dataRepository;
    this.workIndex = workIndex;
  }

  @Override
  public void openDefinition(String word) {
    view.showDefinition(word);
  }

  @Override
  public void start() {

  }

  @Override
  public void openWork(int sceneIndex) {
    view.showDrama(dataRepository.getDrama(workIndex), sceneIndex);
    dataRepository.updateDramaAccessTime(workIndex);
  }
}
