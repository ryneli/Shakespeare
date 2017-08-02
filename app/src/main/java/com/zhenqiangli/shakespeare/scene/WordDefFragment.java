package com.zhenqiangli.shakespeare.scene;

import static com.android.volley.VolleyLog.TAG;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zhenqiangli.shakespeare.R;
import com.zhenqiangli.shakespeare.data.dictionary.Definition;
import com.zhenqiangli.shakespeare.network.VolleyRequester;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhenqiangli on 8/2/17.
 */

public class WordDefFragment extends DialogFragment {
  private static final String ARGUMENT_WORD = "word";
  private RecyclerView definitionsView;
  private DefinitionsAdapter adapter;
  private String word;

  public static WordDefFragment newInstance(String word) {
    WordDefFragment fragment = new WordDefFragment();
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
    adapter = new DefinitionsAdapter(getActivity(), word);
    definitionsView.setLayoutManager(new LinearLayoutManager(getActivity()));
    definitionsView.setAdapter(adapter);
    return v;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    VolleyRequester.getInstance(getActivity()).getDefinition(word,
        response -> {
          if (response.length > 3) {
            List<Definition> defs = new LinkedList<>();
            for (int i = 0; i < 3; i++) {
              defs.add(response[i]);
            }
            adapter.setDefinitions(defs);
          } else {
            adapter.setDefinitions(Arrays.asList(response));
          }
        },
        error -> Log.d(TAG, "onViewCreated: get definition error"));
  }

  private static class DefinitionsAdapter extends RecyclerView.Adapter<TextViewHolder> {
    private Context context;
    private String word;
    private List<Definition> definitions = new LinkedList<>();
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_DEFINITION = 2;

    DefinitionsAdapter(Context context, String word) {
      super();
      this.context = context;
      this.word = word;
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(context).inflate(R.layout.item_word_definition, parent, false);
      if (viewType == TYPE_HEADER) {
        return new HeaderViewHolder(v);
      } else {
        return new DefinitionViewHolder(v);
      }
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {
      if (holder instanceof HeaderViewHolder) {
        ((HeaderViewHolder)holder).bind(word);
      } else if (holder instanceof DefinitionViewHolder) {
        ((DefinitionViewHolder)holder).bind(definitions.get(position-1));
      }
    }

    @Override
    public int getItemCount() {
      return 1 + definitions.size(); /* header + definitions */
    }

    @Override
    public int getItemViewType(int position) {
      if (position == 0) {
         return TYPE_HEADER;
      } else {
        return TYPE_DEFINITION;
      }
    }

    void setDefinitions(List<Definition> definitions) {
      this.definitions = definitions;
      notifyDataSetChanged();
    }
  }

  private static class HeaderViewHolder extends TextViewHolder {
    HeaderViewHolder(View v) {
      super(v);
    }

    void bind(String word) {
      Log.d(TAG, "bind: HeaderViewHolder(" + word + ")");
      textView.setText(word);
    }
  }

  private static class DefinitionViewHolder extends TextViewHolder {
    DefinitionViewHolder(View v) {
      super(v);
    }

    void bind(Definition def) {
      textView.setText(Html.fromHtml(def.getText()));
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
