package com.example.administrator.ex02;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

/**
 * Created by ilandbt on 15/11/2015.
 */
public class AppEntryTimeDAL {

    private AppEntryTimeDBHelper helper;

    public AppEntryTimeDAL(Context context){
        helper = new AppEntryTimeDBHelper(context);
    }

    public  void removeAll(){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(AppEntryTimeContract.AppEntryTime.TABLE_NAME, null, null);
    }

    // add colo
    public void addEntryTime(int complexity , int level , String time){
        //get DB
        SQLiteDatabase db = helper.getWritableDatabase();

        //values to save
        ContentValues values = new ContentValues();

        values.put(AppEntryTimeContract.AppEntryTime.COMPLEXITY , complexity);
        values.put(AppEntryTimeContract.AppEntryTime.LEVEL , level);
        values.put(AppEntryTimeContract.AppEntryTime.TIME , time);

        //save the values
        db.insert(AppEntryTimeContract.AppEntryTime.TABLE_NAME, null, values);
        db.close();
    }



    // not complite
    public void upDateEntryTime(int complexity , int level , String time){
        SQLiteDatabase db =helper.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(AppEntryTimeContract.AppEntryTime.TIME, time );

        // Which row to update, based on the ID
        String selection = AppEntryTimeContract.AppEntryTime.COMPLEXITY + " = " + complexity + " AND "
                + AppEntryTimeContract.AppEntryTime.LEVEL + " = " + level ;
        //String[] selectionArgs = { String.valueOf(rowId) };

        int count = db.update(AppEntryTimeContract.AppEntryTime.TABLE_NAME, values, selection, null);
    }


    //
    public Cursor getAllEntryTimesCursor(){
        //get DB
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + AppEntryTimeContract.AppEntryTime.TABLE_NAME, null);

        return c;
    }


    ///
    public ArrayList getAllEntryTimesList(){

        ArrayList entryTimes = new ArrayList();

        //get cursor
        Cursor c = getAllEntryTimesCursor();

        if (c != null) {
            while (c.moveToNext()) {
                //get column index
                int entryTimeColumnIndex;

                entryTimeColumnIndex = c.getColumnIndex(AppEntryTimeContract.AppEntryTime.COMPLEXITY);

                //get entry
                try{
                    String entryTime = c.getString(entryTimeColumnIndex);
                    entryTimes.add(entryTime);
                }
                //save in array
                catch (Exception e){
                    throw e;
                }

                entryTimeColumnIndex = c.getColumnIndex(AppEntryTimeContract.AppEntryTime.LEVEL);

                //get entry
                try{
                    String entryTime = c.getString(entryTimeColumnIndex);
                    entryTimes.add(entryTime);
                }
                //save in array
                catch (Exception e){
                    throw e;
                }

                entryTimeColumnIndex = c.getColumnIndex(AppEntryTimeContract.AppEntryTime.TIME);

                //get entry
                try{
                    String entryTime = c.getString(entryTimeColumnIndex);
                    entryTimes.add(entryTime);
                }
                //save in array
                catch (Exception e){
                    throw e;
                }

            }
        }
        return entryTimes;
    }

}