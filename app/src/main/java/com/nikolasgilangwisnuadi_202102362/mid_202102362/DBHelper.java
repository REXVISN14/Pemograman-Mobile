package com.nikolasgilangwisnuadi_202102362.mid_202102362;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "BiodataMhs_202102288.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "biodata";
    public static final String COLUMN_NIM = "nim";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_JENIS_KELAMIN = "jeniskelamin";
    public static final String COLUMN_ALAMAT = "alamat";
    public static final String COLUMN_EMAIL = "email";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_NIM + " TEXT PRIMARY KEY, "
                + COLUMN_NAMA + " TEXT, "
                + COLUMN_JENIS_KELAMIN + " TEXT, "
                + COLUMN_ALAMAT + " TEXT, "
                + COLUMN_EMAIL + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String nim, String nama, String jenisKelamin, String alamat, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NIM, nim);
        values.put(COLUMN_NAMA, nama);
        values.put(COLUMN_JENIS_KELAMIN, jenisKelamin);
        values.put(COLUMN_ALAMAT, alamat);
        values.put(COLUMN_EMAIL, email);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result != -1;
    }

    public boolean checkNim(String nim) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NIM},
                COLUMN_NIM + "=?", new String[]{nim}, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    public Cursor tampilData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public boolean hapusData(String nim) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_NIM + "=?", new String[]{nim});
        db.close();

        return result > 0;
    }

    public boolean editData(String nim, String nama, String jenisKelamin, String alamat, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAMA, nama);
        values.put(COLUMN_JENIS_KELAMIN, jenisKelamin);
        values.put(COLUMN_ALAMAT, alamat);
        values.put(COLUMN_EMAIL, email);

        int result = db.update(TABLE_NAME, values, COLUMN_NIM + "=?", new String[]{nim});
        db.close();

        return result > 0;
    }
}
