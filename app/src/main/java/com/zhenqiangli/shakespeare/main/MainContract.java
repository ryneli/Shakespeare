package com.zhenqiangli.shakespeare.main;

import com.zhenqiangli.shakespeare.BasePresenter;
import com.zhenqiangli.shakespeare.BaseView;
import java.util.List;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public interface MainContract {
  interface View extends BaseView<Presenter> {
    void showWorkList(List<String> workList, List<String> workDetailList);
  }
  interface Presenter extends BasePresenter {
    void openWorkList();
  }
}
