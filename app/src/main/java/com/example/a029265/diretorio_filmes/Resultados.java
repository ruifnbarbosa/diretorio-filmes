package com.example.a029265.diretorio_filmes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.a029265.diretorio_filmes.ClassesAssistentes.Pesquisa;

import java.util.ArrayList;

public class Resultados extends Activity {

    protected ArrayList<Pesquisa> pesquisaList;
    protected Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        intent = getIntent();
        Bundle args = intent.getBundleExtra("pesquisaBundle");
        pesquisaList = (ArrayList<Pesquisa>) args.getSerializable("pesquisa");

        Toast.makeText(this, "" + pesquisaList.get(0).getNome(), Toast.LENGTH_SHORT).show();
    }
}
