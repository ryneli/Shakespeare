package com.zhenqiangli.shakespeare.scene;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.zhenqiangli.shakespeare.R;
import com.zhenqiangli.shakespeare.data.DataRepository;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class SceneActivity extends AppCompatActivity {
  private static final String EXTRA_WORK = "work";
  private static final String EXTRA_ACT = "act";
  private static final String EXTRA_SCENE = "scene";
  private SceneFragment fragment;
  private ScenePresenter presenter;

  public static Intent newIntent(Context context, int work, int act, int scene) {
    Intent intent = new Intent(context, SceneActivity.class);
    intent.putExtra(EXTRA_WORK, work);
    intent.putExtra(EXTRA_ACT, act);
    intent.putExtra(EXTRA_SCENE, scene);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scene);
    Intent intent = getIntent();
    int work = intent.getIntExtra(EXTRA_WORK, 0);
    int act = intent.getIntExtra(EXTRA_ACT, 0);
    int scene = intent.getIntExtra(EXTRA_SCENE, 0);
    if (fragment == null) {
      fragment = SceneFragment.newInstance(work, act, scene);
      getSupportFragmentManager().beginTransaction()
          .add(R.id.scene_container, fragment)
          .commit();
    }

    presenter = new ScenePresenter(DataRepository.get(this), fragment, work, act, scene);
    fragment.setPresenter(presenter);
  }

  @Override
  protected void onResume() {
    super.onResume();
    presenter.openScene();
  }
}
