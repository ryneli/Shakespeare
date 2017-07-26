package com.zhenqiangli.shakespeare.scene;

import com.zhenqiangli.shakespeare.BasePresenter;
import com.zhenqiangli.shakespeare.BaseView;
import com.zhenqiangli.shakespeare.data.model.Scene;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public interface SceneContract {
  interface View extends BaseView<Presenter> {
    void showScene(Scene scene);
  }
  interface Presenter extends BasePresenter {
    void openScene();
  }
}
