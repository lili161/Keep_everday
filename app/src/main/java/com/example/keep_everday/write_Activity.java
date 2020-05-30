package com.example.keep_everday;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class write_Activity extends AppCompatActivity {

    private mySQLite mysqlite =new mySQLite(this, "keep_everyday", null, 2);
    private SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
    private Date date=new Date();
    String today_time=fmt.format(date);

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_in);
        mysqlite = new mySQLite(this, "keep_everyday", null, 2);
        SQLiteDatabase db_rd=mysqlite.getWritableDatabase();
        Cursor cursor =db_rd.query("keep_everyday",new String[]{"run","rope_skipping","openclosingjump","poppy_jump","sitAndlying"},"date=?",new String[]{today_time.replaceAll("-", "")},null,null,null);
        Log.e("count",String.valueOf(cursor.getCount()));
        if(cursor.getCount()>0){
            cursor.moveToNext();
            int run_res=  cursor.getInt(cursor.getColumnIndex("run"));
            String rope_skipping_res=  cursor.getString(cursor.getColumnIndex("rope_skipping"));
            String openclosingjump_res=  cursor.getString(cursor.getColumnIndex("openclosingjump"));
            String poppy_jump_res=  cursor.getString(cursor.getColumnIndex("poppy_jump"));
            String sitAndlying_res=  cursor.getString(cursor.getColumnIndex("sitAndlying"));
            db_rd.close();
            Switch switch1=findViewById(R.id.switch1);
            if(run_res==1)
                switch1.setChecked(true);
            else if(run_res==0)
                switch1.setChecked(false);
            TextView textView = findViewById(R.id.rope_jump_times);
            textView.setText(rope_skipping_res);
            TextView textView2 = findViewById(R.id.poppy_jump_times);
            textView2.setText(poppy_jump_res);
            TextView textView3 = findViewById(R.id.open_closing_jump_times);
            textView3.setText(openclosingjump_res);
            TextView textView4 = findViewById(R.id.sitAndlying_times);
            textView4.setText(sitAndlying_res);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    public void click(View view) throws InterruptedException {
        if(view.getId()==R.id.rope_jumping){
            final EditText editText =new EditText(this);
            AlertDialog.Builder dialog=new AlertDialog.Builder(this);
            dialog.setTitle("跳绳次数：");
            dialog.setView(editText);
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TextView textView =findViewById(R.id.rope_jump_times);
                    textView.setText(editText.getText());
                    if(editText.getText().toString().equals(""))
                        textView.setText("0");
                }
            });
            dialog.setNegativeButton("写错了",null);
            dialog.show();

        }else if(view.getId()==R.id.poppy_jump){

            final EditText editText =new EditText(this);
            AlertDialog.Builder dialog=new AlertDialog.Builder(this);
            dialog.setTitle("波比跳次数：");
            dialog.setView(editText);
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TextView textView =findViewById(R.id.poppy_jump_times);
                    textView.setText(editText.getText());
                    if(editText.getText().toString().equals(""))
                        textView.setText("0");
                }
            });
            dialog.setNegativeButton("写错了",null);
            dialog.show();
        }else if(view.getId()==R.id.openandclosing_jump){

            final EditText editText =new EditText(this);
            AlertDialog.Builder dialog=new AlertDialog.Builder(this);
            dialog.setTitle("开合跳次数：");
            dialog.setView(editText);
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TextView textView =findViewById(R.id.open_closing_jump_times);
                    textView.setText(editText.getText());
                    if(editText.getText().toString().equals(""))
                        textView.setText("0");
                }
            });
            dialog.setNegativeButton("写错了",null);
            dialog.show();
        }else if(view.getId()==R.id.sitAndlying){

            final EditText editText =new EditText(this);
            AlertDialog.Builder dialog=new AlertDialog.Builder(this);
            dialog.setTitle("卷腹次数：");
            dialog.setView(editText);
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TextView textView =findViewById(R.id.sitAndlying_times);
                    textView.setText(editText.getText());
                    if(editText.getText().toString().equals(""))
                        textView.setText("0");
                }
            });
            dialog.setNegativeButton("写错了",null);
            dialog.show();
        }else if(view.getId()==R.id.submit){
            Button bt= findViewById(R.id.button);
            //sumbmit

           if(MainActivity.flag.equals("打卡")) {
               Switch sw = findViewById(R.id.switch1);
               int[] result = new int[5];
               if (sw.isChecked() == true) {
                   result[0] = 1;
               } else {
                   result[0] = 0;
               }
               TextView textView = findViewById(R.id.rope_jump_times);
               result[1] = Integer.parseInt(textView.getText().toString());
               TextView textView2 = findViewById(R.id.poppy_jump_times);
               result[2] = Integer.parseInt(textView2.getText().toString());
               TextView textView3 = findViewById(R.id.open_closing_jump_times);
               result[3] = Integer.parseInt(textView3.getText().toString());
               TextView textView4 = findViewById(R.id.sitAndlying_times);
               result[4] = Integer.parseInt(textView4.getText().toString());
               mysqlite = new mySQLite(this, "keep_everyday", null, 2);
               SQLiteDatabase db = mysqlite.getWritableDatabase();
               mysqlite.insert(today_time.replaceAll("-", ""), result[0], result[1], result[2], result[3], result[4], db);
               Toast.makeText(this, "打卡成功！", 0);
               Button bt1= findViewById(R.id.submit);
               bt1.setEnabled(false);
               Intent intent=new Intent(write_Activity.this,MainActivity.class);
               startActivity(intent);
               finish();
           }else if(MainActivity.flag.equals("修改")){
//               mysqlite = new mySQLite(this, "keep_everyday", null, 1);
//                SQLiteDatabase db_rd=mysqlite.getWritableDatabase();
//               Cursor cursor =db_rd.query("keep_everyday",new String[]{"run","rope_skipping","openclosingjump","poppy_jump","sitAndlying"},"date=?",new String[]{today_time.replaceAll("-", "")},null,null,null);
//               Log.e("count",String.valueOf(cursor.getCount()));
//               if(cursor.getCount()>0){
//                   cursor.moveToNext();
//                 int run_res=  cursor.getInt(cursor.getColumnIndex("run"));
//                 String rope_skipping_res=  cursor.getString(cursor.getColumnIndex("rope_skipping"));
//                 String openclosingjump_res=  cursor.getString(cursor.getColumnIndex("openclosingjump"));
//                 String poppy_jump_res=  cursor.getString(cursor.getColumnIndex("poppy_jump"));
//                 String sitAndlying_res=  cursor.getString(cursor.getColumnIndex("sitAndlying"));
//                    db_rd.close();
//                    Switch switch1=findViewById(R.id.switch1);
//                    if(run_res==1)
//                         switch1.setChecked(true);
//                    else if(run_res==0)
//                        switch1.setChecked(false);
//                   TextView textView = findViewById(R.id.rope_jump_times);
//                   textView.setText(rope_skipping_res);
//                   TextView textView2 = findViewById(R.id.poppy_jump_times);
//                   textView2.setText(poppy_jump_res);
//                   TextView textView3 = findViewById(R.id.open_closing_jump_times);
//                   textView3.setText(openclosingjump_res);
//                   TextView textView4 = findViewById(R.id.sitAndlying_times);
//                   textView4.setText(sitAndlying_res);
//               }
               //
               Switch sw = findViewById(R.id.switch1);
               int[] result = new int[5];
               if (sw.isChecked() == true) {
                   result[0] = 1;
               } else {
                   result[0] = 0;
               }
               TextView textView = findViewById(R.id.rope_jump_times);
               result[1] = Integer.parseInt(textView.getText().toString());
               TextView textView2 = findViewById(R.id.poppy_jump_times);
               result[2] = Integer.parseInt(textView2.getText().toString());
               TextView textView3 = findViewById(R.id.open_closing_jump_times);
               result[3] = Integer.parseInt(textView3.getText().toString());
               TextView textView4 = findViewById(R.id.sitAndlying_times);
               result[4] = Integer.parseInt(textView4.getText().toString());
               mysqlite = new mySQLite(this, "keep_everyday", null, 2);
               SQLiteDatabase db = mysqlite.getWritableDatabase();
               mysqlite.data_update(today_time.replaceAll("-", ""), result[0], result[1], result[2], result[3], result[4], db);
                 Toast.makeText(this, "修改成功！", 0).show();
               Button bt1= findViewById(R.id.submit);
               bt1.setEnabled(false);
               Intent intent=new Intent(write_Activity.this,MainActivity.class);
               startActivity(intent);
               finish();
           }
        }
    }
}
