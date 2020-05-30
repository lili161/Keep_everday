package com.example.keep_everday;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class mySQLite extends SQLiteOpenHelper {
    public static final String  CREAT ="CREATE TABLE [keep_everyday]([id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, [date] TEXT NOT NULL, [rope_skipping] INTEGER, [openclosingjump] INTEGER, [poppy_jump] INTEGER, [run] INTEGER,[sitAndlying] INTEGER,[last] INTEGER)";
    // insert into keep_everyday(date,rope_skipping,openclosingjump,poppy_jump,run) values ("2020-05-06",1,1,1,1);
    private Context mycontext;
    public mySQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mycontext=context;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT);
        Toast.makeText(mycontext,"今天是你的第一次打卡加油！",0).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("alter table keep_everyday  add column  last integer ");
    }
    public int search(String date,SQLiteDatabase db){
        Cursor cursor= db.query("keep_everyday",new String[]{"id"},"date=?",new  String[]{date},null,null,"id");
       db.close();
        if(cursor.getCount()>0){
            return 1;
        }else {
            return 0;
        }
    }

    @SuppressLint("WrongConstant")
    public void insert(String date, int run, int rope_skipping, int poppy_jump, int openclosing_jump, int sitAndlying,SQLiteDatabase db){
        int last =1>isconnect(date,db)?1:isconnect(date,db);

       db.execSQL("insert into keep_everyday(date,run,rope_skipping,openclosingjump,poppy_jump,sitAndlying,last) values ("+date+","+run+","+rope_skipping+","+openclosing_jump+","+poppy_jump+","+sitAndlying+","+last+")");
        db.close();;
       Toast.makeText(mycontext,"打卡成功！",0).show();
    }
    // private sql=new
    public  void  data_update(String date,int run ,int rope_skipping ,int poppy_jump,int openclosingjump,int sitAndlying,SQLiteDatabase db){
        ContentValues values =new ContentValues();
        values.put("run",run);
        values.put("rope_skipping",rope_skipping);
        values.put("poppy_jump",poppy_jump);
        values.put("openclosingjump",openclosingjump);
        values.put("sitAndlying",sitAndlying);
        db.update("keep_everyday",values,"date=?",new String[]{date});
        db.close();
    }
    public int isconnect(String date,SQLiteDatabase db){
      int flag=-1;
          Cursor cursor=db.rawQuery("select date,last from keep_everyday",null);
        cursor.moveToLast();
      try {
          String yesterday=cursor.getString(cursor.getColumnIndex("date"));
          String last=cursor.getString(cursor.getColumnIndex("last"));
          Log.e("tipPPPP",last);
          Date dt=new Date();
          SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
          String s_date=fmt.format(dt);
          int yes_year=Integer.parseInt(yesterday.substring(0,4));
          int yes_month=Integer.parseInt(yesterday.substring(4,6));
          int yes_day=Integer.parseInt(yesterday.substring(6,8));
          if(date_compare(yes_year,yes_month,yes_day,s_date)==1){
              flag=Integer.parseInt(last)+1;
          }


      }catch (CursorIndexOutOfBoundsException e){
      }
       // db.query("kepp_everyday",new String[]{"last"},"id=?")
        return flag;
    }
    public int date_compare(int yes_year,int yes_month,int yes_day,String  date){
        Calendar calendar =Calendar.getInstance();
        String year=date.substring(0,4);
        String month=date.substring(5,7);
        String day=date.substring(8,10);
        Log.e("目前时间：",year+" "+month+" "+day);
        calendar.set(Integer.parseInt(year),Integer.parseInt(month)-1,Integer.parseInt(day));
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-1);
        int b_day=calendar.get(Calendar.DATE);
        int b_month=calendar.get(Calendar.MONTH)+1;
        int b_year=calendar.get(Calendar.YEAR);
        if(yes_year==b_year&&yes_month==b_month&&yes_day==b_day){
            return 1;
        }else{
            return 0;
        }
    }
}
