package ru.abityrienty.vyzi;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

public class Preferences extends AppCompatActivity {
    ListView listView;
    ListOfVyziAdapter adapter;
    DBhelper1 dBhelper1;
    SQLiteDatabase sqLiteDatabasePref;
    Cursor cursorPref;
    Cursor cursor;
    MyDBHelper myDBHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.list_preferences);
        dBhelper1 = new DBhelper1(getApplicationContext());
        try {
        sqLiteDatabasePref = dBhelper1.open();



        cursorPref = sqLiteDatabasePref.query("preferences", null, null, null, null, null, null);
        cursorPref.moveToFirst();

        do {
            int id = cursorPref.getInt(cursorPref.getColumnIndex("_id"));
            String tName = cursorPref.getString(cursorPref.getColumnIndex("tableName"));

            myDBHelper = new MyDBHelper(getApplicationContext());
            sqLiteDatabase = myDBHelper.open();
            cursor = sqLiteDatabase.query(tName, new String[]{DirectionsTableColumns.ID, DirectionsTableColumns.NAME,
                    DirectionsTableColumns.IMG_SRC}, "_id=" + id, null, null, null, null);
            cursor.moveToFirst();
            adapter = new ListOfVyziAdapter(getApplicationContext(), R.layout.layout_for_list, cursor,
                    new String[]{DirectionsTableColumns.IMG_SRC, DirectionsTableColumns.NAME},
                    new int[]{R.id.imageView, R.id.main_text}, 0);

        } while (cursorPref.moveToNext());}
        finally {

        }

    }

    class DBhelper1 extends SQLiteOpenHelper {
        public DBhelper1(Context context) {
            super(context, "preferences", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table preferences ("
                    + "_id integer,"
                    + "tableName text" + ");");
        }
        public SQLiteDatabase open()throws SQLException {

            return SQLiteDatabase.openDatabase("/data/data/ru.abityrienty.vyzi/databases/"+"preferences.db",
                    null, SQLiteDatabase.OPEN_READWRITE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
