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
        myDBHelper = new MyDBHelper(getApplicationContext());
        sqLiteDatabase = myDBHelper.open();

        //получаем данные из бд в виде курсора
        cursor =  sqLiteDatabase.query(TablesNames.UNIVERSITIES,
                new String[] {UniMainInfoConsts.ID,
                        UniMainInfoConsts.IMG_SRC, MyDBHelper.COLUMN_UNI,
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
