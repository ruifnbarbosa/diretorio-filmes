package com.example.a029265.diretorio_filmes;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class VerMaisTarde extends Activity {

    protected TextView nomeFilme;
    protected Button tirarMaisTarde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_mais_tarde);

        nomeFilme = findViewById(R.id.nomeFilme);
        tirarMaisTarde = findViewById(R.id.tirarVerMaisTarde);
    }
}