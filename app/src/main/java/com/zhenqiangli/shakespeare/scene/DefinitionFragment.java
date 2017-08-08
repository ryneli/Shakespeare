package com.zhenqiangli.shakespeare.scene;

import static com.android.volley.VolleyLog.TAG;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zhenqiangli.shakespeare.R;
import com.zhenqiangli.shakespeare.data.DictDataRepository;
import com.zhenqiangli.shakespeare.data.dictionary.WordInfo;

/**
 * Definition Fragment.
 */

public class DefinitionFragment extends DialogFragment {

  private static final String ARGUMENT_WORD = "word";
  private RecyclerView definitionsView;
  private String word;

  public static DefinitionFragment newInstance(String word) {
    DefinitionFragment fragment = new DefinitionFragment();
    Bundle args = new Bundle();
    args.putString(ARGUMENT_WORD, word);
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    word = getArguments().getString(ARGUMENT_WORD);
    View v = inflater.inflate(R.layout.fragment_word_definition, container, false);
    definitionsView = (RecyclerView) v.findViewById(R.id.word_definition);
    definitionsView.setLayoutManager(new LinearLayoutManager(getActivity()));
    return v;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    DictDataRepository.get(getActivity()).getDefinition(word,
        result -> definitionsView.setAdapter(
            new DefinitionsAdapter(getActivity(), result.getWordInfo())));
  }

  private static class DefinitionsAdapter extends RecyclerView.Adapter<TextViewHolder> {

    private Context context;
    private WordInfo wordInfo;
    private static final int TYPE_KEYWORD = 1;
    private static final int TYPE_PRONUNCIATION = 2;
    private static final int TYPE_DEFINITION = 3;

    DefinitionsAdapter(Context context, WordInfo wordInfo) {
      super();
      this.context = context;
      this.wordInfo = wordInfo;
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(context).inflate(R.layout.item_word_definition, parent, false);
      switch (viewType) {
        case TYPE_KEYWORD:
          return new KeywordViewHolder(v);
        case TYPE_PRONUNCIATION:
          return new PronunciationViewHolder(v);
        case TYPE_DEFINITION:
          return new DefinitionViewHolder(v);
        default:
          Log.e(TAG, "onCreateViewHolder: unknown type");
          return null;
      }
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {
      if (holder instanceof KeywordViewHolder) {
        Log.d(TAG, "onBindViewHolder: 1 " + position);
        ((KeywordViewHolder) holder).bind(wordInfo.getKeyword());
      } else if (holder instanceof PronunciationViewHolder) {
        Log.d(TAG, "onBindViewHolder: 2 " + position);
        ((PronunciationViewHolder) holder).bind(wordInfo.getPronunciation());
      } else if (holder instanceof DefinitionViewHolder) {
        Log.d(TAG, "onBindViewHolder: 3 " + position);
        ((DefinitionViewHolder) holder).bind(wordInfo.getDefinitions().get(position - 1 - 1));
      }
    }

    @Override
    public int getItemCount() {
      return 1 + 1 + wordInfo.getDefinitions().size(); /* keyword + pronunciation + definitions */
    }

    @Override
    public int getItemViewType(int position) {
      if (position == 0) {
        return TYPE_KEYWORD;
      } else if (position == 1) {
        return TYPE_PRONUNCIATION;
      } else {
        return TYPE_DEFINITION;
      }
    }
  }

  private static class KeywordViewHolder extends TextViewHolder {

    KeywordViewHolder(View v) {
      super(v);
    }

    void bind(String word) {
      Log.d(TAG, "bind: KeywordViewHolder(" + word + ")");
      textView.setText(word);
    }
  }

  private static class PronunciationViewHolder extends TextViewHolder {

    PronunciationViewHolder(View v) {
      super(v);
    }

    void bind(String pron) {
      textView.setText(pron);
    }
  }

  private static class DefinitionViewHolder extends TextViewHolder {

    DefinitionViewHolder(View v) {
      super(v);
    }

    void bind(String def) {
      textView.setText(def);
    }
  }

  private static abstract class TextViewHolder extends RecyclerView.ViewHolder {

    TextView textView;

    TextViewHolder(View v) {
      super(v);
      textView = (TextView) v.findViewById(R.id.text_view);
    }
  }
}