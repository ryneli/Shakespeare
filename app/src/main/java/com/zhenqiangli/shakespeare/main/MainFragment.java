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
import com.zhenqiangli.shakespeare.scene.SceneActivity;
import com.zhenqiangli.shakespeare.util.RecyclerItemClickListener;
import com.zhenqiangli.shakespeare.util.TimeUtil;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class MainFragment extends Fragment implements MainContract.View {
  private static final String TAG = "MainFragment";
  Presenter presenter;
  RecyclerView worksView;
  WorksAdapter adapter;

  public static MainFragment newInstance() {
    return new MainFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_main, container, false);
    worksView = (RecyclerView) view.findViewById(R.id.book_list);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    worksView.setLayoutManager(linearLayoutManager);
    DividerItemDecoration itemDecoration = new DividerItemDecoration(worksView.getContext(),
            linearLayoutManager.getOrientation());
    worksView.addItemDecoration(itemDecoration);
    adapter = new WorksAdapter(getActivity());
    worksView.setAdapter(adapter);
    return view;
  }

  @Override
  public void showWorkList(List<DramaSummary> dramaSummaryList) {
    Log.d(TAG, "showWorkList: ");
    adapter.setDramaSummaryList(dramaSummaryList);
    worksView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), worksView, adapter));
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  private class WorksAdapter extends RecyclerView.Adapter<WorkViewHolder> implements RecyclerItemClickListener.OnItemClickListener {
    List<DramaSummary> dramaSummaryList = new LinkedList<>();
    Context context;

    public WorksAdapter(Context context) {
      this.context = context;
    }

    public void setDramaSummaryList(List<DramaSummary> dramaSummaryList) {
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
      startActivity(SceneActivity.newIntent(getActivity(), dramaSummaryList.get(position).getWorkId(), 0));
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
