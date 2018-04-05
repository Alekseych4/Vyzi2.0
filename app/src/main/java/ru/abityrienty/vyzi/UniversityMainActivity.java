package ru.abityrienty.vyzi;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class UniversityMainActivity extends AppCompatActivity {

    Intent intent;
    long list_id;
    SQLiteDatabase sqLiteDatabase;
    MyDBHelper myDBHelper;
    Cursor cursor;
    TextView textView;
    String id;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Button button;
    Intent intent_send;
    int column_next_table;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab_uni = (FloatingActionButton) findViewById(R.id.fab_uni);
        fab_uni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        Bundle bundle = intent.getExtras();
        list_id = bundle.getLong("_id");
        id = Long.toString(list_id);
        textView = (TextView) findViewById(R.id.info);

    }

    @Override
    protected void onResume() {
        super.onResume();
        myDBHelper = new MyDBHelper(getApplicationContext());
        sqLiteDatabase = myDBHelper.open();


        cursor = sqLiteDatabase.query(TablesNames.UNIVERSITIES,
                new String[] {UniMainInfoConsts.UNI_FULL_NAME, UniMainInfoConsts.UNI_BRIEF_NAME,
                UniMainInfoConsts.IMG_SRC, UniMainInfoConsts.INFO_ABOUT, UniMainInfoConsts.NEXT_TABLE},
                "_id="+list_id,null,null,null,null,null);
        cursor.moveToFirst();
        //Getting the columns' indexes
        int column_name = cursor.getColumnIndex(UniMainInfoConsts.UNI_FULL_NAME);
        int column_brief_name = cursor.getColumnIndex(UniMainInfoConsts.UNI_BRIEF_NAME);
        int column_img = cursor.getColumnIndex(UniMainInfoConsts.IMG_SRC);
        int column_info = cursor.getColumnIndex(UniMainInfoConsts.INFO_ABOUT);
        column_next_table = cursor.getColumnIndex(UniMainInfoConsts.NEXT_TABLE);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        byte [] blob_img = cursor.getBlob(column_img);
        collapsingToolbarLayout.setBackground(decodeImg(blob_img));

        setTitle(String.valueOf(cursor.getString(column_brief_name)));


        textView.setText(cursor.getString(column_info));

        button = (Button) findViewById(R.id.directions_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_send = new Intent(getApplicationContext(), Directions_list.class);
                intent_send.putExtra("tb_name", cursor.getString(column_next_table));
                startActivity(intent_send);
            }
        });


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
