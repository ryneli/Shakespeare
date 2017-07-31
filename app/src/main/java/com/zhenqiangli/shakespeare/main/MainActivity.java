package com.zhenqiangli.shakespeare.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.zhenqiangli.shakespeare.R;
import com.zhenqiangli.shakespeare.data.DataRepository;
import com.zhenqiangli.shakespeare.main.MainContract.Presenter;
import com.zhenqiangli.shakespeare.network.VolleyRequester;

public class MainActivity extends AppCompatActivity {
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

        VolleyRequester.getInstance(this).getDefinition("ambition");
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.openWorkList();
    }
}
