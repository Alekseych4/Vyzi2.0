package ru.abityrienty.vyzi;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        cursor.close();
    }
}
