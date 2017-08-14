package com.zhenqiangli.shakespeare.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zhenqiangli.shakespeare.R;
import com.zhenqiangli.shakespeare.data.model.DramaSummary;
import com.zhenqiangli.shakespeare.main.MainContract.Presenter;
import com.zhenqiangli.shakespeare.util.RecyclerItemClickListener;
import com.zhenqiangli.shakespeare.util.TimeUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * View for MainActivity
 */

public class BookGenreFragment extends Fragment {

  private static final String TAG = "BookGenreFragment";
  private static final String ARGUMENT_DRAMA_SUMMARY_LIST = "drama_summary_list";
  Presenter presenter;
  RecyclerView worksView;
  WorksAdapter adapter;
  ArrayList<DramaSummary> dramaSummaries;
  int position;
  RecyclerItemClickListener recyclerItemClickListener;

  public static BookGenreFragment newInstance(ArrayList<DramaSummary> dramaSummaryList) {
    Bundle args = new Bundle();
    args.putParcelableArrayList(ARGUMENT_DRAMA_SUMMARY_LIST, dramaSummaryList);
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
    dramaSummaries = args.getParcelableArrayList(ARGUMENT_DRAMA_SUMMARY_LIST);
    worksView = (RecyclerView) view.findViewById(R.id.book_list);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    worksView.setLayoutManager(linearLayoutManager);
    DividerItemDecoration itemDecoration = new DividerItemDecoration(worksView.getContext(),
        linearLayoutManager.getOrientation());
    worksView.addItemDecoration(itemDecoration);
    adapter = new WorksAdapter(getActivity(), dramaSummaries);
    worksView.setAdapter(adapter);
    position = args.getInt(ARGUMENT_DRAMA_SUMMARY_LIST);
    recyclerItemClickListener = new RecyclerItemClickListener(getActivity(), worksView, adapter);
    worksView.addOnItemTouchListener(recyclerItemClickListener);
    return view;
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

    WorksAdapter(Context context, List<DramaSummary> dramaSummaryList) {
      this.context = context;
      this.dramaSummaryList = dramaSummaryList;
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

    private TextView bookNameView;
    private TextView bookDetailView;

    WorkViewHolder(View v) {
      super(v);
      bookNameView = (TextView) v.findViewById(R.id.book_name);
      bookDetailView = (TextView) v.findViewById(R.id.book_detail);
    }

    void bind(DramaSummary dramaSummary) {
      bookNameView.setText(dramaSummary.getTitle());
      bookDetailView.setText(String.format("%s - %s (%s) %s",
          dramaSummary.getSubtitle(),
          dramaSummary.getGenre(),
          dramaSummary.getYear(),
          TimeUtil.getDate(dramaSummary.getLastAccess())));
    }
  }
}
