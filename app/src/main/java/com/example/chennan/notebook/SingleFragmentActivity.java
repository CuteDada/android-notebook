package com.example.chennan.notebook;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by chennan on 2018/6/4.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.Fragment f = fm.findFragmentById(R.id.main);
        if (f==null){
            f=createFragment();
            fm.beginTransaction()
                    .add(R.id.main,f).commit();
        }
    }
}
