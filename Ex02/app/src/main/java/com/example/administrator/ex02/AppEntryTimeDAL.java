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
    int level;
    String complexity;

    public AppEntryTimeDAL(Context context , int level , String complexity){
        helper = new AppEntryTimeDBHelper(context);
        this.level = level;
        this.complexity = complexity;
    }

    public void addEntryTime(String time){
        //get DB
        SQLiteDatabase db = helper.getWritableDatabase();

        //values to save
        ContentValues values = new ContentValues();
        switch (complexity){
            case "0":
                values.put(AppEntryTimeContract.AppEntryTime.TIME0
                        + "WHERE " + AppEntryTimeContract.AppEntryTime._ID + " = " + level , time);
                break;
            case "1":
                values.put(AppEntryTimeContract.AppEntryTime.TIME1
                        + "WHERE " + AppEntryTimeContract.AppEntryTime._ID + " = " + level , time);
                break;
            case "2":
                values.put(AppEntryTimeContract.AppEntryTime.TIME2
                        + "WHERE " + AppEntryTimeContract.AppEntryTime._ID + " = " + level , time);
                break;
            case "3":
                values.put(AppEntryTimeContract.AppEntryTime.TIME3
                        + "WHERE " + AppEntryTimeContract.AppEntryTime._ID + " = " + level , time);
                break;
            case "4":
                values.put(AppEntryTimeContract.AppEntryTime.TIME4
                        + "WHERE " + AppEntryTimeContract.AppEntryTime._ID + " = " + level , time);
                break;
        }

        //save the values
        db.insert(AppEntryTimeContract.AppEntryTime.TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getAllEntryTimesCursor(){
        //get DB
        SQLiteDatabase db = helper.getReadableDatabase();


        Cursor c = db.rawQuery("SELECT * FROM " + AppEntryTimeContract.AppEntryTime.TABLE_NAME +
                "WHERE " + AppEntryTimeContract.AppEntryTime._ID + " = " + level , null );

        return c;
    }

    public ArrayList getAllEntryTimesList(){

        ArrayList entryTimes = new ArrayList();

        //get cursor
        Cursor c = getAllEntryTimesCursor();

        if (c != null) {
            while (c.moveToNext()) {
                //get column index
                int entryTimeColumnIndex;
                switch (complexity) {
                    case "0":
                        entryTimeColumnIndex = c.getColumnIndex(AppEntryTimeContract.AppEntryTime.TIME0);
                        break;
                    case "1":
                        entryTimeColumnIndex = c.getColumnIndex(AppEntryTimeContract.AppEntryTime.TIME1);
                        break;
                    case "2":
                        entryTimeColumnIndex = c.getColumnIndex(AppEntryTimeContract.AppEntryTime.TIME2);
                        break;
                    case "3":
                        entryTimeColumnIndex = c.getColumnIndex(AppEntryTimeContract.AppEntryTime.TIME3);
                        break;
                    case "4":
                        entryTimeColumnIndex = c.getColumnIndex(AppEntryTimeContract.AppEntryTime.TIME4);
                        break;
                    default:
                        entryTimeColumnIndex = -1;
                }


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