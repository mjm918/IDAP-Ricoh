package com.inti.ricoh.julfi.idap.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.inti.ricoh.julfi.idap.Helper.Config.COLUMN_ADDRESS;
import static com.inti.ricoh.julfi.idap.Helper.Config.COLUMN_BACC;
import static com.inti.ricoh.julfi.idap.Helper.Config.COLUMN_BHOLDER;
import static com.inti.ricoh.julfi.idap.Helper.Config.COLUMN_BNAME;
import static com.inti.ricoh.julfi.idap.Helper.Config.COLUMN_CITIZEN;
import static com.inti.ricoh.julfi.idap.Helper.Config.COLUMN_DOB;
import static com.inti.ricoh.julfi.idap.Helper.Config.COLUMN_EMAIL;
import static com.inti.ricoh.julfi.idap.Helper.Config.COLUMN_IC;
import static com.inti.ricoh.julfi.idap.Helper.Config.COLUMN_ID;
import static com.inti.ricoh.julfi.idap.Helper.Config.COLUMN_MAJOR;
import static com.inti.ricoh.julfi.idap.Helper.Config.COLUMN_MOBILE;
import static com.inti.ricoh.julfi.idap.Helper.Config.COLUMN_NAME;
import static com.inti.ricoh.julfi.idap.Helper.Config.COLUMN_STYPE;
import static com.inti.ricoh.julfi.idap.Helper.Config.TABLE_NAME;

/**
 * Created by julfi on 31/07/2017.
 */

public class StudentDB extends SQLiteOpenHelper {

    public StudentDB(Context context) {
        super(context,TABLE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ TABLE_NAME + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_NAME + " TEXT, " +
                COLUMN_ID + " TEXT, " +
                COLUMN_MAJOR + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_STYPE + " TEXT, " +
                COLUMN_MOBILE + " TEXT, " +
                COLUMN_CITIZEN + " TEXT, " +
                COLUMN_IC + " TEXT, " +
                COLUMN_BNAME + " TEXT, " +
                COLUMN_BACC + " TEXT, " +
                COLUMN_BHOLDER + " TEXT, "+
                COLUMN_DOB + " TEXT, " +
                COLUMN_ADDRESS + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public boolean AddInfo(String name,String id,String major,String ic,String bname,String bacc,String bholder,
                           String email,String stype,String mobile,String citizen,String dob,String address){

        boolean flag;

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_ID,id);
        values.put(COLUMN_EMAIL,email);
        values.put(COLUMN_STYPE,stype);
        values.put(COLUMN_MOBILE,mobile);
        values.put(COLUMN_CITIZEN,citizen);
        values.put(COLUMN_DOB,dob);
        values.put(COLUMN_ADDRESS,address);
        values.put(COLUMN_MAJOR,major);
        values.put(COLUMN_IC,ic);
        values.put(COLUMN_BNAME,bname);
        values.put(COLUMN_BACC,bacc);
        values.put(COLUMN_BHOLDER,bholder);

        long result = database.insert(TABLE_NAME,null,values);

        flag = result != -1;

        return flag;
    }

    public boolean DeleteAll(){

        SQLiteDatabase database = this.getReadableDatabase();
        int affected = database.delete(TABLE_NAME,null,null);
        return affected > 0;

    }

    public Cursor GetAllData(){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return database.rawQuery(query,null);
    }
}
