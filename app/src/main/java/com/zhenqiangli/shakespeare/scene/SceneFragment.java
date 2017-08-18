package com.zhenqiangli.shakespeare.scene;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;
import com.zhenqiangli.shakespeare.R;
import com.zhenqiangli.shakespeare.comment.CommentActivity;
import com.zhenqiangli.shakespeare.data.model.Drama;
import com.zhenqiangli.shakespeare.data.model.Scene;
import com.zhenqiangli.shakespeare.scene.SceneContract.Presenter;
import java.util.LinkedList;
import java.util.List;

/**
 * Fragment as View to show scenes of a drama.
 */

public class SceneFragment extends Fragment implements SceneContract.View {

  private static final String TAG = "SceneFragment";
  private Callbacks callbacks;
  private SceneViewAdapter adapter;
  private Presenter presenter;
  private FloatingActionButton commentButton;

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
    commentButton = (FloatingActionButton) view.findViewById(R.id.comment_button);
    commentButton.setOnClickListener(v -> startActivity(new Intent(getActivity(), CommentActivity.class)));
    RecyclerView scenesView = (RecyclerView) view.findViewById(R.id.rv_scenes);
    scenesView.setLayoutManager(new LinearLayoutManager(getActivity()));
    adapter = new SceneViewAdapter(getActivity());
    scenesView.setAdapter(adapter);
    return view;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    callbacks = (Callbacks) context;
  }

  @Override
  public void onDetach() {
    super.onDetach();
    callbacks = null;
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void showDrama(Drama drama, int sceneIndex) {
    adapter.setDrama(drama, sceneIndex);
  }

  @Override
  public void showDefinition(String word) {
    callbacks.showDefinition(word);
  }

  public interface Callbacks {

    void showDefinition(String word);
  }

  private static abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    BaseViewHolder(View v) {
      super(v);
    }

    protected abstract void bind(Line line);
  }

  private class Line {

    private int type;
    private int index;
    private String text;
    private boolean underline;

    public Line(int type, int index, String text, boolean underline) {
      this.type = type;
      this.index = index;
      this.text = text;
      this.underline = underline;
    }

    public int getType() {
      return type;
    }

    public int getIndex() {
      return index;
    }

    public String getText() {
      return text;
    }

    public boolean isUnderline() {
      return underline;
    }

    public void setUnderline(boolean underline) {
      this.underline = underline;
    }
  }

  private class SceneViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int TYPE_SCENE_TITLE = 1;
    private static final int TYPE_CHARACTER_NAME = 2;
    private static final int TYPE_SENTENCE = 3;
    private List<Line> items;
    private int sceneIndex;
    private Context context;

    SceneViewAdapter(Context context) {
      this.context = context;
    }

    private List<Line> from(Drama drama) {
      List<Line> items = new LinkedList<>();
      for (int i = 0; i < drama.getNumScenes(); i++) {
        Scene scene = drama.getScene(i);
        items.add(new Line(TYPE_SCENE_TITLE, 0,
            String.format("Act %s, Scene %s", scene.getActIndex(), scene.getSceneIndex()), false));
        scene.getParagraphs().values().stream().forEach(
            p -> {
              items.add(new Line(TYPE_CHARACTER_NAME, 0, p.getCharactorName(), false));
              p.getLines().stream().forEach(
                  l -> items.add(new Line(TYPE_SENTENCE, 0, l, false))
              );
            }
        );
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
      holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
      return items.size();
    }

    @Override
    public int getItemViewType(int position) {
      return items.get(position).getType();
    }
  }

  private class TextViewHolder extends BaseViewHolder {

    TextView contentView;
    Line line;

    TextViewHolder(View v) {
      super(v);
      contentView = (TextView) v.findViewById(R.id.item);
    }

    @Override
    protected void bind(Line line) {
      contentView.setMovementMethod(LinkMovementMethod.getInstance());

      if (line.isUnderline()) {
        contentView.setText(getWordClicableUnderlineText(line.getText()));
      } else {
        contentView.setText(getWordClickableText(line.getText()));
      }
      contentView.setOnLongClickListener(v -> {
        Toast.makeText(getActivity(), "Long click!", Toast.LENGTH_SHORT).show();
        if (line.isUnderline()) {
          contentView.setText(getWordClickableText(line.getText()));
          line.setUnderline(false);
        } else {
          contentView.setText(getWordClicableUnderlineText(line.getText()));
          line.setUnderline(true);
        }
        return true;
      });
      this.line = line;
    }

    private SpannableStringBuilder getWordClickableText(String text) {
      SpannableStringBuilder builder = new SpannableStringBuilder();
      for (String word : text.split(" ")) {
        builder.append(word);
        builder.setSpan(new ClickableWordSpan(word),
            builder.length() - word.length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(" ");
      }
      return builder;
    }

    private SpannableStringBuilder getWordClicableUnderlineText(String text) {
      SpannableStringBuilder builder = new SpannableStringBuilder();
      for (String word : text.split(" ")) {
        builder.append(word);
        builder.setSpan(new SelectedWordSpan(word),
            builder.length() - word.length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(" ");
        builder.setSpan(new UnderlineSpan(),
            builder.length() - 1, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
      return builder;
    }
  }

  private class SelectedWordSpan extends ClickableWordSpan {

    public SelectedWordSpan(String word) {
      super(word);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
      ds.setUnderlineText(true);
    }
  }

  private class UnderlineSpan extends ClickableSpan {

    @Override
    public void onClick(View widget) {
      // do nothing
    }
  }

  private class ClickableWordSpan extends ClickableSpan {

    String word;

    public ClickableWordSpan(String word) {
      this.word = word;
    }

    @Override
    public void onClick(View widget) {
      presenter.openDefinition(word);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
      ds.setUnderlineText(false);
    }
  }
}
