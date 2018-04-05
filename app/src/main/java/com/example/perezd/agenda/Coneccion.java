package com.example.perezd.agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DarkCraid on 14/02/2018.
 */

public class Coneccion extends SQLiteOpenHelper {
    public Coneccion(Context cont, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(cont,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table contactos(id integer primary key autoincrement, nombre text,telefono text, direccion text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}