package ru.abityrienty.vyzi;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ru.abityrienty.vyzi.constants.UniMainInfoConsts;
import ru.abityrienty.vyzi.constants.TablesNames;
import ru.abityrienty.vyzi.utils.ListOfVyziAdapter;
import ru.abityrienty.vyzi.utils.MyDBHelper;


public class List_of_Vyzi_activity extends AppCompatActivity {

    MyDBHelper myDBHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ListView listView;
    ListOfVyziAdapter listOfVyziAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of__vyzi_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listView = (ListView) findViewById(R.id.list_of_vyzi);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), UniversityMainActivity.class);
                intent.putExtra("_id", id);

                startActivity(intent);
            }
        });


        // открываем подключение
        myDBHelper = new MyDBHelper(this);
        sqLiteDatabase = myDBHelper.open();

        //получаем данные из бд в виде курсора
        cursor =  sqLiteDatabase.query(TablesNames.UNIVERSITIES,
                new String[] {UniMainInfoConsts.ID,UniMainInfoConsts.IMG_SRC, MyDBHelper.COLUMN_UNI,
                        MyDBHelper.COLUMN_BRI}, null, null,null,null,null);

        // определяем, какие столбцы из курсора будут выводиться в ListView
        cursor.moveToFirst();
        String[] headers = new String[] {UniMainInfoConsts.IMG_SRC, MyDBHelper.COLUMN_UNI, MyDBHelper.COLUMN_BRI};
        // создаем адаптер, передаем в него курсор
        listOfVyziAdapter = new ListOfVyziAdapter(this, R.layout.layout_for_list,
                cursor, headers, new int[]{R.id.imageView, R.id.main_text, R.id.sub_text}, 0);

        listView.setAdapter(listOfVyziAdapter);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        sqLiteDatabase.close();
        cursor.close();
        myDBHelper.close();
    }


    public void openAddComment (View v){
        Intent intent = new Intent(this, Add_comment_activity.class);
        startActivity(intent);
    }

}
