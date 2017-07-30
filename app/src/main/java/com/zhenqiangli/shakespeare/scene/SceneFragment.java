package com.zhenqiangli.shakespeare.scene;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhenqiangli.shakespeare.R;
import com.zhenqiangli.shakespeare.data.model.Drama;
import com.zhenqiangli.shakespeare.data.model.Line;
import com.zhenqiangli.shakespeare.data.model.Paragraph;
import com.zhenqiangli.shakespeare.data.model.Scene;
import com.zhenqiangli.shakespeare.scene.SceneContract.Presenter;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
    scenesView.addOnScrollListener(new HidingScrollListener() {
      @Override
      public void onHide() {
        hideSystemUI(scenesView);
      }

      @Override
      public void onShow() {
        showSystemUI(scenesView);
      }
    });
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
  private class SceneViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int TYPE_SCENE_TITLE = 1;
    private static final int TYPE_CHARACTER_NAME = 2;
    private static final int TYPE_SENTENCE = 3;
    private List<Pair<Integer, String>> items;
    private int sceneIndex;
    private Context context;

    SceneViewAdapter(Context context) {
      this.context = context;
    }

    private List<Pair<Integer, String>> from(Drama drama) {
      List<Pair<Integer, String>> items = new LinkedList<>();
      for (int i = 0; i < drama.getNumScenes(); i++) {
        Scene scene = drama.getScene(i);
        items.add(new Pair<>(TYPE_SCENE_TITLE,
                String.format("Act %s, Scene %s", scene.getActIndex(), scene.getSceneIndex())));
        for (Paragraph p : scene.getParagraphs().values()) {
          items.add(new Pair<>(TYPE_CHARACTER_NAME, p.getCharactorName()));
          for (Line l : p.getLines().values()) {
            items.add(new Pair<>(TYPE_SENTENCE, l.getContent()));
          }
        }
      }
      return items;
    }

    void setDrama(Drama drama, int sceneIndex) {
      this.items = from(drama);
      this.sceneIndex = sceneIndex;
      notifyDataSetChanged();
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      int layoutId = 0;
      switch (viewType) {
        case TYPE_CHARACTER_NAME:
          layoutId = R.layout.item_character_name;
          break;
        case TYPE_SCENE_TITLE:
          layoutId = R.layout.item_scene_title;
          break;
        case TYPE_SENTENCE:
          layoutId = R.layout.item_sentence;
          break;
        default:
          throw new RuntimeException("Unknown type " + viewType);
      }
      View v = LayoutInflater.from(context).inflate(layoutId, parent, false);
      return new TextViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
      holder.bind(items.get(position).second);
    }

    @Override
    public int getItemCount() {
      return items.size();
    }

    @Override
    public int getItemViewType(int position) {
      return items.get(position).first;
    }
  }

  private static class TextViewHolder extends BaseViewHolder {
    TextView contentView;
    public TextViewHolder(View v) {
      super(v);
      contentView = (TextView) v.findViewById(R.id.item);
    }
    @Override
    public void bind(String text) {
      contentView.setText(text);
    }
  }

  private static abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View v) {
      super(v);
    }

    public abstract void bind(String text);
  }

  // https://mzgreen.github.io/2015/02/15/How-to-hideshow-Toolbar-when-list-is-scroling%28part1%29/
  public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {
    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      super.onScrolled(recyclerView, dx, dy);

      if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
        onHide();
        controlsVisible = false;
        scrolledDistance = 0;
      } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
        onShow();
        controlsVisible = true;
        scrolledDistance = 0;
      }

      if((controlsVisible && dy>0) || (!controlsVisible && dy<0)) {
        scrolledDistance += dy;
      }
    }

    public abstract void onHide();
    public abstract void onShow();

  }

  // https://developer.android.com/training/system-ui/immersive.html#compare
  // This snippet hides the system bars.
  private void hideSystemUI(View v) {
    // Set the IMMERSIVE flag.
    // Set the content to appear under the system bars so that the content
    // doesn't resize when the system bars hide and show.
    v.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE);
  }

  // This snippet shows the system bars. It does this by removing all the flags
// except for the ones that make the content appear under the system bars.
  private void showSystemUI(View v) {
    // TODO: add timer to hide toolbar after 2 seconds
    v.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
  }
}
