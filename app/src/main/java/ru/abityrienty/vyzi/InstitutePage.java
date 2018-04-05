package ru.abityrienty.vyzi;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.File;

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
    DBhelper preferHelper;
    SQLiteDatabase databasePref;
    FloatingActionButton star;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_page);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collaps_tool_institute);
        textView = (TextView) findViewById(R.id.institute_text);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        receiveIntent = getIntent();
        Bundle bundle = receiveIntent.getExtras();
        listId = bundle.getLong("_id");
        tableName = bundle.getString("tb_name");





        star = (FloatingActionButton) findViewById(R.id.fab_inst);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String DB_PATH = "/data/data/ru.abityrienty.vyzi/databases/"+"preferences.db";
                File file = new File(DB_PATH);
                if (!file.exists()){
                    preferHelper = new DBhelper(getApplicationContext());
                    ContentValues contentValues = new ContentValues();
                    databasePref = preferHelper.getWritableDatabase();
                    contentValues.put("_id", listId);
                    contentValues.put("tableName", tableName);
                    databasePref.insert("preferences", null, contentValues);
                    star.setImageResource(R.drawable.ic_star_pressed_24dp);
                    databasePref.close();
                }   else {
                    preferHelper = new DBhelper(getApplicationContext());
                    databasePref = preferHelper.getWritableDatabase();
                    Cursor cursor1 = databasePref.query("preferences",null,null,null,null,null,null);
                    if(cursor1.moveToFirst()){

                        do {
                            int idInd = cursor1.getInt(cursor1.getColumnIndex("_id"));
                            String tName = cursor1.getString(cursor1.getColumnIndex("tableName"));
                            if((idInd==listId)&&(tName.equals(tableName))){
                                databasePref.delete("preferences", "_id = "+listId+" and "+
                                        "tableName = "+"'"+tableName+"'",null);
                                star.setImageResource(R.drawable.ic_star_unpressed_24dp);
                            }
                        } while (cursor1.moveToNext());

                    }   else {
                        ContentValues cv = new ContentValues();
                        cv.put("_id", listId);
                        cv.put("tableName", tableName);
                        databasePref.insert("preferences", null, cv);
                        star.setImageResource(R.drawable.ic_star_pressed_24dp);
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

    }

    @Override
    protected void onStart() {
        super.onStart();
        myDBHelper = new MyDBHelper(getApplicationContext());
        sqLiteDatabase = myDBHelper.open();
        cursor = sqLiteDatabase.query(tableName, new String[]{DirectionsTableColumns.IMG_SRC,
        DirectionsTableColumns.NAME, DirectionsTableColumns.INFO},"_id="+listId,null,null,null,null);

        cursor.moveToFirst();
        int column_name = cursor.getColumnIndex(DirectionsTableColumns.NAME);
        int column_img = cursor.getColumnIndex(DirectionsTableColumns.IMG_SRC);
        int column_info = cursor.getColumnIndex(DirectionsTableColumns.INFO);

        byte [] blob_img = cursor.getBlob(column_img);
        collapsingToolbarLayout.setBackground(decodeImg(blob_img));
        setTitle(cursor.getString(column_name));
        textView.setText(cursor.getString(column_info));
    }
    public  static Drawable decodeImg (byte [] img){
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        Drawable drawable = new BitmapDrawable(Resources.getSystem(), bitmap);
        return drawable;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        sqLiteDatabase.close();
        myDBHelper.close();
        cursor.close();
    }

    class DBhelper extends SQLiteOpenHelper {
        public DBhelper(Context context) {
            super(context, "preferences", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table preferences ("
                    + "_id integer,"
                    + "tableName text" + ");");
            Log.d("DB NEW", "DB pref created");
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
