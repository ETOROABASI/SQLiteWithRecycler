package com.example.android.sqlitewithrecycler.data;

import android.provider.BaseColumns;

import java.sql.Timestamp;

/**
 * Created by ETORO on 17/10/2017.
 */

public class DbContract {


    public static final class DbEntryData implements BaseColumns{
        public static final String TABLE_NAME = "student_table";
        public static final String COL_FIRST_NAME = "FIRST_NAME";
        public static final String COL_LAST_NAME = "LAST_NAME";
        public static final String COL_MARKS = "MARKS";
        public static final String COL_TIMESTAMP = "TIMESTAMP";
    }

}
