package ru.abityrienty.vyzi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.abityrienty.vyzi.utils.MyDBHelper;


public class MainActivity extends AppCompatActivity {
    MyDBHelper myDBHelper;
    Button btnP;
    Toolbar toolbar;
    CreatingTask creatingTask;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("ВУЗы Казани");

        //Создаём базу данных на устройстве на основе имеющейся
        myDBHelper = new MyDBHelper(this);
        if(!myDBHelper.check_exist_db()){
            creatingTask = new CreatingTask();
            creatingTask.execute(this);
        }



        final Button btn = (Button) findViewById(R.id.btn_introduction);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openListOfVyzi(btn);
            }
        });
        btnP = (Button) findViewById(R.id.btn_prefer);
        btnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getBaseContext(), Preferences.class);
                startActivity(intent1);
            }
        });

    }

    public void openListOfVyzi (View view) {
        Intent intent = new Intent(this, List_of_Vyzi_activity.class);
        startActivity(intent);
    }

    public void openUniversityMainActivity(View view){
        Intent intent = new Intent(this, UniversityMainActivity.class);
        String id = view.getResources().getResourceEntryName(view.getId());
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(id);
        int start = 0;
        long put =0;
        while (matcher.find(start)) {
            String value = id.substring(matcher.start(), matcher.end());
            put = Long.parseLong(value);
            start = matcher.end();
        }
        intent.putExtra("_id", put);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDBHelper.close();
    }

    class CreatingTask extends AsyncTask<Context, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Подготовка файлов");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Context... params) {
            MyDBHelper md = new MyDBHelper(params[0]);
            md.create_db();
            md.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.setProgress(100);
            progressDialog.dismiss();

        }
    }
 }
