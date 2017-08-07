package com.zhenqiangli.shakespeare.main;

import com.zhenqiangli.shakespeare.data.DataRepository;
import com.zhenqiangli.shakespeare.main.MainContract.View;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class MainPresenter implements MainContract.Presenter {
  DataRepository dataRepository;
  View view;

  @Override
  public void openDramaList() {
    view.showDramaList(dataRepository.getDramaSummaryList());
  }

  @Override
  public void openDrama(int workIndex, int sceneIndex) {
    view.showDrama(workIndex, sceneIndex);
  }

  public MainPresenter(DataRepository dataRepository, View view) {
    this.dataRepository = dataRepository;
    this.view = view;
  }

  @Override
  public void start() {
    openDramaList();
  }
}
