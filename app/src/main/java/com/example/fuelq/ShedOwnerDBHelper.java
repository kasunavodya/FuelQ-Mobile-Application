package com.example.fuelq;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShedOwnerDBHelper extends SQLiteOpenHelper {
    public ShedOwnerDBHelper(Context context) {
        super(context, "ShedOwner.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table owners(email TEXT primary key, ownerName TEXT, contactNo TEXT, stationName TEXT, location TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists owners");
    }
    public Boolean insertData(String email, String ownerName, String contactNo, String  stationName, String location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("ownerName", ownerName);
        contentValues.put("contactNo", contactNo);
        contentValues.put("stationName", stationName);
        contentValues.put("location", location);
        long result = db.insert("owners",null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Boolean checkUserExist(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from owners where email = ?", new String[] {email});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }
}