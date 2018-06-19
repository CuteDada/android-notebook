package com.example.chennan.notebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.example.chennan.notebook.db.NoteBaseHelper;
import com.example.chennan.notebook.db.NoteCursorWrapper;
import com.example.chennan.notebook.db.NoteDbSchema;
import com.example.chennan.notebook.db.NoteDbSchema.NoteTable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by chennan on 2018/6/4.
 */

public class NoteLab {
    private static NoteLab sNoteLab;

//    private List<Note> mNotes;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static NoteLab get(Context context){
        if (sNoteLab==null){
            sNoteLab=new NoteLab(context);
        }
        return sNoteLab;
    }
    private NoteLab(Context context){
        mContext=context.getApplicationContext();
        mDatabase=new NoteBaseHelper(mContext).getWritableDatabase();

    }
    public List<Note> getNotes(){
//        return mNotes;
        List<Note> notes=new ArrayList<>();
        NoteCursorWrapper cursorWrapper=queryNotes(null,null);
        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                notes.add(cursorWrapper.getNote());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }
        return notes;

    }

    public Note getNote(UUID id){
//        for (Note note:mNotes){
//            if(note.getId().equals(id)){
//                return note;
//            }
//        }
        NoteCursorWrapper noteCursorWrapper=
                queryNotes(NoteTable.Cols.UUID+" =? ",new String[]{id.toString()});
        try {
            if (noteCursorWrapper.getCount() == 0) {
                return null;
            }
            noteCursorWrapper.moveToFirst();
            return noteCursorWrapper.getNote();
        } finally {
            noteCursorWrapper.close();
        }
    }
    public void addNote(Note note) {
        ContentValues contentValues = getContentValues(note);
        mDatabase.insert(NoteTable.NAME, null, contentValues);
    }

    public void updateNote(Note note){
        String UUIDString=note.getId().toString();
        ContentValues contentValues=getContentValues(note);
        mDatabase.update(NoteTable.NAME, contentValues,
                NoteTable.Cols.UUID + " = ?",
                new String[] { UUIDString });
    }
    public void deleteCrime(Note note) {
        mDatabase.delete(NoteTable.NAME, NoteTable.Cols.UUID +
                " = ?", new String[]{note.getId().toString()});
    }
    private NoteCursorWrapper queryNotes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                NoteTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new NoteCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Note note){
        ContentValues contentValues=new ContentValues();
        contentValues.put(NoteTable.Cols.UUID,note.getId().toString());
        contentValues.put(NoteTable.Cols.TITLE,note.getTitle());
        contentValues.put(NoteTable.Cols.CREADATE, note.getCreateDate().getTime());
        contentValues.put(NoteTable.Cols.CONTAINER, note.getContainer());
        contentValues.put(NoteTable.Cols.ISVITAL,note.isVatal());
        contentValues.put(NoteTable.Cols.NOTETYPE,note.getSpinner());

        return contentValues;
    }
}
