package com.example.fa_naveenmannem_c0825762_android;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "project_db";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DataModel.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataModel.TABLE_NAME);

        onCreate(db);

    }



    public long insert(String name,String lat,String lng,String completed) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(DataModel.COLUMN_NAME, name);
        values.put(DataModel.COLUMN_LAT, lat);
        values.put(DataModel.COLUMN_LNG, lng);

        // insert row
        long id = db.insert(DataModel.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    @SuppressLint("Range")
    public List<DataModel> getAllPlaces() {
        List<DataModel> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DataModel.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataModel note = new DataModel();
                note.setId(cursor.getInt(cursor.getColumnIndex(DataModel.COLUMN_ID)));
                note.setPlaceName(cursor.getString(cursor.getColumnIndex(DataModel.COLUMN_NAME)));
                note.setLat(cursor.getString(cursor.getColumnIndex(DataModel.COLUMN_LAT)));
                note.setLng(cursor.getString(cursor.getColumnIndex(DataModel.COLUMN_LNG)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public void deletePlace(DataModel note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DataModel.TABLE_NAME, DataModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }

    public int updatePlace(DataModel note,String name,String lat,String lng) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataModel.COLUMN_NAME, name);
        values.put(DataModel.COLUMN_LAT, lat);
        values.put(DataModel.COLUMN_LNG, lng);
        // updating row
        return db.update(DataModel.TABLE_NAME, values, DataModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }



}
