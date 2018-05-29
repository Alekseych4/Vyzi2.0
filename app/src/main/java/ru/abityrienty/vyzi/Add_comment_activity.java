package ru.abityrienty.vyzi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import info.hoang8f.widget.FButton;

public class Add_comment_activity extends AppCompatActivity {
    FButton fButton;
    EditText editText1, editText2, editText3, email;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fButton = (FButton) findViewById(R.id.send_btn);
        editText1 = (EditText) findViewById(R.id.name);
        editText2 = (EditText) findViewById(R.id.your_location);
        editText3 = (EditText) findViewById(R.id.comment);
        email = (EditText) findViewById(R.id.your_email);


        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = editText1.getText().toString();
                String text2 = editText2.getText().toString();
                String text3 = editText3.getText().toString();
                String email_text = email.getText().toString();

                if (text1.isEmpty()||text2.isEmpty()){
                    Toast.makeText(getBaseContext(),"Обязательные поля не заполнены", Toast.LENGTH_LONG).show();
                }   else {

                    intent = new Intent();
                    intent.setAction(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:" + "vyziofkazan@gmail.com"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, text1);
                    intent.putExtra(Intent.EXTRA_TEXT, text2 + "\r\n" + email_text + "\r\n" + text3);

                    try {
                        startActivity(Intent.createChooser(intent, "Послать пожелание"));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(Add_comment_activity.this, "Нет установленных почтовых приложений", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
