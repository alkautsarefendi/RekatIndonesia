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

@Table(name = "tabelpenugasanpulau")
public class TbPenugasanPulau extends BaseDAO {

    public TbPenugasanPulau(Context context) {
        super(context);
    }

    @Primarykey
    @Column
    String KD_PULAU;

    @Column
    public String NM_PULAU;


    public String getKD_PULAU() {
        return KD_PULAU;
    }

    public void setKD_PULAU(String KD_PULAU) {
        this.KD_PULAU = KD_PULAU;
    }

    public String getNM_PULAU() {
        return NM_PULAU;
    }

    public void setNM_PULAU(String NM_PULAU) {
        this.NM_PULAU = NM_PULAU;
    }

    @Override
    public List<TbPenugasanPulau> retrieveAll() {
        List<TbPenugasanPulau> w = new ArrayList<TbPenugasanPulau>();

        DBHelper db = new DBHelper(context);
        Cursor cursor = db.getAllData(this);
        if (cursor.moveToFirst()) {
            do {
                TbPenugasanPulau m = new TbPenugasanPulau(context);
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

    public List<TbPenugasanPulau> getAllLabels(){
        List<TbPenugasanPulau> labels = new ArrayList<TbPenugasanPulau>();

        // Select All Query
        DBHelper db = new DBHelper(context);
        SQLiteDatabase readabledatabase = db.getReadableDatabase();
        String sql = "SELECT * FROM tabelpenugasanpulau";
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

    public TbPenugasanPulau retrieveKodePenugasan(String namaPenugasan) {
        DBHelper db = new DBHelper(context);
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select KD_PULAU from tabelpenugasanpulau where NM_PULAU=?",
                new String[]{namaPenugasan}
        );
        if(cursor.getCount() > 0 ){
            if(cursor != null) cursor.moveToFirst();
            String[] columnNames = cursor.getColumnNames();
            for (String string : columnNames) {
                try {
                    System.out.println("test jon "+cursor.getString(cursor.getColumnIndex(string)));
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
