package ru.abityrienty.vyzi;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;

import ru.abityrienty.vyzi.constants.DirectionsTableColumns;
import ru.abityrienty.vyzi.utils.DbHelperPref;

public class InstitutePage extends AppCompatActivity {
    Intent receiveIntent;
    long listId;
    String tableName;
    Toolbar toolbar;
    MyDBHelper myDBHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView textView;
    DbHelperPref preferHelper, dbForStar;
    SQLiteDatabase databasePref, sqliteForStar;
    FloatingActionButton star;
    InputStream inputStream;
    Drawable drawable;
    int column_name;
    String img;
    final String DB_PATH = "/data/data/ru.abityrienty.vyzi/databases/preferences";
    File fileCheck;
    TextView director, phone, loc, email, inst_name;
    ExpandableListView expandableListView;
    SimpleCursorTreeAdapter simpleCursorTreeAdapter;
    Cursor c;
    ImageView collapse_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_page);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collaps_tool_institute);
        collapse_img = (ImageView) findViewById(R.id.inst_collapse_img);
        textView = (TextView) findViewById(R.id.institute_text);
        star = (FloatingActionButton) findViewById(R.id.fab_inst);
        director = (TextView) findViewById(R.id.director);
        phone =(TextView) findViewById(R.id.phone);
        loc = (TextView) findViewById(R.id.location);
        email = (TextView) findViewById(R.id.email);
        inst_name = (TextView) findViewById(R.id.inst_name);
        expandableListView = (ExpandableListView) findViewById(R.id.inst_expandable);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        receiveIntent = getIntent();
        Bundle bundle = receiveIntent.getExtras();
        listId = bundle.getLong("_id");
        tableName = bundle.getString("tb_name");



        //Для нормальной работы звёздочки
        fileCheck = new File(DB_PATH);
        if (fileCheck.exists()) {
            dbForStar = new DbHelperPref(getApplicationContext());
            sqliteForStar = dbForStar.getReadableDatabase();
            Cursor curForStar = sqliteForStar.query("preferences", new String[]{"tableId", "tableName"},
                    null, null, null, null, null);
            if(curForStar.moveToFirst()){
                curForStar.moveToFirst();
                do {
                    int idInd = curForStar.getInt(curForStar.getColumnIndex("tableId"));
                    String tName = curForStar.getString(curForStar.getColumnIndex("tableName"));
                    if((idInd==listId)&&(tName.equals(tableName))){
                        star.setImageResource(R.drawable.ic_star_pressed_24dp);
                        break;
                    }
                }   while (curForStar.moveToNext());
            }
            sqliteForStar.close();
            curForStar.close();
        }

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!fileCheck.exists()){
                    Log.d("Lk", "HERE");
                    preferHelper = new DbHelperPref(getApplicationContext());
                    ContentValues contentValues = new ContentValues();
                    databasePref = preferHelper.getWritableDatabase();

                    contentValues.put("tableId", listId);
                    contentValues.put("tableName", tableName);
                    contentValues.put("name", cursor.getString(column_name));
                    contentValues.put("img_src", img);
                    databasePref.insert("preferences", null, contentValues);

                    star.setImageResource(R.drawable.ic_star_pressed_24dp);
                    Toast.makeText(getApplicationContext(),"Добавлено в избранное", Toast.LENGTH_LONG).show();
                    databasePref.close();
                }   else {
                    preferHelper = new DbHelperPref(getApplicationContext());
                    databasePref = preferHelper.getWritableDatabase();
                    Cursor cursor1 = databasePref.query("preferences", new String[] {"tableId", "tableName"},
                            null,null,null,null,null);
                    if(cursor1.moveToFirst()){
                            cursor1.moveToFirst();
                            boolean k = true;
                        do {
                            int idInd = cursor1.getInt(cursor1.getColumnIndex("tableId"));
                            String tName = cursor1.getString(cursor1.getColumnIndex("tableName"));
                            if((idInd==listId)&&(tName.equals(tableName))){
                                databasePref.delete("preferences", "tableId = "+idInd+" and "+
                                        "tableName = "+"'"+tName+"'",null);
                                star.setImageResource(R.drawable.ic_star_unpressed_24dp);
                                Toast.makeText(getApplicationContext(),"Удалено из избранного", Toast.LENGTH_LONG).show();
                                k = false;
                                break;
                            }
                        } while (cursor1.moveToNext());
                        if (k){
                            ContentValues cv = new ContentValues();
                            cv.put("tableId", listId);
                            cv.put("tableName", tableName);
                            cv.put("name", cursor.getString(column_name));
                            cv.put("img_src", img);
                            databasePref.insert("preferences", null, cv);
                            star.setImageResource(R.drawable.ic_star_pressed_24dp);
                            Toast.makeText(getApplicationContext(),"Добавлено в избранное", Toast.LENGTH_LONG).show();
                        }

                    }   else {
                        ContentValues cv = new ContentValues();
                        cv.put("tableId", listId);
                        cv.put("tableName", tableName);
                        cv.put("name", cursor.getString(column_name));
                        cv.put("img_src", img);
                        databasePref.insert("preferences", null, cv);
                        star.setImageResource(R.drawable.ic_star_pressed_24dp);
                        Toast.makeText(getApplicationContext(),"Добавлено в избранное", Toast.LENGTH_LONG).show();
                    }
                    cursor1.close();
                    databasePref.close();
                }
            }
        });





        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        myDBHelper = new MyDBHelper(getApplicationContext());
        sqLiteDatabase = myDBHelper.open();
        cursor = sqLiteDatabase.query(tableName, new String[]{DirectionsTableColumns.IMG_SRC,
        DirectionsTableColumns.NAME, DirectionsTableColumns.INFO, "director", "email", "location", "main_phone"},
                "_id="+listId,
                null,null,null,null);

        cursor.moveToFirst();
        column_name = cursor.getColumnIndex(DirectionsTableColumns.NAME);
        int column_img = cursor.getColumnIndex(DirectionsTableColumns.IMG_SRC);
        int column_info = cursor.getColumnIndex(DirectionsTableColumns.INFO);

        img = cursor.getString(column_img);
        Uri uri = Uri.parse(img);
        Picasso.with(this).load(uri).fit().into(collapse_img);

        setTitle(null);
        textView.setText(cursor.getString(column_info));

        inst_name.setText(cursor.getString(column_name));
        director.setText(cursor.getString(cursor.getColumnIndex("director")));
        phone.setText(cursor.getString(cursor.getColumnIndex("main_phone")));
        email.setText(cursor.getString(cursor.getColumnIndex("email")));
        loc.setText(cursor.getString(cursor.getColumnIndex("location")));

        c = sqLiteDatabase.query(tableName, new String[]{"_id",DirectionsTableColumns.NAME, DirectionsTableColumns.INFO},
                null, null,null,null,null);
        c.moveToFirst();
        String [] groupFrom = {DirectionsTableColumns.NAME};
        int [] groupTo = {android.R.id.text1};
        String [] childFrom = {"info"};
        int [] childTo = {android.R.id.text1};

        simpleCursorTreeAdapter = new SimpleCursorTreeAdapter(getApplicationContext(), c,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom, groupTo, android.R.layout.simple_list_item_1, childFrom, childTo) {
            @Override
            protected Cursor getChildrenCursor(Cursor groupCursor) {
                int id = groupCursor.getInt(groupCursor.getColumnIndex("_id"));
                MyDBHelper md = new MyDBHelper(getApplicationContext());
                SQLiteDatabase sq = md.open();
                Cursor cursorChild = sq.query("dop", new String [] {"_id","info"},"_id=2",null,null,null,null);
                return cursorChild;
            }
        };

        expandableListView.setAdapter(simpleCursorTreeAdapter);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        sqLiteDatabase.close();
        myDBHelper.close();
        cursor.close();
        System.gc();
        Log.d("DESTROY", "InstPage has destroyed");
    }


}
