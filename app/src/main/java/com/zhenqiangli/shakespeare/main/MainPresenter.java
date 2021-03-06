package com.zhenqiangli.shakespeare.main;

import android.graphics.drawable.Drawable;
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

  public MainPresenter(DataRepository dataRepository, View view) {
    this.dataRepository = dataRepository;
    this.view = view;
  }

  @Override
  public void openDrama(int workIndex, int sceneIndex) {
    view.showDrama(workIndex, sceneIndex);
  }

  @Override
  public void start() {
  }

  @Override
  public int getGenreSize() {
    return dataRepository.getGenreList().size();
  }

  @Override
  public String getGenreName(int position) {
    return dataRepository.getGenreList().get(position);
  }

  @Override
  public List<DramaSummary> getDramaList(int position) {
    return dataRepository.getDramaSummaryList(dataRepository.getGenreList().get(position));
  }

  @Override
  public Drawable getBookCover(int workId) {
    return dataRepository.getBookCover(workId);
  }

  @Override
  public void login() {
    view.login();
  }
}
