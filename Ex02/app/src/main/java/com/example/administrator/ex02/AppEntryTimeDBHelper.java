package com.example.administrator.ex02;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ilandbt on 15/11/2015.
 */
public class AppEntryTimeDBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AppTimeEntries.db";


    public AppEntryTimeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + AppEntryTimeContract.AppEntryTime.TABLE_NAME  + " ( " +
                        AppEntryTimeContract.AppEntryTime.COMPLEXITY + " INTEGER ," +
                        AppEntryTimeContract.AppEntryTime.LEVEL + " INTEGER ," +
                        AppEntryTimeContract.AppEntryTime.TIME + " TEXT" + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + AppEntryTimeContract.AppEntryTime.TABLE_NAME);
        onCreate(db);
    }
}