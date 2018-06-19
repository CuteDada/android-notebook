package com.example.chennan.notebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.widget.CompoundButton.*;

/**
 * Created by chennan on 2018/5/27.
 */

public class NoteFragment extends Fragment {
    private EditText mTitleField;
    private EditText mContainField;
    private CheckBox mIsVatal;
    private Note mNote;
    private Spinner spinner2=null;
    private static final String AGR_NOTE_ID="note_id";
    private static final String DIALOG_NOTE = "DialogNote";


    public static NoteFragment newInstance(UUID uuid) {

        Bundle args = new Bundle();
        args.putSerializable(AGR_NOTE_ID,uuid);

        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID uuid=(UUID)getArguments().getSerializable(AGR_NOTE_ID);
        mNote=NoteLab.get(getActivity()).getNote(uuid);
//        mNote.getSpinner();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.del_note:
                NoteLab.get(getActivity()).deleteCrime(mNote);
                FragmentManager manager = getFragmentManager();
                NotePickerFragment dialog = new NotePickerFragment();
                dialog.show(manager, DIALOG_NOTE);
//                getActivity().finish();
                return true;
            case R.id.note_share:
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"分享");
                intent.putExtra(Intent.EXTRA_TEXT, "I would like to share this with you...");
                startActivity(Intent.createChooser(intent,mNote.getTitle()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_note_delete,menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note, container, false);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        TextView time1 = v.findViewById(R.id.modifyDate);
        time1.setText(simpleDateFormat.format(date));

        mTitleField=(EditText) v.findViewById(R.id.noteTitle);
        mTitleField.setText(mNote.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNote.setTitle(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mContainField=v.findViewById(R.id.editText2);
        mContainField.setText(mNote.getContainer());
        mContainField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNote.setContainer(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mIsVatal=v.findViewById(R.id.note_isVatal2);
        mIsVatal.setChecked(mNote.isVatal());
        mIsVatal.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mNote.setVatal(b);
            }
        });
//        final String [] values = {"Note Type","开发类","数学类","文学类","英语类"};
        final List<String> values = new ArrayList<>();
        values.add("开发类");
        values.add("数学类");
        values.add("英语类");
        values.add("游戏类");
        spinner2=v.findViewById(R.id.spDwon);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String str = adapterView.getItemAtPosition(i).toString();
                mNote.setSpinner(str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mNote.setSpinner("请选择类型！");
            }
        });


        return v;
    }



    @Override
    public void onPause() {
        super.onPause();

        NoteLab.get(getActivity()).updateNote(mNote);
    }
}
