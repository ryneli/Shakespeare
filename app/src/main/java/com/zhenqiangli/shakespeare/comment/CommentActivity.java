package com.zhenqiangli.shakespeare.comment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.zhenqiangli.shakespeare.BaseActivity;
import com.zhenqiangli.shakespeare.R;
import com.zhenqiangli.shakespeare.comment.CommentContract.Presenter;

/**
 * Created by zhenqiangli on 8/18/17.
 */

public class CommentActivity extends BaseActivity {
  CommentFragment fragment;
  Presenter presenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_comment);

    if (fragment == null) {
      fragment = CommentFragment.newInstance();
      getSupportFragmentManager().beginTransaction()
          .add(R.id.comment_fragment_container, fragment)
          .commit();
    }
    presenter = new CommentPresenter();
    fragment.setPresenter(presenter);
  }
}
