package com.fortuna.rgpmobile.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fortuna.rgpmobile.annotation.Column;
import com.fortuna.rgpmobile.annotation.Primarykey;
import com.fortuna.rgpmobile.annotation.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tabelkelurahan")
public class TbKelurahan extends BaseDAO{

    public TbKelurahan(Context context) {
        super(context);
    }

    @Primarykey
    @Column
    String kd_kel;

    @Column
    public String nm_kel;

    @Column
    public String kd_pos;

    public String getKd_kel() {
        return kd_kel;
    }

    public void setKd_kel(String kd_kel) {
        this.kd_kel = kd_kel;
    }

    public String getNm_kel() {
        return nm_kel;
    }

    public void setNm_kel(String nm_kel) {
        this.nm_kel = nm_kel;
    }

    public String getKd_pos() {
        return kd_pos;
    }

    public void setKd_pos(String kd_pos) {
        this.kd_pos = kd_pos;
    }

    @Override
    public List<TbKelurahan> retrieveAll() {
        List<TbKelurahan> w = new ArrayList<TbKelurahan>();

        DBHelper db = new DBHelper(context);
        Cursor cursor = db.getAllData(this);
        if (cursor.moveToFirst()) {
            do {
                TbKelurahan m = new TbKelurahan(context);
                String[] columnNames = cursor.getColumnNames();
                for (String string : columnNames) {
                    try {
                        Field declaredField = this.getClass().getDeclaredField(string);
                        declaredField.set(m, cursor.getString(cursor.getColumnIndex(string)));
                    } catch (NoSuchFieldException e) {
                    } catch (IllegalArgumentException e) {
                    } catch (IllegalAccessException e) {
                    }
                }
                w.add(m);
            } while (cursor.moveToNext());
        }
        db.close();
        return w;
    }

    @Override
    public Object retrieveByID() {
        DBHelper db = new DBHelper(context);
        Cursor cursor = db.getDataByPrimaryKey(this);
        if (cursor != null) cursor.moveToFirst();
        String[] columnNames = cursor.getColumnNames();
        for (String string : columnNames) {
            try {
                Field declaredField = this.getClass().getDeclaredField(string);
                declaredField.set(this, cursor.getString(cursor.getColumnIndex(string)));
            } catch (NoSuchFieldException e) {
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            }

        }
        db.close();
        return this;
    }

    public List<TbKelurahan> getAllLabels(){
        List<TbKelurahan> labels = new ArrayList<TbKelurahan>();

        // Select All Query
        DBHelper db = new DBHelper(context);
        SQLiteDatabase readabledatabase = db.getReadableDatabase();
        String sql = "SELECT * FROM tabelkecamatan";
        try {
            Cursor cursor = readabledatabase.rawQuery(sql,null);
            if(cursor != null && cursor.getCount() > 0){
                if(cursor != null) cursor.moveToFirst();
                String[] columnNames = cursor.getColumnNames();
                for (String string : columnNames) {
                    try {
                        Field declaredField = this.getClass().getDeclaredField(string);
                        declaredField.set(this, cursor.getString(cursor.getColumnIndex(string)));
                    } catch (NoSuchFieldException e) {
                    } catch (IllegalArgumentException e) {
                    } catch (IllegalAccessException e) {
                    }

                }
                return labels;
            }
            return null;
        } finally {
            db.close();
        }
    }

    /*public TbKelurahan retrieveKodePos(String namaKel) {
        DBHelper db = new DBHelper(context);
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select kd_pos from tabelkelurahan where nm_kel=?",
                new String[]{namaKel}
        );
        if(cursor.getCount() > 0 ){
            if(cursor != null) cursor.moveToFirst();
            String[] columnNames = cursor.getColumnNames();
            for (String string : columnNames) {
                try {
                    System.out.println("test jon ="+cursor.getString(cursor.getColumnIndex(string)));
                    Field declaredField = this.getClass().getDeclaredField(string);
                    declaredField.set(this, cursor.getString(cursor.getColumnIndex(string)));
                } catch (NoSuchFieldException e) {
                } catch (IllegalArgumentException e) {
                } catch (IllegalAccessException e) {
                }

            }
            return this;
        }
        readableDatabase.close();
        return null;
    }*/

    public TbKelurahan retrieveKodeKelurahan(String namaKel) {
        DBHelper db = new DBHelper(context);
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select kd_kel from tabelkelurahan where nm_kel=?",
                new String[]{namaKel}
        );
        if(cursor.getCount() > 0 ){
            if(cursor != null) cursor.moveToFirst();
            String[] columnNames = cursor.getColumnNames();
            for (String string : columnNames) {
                try {
                    System.out.println("test kelurahan ="+cursor.getString(cursor.getColumnIndex(string)));
                    Field declaredField = this.getClass().getDeclaredField(string);
                    declaredField.set(this, cursor.getString(cursor.getColumnIndex(string)));
                } catch (NoSuchFieldException e) {
                } catch (IllegalArgumentException e) {
                } catch (IllegalAccessException e) {
                }

            }
            return this;
        }
        readableDatabase.close();
        return null;
    }
}
