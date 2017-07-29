package com.zhenqiangli.shakespeare.scene;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zhenqiangli.shakespeare.R;
import com.zhenqiangli.shakespeare.data.DataRepository;
import com.zhenqiangli.shakespeare.data.model.Drama;
import com.zhenqiangli.shakespeare.data.model.Line;
import com.zhenqiangli.shakespeare.data.model.Paragraph;
import com.zhenqiangli.shakespeare.data.model.Scene;
import com.zhenqiangli.shakespeare.scene.SceneContract.Presenter;
import com.zhenqiangli.shakespeare.util.HtmlBuilder;

/**
 * Fragment as View to show scenes of a drama.
 */

public class SceneFragment extends Fragment implements SceneContract.View {
  private static final String TAG = "SceneFragment";

  private SceneViewAdapter adapter;

  public static SceneFragment newInstance() {
    Bundle arguments = new Bundle();
    SceneFragment fragment = new SceneFragment();
    fragment.setArguments(arguments);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_scene, container, false);
    RecyclerView scenesView = (RecyclerView) view.findViewById(R.id.rv_scenes);
    scenesView.setLayoutManager(new LinearLayoutManager(getActivity()));
    adapter = new SceneViewAdapter(getActivity());
    scenesView.setAdapter(adapter);
    return view;
  }

  @Override
  public void setPresenter(Presenter presenter) {
    // Not used for now
  }

  @Override
  public void showDrama(Drama drama, int sceneIndex) {
    adapter.setDrama(drama, sceneIndex);
  }
  private class SceneViewAdapter extends RecyclerView.Adapter<SceneViewHolder> {
    private Drama drama;
    private int sceneIndex;
    private Context context;

    SceneViewAdapter(Context context) {
      this.context = context;
    }

    void setDrama(Drama drama, int sceneIndex) {
      this.drama = drama;
      this.sceneIndex = sceneIndex;
      notifyDataSetChanged();
    }
    @Override
    public SceneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(context).inflate(R.layout.item_scene, parent, false);
      return new SceneViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SceneViewHolder holder, int position) {
      holder.bind(drama.getScene(position));
    }

    @Override
    public int getItemCount() {
      return drama.getNumScenes();
    }
  }

  private static class SceneViewHolder extends RecyclerView.ViewHolder {
    private TextView titleView;
    private TextView contentView;
    SceneViewHolder(View v) {
      super(v);
      titleView = (TextView) v.findViewById(R.id.scene_title);
      contentView = (TextView) v.findViewById(R.id.scene_content);
    }

    void bind(Scene scene) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        contentView.setText(Html.fromHtml(parseScene(scene), Html.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL));
      }
      titleView.setText(String.format("Act %s Scene %s", scene.getActIndex(), scene.getSceneIndex()));
    }

    private String parseScene(Scene scene) {
      HtmlBuilder htmlBuilder = new HtmlBuilder();
      for (Paragraph p : scene.getParagraphs().values()) {
        htmlBuilder.h1(p.getCharactorName()).br();
        for (Line l : p.getLines().values()) {
          htmlBuilder.append(l.getContent()).br();
        }
      }
      return htmlBuilder.build();
    }
  }
}
