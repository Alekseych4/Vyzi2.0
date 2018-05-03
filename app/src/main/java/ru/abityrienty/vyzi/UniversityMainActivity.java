package ru.abityrienty.vyzi;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;

import ru.abityrienty.vyzi.constants.UniMainInfoConsts;
import ru.abityrienty.vyzi.utils.Decoding_bitmaps;
import ru.abityrienty.vyzi.constants.TablesNames;
import ru.abityrienty.vyzi.utils.MyDBHelper;

public class UniversityMainActivity extends AppCompatActivity {

    Intent intent;
    long list_id;
    SQLiteDatabase sqLiteDatabase;
    MyDBHelper myDBHelper;
    Cursor cursor;
    TextView textView;
    String id;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Decoding_bitmaps decoding_bitmaps;
    Button button;
    Intent intent_send;
    Drawable drawable;
    InputStream inputStream;
    int column_next_table;
    TextView rector, phone, location, www;
    ImageView collapseImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        Bundle bundle = intent.getExtras();
        list_id = bundle.getLong("_id");
        id = Long.toString(list_id);

        textView = (TextView) findViewById(R.id.info);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapseImage = (ImageView) findViewById(R.id.collaps_image);
        rector = (TextView) findViewById(R.id.rector);
        phone = (TextView) findViewById(R.id.phone_uni);
        location = (TextView) findViewById(R.id.location_uni);
        www = (TextView) findViewById(R.id.www_uni);

        //Database
        myDBHelper = new MyDBHelper(getApplicationContext());
        sqLiteDatabase = myDBHelper.open();
        cursor = sqLiteDatabase.query(TablesNames.UNIVERSITIES,
                new String[] {UniMainInfoConsts.UNI_FULL_NAME, UniMainInfoConsts.UNI_BRIEF_NAME,
                        UniMainInfoConsts.IMG_SRC, UniMainInfoConsts.INFO_ABOUT, UniMainInfoConsts.NEXT_TABLE,
                UniMainInfoConsts.PHONE, UniMainInfoConsts.LOCATION, UniMainInfoConsts.RECTOR, UniMainInfoConsts.WWW},
                "_id="+list_id,null,null,null,null,null);
        cursor.moveToFirst();

        //Getting the columns' indexes
        int column_name = cursor.getColumnIndex(UniMainInfoConsts.UNI_FULL_NAME);
        int column_brief_name = cursor.getColumnIndex(UniMainInfoConsts.UNI_BRIEF_NAME);
        int column_img = cursor.getColumnIndex(UniMainInfoConsts.IMG_SRC);
        int column_info = cursor.getColumnIndex(UniMainInfoConsts.INFO_ABOUT);
        column_next_table = cursor.getColumnIndex(UniMainInfoConsts.NEXT_TABLE);

        rector.setText(cursor.getString(cursor.getColumnIndex(UniMainInfoConsts.RECTOR)));
        www.setText(cursor.getString(cursor.getColumnIndex(UniMainInfoConsts.WWW)));
        phone.setText(cursor.getString(cursor.getColumnIndex(UniMainInfoConsts.PHONE)));
        location.setText(cursor.getString(cursor.getColumnIndex(UniMainInfoConsts.LOCATION)));

        String img = cursor.getString(column_img);
        Uri uri = Uri.parse(img);
        Picasso.with(this).load(uri).fit().into(collapseImage);
        /*try {
            Bitmap bitmap = Picasso.with(this).load(img).fit().get();
            Drawable drawable = new BitmapDrawable(getResources(),bitmap);
            collapsingToolbarLayout.setBackground(drawable);

        } catch (IOException e) {

            e.printStackTrace();
        }*/
        /*Uri uri = Uri.parse(img);
        try{
            inputStream = getContentResolver().openInputStream(uri);
            drawable = Drawable.createFromStream(inputStream, uri.toString());
        }  catch (FileNotFoundException e) {
            drawable = Drawable.createFromPath("android.resource://ru.abityrienty.vyzi/drawable/img_default.jpeg");
        }

        collapsingToolbarLayout.setBackground(drawable);*/

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

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        sqLiteDatabase.close();
        cursor.close();
        myDBHelper.close();
        System.gc();
        /*try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Log.d("destr", "UniMain destr");
    }
}
