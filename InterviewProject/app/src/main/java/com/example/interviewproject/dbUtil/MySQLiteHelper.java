package com.example.interviewproject.dbUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jinyoung on 2015-12-04.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public MySQLiteHelper(Context context) {
        super(context, "person.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table person (_id integer primary key autoincrement, " +
                "name text not null, age integer not null, gender integer not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS person");
        onCreate(db);
    }
}
