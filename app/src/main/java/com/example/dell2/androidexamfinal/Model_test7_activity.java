package com.example.dell2.androidexamfinal;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import collector.BaseActivity;
import db.DBHandler;

import db.DbHelp;

import hash.HashName;

/**
 * Created by wangyan on 2017/6/19.
 */

public class Model_test7_activity extends BaseActivity implements View.OnClickListener{
    private DbHelp dbHelp;
    private DBHandler dbHandler;
    private LinearLayout test7_LL_1;
    private LinearLayout test7_LL_2;
    private LinearLayout test7_LL_3;
    private LinearLayout test7_LL_4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_model_test7);
        init();
    }
    public void init(){
        test7_LL_1=(LinearLayout)findViewById(R.id.test7_LL_1);
        test7_LL_2=(LinearLayout)findViewById(R.id.test7_LL_2);
        test7_LL_3=(LinearLayout)findViewById(R.id.test7_LL_3);
        test7_LL_4=(LinearLayout)findViewById(R.id.test7_LL_4);
        test7_LL_1.setOnClickListener(this);
        test7_LL_2.setOnClickListener(this);
        test7_LL_3.setOnClickListener(this);
        test7_LL_4.setOnClickListener(this);
        dbHelp=new DbHelp(this,"Name.db",null,1);
        dbHandler=new DBHandler(new WeakReference<BaseActivity>(this));
    }
    public static void actionStart(Context context){
        Intent intent=new Intent(context,Model_test7_activity.class);
        context.startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        SQLiteDatabase sqLiteDatabase;
        switch (view.getId()){
            case R.id.test7_LL_1:
                dbHelp.getWritableDatabase();
                break;
            case R.id.test7_LL_2:
                dbHandler.sendEmptyMessageDelayed(DBHandler.DOING,1000);
                break;
            case R.id.test7_LL_3:
                dbHandler.sendEmptyMessage(DBHandler.OVER);
                Toast.makeText(Model_test7_activity.this,"over",Toast.LENGTH_SHORT).show();
                break;
            case R.id.test7_LL_4:
                displayDb();
                break;
            default:
                break;
        }
    }
    public void insertDb(){
        SQLiteDatabase sqLiteDatabase=dbHelp.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name", HashName.getHashRandom(8));
//        name属性名
        sqLiteDatabase.insert("name",null,contentValues);
//        name表名
        Toast.makeText(Model_test7_activity.this,"insert success",Toast.LENGTH_SHORT).show();
    }
    public void displayDb(){
        SQLiteDatabase sqLiteDatabase=dbHelp.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.query("name",null,null,null,null,null,null);
//        name表名
        if(cursor.moveToFirst()){
            do{
                String name=cursor.getString(cursor.getColumnIndex("name"));
                Log.d("Test7","Name"+name);
            }while(cursor.moveToNext());
        }
        cursor.close();
    }
    public DBHandler getHandler(){
        return this.dbHandler;
    }
}