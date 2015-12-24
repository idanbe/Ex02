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
    private final String str_init = "00:00";


    public AppEntryTimeDAL(Context context){
        helper = new AppEntryTimeDBHelper(context);
    }


    // add row to DB
    public void addTime(int complexity , int level , String time){
        SQLiteDatabase db = helper.getWritableDatabase();

        // if the element is exist then need upData
        if (thereIsRow(complexity, level)){
            upDateEntryTime(complexity, level, time);
        }

        // if element not exist then add element
        else {
            //values to save
            ContentValues values = new ContentValues();

            values.put(AppEntryTimeContract.AppEntryTime.COMPLEXITY, complexity);
            values.put(AppEntryTimeContract.AppEntryTime.LEVEL, level);
            values.put(AppEntryTimeContract.AppEntryTime.TIME, time);

            //save the values
            db.insert(AppEntryTimeContract.AppEntryTime.TABLE_NAME, null, values);
            db.close();
        }
    }


    // upDate DB
    public void upDateEntryTime(int complexity , int level , String time){
        SQLiteDatabase db = helper.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(AppEntryTimeContract.AppEntryTime.TIME, time );

        // Which row to update, based on the ID
        String selection = AppEntryTimeContract.AppEntryTime.COMPLEXITY + " = " + complexity + " AND "
                + AppEntryTimeContract.AppEntryTime.LEVEL + " = " + level ;
        //String[] selectionArgs = { String.valueOf(rowId) };

        int count = db.update(AppEntryTimeContract.AppEntryTime.TABLE_NAME, values, selection, null);
    }


    // TODO : remove this function  , only for test
    // remove all rows
    public  void removeAll(){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(AppEntryTimeContract.AppEntryTime.TABLE_NAME, null, null);
    }


    // TODO : remove this function  , only for test
    // remove one row
    public  void removeRow(int complexity , int level){
        SQLiteDatabase db = helper.getWritableDatabase();

        String selection = AppEntryTimeContract.AppEntryTime.COMPLEXITY + " = " + complexity + " AND "
                + AppEntryTimeContract.AppEntryTime.LEVEL + " = " + level ;

        db.delete(AppEntryTimeContract.AppEntryTime.TABLE_NAME, selection, null);
    }

    // if there is row with this complexity and level
    private Boolean thereIsRow(int complexity , int level){
        Cursor c = getRow(complexity, level);
            if(c.moveToNext()){
                return true;
            }
        return false;
    }

    // get row according to complexity and level
    private Cursor getRow(int complexity , int level){
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + AppEntryTimeContract.AppEntryTime.TABLE_NAME + " WHERE "
                + AppEntryTimeContract.AppEntryTime.COMPLEXITY + " = " + complexity + " AND "
                + AppEntryTimeContract.AppEntryTime.LEVEL + " = " + level, null);

        return c;
    }

    // TODO : remove this function , only for test
    // get record according to complexity and level
    public String getRecord(int complexity , int level){

        String timeString = str_init;

        Cursor c = getRow(complexity, level); //get cursor on start row

        if (c != null) {
            while (c.moveToNext()) {

                //get column index
                int entryTimeColumnIndex = c.getColumnIndex(AppEntryTimeContract.AppEntryTime.TIME);

                try{
                    timeString = c.getString(entryTimeColumnIndex);
                }
                catch (Exception e){
                }
            }
        }
        return timeString;
    }


    /************************************************************************/

   // TODO : remove this function  , only for test
    public Cursor getAllDBCursor(){
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + AppEntryTimeContract.AppEntryTime.TABLE_NAME, null);

        return c;
    }
    // TODO : remove this function , only for test
    // write all DB in ArrayList
    public ArrayList getDb(){

        ArrayList entryTimes = new ArrayList();

        //get cursor
        Cursor c = getAllDBCursor();

        if (c != null) {
            while (c.moveToNext()) {
                //get column index

                int entryTimeColumnIndex = c.getColumnIndex(AppEntryTimeContract.AppEntryTime.COMPLEXITY);

                //get entry
                try{
                    String entryTime = c.getString(entryTimeColumnIndex);
                    entryTimes.add(entryTime);
                }
                //save in array
                catch (Exception e){
                }

                entryTimeColumnIndex = c.getColumnIndex(AppEntryTimeContract.AppEntryTime.LEVEL);

                //get entry
                try{
                    String entryTime = c.getString(entryTimeColumnIndex);
                    entryTimes.add(entryTime);
                }
                //save in array
                catch (Exception e){
                }

                entryTimeColumnIndex = c.getColumnIndex(AppEntryTimeContract.AppEntryTime.TIME);

                //get entry
                try{
                    String entryTime = c.getString(entryTimeColumnIndex);
                    entryTimes.add(entryTime);
                }
                //save in array
                catch (Exception e){

                }

            }

        }
        return entryTimes;
    }


/*****************************************************************/

}