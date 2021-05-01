package com.example.myreminder;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter {



    List list=new ArrayList();
    DatabaseHelper mydb;
    Context ct;
    public ListAdapter(Context context, int resource) {
        super(context, resource);
        ct=context;
    }
    static class LayoutHandler{
        TextView tv1,tv2,del;
    }
    public void add(Object object){
        super.add(object);
        list.add(object);
    }
    @Override
    public int getCount(){
        return list.size();
    }
    @Nullable
    @Override
    public Object getItem(int position){
        return list.get(position);
    }
    @Nullable
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutHandler layoutHandler;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.row_layout,parent,false);
            layoutHandler = new LayoutHandler();
            layoutHandler.tv1 = (TextView) row.findViewById(R.id.tv1);
            layoutHandler.tv2 = (TextView) row.findViewById(R.id.tv2);
            layoutHandler.del=(Button)row.findViewById(R.id.del);



            row.setTag(layoutHandler);
        }else{
            layoutHandler=(LayoutHandler)row.getTag();

        }

        DataProvider dataProvider=(DataProvider)this.getItem(position);
        layoutHandler.tv1.setText(dataProvider.getTitle());
        layoutHandler.tv2.setText(dataProvider.getDetails());

        layoutHandler.del.setOnClickListener(new View.OnClickListener() {
           public void deleteData(){
               Integer deleteRows=mydb.deleteData(layoutHandler.tv1.getText().toString(),layoutHandler.tv2.getText().toString());
           }
            @Override
            public void onClick(View view) {

                callAlert(position);
            }
        });
        return row;

    };

    void callAlert(final int p)
    {
        AlertDialog.Builder b=new AlertDialog.Builder(ct);
        b.setTitle("Are you sure you want to delete the event from the list");
        b.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                list.remove(p);
                notifyDataSetChanged();
            }
        });
        b.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog ad=b.create();
        ad.show();
    }






    }



