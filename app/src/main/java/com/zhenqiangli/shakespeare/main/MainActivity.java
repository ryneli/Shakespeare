package com.zhenqiangli.shakespeare.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.zhenqiangli.shakespeare.R;
import com.zhenqiangli.shakespeare.scene.SceneActivity;

public class MainActivity extends AppCompatActivity {
    TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentTextView = (TextView) findViewById(R.id.content);
        contentTextView.setOnClickListener(v -> displayFirstScene());
    }

    private void displayFirstScene() {
        Intent intent = SceneActivity.newIntent(this, 34, 1, 1);
        startActivity(intent);
    }
}
