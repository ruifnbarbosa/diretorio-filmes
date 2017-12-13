package com.example.a029265.diretorio_filmes.ClassesAssistentes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class AdaptadorBaseDados {
    private AjudaUsoBaseDados dbHelper;
    private SQLiteDatabase database;

    public AdaptadorBaseDados(Context context) {
        dbHelper = new AjudaUsoBaseDados(context.getApplicationContext());
    }

    public int obterFavoritos(List<Integer> id, List<String> idFilme, List<String> nomeFilme, List<String> pontuacao, List<Integer> estado) {
        String[] colunas = new String[50];
        colunas[0] = "_id";
        colunas[1] = "id_filme";
        colunas[2] = "nomeFilme";
        colunas[3] = "pontuacao";
        colunas[4] = "estado";

        final String QUERY = "SELECT * FROM filmes WHERE estado=?;";

        Cursor c = database.rawQuery(QUERY, new String[]{"1"});

        //Cursor c = database.query("filmes", colunas, "estado", new String[]{"1"}, null, null, null);
        if (c.moveToFirst()) {
            do {
                id.add(c.getInt(0));
                idFilme.add(c.getString(1));
                nomeFilme.add(c.getString(2));
                pontuacao.add(c.getString(3));
                estado.add(c.getInt(4));
            } while (c.moveToNext());
        }

        c.close();
        return id.size();
    }

    public int obterVerMaisTarde(List<Integer> id, List<String> idFilme, List<String> nomeFilme, List<String> pontuacao, List<Integer> estado) {
        String[] colunas = new String[50];
        colunas[0] = "_id";
        colunas[1] = "id_filme";
        colunas[2] = "nomeFilme";
        colunas[3] = "pontuacao";
        colunas[4] = "estado";

        Cursor c = database.query("filmes", colunas, "estado", new String[]{"2"}, null, null, null);
        if (c.moveToFirst()) {
            do {
                id.add(c.getInt(0));
                idFilme.add(c.getString(1));
                nomeFilme.add(c.getString(2));
                pontuacao.add(c.getString(3));
                estado.add(c.getInt(4));
            } while (c.moveToNext());
        }

        c.close();
        return id.size();
    }

    private Cursor obterEstado(String filme) {

        final String QUERY = "SELECT estado FROM filmes WHERE nomeFilme=?;";

        return database.rawQuery(QUERY, new String[]{filme});
    }

    public boolean existeFavoritos(String idFilme) {
        final String QUERY = "SELECT id_filme FROM filmes where id_filme=? AND estado=1;";
        Cursor cursor = database.rawQuery(QUERY, new String[]{idFilme});
        boolean b = cursor.getCount() >= 1;
        cursor.close();
        return b;
    }

    public boolean existeMaisTarde(String idFilme) {
        final String QUERY = "SELECT id_filme FROM filmes where id_filme=? AND estado=2;";
        Cursor cursor = database.rawQuery(QUERY, new String[]{idFilme});
        boolean b = cursor.getCount() >= 1;
        cursor.close();
        return b;
    }

    public AdaptadorBaseDados open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public long inserirBaseDados(String id_filme, String filme, String pontuacao, int estado) {
        ContentValues values = new ContentValues();
        values.put("id_filme", id_filme);
        values.put("nomeFilme", filme);
        values.put("pontuacao", pontuacao);
        values.put("estado", estado);
        return database.insert("filmes", null, values);
    }

    public void close() {
        dbHelper.close();
    }
}