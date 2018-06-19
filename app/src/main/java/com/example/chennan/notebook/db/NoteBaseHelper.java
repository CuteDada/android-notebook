package com.example.chennan.notebook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.chennan.notebook.db.NoteDbSchema.NoteTable;

/**
 * Created by chennan on 2018/6/5.
 */

public class NoteBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="noteBase.db";

    public NoteBaseHelper(Context context) {
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+ NoteTable.NAME +
                "(" + " _id integer primary key autoincrement, " +
                NoteTable.Cols.UUID + ", " +
                NoteTable.Cols.TITLE + ", " +
                NoteTable.Cols.CREADATE + ", " +
                NoteTable.Cols.CONTAINER + ", " +
                NoteTable.Cols.ISVITAL +", " +
                NoteTable.Cols.NOTETYPE +
                ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
