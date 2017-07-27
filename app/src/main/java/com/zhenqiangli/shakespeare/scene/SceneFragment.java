package com.zhenqiangli.shakespeare.scene;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zhenqiangli.shakespeare.R;
import com.zhenqiangli.shakespeare.data.model.Line;
import com.zhenqiangli.shakespeare.data.model.Paragraph;
import com.zhenqiangli.shakespeare.data.model.Scene;
import com.zhenqiangli.shakespeare.scene.SceneContract.Presenter;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class SceneFragment extends Fragment implements SceneContract.View {
  private static final String TAG = "SceneFragment";
  private static final String ARGUMENT_WORK = "work";
  private static final String ARGUMENT_ACT = "act";
  private static final String ARGUMENT_SCENE = "scene";

  private Presenter presenter;
  private TextView contentTextView;
  private TextView titleTextView;
  private int workIndex;
  private int actIndex;
  private int sceneIndex;

  public static SceneFragment newInstance(int work, int act, int scene) {
    Bundle arguments = new Bundle();
    arguments.putInt(ARGUMENT_WORK, work);
    arguments.putInt(ARGUMENT_ACT, act);
    arguments.putInt(ARGUMENT_SCENE, scene);
    SceneFragment fragment = new SceneFragment();
    fragment.setArguments(arguments);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_scene, container, false);
    contentTextView = (TextView) view.findViewById(R.id.scene_content);
    titleTextView = (TextView) view.findViewById(R.id.scene_title);

    Bundle args = getArguments();
    workIndex = args.getInt(ARGUMENT_WORK);
    actIndex = args.getInt(ARGUMENT_ACT);
    sceneIndex = args.getInt(ARGUMENT_SCENE);
    return view;
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void showScene(Scene scene) {
    String s = "";
    for (Paragraph p : scene.getParagraphs().values()) {
      s += p.getCharactorName() + "\n";
      for (Line l : p.getLines().values()) {
        s += "\t" + l.getContent() + "\n";
      }
    }
    contentTextView.setText(s);
    titleTextView.setText(String.format("Act %s Scene %s", actIndex, sceneIndex));
  }
}
