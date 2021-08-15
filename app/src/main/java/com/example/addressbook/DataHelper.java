package com.example.addressbook;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Path;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataHelper extends Activity {
    private static final String DATABASE_NAME="AddressBook.db";private static final String TABLE_NAME="AddressContact";
    private static final int DATABASE_VERSION=1; private Context context;
    private SQLiteDatabase db; private SQLiteStatement insertStart;
    private static final String INSERT ="INSERT INTO "+TABLE_NAME +"(name,address) values (?,?)";
    public DataHelper(Context context) { this.context = context; OpenHelper openHelper=new OpenHelper(this.context);
        this.db=openHelper.getWritableDatabase(); this.insertStart=this.db.compileStatement(INSERT); }
    public long insert(String name,String address){ try { this.insertStart.bindString(1,name);
            this.insertStart.bindString(2,address);
            return this.insertStart.executeInsert();
        }catch (SQLException ex){ return 0; } }
    public void delete(String id){
        this.db.delete(TABLE_NAME,"id-"+id+" ",null);
    }
    public void deleteAll(){
        this.db.delete(TABLE_NAME,null,null);
    }
    public List<String> selectAll(){
        List<String> list=new ArrayList<>();
        Cursor cursor=this.db.query(TABLE_NAME,new String[] {"name"},null,null,null,null,"name desc");
        if (cursor.moveToFirst()){ do { list.add(cursor.getString(0)); }while (cursor.moveToNext()); }
        if (cursor!=null && !cursor.isClosed()){ cursor.close(); } return list; }
    public String SelectSpecific(String name){ String data="";  try {
        Cursor cursor=this.db.query(TABLE_NAME,new String[] {"id","name","address"},"name="+name+"",null,null,null,null);
            if (cursor.moveToFirst()){ do { data=cursor.getString(0).toString();
                    data=data+"@"+cursor.getString(1).toString(); data=data+"@"+cursor.getString(2).toString(); }
                while (cursor.moveToNext()); }
            if (cursor!=null&&!cursor.isClosed()){ cursor.close(); } return data;
        }catch (SQLException e){ return data; } }
        private static class OpenHelper extends SQLiteOpenHelper{ public OpenHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION); } @Override
        public void onCreate(SQLiteDatabase db) { db.execSQL("CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY,name TEXT,address TEXT)"); } @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { Log.v("address","Upgrading database,this will drop table and recreate");
         db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME); onCreate(db); }}}
