package com.example.a029265.diretorio_filmes;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a029265.diretorio_filmes.ClassesAssistentes.Pesquisa;

import java.util.ArrayList;

public class Resultados extends ListActivity {

    //Receber de Main
    protected ArrayList<Pesquisa> pesquisaList;
    protected Intent intent;

    //Variaveis não importadas
    protected ArrayAdapter<Pesquisa> adapter;
    protected Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        activity = this;
        intent = getIntent();
        Bundle args = intent.getBundleExtra("pesquisaBundle");
        pesquisaList = (ArrayList<Pesquisa>) args.getSerializable("pesquisa");

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new ArrayAdapter<Pesquisa>(this, android.R.layout.simple_list_item_1, pesquisaList);
        setListAdapter(adapter);
    }

    void AbreActivity(Class<?> subActivity, String id) {
        Intent intent2 = new Intent(this, subActivity);
        intent2.putExtra("idFilme", id);
        startActivity(intent2);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        AbreActivity(Filme.class, pesquisaList.get(position).getId());
    }
}