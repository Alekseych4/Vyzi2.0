package ru.abityrienty.vyzi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import info.hoang8f.widget.FButton;

public class Add_comment_activity extends AppCompatActivity {
    FButton fButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fButton = (FButton) findViewById(R.id.send_btn);

        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Комментарий отправлен", Toast.LENGTH_LONG).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
