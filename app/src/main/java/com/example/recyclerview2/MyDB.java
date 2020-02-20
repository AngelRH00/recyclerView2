package com.example.recyclerview2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDB {

    private DatabaseHelper dbHelper;

    private SQLiteDatabase database;

    public final static String Categoria3 ="Categoria3"; // name of table

    public final static String _id = "_id"; // id value for employee
    public final static String Nombres = "Nombres";  // name of employee

    private static final String[] items = {"Gravity: Death Star I - 3.53036e-07", "Gravity: Earth - 9.80685", "Gravity: Jupiter - 23.12",
            "Gravity: Mars - 3.71", "Gravity: Mercury - 3.7",
            "Gravity: Moon - 1.6", "Gravity: Neptune - 11", "Gravity: Pluto - 0.6", "Gravity: Saturn - 8.96", "Gravity: Sun - 275",
            "Gravity: The Island - 4.81516", "Gravity: Uranus - 8.69", "Gravity: Venus - 8.87"};
    /**
     *
     * @param context
     */
    public MyDB(Context context){
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }


    public long createRecords(int id, String name){
        ContentValues values = new ContentValues();
        values.put(_id, id);
        values.put(Nombres, name);
        return database.insert(Categoria3, null, values);
    }
    public void insertItems() {
        for (int i = 0; i < items.length; i++) {
            createRecords(i, items[i]);
        }
    }

    public Cursor selectRecords(String columnas, String tabla,String where) {
        String[] cols = new String[] {_id, Nombres};
        Cursor mCursor = database.rawQuery("SELECT " + columnas + " FROM " + tabla +" "+ where + ";",null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; // iterate to get each value.
    }

    public void deleteItem(int i) {
        database.delete(Categoria3, "_id = "+i,null);
    }
}
