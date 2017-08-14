package com.zhenqiangli.shakespeare.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zhenqiangli.shakespeare.R;
import com.zhenqiangli.shakespeare.main.MainContract.Presenter;
import com.zhenqiangli.shakespeare.scene.SceneActivity;
import java.util.ArrayList;

/**
 * Created by zhenqiangli on 8/14/17.
 */

public class MainFragment extends Fragment implements MainContract.View {
  private static final String TAG = "MainFragment";
  Presenter presenter;
  ViewPager booListViewPager;

  public static MainFragment newInstance() {
    return new MainFragment();
  }

  @Nullable
  @Override
  public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_main, container, false);
    booListViewPager = (ViewPager) v.findViewById(R.id.book_list_view_pager);
    return v;
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void showDrama(int workIndex, int sceneIndex) {
    startActivity(SceneActivity.newIntent(getActivity(), workIndex, sceneIndex));
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    booListViewPager.setAdapter(new BookGenreViewPagerAdapter(getFragmentManager()));
  }

  private class BookGenreViewPagerAdapter extends FragmentStatePagerAdapter {
    BookGenreViewPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      Log.d(TAG, "getItem: " + position);
      BookGenreFragment fragment = BookGenreFragment.newInstance(new ArrayList<>(presenter.getDramaList(position)));
      fragment.setPresenter(presenter);
      return fragment;
    }

    @Override
    public int getCount() {
      return presenter.getGenreSize();
    }
  }
}
