package com.example.administrator.ex02;

import android.provider.BaseColumns;

/**
 * Created by ilandbt on 15/11/2015.
 */
public class AppEntryTimeContract {

    public AppEntryTimeContract() {};

    public static abstract class AppEntryTime implements  BaseColumns {

        public static final String TABLE_NAME = "SaveTime";
        // ID IS OUTOmat
        public static final String TIME0 = "entryTimeColumn0";
        public static final String TIME1 = "entryTimeColumn1";
        public static final String TIME2 = "entryTimeColumn2";
        public static final String TIME3 = "entryTimeColumn3";
        public static final String TIME4 = "entryTimeColumn4";
    }


}