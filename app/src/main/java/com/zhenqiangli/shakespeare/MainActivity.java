package com.zhenqiangli.shakespeare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhenqiangli.shakespeare.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
