package com.android.shiz.informationalinfluence.mvp.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.shiz.informationalinfluence.R;

public class MainActivity extends AppCompatActivity implements MainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResult(String result) {

    }

    @Override
    public void showError(String error) {

    }
}
