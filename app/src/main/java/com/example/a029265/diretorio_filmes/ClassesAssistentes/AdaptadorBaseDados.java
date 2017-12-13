package com.example.a029265.diretorio_filmes.ClassesAssistentes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

class AdaptadorBaseDados {
    private AjudaUsoBaseDados dbHelper;
    private SQLiteDatabase database;

    public AdaptadorBaseDados(Context context) {
        dbHelper = new AjudaUsoBaseDados(context.getApplicationContext());
    }

    private Cursor obterFavoritos() {
        String[] colunas = new String[4];
        colunas[0] = "_id";
        colunas[1] = "nome";
        colunas[2] = "pontuacao";
        colunas[3] = "estado";

        return database.query("filmes", colunas, "estado", new String[]{"1"}, null, null, null);
    }

    private Cursor obterVerMaisTarde() {
        String[] colunas = new String[4];
        colunas[0] = "_id";
        colunas[1] = "nome";
        colunas[2] = "estado";

        return database.query("filmes", colunas, "estado", new String[]{"2"}, null, null, null);
    }

    private Cursor obterEstado(String filme) {

        final String QUERY = "SELECT estado FROM filmes WHERE nome=?";

        return database.rawQuery(QUERY, new String[]{filme});
    }

    public boolean existe(String filme) {
        final String QUERY = "SELECT nome FROM filmes where nome=?";
        Cursor cursor = database.rawQuery(QUERY, new String[]{filme});
        boolean b = cursor.getCount() >= 1;
        cursor.close();
        return b;
    }

    public AdaptadorBaseDados open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }
}