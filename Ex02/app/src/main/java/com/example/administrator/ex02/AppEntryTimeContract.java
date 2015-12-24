package com.example.administrator.ex02;

import android.provider.BaseColumns;

/**
 * Created by ilandbt on 15/11/2015.
 */
public class AppEntryTimeContract {

    public AppEntryTimeContract() {};

    public static abstract class AppEntryTime implements  BaseColumns {


        public static final String TABLE_NAME = "SaveTime"; // table name
        // ID column automatically
        public static final String COMPLEXITY = "complexity";
        public static final String LEVEL = "level";
        public static final String TIME = "time";
    }


}