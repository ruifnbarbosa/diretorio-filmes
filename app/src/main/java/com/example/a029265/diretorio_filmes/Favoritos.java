package com.example.a029265.diretorio_filmes;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a029265.diretorio_filmes.ClassesAssistentes.AdaptadorBaseDados;

import java.util.ArrayList;

public class Favoritos extends Activity {

    protected TextView nomeFilme, pontuacaoFilme;
    protected Spinner spinner;
    protected ArrayList<Integer> id, estadoArray;
    protected ArrayList<String> idFilmeArray, nomeFilmeArray, pontuacaoArray;
    protected AdaptadorBaseDados adaptadorBaseDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        nomeFilme = findViewById(R.id.nomeFilme);
        pontuacaoFilme = findViewById(R.id.pontuacaoFavoritos);
        spinner = findViewById(R.id.spinner);

        id = new ArrayList<Integer>();
        estadoArray = new ArrayList<Integer>();
        idFilmeArray = new ArrayList<String>();
        nomeFilmeArray = new ArrayList<String>();
        pontuacaoArray = new ArrayList<String>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adaptadorBaseDados = new AdaptadorBaseDados(this).open();
        adaptadorBaseDados.obterFavoritos(id, idFilmeArray, nomeFilmeArray, pontuacaoArray, estadoArray);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nomeFilmeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (nomeFilmeArray.size() <= 0) {
            spinner.setEnabled(false);
            Toast.makeText(this, "Não existem filmes nos favoritos", Toast.LENGTH_SHORT).show();
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nomeFilme.setText(nomeFilmeArray.get(i));
                pontuacaoFilme.setText(pontuacaoArray.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptadorBaseDados.close();
    }
}