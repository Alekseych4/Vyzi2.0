package ru.abityrienty.vyzi;

import android.app.LoaderManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;


public class List_of_Vyzi_activity extends AppCompatActivity {

    MyDBHelper myDBHelper;
    SQLiteDatabase sqLiteDatabase;
    SimpleCursorAdapter simpleCursorAdapter;
    Cursor cursor;
    ListView listView;
    ListOfVyziAdapter listOfVyziAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of__vyzi_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        myDBHelper = new MyDBHelper(getApplicationContext());
        // создаем базу данных

          if(!myDBHelper.check_exist_db()){
        myDBHelper.create_db();
        }

        listView = (ListView) findViewById(R.id.list_of_vyzi);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UniversityMainActivity.class);
                intent.putExtra("_id", id);

                startActivity(intent);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        sqLiteDatabase = myDBHelper.open();
        Log.d("DB","DB opened");
        //получаем данные из бд в виде курсора
        //String [] columns = {"university_name"};
        cursor =  sqLiteDatabase.rawQuery("select * from "+TablesNames.UNIVERSITIES, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        cursor.moveToFirst();
        String[] headers = new String[] {UniMainInfoConsts.IMG_SRC, MyDBHelper.COLUMN_UNI, MyDBHelper.COLUMN_BRI};
        // создаем адаптер, передаем в него курсор
        listOfVyziAdapter = new ListOfVyziAdapter(this, R.layout.layout_for_list,
                cursor, headers, new int[]{R.id.imageView, R.id.main_text, R.id.sub_text}, 0);

        listView.setAdapter(listOfVyziAdapter);


        /*String[] headers = new String[] {MyDBHelper.COLUMN_UNI, MyDBHelper.COLUMN_BRI};
        cursor = sqLiteDatabase.query(TablesNames.UNIVERSITIES, headers, null, null,null,null,null);
        simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, cursor, headers,
                new int[]{android.R.id.text1, android.R.id.text2},0);
        list_of_vyzi.setAdapter(simpleCursorAdapter);*/
    }

    public void add(View view){
        Intent intent = new Intent(this, UniversityMainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        sqLiteDatabase.close();
        cursor.close();
    }





    public void openAddComment (View v){
        Intent intent = new Intent(this, Add_comment_activity.class);
        startActivity(intent);
    }



}
