package com.searchdm.darkroom.mydb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context) {
        super(context,"Db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table std (id integer primary key autoincrement ,name varchar(20),address varchar(20))");
    }

    public boolean insertData(String name,String add){
      SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name",name);
        cv.put("address",add);
      float res=db.insert("std",null,cv);
      if(res==-1){
          return false;
      }
      else
          return true;

    }
    public Cursor getData(){
       SQLiteDatabase db=this.getWritableDatabase();
       Cursor res=db.rawQuery("select * from std",null);
       return  res;
    }
    public Integer delData(String id){
        SQLiteDatabase db=getWritableDatabase();
        return db.delete("std","id=?",new String[]{id});
    }
    public Integer updateData(String name,String add, String id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name",name);
        cv.put("address",add);
        return  db.update("std",cv,"id=?",new String[]{id});
        //return true;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
