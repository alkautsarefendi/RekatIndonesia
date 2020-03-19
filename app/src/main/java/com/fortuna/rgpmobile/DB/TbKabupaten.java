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

@Table(name = "tabelkabupaten")
public class TbKabupaten extends BaseDAO{

    public TbKabupaten(Context context) {
        super(context);
    }

    @Primarykey
    @Column
    String kd_kotakab;

    @Column
    public String nm_kotakab;

    public String getKd_kotakab() {
        return kd_kotakab;
    }

    public void setKd_kotakab(String kd_kotakab) {
        this.kd_kotakab = kd_kotakab;
    }

    public String getNm_kotakab() {
        return nm_kotakab;
    }

    public void setNm_kotakab(String nm_kotakab) {
        this.nm_kotakab = nm_kotakab;
    }

    @Override
    public List<TbKabupaten> retrieveAll() {
        List<TbKabupaten> w = new ArrayList<TbKabupaten>();

        DBHelper db = new DBHelper(context);
        Cursor cursor = db.getAllData(this);
        if (cursor.moveToFirst()) {
            do {
                TbKabupaten m = new TbKabupaten(context);
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

    public List<TbKabupaten> getAllLabels(){
        List<TbKabupaten> labels = new ArrayList<TbKabupaten>();

        // Select All Query
        DBHelper db = new DBHelper(context);
        SQLiteDatabase readabledatabase = db.getReadableDatabase();
        String sql = "SELECT * FROM tabelkabupaten";
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

    public TbKabupaten retrieveKodeKabupaten(String namaKabupaten) {
        DBHelper db = new DBHelper(context);
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select kd_kotakab from tabelkabupaten where nm_kotakab=?",
                new String[]{namaKabupaten}
        );
        if(cursor.getCount() > 0 ){
            if(cursor != null) cursor.moveToFirst();
            String[] columnNames = cursor.getColumnNames();
            for (String string : columnNames) {
                try {
                    System.out.println("test kabupaten = "+cursor.getString(cursor.getColumnIndex(string)));
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
