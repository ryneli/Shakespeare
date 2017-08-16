package com.zhenqiangli.shakespeare.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.zhenqiangli.shakespeare.R;
import com.zhenqiangli.shakespeare.data.model.DramaSummary;
import com.zhenqiangli.shakespeare.main.MainContract.Presenter;
import com.zhenqiangli.shakespeare.util.RecyclerItemClickListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * View for MainActivity
 */

public class BookGenreFragment extends Fragment {

  private static final String TAG = "BookGenreFragment";
  private static final String ARGUMENT_POSITION = "position";
  Presenter presenter;
  RecyclerView worksView;
  WorksAdapter adapter;
  int position;
  ArrayList<DramaSummary> dramaSummaries;
  RecyclerItemClickListener recyclerItemClickListener;

  public static BookGenreFragment newInstance(int position) {
    Bundle args = new Bundle();
    args.putInt(ARGUMENT_POSITION, position);
    BookGenreFragment fragment = new BookGenreFragment();
    fragment.setArguments(args);
    return fragment;
  }

  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_book_genre, container, false);
    Bundle args = getArguments();
    position = args.getInt(ARGUMENT_POSITION);
    worksView = (RecyclerView) view.findViewById(R.id.book_list);
    LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
    worksView.setLayoutManager(layoutManager);
    adapter = new WorksAdapter(getActivity());
    worksView.setAdapter(adapter);
    recyclerItemClickListener = new RecyclerItemClickListener(getActivity(), worksView, adapter);
    worksView.addOnItemTouchListener(recyclerItemClickListener);
    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    adapter.setDramaSummaryList(presenter.getDramaList(position));
  }

  @Override
  public void onDetach() {
    worksView.removeOnItemTouchListener(recyclerItemClickListener);
    super.onDetach();
  }

  private class WorksAdapter extends RecyclerView.Adapter<WorkViewHolder> implements
      RecyclerItemClickListener.OnItemClickListener {

    List<DramaSummary> dramaSummaryList;
    Context context;

    WorksAdapter(Context context) {
      this.context = context;
      dramaSummaryList = new LinkedList<>();
    }

    public void setDramaSummaryList(
        List<DramaSummary> dramaSummaryList) {
      this.dramaSummaryList = dramaSummaryList;
      notifyDataSetChanged();
    }

    @Override
    public WorkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(context).inflate(R.layout.item_work, parent, false);
      return new WorkViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WorkViewHolder holder, int position) {
      holder.bind(dramaSummaryList.get(position));
    }

    @Override
    public int getItemCount() {
      return dramaSummaryList.size();
    }

    @Override
    public void onItemClick(View view, int position) {
      Log.d(TAG, "onItemClick: " + position);
      presenter.openDrama(dramaSummaryList.get(position).getWorkId(), 0);
    }

    @Override
    public void onLongItemClick(View view, int position) {
      Log.d(TAG, "onLongItemClick: " + position);
    }
  }

  private class WorkViewHolder extends RecyclerView.ViewHolder {

    private ImageView bookCoverImageView;
    private TextView bookNameView;
    private TextView bookDetailView;

    WorkViewHolder(View v) {
      super(v);
      bookCoverImageView = (ImageView) v.findViewById(R.id.book_icon);
      bookNameView = (TextView) v.findViewById(R.id.book_name);
      bookDetailView = (TextView) v.findViewById(R.id.book_detail);
    }

    void bind(DramaSummary dramaSummary) {
      Drawable bookCover = presenter.getBookCover(dramaSummary.getWorkId());
      if (bookCover != null) {
        bookCoverImageView.setImageDrawable(bookCover);
      }
      bookNameView.setText(dramaSummary.getTitle());
      bookDetailView.setText(String.format("%s",
          dramaSummary.getYear()));
    }
  }
}
