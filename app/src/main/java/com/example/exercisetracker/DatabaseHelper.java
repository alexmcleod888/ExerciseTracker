package com.example.exercisetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "WorkoutData.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Workout(name TEXT, time BIGINT primary key, exercise1 TEXT, " +
                "exercise2 TEXT, exercise3 TEXT, exercise4 TEXT, exercise5 TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists Workout ");
    }

    public boolean insertWorkoutData(String name, long time, String exercise1, String exercise2,
                                     String exercise3, String exercise4, String exercise5) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("time", time);
        contentValues.put("exercise1", exercise1);
        contentValues.put("exercise2", exercise2);
        contentValues.put("exercise3", exercise3);
        contentValues.put("exercise4", exercise4);
        contentValues.put("exercise5", exercise5);

        long result = db.insert("Workout", null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }
    public boolean updateWorkoutData(String name, long time, String exercise1, String exercise2,
                                     String exercise3, String exercise4, String exercise5) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("exercise1", exercise1);
        contentValues.put("exercise2", exercise2);
        contentValues.put("exercise3", exercise3);
        contentValues.put("exercise4", exercise4);
        contentValues.put("exercise5", exercise5);

        //convert to string to use in query
        String timeString = Long.toString(time);

        Cursor cursor = db.rawQuery("Select * from Workout where time = ?", new String[] {timeString});
        if(cursor.getCount() > 0)
        {
            long result = db.update("UserDetails", contentValues, "name=?", new String[] {timeString});
            if(result == -1){
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }

    }

    public boolean deleteWorkoutData(Long time)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String timeString = Long.toString(time);

        Cursor cursor = db.rawQuery("Select * from Workout where time = ?", new String[] {timeString});
        if (cursor.getCount() > 0) {
            long result = db.delete("Workout", "time=?", new String[] {timeString});
            if (result == -1) {
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }

    public Cursor getData () {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Workout", null);
        return cursor;
    }
}
