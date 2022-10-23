package com.example.fuelq;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CustomerDBHelper extends SQLiteOpenHelper {
    public CustomerDBHelper(Context context) {
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

}