package ru.abityrienty.vyzi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

public class Directions_list extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    MyDBHelper myDBHelper;
    SimpleCursorAdapter simpleCursorAdapter;
    Cursor cursor;
    Context context;
    ListOfVyziAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.directions_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myDBHelper = new MyDBHelper(getApplicationContext());
        sqLiteDatabase = myDBHelper.open();
        cursor = sqLiteDatabase.query(TablesNames.UNIVERSITY_BRIEF_INFO, null,
                null, null, null, null,null);
        Log.d("Check","Before adapter");
        cursor.moveToFirst();
        int index = cursor.getColumnIndex("img_src");
        adapter = new ListOfVyziAdapter(getApplicationContext(), R.layout.layout_for_list,
                cursor, new String[] {"img_src", "university_name", "www"},
                new int [] {R.id.imageView, R.id.main_text, R.id.sub_text},0);
        Log.d("Check","After initializing adapter");
        listView.setAdapter(adapter);
        Log.d("Check","After setting adapter");
        context = getApplicationContext();
        String name = context.getFilesDir().getPath();

        //int resId = cursor.getString(index);
        //imageView.setImageResource(resId);
    }

}
