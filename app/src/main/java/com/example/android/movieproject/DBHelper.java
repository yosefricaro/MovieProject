package com.example.android.movieproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MovieProject.db";
    private static final int DATABASE_VERSION = 1;

    public static final String RATING_TABLE_NAME = "rating";
    public static final String RATING_COLUMN_YEAR = "year";
    public static final String RATING_COLUMN_VOTE = "vote";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + RATING_TABLE_NAME +
                        "(" + RATING_COLUMN_YEAR + " TEXT PRIMARY KEY, " +
                        RATING_COLUMN_VOTE + " INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RATING_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertRating(String year, int vote) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(RATING_COLUMN_YEAR, year);
        contentValues.put(RATING_COLUMN_VOTE, vote);

        db.insert(RATING_TABLE_NAME, null, contentValues);
        return true;
    }

//    public int numberOfRows() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        int numRows = (int) DatabaseUtils.queryNumEntries(db, RATING_TABLE_NAME);
//        return numRows;
//    }

    public boolean updateRating(String year, int vote) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RATING_COLUMN_VOTE, vote);
        db.update(RATING_TABLE_NAME, contentValues, RATING_COLUMN_YEAR + " = ? ", new String[] { year } );
        return true;
    }

    public Integer deleteRating(String year) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(RATING_TABLE_NAME,
                RATING_COLUMN_YEAR + " = ? ",
                new String[] { year });
    }

    public Cursor getRating(String year) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + RATING_TABLE_NAME + " WHERE " +
                RATING_COLUMN_YEAR + "=?", new String[]{ year });
        return res;
    }

//    public Cursor getAllPersons() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res =  db.rawQuery( "SELECT * FROM " + RATING_TABLE_NAME, null );
//        return res;
//    }
}
