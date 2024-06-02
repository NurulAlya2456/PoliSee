package com.example.polisee;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "politician.db";
    private static final int DATABASE_VERSION = 1;
    public  static final String TABLE_NAME = "data";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_PARTY = "party";
    public static final String COLUMN_POSITION = "position";
    public static final String COLUMN_YEARS_IN_OFFICE = "years_in_office";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_BIOGRAPHY = "biography";
    public static final String COLUMN_LAST_ADDED_TIMESTAMP = "last_added_timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DOB + " TEXT, " +
                COLUMN_COUNTRY + " TEXT, " +
                COLUMN_PARTY + " TEXT, " +
                COLUMN_POSITION + " TEXT, " +
                COLUMN_YEARS_IN_OFFICE + " TEXT, " +
                COLUMN_IMAGE + " TEXT, " +
                COLUMN_BIOGRAPHY + " TEXT, " +
                COLUMN_LAST_ADDED_TIMESTAMP + " INTEGER " +
                ")";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addFavorite(Politician politician) {
        if (!isFavorite(politician.getId())) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, politician.getId());
            values.put(COLUMN_NAME, politician.getName());
            values.put(COLUMN_DOB, politician.getDob());
            values.put(COLUMN_COUNTRY, politician.getCountry());
            values.put(COLUMN_PARTY, politician.getParty());
            values.put(COLUMN_POSITION, politician.getPosition());
            values.put(COLUMN_YEARS_IN_OFFICE, politician.getYears_in_office());
            values.put(COLUMN_IMAGE, politician.getImage());
            values.put(COLUMN_BIOGRAPHY, politician.getBiography());
            values.put(COLUMN_LAST_ADDED_TIMESTAMP, System.currentTimeMillis());

            long result = db.insert(TABLE_NAME, null, values);
            db.close();
            return result != -1;
        }
        return false;
    }

    public boolean removeFavorite(int politicianId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(politicianId)});
        return deletedRows > 0;
    }

    public boolean isFavorite(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID}, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public List<Politician> getFavorite() {
        List<Politician> favoriteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_LAST_ADDED_TIMESTAMP + " DESC");

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Politician politician = new Politician(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_POSITION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DOB)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PARTY)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_YEARS_IN_OFFICE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_BIOGRAPHY)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)),
                        cursor.getLong(cursor.getColumnIndex(COLUMN_LAST_ADDED_TIMESTAMP))
                );
                favoriteList.add(politician);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return favoriteList;
    }
}