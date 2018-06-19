package com.example.chennan.notebook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by chennan on 2018/5/27.
 */

public class NoteListActivity extends SingleFragmentActivity{
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment);
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment f = fm.findFragmentById(R.id.main);
//        if (f==null){
//            f=new NoteListFragment();
//            fm.beginTransaction()
//                    .add(R.id.main,f).commit();
//        }
//    }

    @Override
    protected Fragment createFragment() {
        return new NoteListFragment();
    }
}
