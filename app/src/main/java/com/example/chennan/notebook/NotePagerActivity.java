package com.example.chennan.notebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by chennan on 2018/6/5.
 */

public class NotePagerActivity extends AppCompatActivity {
    public static final String EXTRA_NOTE_ID = "com.example.chennan.notebook.note_id";

    private ViewPager mViewPager;
    private List<Note> mNotes;

    private Spinner spinProvince=null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_pager);


        //从NoteActivity顺延过来的~~~
        UUID uuid=(UUID)getIntent().getSerializableExtra(EXTRA_NOTE_ID);

        //找到视图中的note_view_pager
        mViewPager=findViewById(R.id.note_view_pager);
        //从NoteLab获取数据集
        mNotes=NoteLab.get(this).getNotes();
        FragmentManager fragmentManager=getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Note note=mNotes.get(position);
                return NoteFragment.newInstance(note.getId());
            }

            @Override
            public int getCount() {
                return mNotes.size();
            }
        });
        //for循环获取当前点击的列表项对应的id，设置给mViewPager的函数setCurrentItem
        for (int i=0;i<mNotes.size();i++){
            if(mNotes.get(i).getId().equals(uuid)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }



        //从NoteActivity顺延过来的~~~
    public static Intent newIntent(Context packageContext, UUID uuid) {
        Intent intent = new Intent(packageContext, NotePagerActivity.class);
        intent.putExtra(EXTRA_NOTE_ID, uuid);
        return intent;
    }
}
