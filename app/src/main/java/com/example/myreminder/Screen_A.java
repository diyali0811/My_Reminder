package com.example.myreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

public class Screen_A extends AppCompatActivity {

    ListView lv1;
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper userDBHelper;
    Cursor cursor;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_);
        lv1=(ListView)findViewById(R.id.lv1);
        userDBHelper=new DatabaseHelper(getApplicationContext());
        sqLiteDatabase=userDBHelper.getReadableDatabase();
        cursor=userDBHelper.getData();
        listAdapter=new ListAdapter(getApplicationContext(),R.layout.row_layout);
        lv1.setAdapter(listAdapter);
        if(cursor.moveToFirst()){
            do {
                String title,details;
                title=cursor.getString(1);
                details=cursor.getString(2);
                DataProvider dataProvider=new DataProvider(title,details);
                listAdapter.add(dataProvider);
            }
            while (cursor.moveToNext());

            }
        }
    }


