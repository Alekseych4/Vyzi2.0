package ru.abityrienty.vyzi.utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

/**
 * Custom SQLiteOpenHelper for using database of preferences conveniently
 */

public class DbHelperPref extends SQLiteOpenHelper {
    public DbHelperPref(Context context) {
        super(context, "preferences", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table preferences ("
                + "_id integer primary key autoincrement, " +"tableId integer, "
                + "tableName text, "+"name text, " + "img_src text"+");");
        Log.d("DB NEW", "DB pref created");
    }
    public SQLiteDatabase open()throws SQLException {

        return SQLiteDatabase.openDatabase("/data/data/ru.abityrienty.vyzi/databases/preferences",
                null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean check_exist_db(){
        File file = new File("/data/data/ru.abityrienty.vyzi/databases/preferences");
        return file.exists();
    }
}


