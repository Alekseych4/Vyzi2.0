package ru.abityrienty.vyzi;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import ru.abityrienty.vyzi.constants.TablesNames;
import ru.abityrienty.vyzi.constants.UniMainInfoConsts;
import ru.abityrienty.vyzi.utils.ListOfVyziAdapter;
import ru.abityrienty.vyzi.utils.MyDBHelper;

/**
 * Created by Роберт on 01.05.2018.
 */

public class FirstFragment extends Fragment {

    View view;
    ListView listView;
    MyDBHelper myDBHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ListOfVyziAdapter listOfVyziAdapter;


    public FirstFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.first_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.list_of_vyzi_frag);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), UniversityMainActivity.class);
                intent.putExtra("_id", id);

                startActivity(intent);
            }
        });


        // открываем подключение
        myDBHelper = new MyDBHelper(getContext());
        sqLiteDatabase = myDBHelper.open();

        //получаем данные из бд в виде курсора
        cursor =  sqLiteDatabase.query(TablesNames.UNIVERSITIES,
                new String[] {UniMainInfoConsts.ID,UniMainInfoConsts.IMG_SRC, MyDBHelper.COLUMN_UNI,
                        MyDBHelper.COLUMN_BRI}, null, null,null,null,null);

        // определяем, какие столбцы из курсора будут выводиться в ListView
        cursor.moveToFirst();
        String[] headers = new String[] {UniMainInfoConsts.IMG_SRC, MyDBHelper.COLUMN_UNI, MyDBHelper.COLUMN_BRI};
        // создаем адаптер, передаем в него курсор
        listOfVyziAdapter = new ListOfVyziAdapter(getContext(), R.layout.layout_for_list,
                cursor, headers, new int[]{R.id.imageView, R.id.main_text, R.id.sub_text}, 0);

        listView.setAdapter(listOfVyziAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    }

