package com.example.recyclerview2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private MyDB mydb;
    private ListView lv;
    private int max = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new MyDB(this);
        lv = (ListView) findViewById(R.id.lista);
        DatabaseHelper mdh = new DatabaseHelper(this);
        LinkedList<String> mWordList=new LinkedList<String>();
        mydb.insertItems();
        setAdapter();
        createAlert();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                builder.show();
                //startActivityForResult(i, 0);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mydb.deleteItem(position);
                setAdapter();
                return false;
            }
        });
    }

    public void addConstant(View view){

    }
    public void setAdapter() {
        lv = findViewById(R.id.lista);
        Cursor cur = mydb.selectRecords("_id AS _id, Nombres AS Nombres", "Categoria3","");
        max = cur.getCount();
        String[] from = new String[]{"Nombres"};
        int[] to = new int[]{android.R.id.text1};
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cur, from, to, 0);
        lv.setAdapter(sca);
    }
    protected void createAlert() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new constant");
        final EditText input = new EditText(this);
        final EditText input2 = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        input2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);


        input.setHint("Name");
        layout.addView(input);
        input2.setHint("Gravity");
        layout.addView(input2);
        builder.setView(layout);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mydb.createRecords(max + 1, input.getText().toString() + " - " + input2.getText().toString());
                setAdapter();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }
}
