package com.zhenqiangli.shakespeare.comment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zhenqiangli.shakespeare.R;
import com.zhenqiangli.shakespeare.comment.CommentContract.Presenter;

/**
 * Created by zhenqiangli on 8/18/17.
 */

public class CommentFragment extends Fragment implements CommentContract.View {
  Presenter presenter;
  private static CommentFragment INSTANCE;
  public static CommentFragment newInstance() {
    if (INSTANCE == null) {
      INSTANCE = new CommentFragment();
    }
    return INSTANCE;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_comment, container, false);
    return v;
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }
}
