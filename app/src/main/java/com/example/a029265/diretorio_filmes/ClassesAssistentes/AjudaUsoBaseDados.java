package com.example.a029265.diretorio_filmes.ClassesAssistentes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AjudaUsoBaseDados extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "filmes.db";
    private static final int VERSION = 1;

    public AjudaUsoBaseDados(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String s = "CREATE TABLE filmes(_id INTEGER primary key autoincrement, id_filme varchar(40), nomeFilme varchar(80), pontuacao varchar(10), estado integer);";
        sqLiteDatabase.execSQL(s);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}