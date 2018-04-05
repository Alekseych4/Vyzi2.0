package ru.abityrienty.vyzi;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Directions_list extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    MyDBHelper myDBHelper;
    Cursor cursor;
    ListOfVyziAdapter adapter;
    ListView listView;
    String tableName;
    Intent receiveIntent, sendIntent;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions_list);

        receiveIntent = getIntent();
        bundle = receiveIntent.getExtras();
        tableName = bundle.getString("tb_name");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.directions_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        myDBHelper = new MyDBHelper(getApplicationContext());
        sqLiteDatabase = myDBHelper.open();
        cursor = sqLiteDatabase.query(tableName, new String[]{DirectionsTableColumns.ID,DirectionsTableColumns.NAME,
                        DirectionsTableColumns.IMG_SRC, "director"},
                null, null, null, null,null);

        cursor.moveToFirst();

        adapter = new ListOfVyziAdapter(getApplicationContext(), R.layout.layout_for_list,
                cursor, new String[] {DirectionsTableColumns.IMG_SRC, DirectionsTableColumns.NAME},
                new int [] {R.id.imageView, R.id.main_text},0);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendIntent = new Intent(getApplicationContext(), InstitutePage.class);
                sendIntent.putExtra("_id", id);
                sendIntent.putExtra("tb_name", tableName);
                startActivity(sendIntent);
            }
        });



    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        sqLiteDatabase.close();
        cursor.close();
        myDBHelper.close();
    }

}
