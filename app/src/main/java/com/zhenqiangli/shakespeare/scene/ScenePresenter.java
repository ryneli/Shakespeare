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
  private int work;
  private int act;
  private int scene;
  public ScenePresenter(DataRepository dataRepository, View view, int work, int act, int scene) {
    this.view = view;
    this.dataRepository = dataRepository;
    this.work = work;
    this.act = act;
    this.scene = scene;
  }

  @Override
  public void start() {

  }

  @Override
  public void openScene() {
    view.showScene(dataRepository.getDrama(work).getScene(act, scene));
  }
}
