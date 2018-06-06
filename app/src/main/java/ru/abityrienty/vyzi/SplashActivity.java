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

        myDBHelper = new MyDBHelper(this);
        myDBHelper.create_db();
        myDBHelper.close();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(myDBHelper.check_exist_db()) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
