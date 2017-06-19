package com.example.dell2.androidexamfinal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import adapt.Test8_Adapt;
import collector.BaseActivity;
import db.DbHelp;

/**
 * Created by wangyan on 2017/6/19.
 */

public class Model_test8_activity extends BaseActivity {
    private DbHelp dbHelp;
    private ListView test8_ListView;
    private Test8_Adapt test8_adapt;
    private List<String> data=new ArrayList<>();
    private int pos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_model_test8);
        init();
    }
    public void init(){
        dbHelp=new DbHelp(this,"Name.db",null,1);
        displayDb();
        test8_ListView=(ListView)findViewById(R.id.test8_ListView);
        test8_adapt=new Test8_Adapt(Model_test8_activity.this,R.layout.listview_model_test8,data);
        test8_ListView.setAdapter(test8_adapt);
        test8_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(Model_test8_activity.this);
                pos=i;
                dialog.setTitle("Delete this item?");
                dialog.setMessage(data.get(i));
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteItem(data.get(pos));
                        data.clear();
                        displayDb();
                        test8_adapt=new Test8_Adapt(Model_test8_activity.this,R.layout.listview_model_test8,data);
                        test8_ListView.setAdapter(test8_adapt);
                    }
                });
                dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Model_test8_activity.this,"delete cancel",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });

    }
    public static void actionStart(Context context){
        Intent intent=new Intent(context,Model_test8_activity.class);
        context.startActivity(intent);
    }
    public void displayDb(){
        SQLiteDatabase sqLiteDatabase=dbHelp.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.query("name",null,null,null,null,null,null);
        int i=0;
        if(cursor.moveToFirst()){
            do{
                i++;
                String name=cursor.getString(cursor.getColumnIndex("name"));
                data.add(name);
            }while(cursor.moveToNext());
        }
        cursor.close();
        Toast.makeText(Model_test8_activity.this,String.valueOf(i),Toast.LENGTH_SHORT).show();
    }
    public void deleteItem(String str){
        SQLiteDatabase sqLiteDatabase=dbHelp.getWritableDatabase();
        sqLiteDatabase.delete("Name"," name = ?",new String[] {str});
        Toast.makeText(Model_test8_activity.this,"delete success",Toast.LENGTH_SHORT).show();
    }
}
