package com.example.fuelq;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDBHelper extends SQLiteOpenHelper {

    public UserDBHelper(Context context) {
        super(context, "Users.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table users(email TEXT primary key, password TEXT, type TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String email, String password, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("type", type);
        long result = db.insert("users", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean checkUserExist(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where email = ?", new String[]{email});

        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkUserEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where email = ? and password = ?", new String[]{email, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public String getUserType(String email, String password) {
        String type = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select type from users where email = ? and password = ?", new String[]{email, password});

        if(cursor.moveToFirst()){
            type = cursor.toString();
        }
        return type;
    }
}