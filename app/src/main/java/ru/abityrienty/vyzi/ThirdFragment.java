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

import ru.abityrienty.vyzi.utils.DbHelperPref;
import ru.abityrienty.vyzi.utils.ListOfVyziAdapter;

/**
 * Created by Роберт on 01.05.2018.
 * Preferences fragment
 */

public class ThirdFragment extends Fragment {
    View view;
    ListView listView;
    ListOfVyziAdapter listOfVyziAdapter;
    DbHelperPref dbHelperPref;
    SQLiteDatabase sqLiteDatabasePref;
    Cursor cursorPref;
    long idInTable;
    String tableName;

    public ThirdFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.third_fragment, container, false);

        listView = (ListView) view.findViewById(R.id.list_preferences_frag);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dbHelperPref = new DbHelperPref(getContext());

        sqLiteDatabasePref = dbHelperPref.getReadableDatabase();

        cursorPref = sqLiteDatabasePref.query("preferences", null, null, null, null, null, null);
        cursorPref.moveToFirst();

        listOfVyziAdapter = new ListOfVyziAdapter(getContext(), R.layout.layout_for_list,
                cursorPref, new String[]{"img_src", "name"},
                new int[]{R.id.imageView, R.id.main_text}, 0);
        listView.setAdapter(listOfVyziAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursorPref.moveToPosition(position);
                idInTable = cursorPref.getLong(cursorPref.getColumnIndex("tableId"));
                tableName = cursorPref.getString(cursorPref.getColumnIndex("tableName"));
                Intent sendIntent = new Intent(getContext(), InstitutePage.class);
                sendIntent.putExtra("_id", idInTable);
                sendIntent.putExtra("tb_name", tableName);
                startActivity(sendIntent);
            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if(dbHelperPref.check_exist_db()) {
            cursorPref.close();
            dbHelperPref.close();
            sqLiteDatabasePref.close();
        }
    }
}
