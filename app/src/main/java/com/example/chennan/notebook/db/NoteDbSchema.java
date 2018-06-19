package com.example.chennan.notebook.db;

import java.util.Date;

/**
 * Created by chennan on 2018/6/5.
 */

public class NoteDbSchema {
    public static final class NoteTable{
        public static final String NAME="notes";

        public static final class Cols{
            public static final String UUID="uuid";
            public static final String TITLE="title";
            public static final String CREADATE="creadate";
            public static final String CONTAINER="container";
            public static final String ISVITAL="isvatal";
            public static final String NOTETYPE="notetype";

        }
    }
}
