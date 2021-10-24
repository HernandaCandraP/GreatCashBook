package org.hernanda.cashbook.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import org.hernanda.cashbook.Pengaturan;

public class SQLiteHelper extends SQLiteOpenHelper {
    static String DATABASE_NAME="cashbook.db";
    public static final String TABLE_LOGIN="login";
    private static final int DATABASE_VERSION = 1;
    public static final String Table_Column_ID ="id";
    public static final String Table_Column_Username="username";
    public static final String Table_Column_Password="password";

    public static final String TABLE_KEUANGAN="keuangan";
    public static final String Table_Column_Tanggal="tanggal";
    public static final String Table_Column_Nominal="nominal";
    public static final String Table_Column_Keterangan="keterangan";
    public static final String Table_Column_Status="status";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String sql = "CREATE TABLE "+TABLE_LOGIN+" ("+Table_Column_ID+" INTEGER PRIMARY KEY, "+Table_Column_Username+" VARCHAR, "+Table_Column_Password+" VARCHAR);";
        db.execSQL(sql);
        sql = "INSERT INTO "+TABLE_LOGIN+" ("+Table_Column_ID+", "+Table_Column_Username+", "+Table_Column_Password+") VALUES ('1', 'user', 'user');";
        db.execSQL(sql);

        sql = "CREATE TABLE "+TABLE_KEUANGAN+" ("+Table_Column_ID+" INTEGER PRIMARY KEY, "+Table_Column_Tanggal+" VARCHAR, "+Table_Column_Nominal+" VARCHAR, "+Table_Column_Keterangan+" VARCHAR, "+Table_Column_Status+" VARCHAR);";
        db.execSQL(sql);
        sql = "INSERT INTO "+TABLE_KEUANGAN+" ("+Table_Column_ID+", "+Table_Column_Tanggal+", "+Table_Column_Nominal+", "+Table_Column_Keterangan+", "+Table_Column_Status+") " +
                "VALUES ('1', '10-05-2021', '100000', 'Sekolah', 'pemasukan');";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }

    public Boolean updatePassword(int id, String oldpassword, String newpassword){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCount= db.rawQuery("select count(*) from "+TABLE_LOGIN+" where "+Table_Column_Password+"='" + oldpassword + "'", null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();

        if(count > 0){
            ContentValues cv = new ContentValues();
            cv.put(Table_Column_Password,newpassword);
            db.update(TABLE_LOGIN, cv, Table_Column_ID + " = ? " ,new String[]{String.valueOf(id)});
            return true;
        }
        return false;
    }

}
