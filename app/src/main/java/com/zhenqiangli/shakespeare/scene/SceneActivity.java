package com.zhenqiangli.shakespeare.scene;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import com.zhenqiangli.shakespeare.BaseActivity;
import com.zhenqiangli.shakespeare.R;
import com.zhenqiangli.shakespeare.data.DataRepository;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class SceneActivity extends BaseActivity implements SceneFragment.Callbacks {

  private static final String TAG = "SceneActivity";
  private static final String EXTRA_WORK = "work";
  private static final String EXTRA_SCENE = "scene";
  private static final String TAG_WORD_DEFINITION_FRAGMENT = "DefinitionFragment";

  private SceneFragment fragment;
  private ScenePresenter presenter;

  public static Intent newIntent(Context context, int work, int scene) {
    Log.d(TAG, "newIntent: " + work + " " + scene);
    Intent intent = new Intent(context, SceneActivity.class);
    intent.putExtra(EXTRA_WORK, work);
    intent.putExtra(EXTRA_SCENE, scene);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scene);
    Intent intent = getIntent();
    int work = intent.getIntExtra(EXTRA_WORK, 0);
    int scene = intent.getIntExtra(EXTRA_SCENE, 0);
    Log.d(TAG, "onCreate: " + work + " " + scene);
    if (fragment == null) {
      fragment = SceneFragment.newInstance();
      getSupportFragmentManager().beginTransaction()
          .add(R.id.scene_container, fragment)
          .commit();
    }

    presenter = new ScenePresenter(DataRepository.get(this), fragment, work);
    fragment.setPresenter(presenter);

  }

  @Override
  protected void onResume() {
    super.onResume();
    Intent intent = getIntent();
    int work = intent.getIntExtra(EXTRA_WORK, 0);
    int scene = intent.getIntExtra(EXTRA_SCENE, 0);
    Log.d(TAG, "onResume: " + work + " " + scene);
    presenter.openWork(scene);
  }

  @Override
  public void showDefinition(String word) {
    DefinitionFragment fragment = (DefinitionFragment) getSupportFragmentManager()
        .findFragmentByTag(TAG_WORD_DEFINITION_FRAGMENT);
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    if (fragment != null) {
      ft.remove(fragment);
    }
    ft.addToBackStack(null);

    DefinitionFragment.newInstance(word).show(ft, TAG_WORD_DEFINITION_FRAGMENT);
  }
}
