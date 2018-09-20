package org.d3ifcool.rememberactivities.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yoga Wahyu Yuwono on 20/09/2018.
 */

public class DBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME=RememberActivitiesContract.myContractEntry.DB_NAME;
    private static final int VERSION=RememberActivitiesContract.myContractEntry.version;
    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(RememberActivitiesContract.myContractEntry.create_table);

        sqLiteDatabase.execSQL(RememberActivitiesContract.pencapaianEntry.SQL_CREATE_PENCAPAIAN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            sqLiteDatabase.execSQL(RememberActivitiesContract.myContractEntry.DROP_TABLE);
            onCreate(sqLiteDatabase);
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR WHILE UPDATING DATABSE");
        }
        /*
        try {
            sqLiteDatabase.execSQL(myContract.secondMyentries.DROP_TABLE_second);
            onCreate(sqLiteDatabase);
        }catch (Exception E){

        }
        */
    }
}
