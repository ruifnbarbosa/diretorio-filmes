package com.example.a029265.diretorio_filmes;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a029265.diretorio_filmes.ClassesAssistentes.AdaptadorBaseDados;

import java.util.ArrayList;

public class VerMaisTarde extends Activity {

    protected TextView nomeFilme;
    protected EditText pontuacao;
    protected Button tirarMaisTarde;
    protected Spinner spinner;

    protected int index;

    protected ArrayList<Integer> id, estadoArray;
    protected ArrayList<String> idFilmeArray, nomeFilmeArray, pontuacaoArray;

    protected AdaptadorBaseDados adaptadorBaseDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_mais_tarde);

        nomeFilme = findViewById(R.id.nomeFilme);
        tirarMaisTarde = findViewById(R.id.tirarVerMaisTarde);
        spinner = findViewById(R.id.spinner);
        pontuacao = findViewById(R.id.pontuacaoVerMaisTarde);

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
        adaptadorBaseDados.obterVerMaisTarde(id, idFilmeArray, nomeFilmeArray, pontuacaoArray, estadoArray);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nomeFilmeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (nomeFilmeArray.size() <= 0) {
            spinner.setEnabled(false);
            Toast.makeText(this, "Não existem filmes guardados para ver mais tarde", Toast.LENGTH_SHORT).show();
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                nomeFilme.setText(nomeFilmeArray.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tirarMaisTarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pontuacao.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(VerMaisTarde.this, "Defina uma pontuação", Toast.LENGTH_SHORT).show();
                } else {
                    adaptadorBaseDados.updateDados(idFilmeArray.get(index).toString(), "1", pontuacao.getText().toString());
                    finish();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptadorBaseDados.close();
    }
}