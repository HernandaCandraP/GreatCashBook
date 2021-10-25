package org.hernanda.cashbook.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    static String DATABASE_NAME="cashbook.db";
    public static final String TABLE_LOGIN="login";
    private static final int DATABASE_VERSION = 1;
    public static final String Table_Column_ID ="id";
    public static final String Table_Column_Username="username";
    public static final String Table_Column_Password="password";

    public static final String TABLE_KEUANGAN="keuangan";
    public static final String Table_Column_Simbol="simbol";
    public static final String Table_Column_Tanggal="tgl";
    public static final String Table_Column_Nominal="nominal";
    public static final String Table_Column_Keterangan="ket";
    public static final String Table_Column_Status="status";



    private static final String TABLE_MEMBER = "members";

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

        sql = "CREATE TABLE "+TABLE_KEUANGAN+" ("+Table_Column_ID+" INTEGER PRIMARY KEY,"+Table_Column_Simbol+" VARCHAR, "+Table_Column_Tanggal+" VARCHAR, "+Table_Column_Nominal+" INT, "+Table_Column_Keterangan+" VARCHAR, "+Table_Column_Status+" VARCHAR);";
        db.execSQL(sql);
        sql = "INSERT INTO "+TABLE_KEUANGAN+" ("+Table_Column_ID+","+Table_Column_Simbol+", "+Table_Column_Tanggal+", "+Table_Column_Nominal+", "+Table_Column_Keterangan+", "+Table_Column_Status+") " +
                "VALUES ('1', '[+]','10-05-2021', 100000, 'Sekolah', 'pemasukan')," +
                "('2', '[-]','10-11-2021', 9000, 'laptop', 'pengeluaran')," +
                "('3', '[+]','10-05-2021', 100000, 'Sekolah', 'pemasukan');";
        db.execSQL(sql);

    }

    public Cursor SelectAllData() {
        // TODO Auto-generated method stub

        try {
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = "SELECT "+Table_Column_ID+" As _id , * FROM " + TABLE_KEUANGAN;
            Cursor cursor = db.rawQuery(strSQL, null);

            return cursor;

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KEUANGAN);
        // Create tables again
        onCreate(db);
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

    // menambahkan fungsi keuangan
    public void insertKeuangan(String simbol,String tgl, Integer nominal, String ket, String status){
        //Get the Data Repos/itory in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(Table_Column_Simbol, simbol);
        cValues.put(Table_Column_Tanggal, tgl);
        cValues.put(Table_Column_Nominal, nominal);
        cValues.put(Table_Column_Keterangan, ket);
        cValues.put(Table_Column_Status, status);

        long newRowId = db.insert(TABLE_KEUANGAN,null, cValues);
        db.close();
    }

    public int countDataPemasukan() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + Table_Column_Nominal + ") as Total FROM " + TABLE_KEUANGAN + " Where "+Table_Column_Status+" = 'pemasukan'", null);

        if(cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return 0;
    }

    public int countDataPengeluaran() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + Table_Column_Nominal + ") as Total FROM " + TABLE_KEUANGAN + " Where "+Table_Column_Status+" = 'pengeluaran'", null);

        if(cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return 0;
    }

}
