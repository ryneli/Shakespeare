package com.zhenqiangli.shakespeare.main;

import static com.android.volley.VolleyLog.TAG;

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
import java.util.List;

/**
 * Created by zhenqiangli on 7/26/17.
 */

public class MainFragment extends Fragment implements MainContract.View {
  Presenter presenter;
  RecyclerView worksView;

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
    worksView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), worksView, new RecyclerItemClickListener.OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: " + position);
        startActivity(SceneActivity.newIntent(getActivity(), position, 0));
      }

      @Override
      public void onLongItemClick(View view, int position) {

      }
    }));
    return view;
  }

  @Override
  public void showWorkList(List<DramaSummary> dramaSummaryList) {
    worksView.setAdapter(new WorksAdapter(dramaSummaryList, getActivity()));
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  private class WorksAdapter extends RecyclerView.Adapter<WorkViewHolder> {
    List<DramaSummary> dramaSummaryList;
    Context context;

    public WorksAdapter(List<DramaSummary> dramaSummaryList, Context context) {
      this.dramaSummaryList = dramaSummaryList;
      this.context = context;
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
          dramaSummary.getLastAccess()));
    }
  }
}
