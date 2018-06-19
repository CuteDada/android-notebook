package com.example.chennan.notebook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;


/**
 * Created by chennan on 2018/5/27.
 */

public class NoteListFragment extends Fragment {
    private RecyclerView mNoteRecyclerView;
    private NoteAdapter mNoteAdapter;
    private ImageView mIsVatal;
    private Spinner mSpinner;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.list_fragment_note,container,false);
        mNoteRecyclerView = (RecyclerView) view
                .findViewById(R.id.note_recycler_view);
        mNoteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNoteRecyclerView=view.findViewById(R.id.note_recycler_view);
        mNoteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //setLayoutManager：摆放屏幕上的列表项
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_note_list,menu);
    }

    private void updateUI(){
        NoteLab noteLab=NoteLab.get(getActivity());
        List<Note> notes=noteLab.getNotes();

//        mNoteAdapter=new NoteAdapter(notes);
//        mNoteRecyclerView.setAdapter(mNoteAdapter);
        if (mNoteAdapter==null){
            mNoteAdapter=new NoteAdapter(notes);
            mNoteRecyclerView.setAdapter(mNoteAdapter);
            ItemTouchHelper mIth = new ItemTouchHelper(
                    new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
                        @Override
                        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("警告框").setMessage("你要删除此项么？")

                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            mNoteAdapter.onItemDelete(viewHolder.getAdapterPosition());
                                        }
                                    })
                                    .setNegativeButton("取消", null).show();
                            //左滑弹出删除确认框

//                                    .setCancelable(true)
//                                    .setMessage("你要删除此项么？")
//                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            mNoteAdapter.onItemDelete(viewHolder.getAdapterPosition());
//                                        }
//                                    }).show();
                            updateUI();
                        }
                    });
            mIth.attachToRecyclerView(mNoteRecyclerView);
        }
        else {
            mNoteAdapter.setNotes(notes);
            mNoteAdapter.notifyDataSetChanged();
        }
//        mNoteAdapter.setNotes(notes);

    }

    private class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mContainTextView;
        private Note mNote;
        private ImageView mVitalImageView;

        public NoteHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_note,parent,false));
            itemView.setOnClickListener(this);

            mTitleTextView=(TextView) itemView.findViewById(R.id.note_title);
            mDateTextView=(TextView)itemView.findViewById(R.id.note_date);
            mContainTextView=(TextView)itemView.findViewById(R.id.note_container);
            mVitalImageView=(ImageView) itemView.findViewById(R.id.imageView);
            mSpinner=(Spinner)itemView.findViewById(R.id.spDwon);

        }
        public void bind(Note note){
            mNote=note;
            mTitleTextView.setText(mNote.getTitle());
            mDateTextView.setText("创建时间："+DateFormat.format("EEEE, MMM dd, yyyy",mNote.getCreateDate()));
//            mDateTextView.setText("笔记创建时间："+mNote.getCreateDate().toString());
            mContainTextView.setText(mNote.getContainer());
            mVitalImageView.setVisibility(mNote.isVatal() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            Intent intent=NotePagerActivity.newIntent(getActivity(),mNote.getId());
            startActivity(intent);


        }

    }



    private class NoteAdapter extends RecyclerView.Adapter<NoteHolder>{
        private List<Note> mNotes;
        public NoteAdapter(List<Note> notes){
            mNotes=notes;
        }

        @Override
        public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            return new NoteHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(NoteHolder holder, int position) {
            Note note=mNotes.get(position);
            holder.bind(note);

        }

        @Override
        public int getItemCount() {
            return mNotes.size();
        }
        public void setNotes(List<Note> notes) {
            mNotes = notes;
        }
        public void onItemDelete(int position){
            //移除数据并刷新
            NoteLab.get(getActivity()).deleteCrime(mNotes.get(position));
            notifyItemRemoved(position);
            updateUI();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_note:
                Note note=new Note();
                NoteLab.get(getActivity()).addNote(note);
                Intent intent=NotePagerActivity.newIntent(getActivity(),note.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
