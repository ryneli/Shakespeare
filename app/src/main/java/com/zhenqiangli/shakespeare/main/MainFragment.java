package com.zhenqiangli.shakespeare.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.zhenqiangli.shakespeare.R;
import com.zhenqiangli.shakespeare.main.MainContract.Presenter;
import com.zhenqiangli.shakespeare.scene.SceneActivity;
import com.zhenqiangli.shakespeare.util.Others;

/**
 * Created by zhenqiangli on 8/14/17.
 */

public class MainFragment extends Fragment implements MainContract.View,
    OnNavigationItemSelectedListener {

  private static final String TAG = "MainFragment";
  Presenter presenter;
  ViewPager bookListViewPager;
  BookGenreViewPagerAdapter adapter;

  public static MainFragment newInstance() {
    return new MainFragment();
  }

  @Nullable
  @Override
  public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_main, container, false);
    bookListViewPager = (ViewPager) v.findViewById(R.id.book_list_view_pager);
    adapter = new BookGenreViewPagerAdapter(getFragmentManager());
    bookListViewPager.setAdapter(adapter);

    TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(bookListViewPager);

    initDrawerMenu(v);
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
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_login:
        Log.d(TAG, "onOptionsItemSelected: " + item.getTitle());
        return true;
      default:
        Log.d(TAG, "onOptionsItemSelected: " + item.getTitle());

    }
    return false;
  }

  private void initDrawerMenu(View root) {
    Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
    AppCompatActivity activity = (AppCompatActivity) getActivity();
    activity.setSupportActionBar(toolbar);
    activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    activity.getSupportActionBar().setHomeButtonEnabled(true);
    NavigationView menuListView = (NavigationView) root.findViewById(R.id.main_drawer_menu);
    menuListView.setNavigationItemSelectedListener(this);
    DrawerLayout drawerLayout = (DrawerLayout) root.findViewById(R.id.main_drawer_layout);
    toolbar.setNavigationOnClickListener(v -> {
      Others.hideSystemUI(v);
      drawerLayout.openDrawer(menuListView);
    });
    drawerLayout.addDrawerListener(new DrawerListener() {
      @Override
      public void onDrawerSlide(View drawerView, float slideOffset) {

      }

      @Override
      public void onDrawerOpened(View drawerView) {

      }

      @Override
      public void onDrawerClosed(View drawerView) {
        Others.showSystemUI(root);
      }

      @Override
      public void onDrawerStateChanged(int newState) {

      }
    });
  }

  private class BookGenreViewPagerAdapter extends FragmentStatePagerAdapter {

    BookGenreViewPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      Log.d(TAG, "getItem: " + position);
      BookGenreFragment fragment = BookGenreFragment
          .newInstance(position);
      fragment.setPresenter(presenter);
      return fragment;
    }

    @Override
    public int getCount() {
      return presenter.getGenreSize();
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return presenter.getGenreName(position);
    }
  }
}
