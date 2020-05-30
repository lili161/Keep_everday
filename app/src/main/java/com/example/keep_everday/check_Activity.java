package com.example.keep_everday;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import java.util.Date;

public class check_Activity extends AppCompatActivity {
    private mySQLite mysqlite =new mySQLite(this, "keep_everyday", null, 2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "加油！！",0).show();

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onStart() {
        super.onStart();

try {
    Cursor cursor, cursor1;
    SQLiteDatabase db = mysqlite.getWritableDatabase();
    cursor = db.rawQuery("select last from keep_everyday", null);
    cursor.moveToLast();
    String last = cursor.getString(cursor.getColumnIndex("last"));
    TextView t1 = findViewById(R.id.lianxudaka);
    t1.setText(last + " 天");

    String all = String.valueOf(cursor.getCount());
    TextView t2 = findViewById(R.id.dakazongshu);
    t2.setText(all + " 天");

    cursor1 = db.rawQuery("select SUM(run),SUM(rope_skipping),SUM(openclosingjump),SUM(poppy_jump),SUM(sitAndlying)  from keep_everyday", null);
    cursor1.moveToFirst();
    String run_times = cursor1.getString(cursor1.getColumnIndex("SUM(run)"));
    TextView t3 = findViewById(R.id.paobucishu);
    t3.setText(run_times + " 次");

    String skipping_times = cursor1.getString(cursor1.getColumnIndex("SUM(rope_skipping)"));
    TextView t4 = findViewById(R.id.tiaoshengcishu);
    t4.setText(skipping_times + " 个");

    String jump_times = cursor1.getString(cursor1.getColumnIndex("SUM(openclosingjump)"));
    TextView t5 = findViewById(R.id.kaihetiao);
    t5.setText(jump_times + " 个");

    String poppy_times = cursor1.getString(cursor1.getColumnIndex("SUM(poppy_jump)"));
    TextView t6 = findViewById(R.id.bobitiao);
    t6.setText(poppy_times + " 个");

    String sit_times = cursor1.getString(cursor1.getColumnIndex("SUM(sitAndlying)"));
    TextView t7 = findViewById(R.id.juanfu);
    t7.setText(sit_times + " 个");
}catch (Exception e){
    //donothing
}

    }
    @SuppressLint("WrongConstant")
    public  void OnClick(View view){
        if (view.getId()==R.id.data_sync){
            Toast.makeText(this,"敬请期待",0).show();
        }
    }

}
