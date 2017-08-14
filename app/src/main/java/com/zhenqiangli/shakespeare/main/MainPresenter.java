package com.zhenqiangli.shakespeare.main;

import com.zhenqiangli.shakespeare.data.DataRepository;
import com.zhenqiangli.shakespeare.data.model.DramaSummary;
import com.zhenqiangli.shakespeare.main.MainContract.View;
import java.util.List;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class MainPresenter implements MainContract.Presenter {
  DataRepository dataRepository;
  View view;

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
  }

  @Override
  public int getGenreSize() {
    return dataRepository.getGenreList().size();
  }

  @Override
  public List<DramaSummary> getDramaList(int position) {
    return dataRepository.getDramaSummaryList(dataRepository.getGenreList().get(position));
  }
}
