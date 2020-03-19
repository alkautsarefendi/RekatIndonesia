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

@Table(name = "tabelprovinsi")
public class TbProvinsi extends BaseDAO{

    public TbProvinsi(Context context) {
        super(context);
    }

    @Primarykey
    @Column
    String KD_PROP;

    @Column
    public String NM_PROP;

    public String getKD_PROP() {
        return KD_PROP;
    }

    public void setKD_PROP(String KD_PROP) {
        this.KD_PROP = KD_PROP;
    }

    public String getNM_PROP() {
        return NM_PROP;
    }

    public void setNM_PROP(String NM_PROP) {
        this.NM_PROP = NM_PROP;
    }

    @Override
    public List<TbProvinsi> retrieveAll() {
        List<TbProvinsi> w = new ArrayList<TbProvinsi>();

        DBHelper db = new DBHelper(context);
        Cursor cursor = db.getAllData(this);
        if (cursor.moveToFirst()) {
            do {
                TbProvinsi m = new TbProvinsi(context);
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

    public List<TbProvinsi> getAllLabels(){
        List<TbProvinsi> labels = new ArrayList<TbProvinsi>();

        // Select All Query
        DBHelper db = new DBHelper(context);
        SQLiteDatabase readabledatabase = db.getReadableDatabase();
        String sql = "SELECT * FROM tabelprovinsi";
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

    public TbProvinsi retrieveKodeProvinsi(String namaProvinsi) {
        DBHelper db = new DBHelper(context);
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select KD_PROP from tabelprovinsi where NM_PROP=?",
                new String[]{namaProvinsi}
        );
        if(cursor.getCount() > 0 ){
            if(cursor != null) cursor.moveToFirst();
            String[] columnNames = cursor.getColumnNames();
            for (String string : columnNames) {
                try {
                    System.out.println("test propinsi = "+cursor.getString(cursor.getColumnIndex(string)));
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
