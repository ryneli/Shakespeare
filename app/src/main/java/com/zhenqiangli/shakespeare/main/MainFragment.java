package com.zhenqiangli.shakespeare.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zhenqiangli.shakespeare.R;
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
        startActivity(SceneActivity.newIntent(getActivity(), position, 0));
      }

      @Override
      public void onLongItemClick(View view, int position) {

      }
    }));
    return view;
  }

  @Override
  public void showWorkList(List<String> workList, List<String> workDetailList) {
    worksView.setAdapter(new WorksAdapter(workList, workDetailList, getActivity()));
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  private class WorksAdapter extends RecyclerView.Adapter<WorkViewHolder> {
    List<String> workNames;
    List<String> workDetailList;
    Context context;

    public WorksAdapter(List<String> workNames, List<String> workDetailList, Context context) {
      this.workNames = workNames;
      this.workDetailList = workDetailList;
      this.context = context;
    }

    @Override
    public WorkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(context).inflate(R.layout.item_work, parent, false);
      return new WorkViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WorkViewHolder holder, int position) {
      holder.bind(workNames.get(position), workDetailList.get(position));
    }

    @Override
    public int getItemCount() {
      return workNames.size();
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
    void bind(String name, String detail) {
      bookNameView.setText(name);
      bookDetailView.setText(detail);
    }
  }
}
