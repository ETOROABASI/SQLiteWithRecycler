package com.example.android.sqlitewithrecycler.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.sqlitewithrecycler.data.DbContract;

import static com.example.android.sqlitewithrecycler.data.DbContract.DbEntryData.TABLE_NAME;

/**
 * Created by ETORO on 17/10/2017.
 */
//YOUR DATABASE CLASS MUST ALWAYS EXTEND SQLiteOpenHelper and implement onCReate and onUpgrade method, together with a constructor
public class DbHelper extends SQLiteOpenHelper {
    //CREATE VARIABLES TO HOLD THE INFO USED TO CREATE YOUR DB


    //YOU CAN PUT THESE STATIC VARIABLES IN A CONTRACT CLASS WHICH IMPLEMENTS BASECOLUMN (CHECK SQLite Master App)
    private static final String DATABASE_NAME = "student.db";
    private static final int DATABASE_VERSION = 1;


    //FOR SIMPLICITY WE MADE THE CONSTRUCTOR TO REQUIRE ONLY ONE ARGUMENT
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //SQLiteDatabase db =  this.getWritableDatabase();  //THIS IS FOR ERROR CORRECTION
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //THIS CREATES THE TABLE student_table
        String Createquery = "CREATE TABLE " + TABLE_NAME + "(" +
                DbContract.DbEntryData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbContract.DbEntryData.COL_FIRST_NAME + " TEXT NOT NULL, " +
                DbContract.DbEntryData.COL_LAST_NAME + " TEXT NOT NULL, " +
                DbContract.DbEntryData.COL_MARKS + " INTEGER NOT NULL, " +
                DbContract.DbEntryData.COL_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIME " +
                "); ";


        db.execSQL(Createquery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);  //THIS DROPS THE PREVIOUS VERSION OF THE DATABASE
        onCreate(db);                                      //THIS CREATES THE UPGRADED VERSION OF THE TABLE


    }

    public boolean addData(String fname, String lname, int marks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbContract.DbEntryData.COL_FIRST_NAME, fname);
        cv.put(DbContract.DbEntryData.COL_LAST_NAME, lname);
        cv.put(DbContract.DbEntryData.COL_MARKS, marks);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result== -1){
            return false;
        }
        else{
         return true;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.query(
                DbContract.DbEntryData.TABLE_NAME,
                null, // Column
                null, // Where clause
                null, // Arguments
                null, // Group by
                null, // having
                DbContract.DbEntryData.COL_TIMESTAMP // Sort_order
        );
    }

    public boolean deleteData(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        Boolean deleteQuery = db.delete(DbContract.DbEntryData.TABLE_NAME, DbContract.DbEntryData._ID + "=" + id, null) > 0;
        return deleteQuery;
    }

    public boolean updateData(String fname, String lname, int marks, long id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbContract.DbEntryData.COL_FIRST_NAME, fname);
        cv.put(DbContract.DbEntryData.COL_LAST_NAME, lname);
        cv.put(DbContract.DbEntryData.COL_MARKS, marks);
        db.update(DbContract.DbEntryData.TABLE_NAME,cv, DbContract.DbEntryData._ID + "="+ id, null);
        return true;
    }


}