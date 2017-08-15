package com.zhenqiangli.shakespeare.main;

import android.os.Bundle;
import com.zhenqiangli.shakespeare.BaseActivity;
import com.zhenqiangli.shakespeare.R;
import com.zhenqiangli.shakespeare.data.DataRepository;
import com.zhenqiangli.shakespeare.main.MainContract.Presenter;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    MainFragment fragment;
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (fragment == null) {
            fragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                .add(R.id.book_list_container, fragment)
                .commit();
        }
        presenter = new MainPresenter(DataRepository.get(this), fragment);
        fragment.setPresenter(presenter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }
}
