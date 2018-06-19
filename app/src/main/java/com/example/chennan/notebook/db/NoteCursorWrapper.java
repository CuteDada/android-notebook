package com.example.chennan.notebook.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.chennan.notebook.Note;
import com.example.chennan.notebook.db.NoteDbSchema.NoteTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by chennan on 2018/6/5.
 */

public class NoteCursorWrapper extends CursorWrapper {
    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote(){
        String UUIDString=getString(getColumnIndex(NoteTable.Cols.UUID));
        String title=getString(getColumnIndex(NoteTable.Cols.TITLE));
        long date = getLong(getColumnIndex(NoteTable.Cols.CREADATE));
        String container=getString(getColumnIndex(NoteTable.Cols.CONTAINER));
        int isVatal = getInt(getColumnIndex(NoteTable.Cols.ISVITAL));
        String spinner=getString(getColumnIndex(NoteTable.Cols.NOTETYPE));

        Note note=new Note(UUID.fromString(UUIDString));
        note.setTitle(title);
        note.setCreateDate(new Date(date));
        note.setContainer(container);
        note.setVatal(isVatal!= 0);
        note.setSpinner(spinner);
        return note;
    }
}
