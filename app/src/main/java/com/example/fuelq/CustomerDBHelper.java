/*
 * Developer ID      :   IT19020822
 * Developer Name    :   Dilshan K.G.T
 * Class             :   Customer DB Helper.java
 * Implemented Date  :   18th October 2022
 */

package com.example.fuelq;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class CustomerDBHelper extends SQLiteOpenHelper {
    String fuelType = "";
    public CustomerDBHelper(@Nullable Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table customers(email TEXT primary key, vehicleNo TEXT, vehicleType TEXT, fuelType TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists customers");
    }

    public Boolean insertData(String email, String vehicleNo, String  vehicleType, String fuelType){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("vehicleNo", vehicleNo);
        contentValues.put("vehicleType", vehicleType);
        contentValues.put("fuelType", fuelType);
        long result = db.insert("customers",null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public Boolean checkUserExist(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from customers where email = ?", new String[] {email});

        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    String getFuelType(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {email};
        Cursor cursor = db.rawQuery("Select fuelType from customers where email = ?", args, null);
        if(cursor.moveToFirst()){
            fuelType = cursor.getString(0);
        }
        return fuelType;
    }

}