package ru.abityrienty.vyzi;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ru.abityrienty.vyzi.utils.DbHelperPref;
import ru.abityrienty.vyzi.utils.ListOfVyziAdapter;

public class Preferences extends AppCompatActivity {
    ListView listView;
    ListOfVyziAdapter listOfVyziAdapter;
    DbHelperPref dbHelperPref;
    SQLiteDatabase sqLiteDatabasePref;
    Cursor cursorPref;
    long idInTable;
    String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.list_preferences);

    }

    @Override
    protected void onResume() {
        super.onResume();


        dbHelperPref = new DbHelperPref(this);

        sqLiteDatabasePref = dbHelperPref.getReadableDatabase();

        cursorPref = sqLiteDatabasePref.query("preferences", null, null, null, null, null, null);
        cursorPref.moveToFirst();

        listOfVyziAdapter = new ListOfVyziAdapter(this, R.layout.layout_for_list,
                cursorPref, new String[]{"img_src", "name"},
                new int[]{R.id.imageView, R.id.main_text}, 0);
        listView.setAdapter(listOfVyziAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursorPref.moveToPosition(position);
                idInTable = cursorPref.getLong(cursorPref.getColumnIndex("tableId"));
                tableName = cursorPref.getString(cursorPref.getColumnIndex("tableName"));
                Intent sendIntent = new Intent(getBaseContext(), InstitutePage.class);
                sendIntent.putExtra("_id", idInTable);
                sendIntent.putExtra("tb_name", tableName);
                startActivity(sendIntent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sqLiteDatabasePref.close();
        cursorPref.close();
        System.gc();
    }
}
