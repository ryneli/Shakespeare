package com.zhenqiangli.shakespeare.scene;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
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

/**
 * Fragment as View to show scenes of a drama.
 */

public class SceneFragment extends Fragment implements SceneContract.View {
  private static final String TAG = "SceneFragment";
  private static final String TAG_WORD_DEFINITION_FRAGMENT = "WordDefFragment";

  private SceneViewAdapter adapter;
  private Presenter presenter;

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
    this.presenter = presenter;
  }

  @Override
  public void showDrama(Drama drama, int sceneIndex) {
    adapter.setDrama(drama, sceneIndex);
  }

  private void showDefinition(String word) {
    WordDefFragment fragment = (WordDefFragment) getActivity().getSupportFragmentManager()
        .findFragmentByTag(TAG_WORD_DEFINITION_FRAGMENT);
    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
    if (fragment != null) {
      ft.remove(fragment);
    }
    ft.addToBackStack(null);

    WordDefFragment.newInstance(word).show(ft, TAG_WORD_DEFINITION_FRAGMENT);
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

  private class TextViewHolder extends BaseViewHolder {
    TextView contentView;
    String text = "";
    TextViewHolder(View v) {
      super(v);
      contentView = (TextView) v.findViewById(R.id.item);
    }
    @Override
    protected void bind(String text) {
      contentView.setMovementMethod(LinkMovementMethod.getInstance());
      SpannableStringBuilder builder = new SpannableStringBuilder();
      for (String word : text.split(" ")) {
        builder.append(word);
        builder.setSpan(new ClickableWordSpan(word),
            builder.length() - word.length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(" ");
      }
      contentView.setText(builder);
      this.text = text;
    }
  }

  private class ClickableWordSpan extends ClickableSpan {
    String word;
    public ClickableWordSpan(String word) {
      this.word = word;
    }

    @Override
    public void onClick(View widget) {
      showDefinition(word);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
      ds.setUnderlineText(false);
    }
  }

  private static abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    BaseViewHolder(View v) {
      super(v);
    }

    protected abstract void bind(String text);
  }
}
