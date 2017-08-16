package com.zhenqiangli.shakespeare.main;

import android.graphics.drawable.Drawable;
import com.zhenqiangli.shakespeare.BasePresenter;
import com.zhenqiangli.shakespeare.BaseView;
import com.zhenqiangli.shakespeare.data.model.DramaSummary;
import java.util.List;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public interface MainContract {

  interface View extends BaseView<Presenter> {

    void showDrama(int workIndex, int sceneIndex);
  }

  interface Presenter extends BasePresenter {

    void openDrama(int workIndex, int sceneIndex);

    int getGenreSize();

    String getGenreName(int position);

    List<DramaSummary> getDramaList(int position);

    Drawable getBookCover(int workId);
  }
}
