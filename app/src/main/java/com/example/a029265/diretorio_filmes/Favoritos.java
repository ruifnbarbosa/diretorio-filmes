package com.example.a029265.diretorio_filmes;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Favoritos extends Activity {

    protected TextView nomeFilme, pontuacaoFilme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        nomeFilme = findViewById(R.id.nomeFilme);
        pontuacaoFilme = findViewById(R.id.pontuacaoFavoritos);
    }
}
