package ru.abityrienty.vyzi.utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Custom SQLiteOpenHelper for using database conveniently
 */

public class MyDBHelper extends SQLiteOpenHelper {

    private  static  final String DB_NAME = "vyziandroid.db";
    private static final int VERSION = 1;
    private static String DB_PATH;
    private Context myContext;

    public static String COLUMN_UNI = "university_name";
    public static String COLUMN_BRI = "brief_name";

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.myContext = context;
        DB_PATH ="/data/data/ru.abityrienty.vyzi/databases/"+DB_NAME;
    }

    public void create_db (){
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DB_PATH);
            if (!file.exists()) {
                this.getReadableDatabase();
                Log.d("DB", "I'M HERE!");
                //получаем локальную бд как поток
                myInput = myContext.getAssets().open(DB_NAME);
                // Путь к новой бд
                String outFileName = DB_PATH;

                // Открываем пустую бд
                myOutput = new FileOutputStream(outFileName);

                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        }
        catch(IOException ex){
            Log.d("DatabaseHelper", ex.getMessage());
        }
    }
    public SQLiteDatabase open()throws SQLException {

        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public boolean check_exist_db(){
        File file = new File(DB_PATH);
        return file.exists();
    }







    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
