package ru.abityrienty.vyzi;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ru.abityrienty.vyzi.utils.MyDBHelper;

public class SplashActivity extends AppCompatActivity {
    MyDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d("SPLASH", "Before 'creating' DB");
        myDBHelper = new MyDBHelper(this);
        myDBHelper.create_db();
        myDBHelper.close();
        //Log.d("SPLASH", "After 'creating' DB");

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d("SPLASH", "onResume()");
        if(myDBHelper.check_exist_db()) {
            //Log.d("SPLASH", "In the if");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
