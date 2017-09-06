package com.inti.ricoh.julfi.idap.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.inti.ricoh.julfi.idap.Helper.Config.COLUMN_EMAIL;
import static com.inti.ricoh.julfi.idap.Helper.Config.COLUMN_ID;
import static com.inti.ricoh.julfi.idap.Helper.Config.COLUMN_MOBILE;
import static com.inti.ricoh.julfi.idap.Helper.Config.COLUMN_NAME;
import static com.inti.ricoh.julfi.idap.Helper.Config.COLUMN_PROGRAMME;
import static com.inti.ricoh.julfi.idap.Helper.Config.COLUMN_WORKING;
import static com.inti.ricoh.julfi.idap.Helper.Config.ETBL_NAME;

/**
 * Created by julfi on 06/09/2017.
 */

public class EmployeeDB extends SQLiteOpenHelper {

    public EmployeeDB(Context context){
        super(context,ETBL_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ ETBL_NAME + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_NAME + " TEXT, " +
                COLUMN_ID + " TEXT, " +
                COLUMN_PROGRAMME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_MOBILE + " TEXT, " +
                COLUMN_WORKING + " TEXT)";
        db.execSQL(createTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    public boolean AddInfo(String name,String id,String programme,String email,String mobile,String working){
        boolean flag;

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_ID,id);
        values.put(COLUMN_EMAIL,email);
        values.put(COLUMN_MOBILE,mobile);
        values.put(COLUMN_PROGRAMME,programme);
        values.put(COLUMN_WORKING,working);

        long result = database.insert(ETBL_NAME,null,values);

        flag = result != -1;

        return flag;
    }

    public boolean DeleteAll(){

        SQLiteDatabase database = this.getReadableDatabase();
        int affected = database.delete(ETBL_NAME,null,null);
        return affected > 0;

    }

    public Cursor GetAllData(){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM " + ETBL_NAME;
        return database.rawQuery(query,null);
    }
}
