package com.example.sqlite;


import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import kotlin.contracts.Returns;

public class SQLiteMaDataBase extends SQLiteOpenHelper {

    public static final String BASE_NAME = "MaBase1.db";
    public static final int BASE_VERSION = 1;
    public static final String NOM_TABLE = "T_clients";
    public static final String COL0 = "idClient";
    public static final String COL1 = "NOM";
    public static final String COL2 = "PRENOM";
    public static final String COL3 = "AGE";

    public SQLiteMaDataBase(Context context) {
        super(context, BASE_NAME, null, BASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql = "CREATE TABLE " + NOM_TABLE + " ("
                + COL0 + " integer primary key autoincrement,"
                + COL1 + " text not null,"
                + COL2 + " text not null,"
                + COL3 + " integer not null );";
        Log.d("Database", "strSql: " + strSql);
        db.execSQL(strSql);
        Log.d("Database", "Création de la base de données OK " + NOM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DataBase", "oldVersion: " + oldVersion);
        Log.d("DataBase", "newVersion: " + newVersion);
        String strSql = "DROP TABLE IF EXISTS " + NOM_TABLE + ";";
        Log.d("DataBase", "requête sql ds Upgrade" + strSql);
        db.execSQL(strSql);
        Log.d("DataBase", "Méthode Upgrade Call: " + NOM_TABLE);

    }


    public void insertionCLIENTS(String NOM, String PRENOM, Integer AGE) {
        //On ne peut pas avoir  de simple cote dans une chaine de caractère
        NOM = NOM.replace("'", " ");
        //On les remplace donc par des espaces
        PRENOM = PRENOM.replace("'", " ");

        //pas ID Il est autoincrement
        String strSql = "INSERT INTO " + NOM_TABLE + "(" + COL1 + "," + COL2 + "," + COL3 + ")"
                + " values ('" + NOM + "','" + PRENOM + "'," + AGE + ");";

        Log.d("DataBase", "StrSql insert: " + strSql);
        getWritableDatabase().execSQL(strSql);
        Log.d("DataBase", "insertionCLIENTS, insertion OK");


    }

    public Cursor lireTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor monCurseur = db.rawQuery("Select * from " + NOM_TABLE, null);
        return monCurseur;
    }

    public Cursor lireTable(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorNomPrenom = db.rawQuery("Select * from T_clients WHERE NOM = '" + name + "' OR PRENOM = '" + name + "';" + NOM_TABLE, null);
        return cursorNomPrenom;
    }

    public Cursor lireTable(int age) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorNomPrenom = db.rawQuery("Select * from T_clients WHERE AGE = " + age + ";" + NOM_TABLE, null);
        return cursorNomPrenom;
    }

}
