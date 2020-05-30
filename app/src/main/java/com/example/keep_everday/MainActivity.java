package com.example.keep_everday;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public  static  String flag="打卡";
    private mySQLite mysqlite =new mySQLite(this, "keep_everyday", null, 2);
    private  SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
    private Date date=new Date();
    String today_time=fmt.format(date);
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onStart() {
        super.onStart();
         SQLiteDatabase db =mysqlite.getWritableDatabase();
      Cursor cursor= db.query("keep_everyday",new String[]{"id"},"date=?",new  String[]{today_time.replaceAll("-","")},null,null,"id");

      if(cursor.getCount()>0){
            Button b=findViewById(R.id.button);
            b.setText("修改");
            TextView t=findViewById(R.id.text);
            t.setText("今日已打卡");
            flag="修改";
            db.close();;
        }else{
          db.close();
      }
      if(flag.equals("打卡"))
          Toast.makeText(this,"今天是："+today_time,0).show();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    public void insert_test(View view){
        if(view.getId()==R.id.button) {

            Intent intent=new Intent(MainActivity.this,write_Activity.class);
            startActivity(intent);
            finish();

//            mysqlite = new mySQLite(this, "keep_everyday", null, 1);
//            SQLiteDatabase db = mysqlite.getWritableDatabase();
//            mysqlite.insert(today_time.replaceAll("-",""), 0, 1 ,0, 0, db);
//            onStart();
        }else if(view.getId()==R.id.see_data){
            Intent intent=new Intent(MainActivity.this,check_Activity.class);
            startActivity(intent);
        }
    }








}
